package com.ljy.oschajsa.oschajsa.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class StoreTest {

    @Test
    @DisplayName("상호명은 null값을 허용하지 않음")
    void nullBusinessName(){
        assertThrows(NullPointerException.class,()->{
            BusinessName.of(null);
        });
    }
    
    @Test
    @DisplayName("상호명은 빈값을 허용하지 않음")
    void emptyBusinessName(){
        assertThrows(InvalidBusinessNameException.class,()->{
            BusinessName.of("");
        });
    }

    @DisplayName("상호명은 한글, 숫자, 영어[대,소문자] 1자 이상 20자 이하만 허용")
    @ParameterizedTest
    @ValueSource(strings = {
            " 테스트",
            "테스트 ",
            "테 스트",
            "테스트!",
            "테43 ^",
            "ㅌㅅㅌ"
    })
    void invalidBusinessName(String businessName){
        assertThrows(InvalidBusinessNameException.class,()->{
           BusinessName.of(businessName);
        });
    }

    @Test
    @DisplayName("상호명 정상 입력")
    void validBusinessName(){
        BusinessName businessName = BusinessName.of("상호명");
        assertEquals(businessName, BusinessName.of("상호명"));
        assertEquals(businessName.get(), "상호명");
    }

    @Test
    @DisplayName("사업자 번호 양식은 000-00-0000 만을 허용함")
    void invalidBusinessNumber(){
        assertThrows(InvalidBusinessNumberException.class,()->{
            BusinessNumber.of("000000000");
        });
    }
    
    @Test
    @DisplayName("사업자 번호는 빈값을 허용하지 않음")
    void emptyBusinessNumber(){
        assertThrows(InvalidBusinessNumberException.class, ()->{
           BusinessNumber.of("");
        });
    }

    @Test
    @DisplayName("사업자 번호 정상 입력")
    void validBusinessNumber(){
        BusinessNumber businessNumber = BusinessNumber.of("000-00-0000");
        assertEquals(businessNumber, BusinessNumber.of("000-00-0000"));
        assertEquals(businessNumber.get(), "000-00-0000");
    }

    @Test
    @DisplayName("업체 태그는 빈값을 허용하지 않음")
    void emptyTag(){
        assertThrows(InvalidTagException.class, ()->{
           Tag.of("");
        });
    }

    @Test
    @DisplayName("태그 정상 입력")
    void validTag(){
        Tag tag = Tag.of("태그1");
        assertEquals(tag, Tag.of("태그1"));
        assertEquals(tag.get(), "태그1");
    }

    @Test
    @DisplayName("업체 태그는 최대 3개까지만 입력 가능")
    void tagLimit3(){
        assertThrows(InvalidTagException.class,()->{
           Tags.withTags(Arrays.asList(Tag.of("태그1"),Tag.of("태그2"),Tag.of("태그3"),Tag.of("태그4")));
        });
    }

    @Test
    @DisplayName("업체 태그는 하나 이상 입력해야함")
    void invalidTags(){
        assertThrows(InvalidTagException.class,()->{
            Tags.withTags(Arrays.asList());
        });
    }

    @Test
    @DisplayName("업체 전화번호는 빈값을 허용하지 않음")
    void emptyBusinessTel(){
        assertThrows(InvalidBusinessTelException.class, ()->{
            BusinessTel.of("");
        });
    }

    @DisplayName("업체 전화번호는 00-000-0000, 000-000-0000, 000-0000-0000 형식을 지켜야함")
    @ParameterizedTest
    @ValueSource(strings = {"aa-aaa-aaaa","aaa-aaa-aaaa","0000-0000-0000","0-000-0000","00-0-0000"})
    void invalidTel(String tel){
        assertThrows(InvalidBusinessTelException.class, ()->{
            BusinessTel.of(tel);
        });
    }

    @Test
    @DisplayName("업체 전화번호 정상 입력")
    void validTel(){
        BusinessTel businessTel = BusinessTel.of("000-000-0000");
        assertEquals(businessTel, BusinessTel.of("000-000-0000"));
        assertEquals(businessTel.get(), "000-000-0000");
    }

}
