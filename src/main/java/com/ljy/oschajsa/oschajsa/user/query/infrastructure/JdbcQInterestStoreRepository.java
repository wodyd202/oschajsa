package com.ljy.oschajsa.oschajsa.user.query.infrastructure;

import com.ljy.oschajsa.oschajsa.user.query.model.QInterestStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class JdbcQInterestStoreRepository implements QInterestStoreRepository {
    @Autowired private JdbcTemplate jdbcTemplate;

    private final String INTEREST_QUERY = "INSERT INTO `q_interest_store` (`query_user_user_id`,`interest_stores`) VALUES (?,?)";
    @Override
    public void interest(String businessNumber, String userId) {
        jdbcTemplate.update(INTEREST_QUERY, new Object[] {userId, businessNumber});
    }

    private final String DE_INTEREST_QUERY = "DELETE FROM `q_interest_store` WHERE `query_user_user_id` = ? AND `interest_stores` = ?";
    @Override
    public void deInterest(String businessNumber, String userId) {
        jdbcTemplate.update(DE_INTEREST_QUERY, new Object[] {userId, businessNumber});
    }

    private final String FIND_BY_USER_QUERY = "SELECT `interest_stores` FROM `q_interest_store` WHERE `query_user_user_id` = ?";
    @Override
    public Set<String> findByUserId(String userId) {
        List<String> query = jdbcTemplate.query(FIND_BY_USER_QUERY, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return String.valueOf(resultSet.getString("interest_stores"));
            }
        }, new Object[]{userId});
        return query.stream().collect(Collectors.toSet());
    }
}
