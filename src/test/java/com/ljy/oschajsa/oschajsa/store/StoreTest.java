package com.ljy.oschajsa.oschajsa.store;

import com.ljy.oschajsa.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.oschajsa.core.object.AddressInfo;
import com.ljy.oschajsa.oschajsa.core.object.Coordinate;
import com.ljy.oschajsa.oschajsa.store.command.application.OpenStoreService;
import com.ljy.oschajsa.oschajsa.store.command.domain.*;
import com.ljy.oschajsa.oschajsa.store.command.application.StoreMapper;
import com.ljy.oschajsa.oschajsa.store.command.domain.StoreRepository;
import com.ljy.oschajsa.oschajsa.store.command.domain.StoreTagRepository;
import com.ljy.oschajsa.oschajsa.store.command.application.model.ChangeBusinessHour;
import com.ljy.oschajsa.oschajsa.store.command.application.model.ChangeCoordinate;
import com.ljy.oschajsa.oschajsa.store.command.application.model.OpenStore;
import com.ljy.oschajsa.oschajsa.store.command.domain.exception.*;
import com.ljy.oschajsa.oschajsa.store.command.domain.read.StoreModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StoreTest {

    @Test
    @DisplayName("상호명은 null값을 허용하지 않음")
    void nullBusinessName() {
        assertThrows(NullPointerException.class, () -> {
            BusinessName.of(null);
        });
    }

    @Test
    @DisplayName("상호명은 빈값을 허용하지 않음")
    void emptyBusinessName() {
        assertThrows(InvalidBusinessNameException.class, () -> {
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
    void invalidBusinessName(String businessName) {
        assertThrows(InvalidBusinessNameException.class, () -> {
            BusinessName.of(businessName);
        });
    }

    @Test
    @DisplayName("상호명 정상 입력")
    void validBusinessName() {
        BusinessName businessName = BusinessName.of("상호명");
        assertEquals(businessName, BusinessName.of("상호명"));
        assertEquals(businessName.get(), "상호명");
    }

    @Test
    @DisplayName("사업자 번호 양식은 000-00-0000 만을 허용함")
    void invalidBusinessNumber() {
        assertThrows(InvalidBusinessNumberException.class, () -> {
            BusinessNumber.of("000000000");
        });
    }

    @Test
    @DisplayName("사업자 번호는 빈값을 허용하지 않음")
    void emptyBusinessNumber() {
        assertThrows(InvalidBusinessNumberException.class, () -> {
            BusinessNumber.of("");
        });
    }

    @Test
    @DisplayName("사업자 번호 정상 입력")
    void validBusinessNumber() {
        BusinessNumber businessNumber = BusinessNumber.of("000-00-0000");
        assertEquals(businessNumber, BusinessNumber.of("000-00-0000"));
        assertEquals(businessNumber.get(), "000-00-0000");
    }

    @Test
    @DisplayName("업체 태그는 빈값을 허용하지 않음")
    void emptyTag() {
        assertThrows(InvalidTagException.class, () -> {
            Tag.of("");
        });
    }

    @Test
    @DisplayName("태그 정상 입력")
    void validTag() {
        Tag tag = Tag.of("태그1");
        assertEquals(tag, Tag.of("태그1"));
        assertEquals(tag.get(), "태그1");
    }

    @Test
    @DisplayName("업체 태그는 최대 3개까지만 입력 가능")
    void tagLimit3() {
        assertThrows(InvalidTagException.class, () -> {
            Tags.withTags(Arrays.asList(Tag.of("태그1"), Tag.of("태그2"), Tag.of("태그3"), Tag.of("태그4")));
        });
    }

    @Test
    @DisplayName("업체 태그는 하나 이상 입력해야함")
    void invalidTags() {
        assertThrows(InvalidTagException.class, () -> {
            Tags.withTags(Arrays.asList());
        });
    }

    @Test
    @DisplayName("업체 전화번호는 빈값을 허용하지 않음")
    void emptyBusinessTel() {
        assertThrows(InvalidBusinessTelException.class, () -> {
            BusinessTel.of("");
        });
    }

    @DisplayName("업체 전화번호는 00-000-0000, 000-000-0000, 000-0000-0000 형식을 지켜야함")
    @ParameterizedTest
    @ValueSource(strings = {"aa-aaa-aaaa", "aaa-aaa-aaaa", "0000-0000-0000", "0-000-0000", "00-0-0000"})
    void invalidTel(String tel) {
        assertThrows(InvalidBusinessTelException.class, () -> {
            BusinessTel.of(tel);
        });
    }

    @Test
    @DisplayName("업체 전화번호 정상 입력")
    void validTel() {
        BusinessTel businessTel = BusinessTel.of("000-000-0000");
        assertEquals(businessTel, BusinessTel.of("000-000-0000"));
        assertEquals(businessTel.get(), "000-000-0000");
    }

    @Test
    @DisplayName("업체 평일 운영시간은 모두 입력해야함")
    void emptyWeekdayHour() {
        assertThrows(InvalidBusinessHourException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(null, null, 9, 18);
        });
    }

    @Test
    @DisplayName("업체 주말 운영시간은 모두 입력해야함")
    void emptyWeekendHour(){
        assertThrows(InvalidBusinessHourException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 18, null, null);
        });
    }

    @Test
    @DisplayName("업체 평일 운영시간은 0~24 사이의 값만 입력해야함")
    void invalidTimeWeekdayHour(){
        assertThrows(InvalidBusinessHourException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 26, 9, 18);
        });
    }

    @Test
    @DisplayName("업체 주말 운영시간은 0~24 사이의 값만 입력해야함")
    void invalidTimeWeekendHour(){
        assertThrows(InvalidBusinessHourException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 18, 9, 27);
        });
    }

    @Test
    @DisplayName("업체 평일 운영 시작시간은 종료시간보다 작아야함")
    void invalidWeekdayHour(){
        assertThrows(InvalidBusinessHourException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(18, 9, 9, 18);
        });
    }

    @Test
    @DisplayName("업체 주말 운영 시작시간은 종료시간보다 작아야함")
    void invalidWeekendHour(){
        assertThrows(InvalidBusinessHourException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 18, 18, 9);
        });
    }

    @Test
    void mapFrom(){
        OpenStore registerStore = OpenStore.builder()
                .businessName("상호명")
                .businessNumber("000-00-0000")
                .businessTel("000-0000-0000")
                .tags(Arrays.asList("태그1","태그2"))
                .businessHour(ChangeBusinessHour.builder()
                        .weekdayStart(9)
                        .weekdayEnd(18)
                        .weekendStart(9)
                        .weekendEnd(18)
                        .build())
                .coordinate(ChangeCoordinate.builder()
                        .lettitude(1.0)
                        .longtitude(1.0)
                        .build())
                .build();
        AddressHelper addressHelper = mock(AddressHelper.class);
        StoreMapper mapper = new StoreMapper(addressHelper);
        Store store = mapper.mapFrom(registerStore, OwnerId.of("owner"));
        assertNotNull(store);
    }

    @Test
    void register() {
        StoreRepository storeRepository = mock(StoreRepository.class);
        StoreTagRepository storeTagRepository = mock(StoreTagRepository.class);

        when(storeTagRepository.findByName(any()))
                .thenReturn(Optional.of(mock(Tag.class)));

        Store store = StoreFixture.aStore(mock(AddressHelper.class), OwnerId.of("owner")).build();
        StoreOpenValidator storeOpenValidator = new StoreOpenValidator(storeRepository, storeTagRepository);
        store.open(storeOpenValidator);
        assertEquals(store.getState(), StoreState.OPEN);
    }

    @Nested
    class StoreOpenServiceTest {

        @Test
        void register(){
            StoreRepository storeRepository = mock(StoreRepository.class);
            StoreOpenValidator storeOpenValidator = mock(StoreOpenValidator.class);
            AddressHelper addressHelper = mock(AddressHelper.class);
            when(addressHelper.getAddressInfoFrom(Coordinate.withLattitudeLongtitude(1.0,1.0)))
                    .thenReturn(AddressInfo.withCityProvinceDong("서울특별시","무슨구","무슨동"));

            OpenStoreService storeOpenService = new OpenStoreService(storeRepository, storeOpenValidator, new StoreMapper(addressHelper), mock(ApplicationEventPublisher.class));
            OpenStore openStore = OpenStore.builder()
                    .businessName("상호명")
                    .businessNumber("000-00-0000")
                    .businessTel("000-0000-0000")
                    .tags(Arrays.asList("태그1","태그2"))
                    .businessHour(ChangeBusinessHour.builder()
                            .weekdayStart(9)
                            .weekdayEnd(18)
                            .weekendStart(9)
                            .weekendEnd(18)
                            .build())
                    .coordinate(ChangeCoordinate.builder()
                            .lettitude(1.0)
                            .longtitude(1.0)
                            .build())
                    .build();
            StoreModel storeModel = storeOpenService.open(openStore, OwnerId.of("test"));
            assertNotNull(storeModel);
        }
    }


}
