package com.ljy.oschajsa.services.interest.application;

import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessNameEvent;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class JdbcInterestRepository {
    private JdbcTemplate jdbcTemplate;

    private final String CHANGE_BUSINESS_NAME_QUERY = "update interest_store set business_name = ? where business_number = ?";
    public void changeBusinessName(ChangedBusinessNameEvent event){
        jdbcTemplate.update(CHANGE_BUSINESS_NAME_QUERY, event.getBusinessName(), event.getBusinessNumber());
    }
}
