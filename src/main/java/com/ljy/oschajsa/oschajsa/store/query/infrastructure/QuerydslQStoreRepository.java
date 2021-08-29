package com.ljy.oschajsa.oschajsa.store.query.infrastructure;

import com.ljy.oschajsa.oschajsa.core.infrastructure.QuerydslRepository;
import com.ljy.oschajsa.oschajsa.core.object.QueryAddress;
import com.ljy.oschajsa.oschajsa.store.query.application.StoreSearchDTO;
import com.ljy.oschajsa.oschajsa.store.query.model.QStoreRepository;
import com.ljy.oschajsa.oschajsa.store.query.model.QueryBusinessHour;
import com.ljy.oschajsa.oschajsa.store.query.model.QueryStore;
import com.ljy.oschajsa.oschajsa.store.query.model.StoreState;
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
public class QuerydslQStoreRepository extends QuerydslRepository<QueryStore> implements QStoreRepository {

    @Autowired private JdbcTemplate jdbcTemplate;

    private final String SELECT_FROM_STORE = "SELECT * FROM q_store WHERE ";

    // STORE DB Colums
    private final String COL_BUSINESS_NAME = "business_name";
    private final String COL_BUSINESS_TEL = "business_tel";

    private final String COL_DONG = "dong";
    private final String COL_PROVINCE = "province";
    private final String COL_CITY = "city";

    private final String COL_LETTITUDE = "lettitude";
    private final String COL_LONGTITUDE = "longtitude";

    private final String COL_WEEKDAY_START = "weekday_start";
    private final String COL_WEEKDAY_END = "weekday_end";
    private final String COL_WEEKEND_START = "weekend_start";
    private final String COL_WEEKEND_END = "weekend_end";

    private final String CALCULATE_DIFFERENCE_COORDINATE = "(6371*ACOS(COS(RADIANS(?))*COS(RADIANS(lettitude))*COS(RADIANS(longtitude)- RADIANS(?))+SIN(RADIANS(?))*SIN(RADIANS(lettitude)))) <= ?\r";
    private final String EQUALS_CITY = COL_CITY + " = ?\r";
    private final String EQUALS_PROVINCE = COL_PROVINCE + " = ?\r";
    private final String EQUALS_DONG = COL_DONG + " = ?\r";

    @Override
    public List<QueryStore> findAll(StoreSearchDTO dto) {
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
            throw new IllegalArgumentException();
        }
        List<QueryStore> list = jdbcTemplate.query(sqlBuilder.toString(), new RowMapper<QueryStore>() {
            @Override
            public QueryStore mapRow(ResultSet resultSet, int i) throws SQLException {
                return QueryStore.builder()
                        .businessName(resultSet.getString(COL_BUSINESS_NAME))
                        .businessTel(resultSet.getString(COL_BUSINESS_TEL))
                        .address(QueryAddress.builder()
                                .dong(resultSet.getString(COL_DONG))
                                .province(resultSet.getString(COL_PROVINCE))
                                .city(resultSet.getString(COL_CITY))
                                .lettitude(resultSet.getDouble(COL_LETTITUDE))
                                .longtitude(resultSet.getDouble(COL_LONGTITUDE))
                                .build())
                        .businessHour(QueryBusinessHour.builder()
                                .weekdayStart(resultSet.getInt(COL_WEEKDAY_START))
                                .weekdayEnd(resultSet.getInt(COL_WEEKDAY_END))
                                .weekendStart(resultSet.getInt(COL_WEEKEND_START))
                                .weekendEnd(resultSet.getInt(COL_WEEKEND_END))
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
