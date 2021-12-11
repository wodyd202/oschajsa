package com.ljy.oschajsa.services.interest.domain;

import com.ljy.oschajsa.services.interest.domain.model.InterestModel;
import com.ljy.oschajsa.services.interest.domain.value.BusinessHour;
import com.ljy.oschajsa.services.interest.domain.value.Registrant;
import com.ljy.oschajsa.services.interest.domain.value.StoreInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 관심업체 도메인 테스트
 */
public class Interest_Test {

    @Test
    void newInterest(){
        // when
        Interest interest = Interest.builder()
                .registrant(Registrant.of("userid"))
                .storeInfo(StoreInfo.builder()
                        .businessHour(BusinessHour.builder()
                                .weekendStart(1)
                                .weekendEnd(15)
                                .weekdayStart(1)
                                .weekdayEnd(15)
                                .build())
                        .businessName("업체명")
                        .businessNumber("000-00-0000")
                        .build())
                .build();
        InterestModel interestModel = interest.toModel();

        // then
        assertEquals(interestModel.getBusinessInfo().getBusinessHour().getWeekdayStart(),1);
        assertEquals(interestModel.getBusinessInfo().getBusinessHour().getWeekdayEnd(),15);
        assertEquals(interestModel.getBusinessInfo().getBusinessHour().getWeekendStart(),1);
        assertEquals(interestModel.getBusinessInfo().getBusinessHour().getWeekendEnd(),15);
        assertEquals(interestModel.getBusinessInfo().getBusinessName(), "업체명");
        assertEquals(interestModel.getBusinessInfo().getBusinessNumber(), "000-00-0000");
    }
}
