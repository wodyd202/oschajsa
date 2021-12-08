package com.ljy.oschajsa.services.user.query.infrastructure;

import com.ljy.oschajsa.config.StoreDBColums;
import com.ljy.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.services.user.query.model.QStoreRepository;
import com.ljy.oschajsa.services.user.query.model.QueryBusinessHour;
import com.ljy.oschajsa.services.user.query.model.QueryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QueryStoreRepository implements QStoreRepository {

    @Autowired private JdbcTemplate jdbcTemplate;

    private final String SELECT_FROM_STORE = "select business_number, city, dong, lettitude, longtitude, province, weekday_end, weekday_start, weekend_end, weekend_start, business_name, business_tel, create_date, state from q_store\r\n";

    @Override
    public List<QueryStore> findByUserId(String userId) {
        StringBuilder sqlBuilder = new StringBuilder(SELECT_FROM_STORE);
        sqlBuilder.append("where owner_id = ?\r\n");
            List<QueryStore> query = jdbcTemplate.query(sqlBuilder.toString(), new RowMapper<QueryStore>() {
                @Override
                public QueryStore mapRow(ResultSet rs, int i) throws SQLException {
                    return QueryStore.builder()
                            .businessNumber(rs.getString(StoreDBColums.COL_BUSINESS_NUMBER))
                            .address(AddressModel.builder()
                                    .city(rs.getString(StoreDBColums.COL_CITY))
                                    .province(rs.getString(StoreDBColums.COL_PROVINCE))
                                    .dong(rs.getString(StoreDBColums.COL_DONG))
                                    .lettitude(rs.getDouble(StoreDBColums.COL_LETTITUDE))
                                    .longtitude(rs.getDouble(StoreDBColums.COL_LONGTITUDE))
                                    .build())
                            .businessHour(QueryBusinessHour.builder()
                                    .weekdayStart(rs.getInt(StoreDBColums.COL_WEEKDAY_START))
                                    .weekdayEnd(rs.getInt(StoreDBColums.COL_WEEKDAY_END))
                                    .weekendStart(rs.getInt(StoreDBColums.COL_WEEKEND_START))
                                    .weekendEnd(rs.getInt(StoreDBColums.COL_WEEKEND_END))
                                    .build())
                            .businessName(rs.getString(StoreDBColums.COL_BUSINESS_NAME))
                            .businessTel(rs.getString(StoreDBColums.COL_BUSINESS_TEL))
                            .createDate(rs.getDate(StoreDBColums.COL_CREATE_DATE))
                            .state(rs.getString(StoreDBColums.COL_STATE))
                            .build();
                }
            }, new Object[]{userId});
        return query;
    }
}
