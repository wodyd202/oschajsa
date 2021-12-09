package com.ljy.oschajsa.services.interest.application;

import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessHourEvent;
import com.ljy.oschajsa.services.store.domain.event.ChangedBusinessNameEvent;
import com.ljy.oschajsa.services.store.domain.event.ChangedLogoEvent;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class JdbcInterestRepository {
    private JdbcTemplate jdbcTemplate;

    private final String CHANGE_BUSINESS_NAME_QUERY = "update interest_store set business_name = ? where business_number = ?";
    public void changeBusinessName(ChangedBusinessNameEvent event){
        jdbcTemplate.update(CHANGE_BUSINESS_NAME_QUERY
                , event.getBusinessName()
                , event.getBusinessNumber());
    }

    private final String CHANGE_BUSINESS_HOUR_QUERY = "update interest_store set weekday_start=?, weekday_end=?, weekend_start=?, weekend_end=? where business_number = ?";
    public void changeBusinessHour(ChangedBusinessHourEvent event) {
        jdbcTemplate.update(CHANGE_BUSINESS_HOUR_QUERY
                , event.getBusinessHour().getWeekdayStart()
                , event.getBusinessHour().getWeekdayEnd()
                , event.getBusinessHour().getWeekendStart()
                , event.getBusinessHour().getWeekendEnd()
                , event.getBusinessNumber());
    }

    private final String CHANGE_BUSINESS_LOGO_QUERY = "update interest_store set logo = ? where business_number = ?";
    public void changeLogo(ChangedLogoEvent event) {
        jdbcTemplate.update(CHANGE_BUSINESS_LOGO_QUERY
                , event.getLogo()
                , event.getBusinessNumber());
    }
}
