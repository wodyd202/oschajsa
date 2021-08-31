package com.ljy.oschajsa.oschajsa.user.command.infrastructure;

import com.ljy.oschajsa.oschajsa.config.StoreDBColums;
import com.ljy.oschajsa.oschajsa.user.command.domain.Store;
import com.ljy.oschajsa.oschajsa.user.command.domain.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.ljy.oschajsa.oschajsa.config.StoreDBColums.COL_BUSINESS_NUMBER;

@Repository
public class JdbcStoreRepository implements StoreRepository {
    @Autowired private JdbcTemplate jdbcTemplate;

    private final String SELECT_FROM_STORE = "SELECT * FROM q_store\r\n";
    @Override
    public Optional<Store> findByBusinessNumber(String businessNumber) {
        StringBuilder sqlBuilder = new StringBuilder(SELECT_FROM_STORE);
        sqlBuilder.append("WHERE " + COL_BUSINESS_NUMBER + " = ?");
        try{
            Store store = jdbcTemplate.queryForObject(sqlBuilder.toString(), new RowMapper<Store>() {
                @Override
                public Store mapRow(ResultSet resultSet, int i) throws SQLException {
                    return Store.of(resultSet.getString(COL_BUSINESS_NUMBER));
                }
            }, new Object[]{businessNumber});
            return Optional.of(store);
        }catch (EmptyResultDataAccessException e){
            return Optional.ofNullable(null);
        }
    }
}