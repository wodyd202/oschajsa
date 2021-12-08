package com.ljy.oschajsa.services.store.query.infrastructure;

import com.ljy.oschajsa.config.StoreDBColums;
import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.store.domain.model.BusinessHourModel;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.StoreState;
import com.ljy.oschajsa.services.store.query.application.QueryStoreRepository;
import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcQueryStoreRepository implements QueryStoreRepository {
    @Autowired private JdbcTemplate jdbcTemplate;

    private final String SELECT_FROM_STORE = "SELECT * FROM q_store WHERE ";
    private final String CALCULATE_DIFFERENCE_COORDINATE = "(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(lettitude))*COS(RADIANS(longtitude)- RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(lettitude)))) <= ?\r";
    private final String EQUALS_CITY = StoreDBColums.COL_CITY + " = ?\r";
    private final String EQUALS_PROVINCE = StoreDBColums.COL_PROVINCE + " = ?\r";
    private final String EQUALS_DONG = StoreDBColums.COL_DONG + " = ?\r";

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
            throw new IllegalArgumentException("invalid store search values");
        }
        sqlBuilder.append("LIMIT " + (dto.getPage() * 10) + ", 10\r\n");
        List<StoreModel> list = jdbcTemplate.query(sqlBuilder.toString(), new RowMapper<StoreModel>() {
            @Override
            public StoreModel mapRow(ResultSet resultSet, int i) throws SQLException {
                return StoreModel.builder()
                        .businessName(resultSet.getString(StoreDBColums.COL_BUSINESS_NAME))
                        .tel(resultSet.getString(StoreDBColums.COL_BUSINESS_TEL))
                        .address(AddressModel.builder()
                                .dong(resultSet.getString(StoreDBColums.COL_DONG))
                                .province(resultSet.getString(StoreDBColums.COL_PROVINCE))
                                .city(resultSet.getString(StoreDBColums.COL_CITY))
                                .lettitude(resultSet.getDouble(StoreDBColums.COL_LETTITUDE))
                                .longtitude(resultSet.getDouble(StoreDBColums.COL_LONGTITUDE))
                                .build())
                        .businessHour(BusinessHourModel.builder()
                                .weekdayStart(resultSet.getInt(StoreDBColums.COL_WEEKDAY_START))
                                .weekdayEnd(resultSet.getInt(StoreDBColums.COL_WEEKDAY_END))
                                .weekendStart(resultSet.getInt(StoreDBColums.COL_WEEKEND_START))
                                .weekendEnd(resultSet.getInt(StoreDBColums.COL_WEEKEND_END))
                                .build())
                        .state(StoreState.valueOf(resultSet.getString("state")))
                        .build();
            }
        },params.toArray());
        return list;
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
