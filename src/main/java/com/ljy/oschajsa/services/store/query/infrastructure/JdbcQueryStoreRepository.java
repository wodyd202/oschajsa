package com.ljy.oschajsa.services.store.query.infrastructure;

import com.ljy.oschajsa.config.StoreDBColums;
import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.store.domain.QStore;
import com.ljy.oschajsa.services.store.domain.Store;
import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import com.ljy.oschajsa.services.store.query.application.QueryStoreRepository;
import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ljy.oschajsa.services.store.domain.QStore.store;

@Repository
public class JdbcQueryStoreRepository implements QueryStoreRepository {
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private JPAQueryFactory jpaQueryFactory;

    private final String SELECT_FROM_STORE = "SELECT * FROM stores WHERE ";
    private final String CALCULATE_DIFFERENCE_COORDINATE = "(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(lettitude))*COS(RADIANS(longtitude)- RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(lettitude)))) <= ?\r";
    private final String EQUALS_CITY = StoreDBColums.COL_CITY + " = ?\r";
    private final String EQUALS_PROVINCE = StoreDBColums.COL_PROVINCE + " = ?\r";
    private final String EQUALS_DONG = StoreDBColums.COL_DONG + " = ?\r";


    @Override
    public Optional<StoreModel> findById(String businessNumber) {
        Store store = jpaQueryFactory.selectFrom(QStore.store)
                .where(QStore.store.businessNumber().number.eq(businessNumber))
                .fetchFirst();
        if(store == null){
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
    public List<StoreModel> findAll(StoreSearchDTO dto) {
        List<Object> params = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(SELECT_FROM_STORE);
        if(shouldDifferenceCoordinateCalculate(dto)){
            sqlBuilder.append(CALCULATE_DIFFERENCE_COORDINATE);
            params.add(dto.getLettitude());
            params.add(dto.getLongtitude());
            params.add(dto.getLettitude());
            params.add(dto.getDifferenceCoordinate());
        }else if(haveCity(dto)){
            sqlBuilder.append(EQUALS_CITY);
            params.add(dto.getCity());
        }else if(haveProvince(dto)){
            sqlBuilder.append(EQUALS_PROVINCE);
            params.add(dto.getProvince());
        }else if(haveDong(dto)){
            sqlBuilder.append(EQUALS_DONG);
            params.add(dto.getDong());
        }else {
            throw new IllegalArgumentException("invalid stores search values");
        }

        sqlBuilder.append("LIMIT " + (dto.getPage() * 10) + ", 10\r\n");
        List<StoreModel> list = jdbcTemplate.query(sqlBuilder.toString(), new RowMapper<StoreModel>() {
            @Override
            public StoreModel mapRow(ResultSet resultSet, int i) throws SQLException {
                return StoreModel.builder()
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
                        .build();
            }
        },params.toArray());
        return list;
    }
    private final String COUNT_QUERY = "SELECT count(*) FROM stores WHERE ";

    @Override
    public long countAll(StoreSearchDTO storeSearchDTO) {
        List<Object> params = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(COUNT_QUERY);
        if(shouldDifferenceCoordinateCalculate(storeSearchDTO)){
            sqlBuilder.append(CALCULATE_DIFFERENCE_COORDINATE);
            params.add(storeSearchDTO.getLettitude());
            params.add(storeSearchDTO.getLongtitude());
            params.add(storeSearchDTO.getLettitude());
            params.add(storeSearchDTO.getDifferenceCoordinate());
        }else if(haveCity(storeSearchDTO)){
            sqlBuilder.append(EQUALS_CITY);
            params.add(storeSearchDTO.getCity());
        }else if(haveProvince(storeSearchDTO)){
            sqlBuilder.append(EQUALS_PROVINCE);
            params.add(storeSearchDTO.getProvince());
        }else if(haveDong(storeSearchDTO)){
            sqlBuilder.append(EQUALS_DONG);
            params.add(storeSearchDTO.getDong());
        }else {
            throw new IllegalArgumentException("invalid stores search values");
        }

        return jdbcTemplate.queryForObject(sqlBuilder.toString(), new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong(1);
            }
        },params.toArray());
    }

    private boolean haveDong(StoreSearchDTO dto) {
        return StringUtils.hasText(dto.getDong());
    }

    private boolean haveProvince(StoreSearchDTO dto) {
        return StringUtils.hasText(dto.getProvince());
    }

    private boolean haveCity(StoreSearchDTO dto) {
        return StringUtils.hasText(dto.getCity());
    }

    private boolean shouldDifferenceCoordinateCalculate(StoreSearchDTO dto){
        return !Objects.isNull(dto.getLettitude()) && !Objects.isNull(dto.getLongtitude()) && !Objects.isNull(dto.getDifferenceCoordinate());
    }
}
