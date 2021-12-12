package com.ljy.oschajsa.services.store.query.infrastructure;

import com.ljy.oschajsa.services.common.address.model.AddressModel;
import com.ljy.oschajsa.services.store.domain.QStore;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import com.ljy.oschajsa.services.store.query.application.QueryStoreRepository;
import com.ljy.oschajsa.services.store.query.application.model.AddressInfoDTO;
import com.ljy.oschajsa.services.store.query.application.model.DifferenceCoordinateDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ljy.oschajsa.services.store.domain.QStore.store;

@Repository
@Transactional(readOnly = true)
public class JdbcQueryStoreRepository implements QueryStoreRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private final String SELECT_FROM_STORE = "SELECT * FROM stores WHERE ";
    private final String COUNT_QUERY = "SELECT count(*) FROM stores WHERE ";
    private final String CALCULATE_DIFFERENCE_COORDINATE = "(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(lettitude))*COS(RADIANS(longtitude)- RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(lettitude)))) <= ?\r";

    @Override
    public Optional<StoreModel> findById(String businessNumber) {
        Store store = jpaQueryFactory.selectFrom(QStore.store)
                .where(QStore.store.businessNumber().number.eq(businessNumber))
                .fetchFirst();
        if (store == null) {
            return Optional.empty();
        }
        return Optional.of(store.toModel());
    }

    @Override
    public List<StoreModel> findByUserId(String userId) {
        return jpaQueryFactory.select(store)
                .from(store)
                .where(store.ownerId().id.eq(userId))
                .fetch().stream().map(Store::toModel).collect(Collectors.toList());
    }

    @Override
    public List<StoreModel> findByDifferenceCoordinate(DifferenceCoordinateDTO differenceCoordinateDTO) {
        List<Object> params = setDifferenceCoordinateParamsAfterReturn(differenceCoordinateDTO);

        StringBuilder sqlBuilder = new StringBuilder(SELECT_FROM_STORE);
        sqlBuilder.append(CALCULATE_DIFFERENCE_COORDINATE);

        return executeQuery(params, sqlBuilder, differenceCoordinateDTO.getPage());
    }

    private void appendLimitQuery(StringBuilder sqlBuilder, int page) {
        sqlBuilder.append("LIMIT " + (page * 10) + ", 10 \r");
    }

    @Override
    public long countByDifferenceCoordinate(DifferenceCoordinateDTO differenceCoordinateDTO) {
        List<Object> params = setDifferenceCoordinateParamsAfterReturn(differenceCoordinateDTO);

        StringBuilder sqlBuilder = new StringBuilder(COUNT_QUERY);
        sqlBuilder.append(CALCULATE_DIFFERENCE_COORDINATE);

        return jdbcTemplate.queryForObject(sqlBuilder.toString(), (resultSet, i) -> resultSet.getLong(1), params.toArray());
    }

    private List<Object> setDifferenceCoordinateParamsAfterReturn(DifferenceCoordinateDTO differenceCoordinateDTO) {
        List<Object> params = new ArrayList<>();
        params.add(differenceCoordinateDTO.getLettitude());
        params.add(differenceCoordinateDTO.getLongtitude());
        params.add(differenceCoordinateDTO.getLettitude());
        params.add(differenceCoordinateDTO.getDifferenceCoordinate());
        return params;
    }

    @Override
    public List<StoreModel> findByAddressInfo(AddressInfoDTO addressInfoDTO) {
        List<Object> params = setAddressInfoParamsAfterReturn(addressInfoDTO);

        StringBuilder sqlBuilder = new StringBuilder(SELECT_FROM_STORE);
        sqlBuilder.append("city = ? ");
        sqlBuilder.append("AND province = ? ");
        sqlBuilder.append("AND dong = ? ");

        return executeQuery(params, sqlBuilder, addressInfoDTO.getPage());
    }

    @Override
    public long countByAddressInfo(AddressInfoDTO addressInfoDTO) {
        List<Object> params = setAddressInfoParamsAfterReturn(addressInfoDTO);

        StringBuilder sqlBuilder = new StringBuilder(COUNT_QUERY);
        sqlBuilder.append("city = ? ");
        sqlBuilder.append("AND province = ? ");
        sqlBuilder.append("AND dong = ? ");

        return jdbcTemplate.queryForObject(sqlBuilder.toString(), (resultSet, i) -> resultSet.getLong(1), params.toArray());
    }

    private List<Object> setAddressInfoParamsAfterReturn(AddressInfoDTO addressInfoDTO) {
        List<Object> params = new ArrayList<>();
        params.add(addressInfoDTO.getCity());
        params.add(addressInfoDTO.getProvince());
        params.add(addressInfoDTO.getDong());
        return params;
    }

    private List<StoreModel> executeQuery(List<Object> params, StringBuilder sqlBuilder, int page) {
        appendLimitQuery(sqlBuilder, page);
        return jdbcTemplate.query(sqlBuilder.toString(), (resultSet, i) -> StoreModel.builder()
                .businessName(resultSet.getString("business_name"))
                .tel(resultSet.getString("tel"))
                .address(AddressModel.builder()
                        .dong(resultSet.getString("dong"))
                        .province(resultSet.getString("province"))
                        .city(resultSet.getString("city"))
                        .lettitude(resultSet.getDouble("lettitude"))
                        .longtitude(resultSet.getDouble("longtitude"))
                        .build())
                .businessHour(BusinessHourModel.builder()
                        .weekdayStart(resultSet.getInt("weekday_start"))
                        .weekdayEnd(resultSet.getInt("weekday_end"))
                        .weekendStart(resultSet.getInt("weekend_start"))
                        .weekendEnd(resultSet.getInt("weekend_end"))
                        .build())
                .state(StoreState.valueOf(resultSet.getString("state")))
                .build(), params.toArray());
    }
}

