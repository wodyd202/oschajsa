package com.ljy.oschajsa.services.user.command.infrastructure;

import com.ljy.oschajsa.services.user.command.domain.InterestStoreRepository;
import com.ljy.oschajsa.services.user.command.domain.Store;
import com.ljy.oschajsa.services.user.command.domain.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class JdbcInterestStoreRepository implements InterestStoreRepository {
    @Autowired private JdbcTemplate jdbcTemplate;

    private final String DE_INTEREST_STORE_QUERY = "DELETE FROM `interest_store` WHERE `user_id` = ? AND `business_number` = ?";
    @Override
    public void deInterestStore(Store store, UserId userId) {
        log.info(DE_INTEREST_STORE_QUERY);
        log.info("params = [%s,%s]", userId.get(), store.getBusinessNumber());
        jdbcTemplate.update(DE_INTEREST_STORE_QUERY, new Object[] {userId.get(), store.getBusinessNumber()});
    }

    private final String INTEREST_STORE_QUERY = "INSERT INTO `interest_store` (`business_number`,`user_id`) VALUES (?,?)";
    @Override
    public void interestStore(Store store, UserId userId) {
        log.info(INTEREST_STORE_QUERY);
        log.info("params = [%s,%s]", store.getBusinessNumber(), userId.get());
        jdbcTemplate.update(INTEREST_STORE_QUERY, new Object[] {store.getBusinessNumber(), userId.get()});
    }

    private final String EXIST_BY_STORE_QUERY = "SELECT * FROM `interest_store` WHERE `business_number` = ? AND `user_id` = ? LIMIT 0,1";
    @Override
    public boolean existByStoreAndUserId(Store store, UserId userId) {
        log.info(EXIST_BY_STORE_QUERY);
        log.info("params = [%s,%s]", store.getBusinessNumber(), userId.get());
        try{
            jdbcTemplate.queryForObject(EXIST_BY_STORE_QUERY, new RowMapper<Boolean>() {
                @Override
                public Boolean mapRow(ResultSet resultSet, int i) throws SQLException {
                    return true;
                }
            }, new Object[]{store.getBusinessNumber(), userId.get()});
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    private final String FIND_BY_STORE_QUERY = "SELECT * FROM `interest_store` WHERE `user_id` = ?";
    @Override
    public Set<Store> findByUserId(UserId userId) {
        log.info(FIND_BY_STORE_QUERY);
        log.info("params = [%s,%s]", userId.get());
        List<Store> query = jdbcTemplate.query(FIND_BY_STORE_QUERY, new RowMapper<Store>() {
            @Override
            public Store mapRow(ResultSet rs, int i) throws SQLException {
                return Store.of(rs.getString("business_number"));
            }
        }, new Object[]{userId.get()});
        return query.stream().collect(Collectors.toSet());
    }

}
