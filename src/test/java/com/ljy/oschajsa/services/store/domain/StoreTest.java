package com.ljy.oschajsa.services.store.domain;

import com.ljy.oschajsa.stub.StubAddressHelper;
import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.store.command.application.StoreMapper;
import com.ljy.oschajsa.services.store.domain.value.*;
import com.ljy.oschajsa.services.store.command.application.model.ChangeBusinessHour;
import com.ljy.oschajsa.services.store.command.application.model.ChangeCoordinate;
import com.ljy.oschajsa.services.store.command.application.model.OpenStore;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.Optional;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 업체 도메인 테스트
 */
public class StoreTest {

    @Test
    @DisplayName("상호명은 null값을 허용하지 않음")
    void nullBusinessName() {
        // when
        assertThrows(NullPointerException.class, () -> {
            BusinessName.of(null);
        });
    }

    @Test
    @DisplayName("상호명은 빈값을 허용하지 않음")
    void emptyBusinessName() {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
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
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessName.of(businessName);
        });
    }

    @Test
    @DisplayName("상호명 정상 입력")
    void validBusinessName() {
        // when
        BusinessName businessName = BusinessName.of("상호명");

        // then
        assertEquals(businessName, BusinessName.of("상호명"));
        assertEquals(businessName.get(), "상호명");
    }

    @Test
    @DisplayName("사업자 번호 양식은 000-00-0000 만을 허용함")
    void invalidBusinessNumber() {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessNumber.of("000000000");
        });
    }

    @Test
    @DisplayName("사업자 번호는 빈값을 허용하지 않음")
    void emptyBusinessNumber() {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessNumber.of("");
        });
    }

    @Test
    @DisplayName("사업자 번호 정상 입력")
    void validBusinessNumber() {
        // when
        BusinessNumber businessNumber = BusinessNumber.of("000-00-0000");

        // then
        assertEquals(businessNumber, BusinessNumber.of("000-00-0000"));
        assertEquals(businessNumber.get(), "000-00-0000");
    }

    @Test
    @DisplayName("업체 태그는 빈값을 허용하지 않음")
    void emptyTag() {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            Tag.of("");
        });
    }

    @Test
    @DisplayName("태그 정상 입력")
    void validTag() {
        // when
        Tag tag = Tag.of("태그1");

        // then
        assertEquals(tag, Tag.of("태그1"));
        assertEquals(tag.get(), "태그1");
    }

    @Test
    @DisplayName("업체 태그는 최대 3개까지만 입력 가능")
    void tagLimit3() {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            Tags.withTags(Arrays.asList(Tag.of("태그1"), Tag.of("태그2"), Tag.of("태그3"), Tag.of("태그4")));
        });
    }

    @Test
    @DisplayName("업체 태그는 하나 이상 입력해야함")
    void invalidTags() {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            Tags.withTags(Arrays.asList());
        });
    }

    @Test
    @DisplayName("업체 전화번호는 빈값을 허용하지 않음")
    void emptyBusinessTel() {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessTel.of("");
        });
    }

    @DisplayName("업체 전화번호는 00-000-0000, 000-000-0000, 000-0000-0000 형식을 지켜야함")
    @ParameterizedTest
    @ValueSource(strings = {"aa-aaa-aaaa", "aaa-aaa-aaaa", "0000-0000-0000", "0-000-0000", "00-0-0000"})
    void invalidTel(String tel) {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessTel.of(tel);
        });
    }

    @Test
    @DisplayName("업체 전화번호 정상 입력")
    void validTel() {
        // when
        BusinessTel businessTel = BusinessTel.of("000-000-0000");

        // then
        assertEquals(businessTel, BusinessTel.of("000-000-0000"));
        assertEquals(businessTel.get(), "000-000-0000");
    }

    @Test
    @DisplayName("업체 평일 운영시간은 모두 입력해야함")
    void emptyWeekdayHour() {
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(null, null, 9, 18);
        });
    }

    @Test
    @DisplayName("업체 주말 운영시간은 모두 입력해야함")
    void emptyWeekendHour(){
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 18, null, null);
        });
    }

    @Test
    @DisplayName("업체 평일 운영시간은 0~24 사이의 값만 입력해야함")
    void invalidTimeWeekdayHour(){
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 26, 9, 18);
        });

        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 18, -1, 18);
        });
    }

    @Test
    @DisplayName("업체 주말 운영시간은 0~24 사이의 값만 입력해야함")
    void invalidTimeWeekendHour(){
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 18, 9, 27);
        });

        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 18, -1, 27);
        });
    }

    @Test
    @DisplayName("업체 평일 운영 시작시간은 종료시간보다 작아야함")
    void invalidWeekdayHour(){
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(18, 9, 9, 18);
        });
    }

    @Test
    @DisplayName("업체 주말 운영 시작시간은 종료시간보다 작아야함")
    void invalidWeekendHour(){
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            BusinessHour.weekdayStartWeekdayEndWeekendStartWeekendEnd(9, 18, 18, 9);
        });
    }

    @Test
    void newStore_emptyBusinessName(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            aStore(addressHelper, OwnerId.of("owner")).businessName(null).build();
        });
    }

    @Test
    void newStore_emptyBusinessNumber(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            aStore(addressHelper, OwnerId.of("owner")).businessNumber(null).build();
        });
    }

    @Test
    void newStore_emptyBusinessTel(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            aStore(addressHelper, OwnerId.of("owner")).businessTel(null).build();
        });
    }

    @Test
    void newStore_emptyBusinessHour(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            aStore(addressHelper, OwnerId.of("owner")).businessHour(null).build();
        });
    }

    @Test
    void newStore_emptyBusinessAddress(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            aStore(addressHelper, OwnerId.of("owner")).address(null).build();
        });
    }

    @Test
    void newStore_emptyBusinessTag(){
        // when
        assertThrows(IllegalArgumentException.class, ()->{
            aStore(addressHelper, OwnerId.of("owner")).tags(null).build();
        });
    }

    AddressHelper addressHelper = new StubAddressHelper();

    @Test
    void mapFrom(){
        // given
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

        // when
        StoreMapper mapper = new StoreMapper(addressHelper);
        Store store = mapper.mapFrom(registerStore, OwnerId.of("owner"));

        // then
        assertNotNull(store);
    }

    @Test
    void register() {
        // given
        StoreRepository storeRepository = mock(StoreRepository.class);
        StoreTagRepository storeTagRepository = mock(StoreTagRepository.class);

        when(storeTagRepository.findByName(any()))
                .thenReturn(Optional.of(mock(Tag.class)));

        // when
        Store store = aStore(addressHelper, OwnerId.of("owner")).build();
        StoreOpenValidator storeOpenValidator = new StoreOpenValidator(storeRepository, storeTagRepository);
        store.open(storeOpenValidator);
        StoreModel storeModel = store.toModel();

        // then
        assertEquals(storeModel.getState(), StoreState.OPEN);
    }

    @Test
    void changeLogo() {
        // given
        Store store = aStore(addressHelper, OwnerId.of("owner")).build();

        // when
        store.changeLogo(OwnerId.of("owner"), new MockMultipartFile("image.png","image.png","image", new byte[]{}));
        StoreModel storeModel = store.toModel();

        // then
        assertNotNull(storeModel.getLogo());
    }

    @Test
    void invalidLogo(){
        // given
        Store store = aStore(addressHelper, OwnerId.of("owner")).build();

        // when
        assertThrows(IllegalArgumentException.class,()->{
            store.changeLogo(OwnerId.of("owner"), new MockMultipartFile("image.exe","image.exe","image", new byte[]{}));
        });
    }

    @Test
    @DisplayName("자기 자신의 업체만 업체명 변경 가능")
    void changeBusinessName_NotMyStore(){
        // given
        Store store = aStore(addressHelper, OwnerId.of("owner")).build();

        assertThrows(IllegalStateException.class,()->{
            store.changeBusinessName(null, OwnerId.of("notMyStore"));
        });
    }
}
