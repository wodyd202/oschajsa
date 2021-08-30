package com.ljy.oschajsa.oschajsa.user.query.infrastructure;

import com.ljy.oschajsa.oschajsa.config.StoreDBColums;
import com.ljy.oschajsa.oschajsa.core.object.AddressModel;
import com.ljy.oschajsa.oschajsa.user.query.model.QStoreRepository;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryBusinessHour;
import com.ljy.oschajsa.oschajsa.user.query.model.QueryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.ljy.oschajsa.oschajsa.config.StoreDBColums.*;

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
                            .businessNumber(rs.getString(COL_BUSINESS_NUMBER))
                            .address(AddressModel.builder()
                                    .city(rs.getString(COL_CITY))
                                    .province(rs.getString(COL_PROVINCE))
                                    .dong(rs.getString(COL_DONG))
                                    .lettitude(rs.getDouble(COL_LETTITUDE))
                                    .longtitude(rs.getDouble(COL_LONGTITUDE))
                                    .build())
                            .businessHour(QueryBusinessHour.builder()
                                    .weekdayStart(rs.getInt(COL_WEEKDAY_START))
                                    .weekdayEnd(rs.getInt(COL_WEEKDAY_END))
                                    .weekendStart(rs.getInt(COL_WEEKEND_START))
                                    .weekendEnd(rs.getInt(COL_WEEKEND_END))
                                    .build())
                            .businessName(rs.getString(COL_BUSINESS_NAME))
                            .businessTel(rs.getString(COL_BUSINESS_TEL))
                            .createDate(rs.getDate(COL_CREATE_DATE))
                            .state(rs.getString(COL_STATE))
                            .build();
                }
            }, new Object[]{userId});
        return query;
    }
}
