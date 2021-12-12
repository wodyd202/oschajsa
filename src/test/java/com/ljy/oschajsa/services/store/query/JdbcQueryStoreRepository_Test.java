package com.ljy.oschajsa.services.store.query;

import com.ljy.oschajsa.services.common.address.application.AddressHelper;
import com.ljy.oschajsa.services.store.StoreAPITest;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.query.application.QueryStoreRepository;
import com.ljy.oschajsa.services.store.query.application.model.AddressInfoDTO;
import com.ljy.oschajsa.services.store.query.application.model.DifferenceCoordinateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.ljy.oschajsa.services.store.StoreFixture.aStore;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JdbcQueryStoreRepository_Test extends StoreAPITest {
    @Autowired QueryStoreRepository queryStoreRepository;
    @Autowired AddressHelper addressHelper;

    @Test
    @DisplayName("업체 정보 가져오기 테스트")
    void findById(){
        // given
        saveStore(aStore(addressHelper, OwnerId.of("findbyid")).businessNumber(BusinessNumber.of("999-88-7777")).build());

        // when
        Optional<StoreModel> storeModel = queryStoreRepository.findById("999-88-7777");

        // then
        assertTrue(storeModel.isPresent());
    }

    @Test
    @DisplayName("해당 사용자의 업체 리스트 정보 가져오기 테스트")
    void findByUserId(){
        // given
        saveStore(aStore(addressHelper, OwnerId.of("userid")).build());

        // when
        List<StoreModel> storeModels = queryStoreRepository.findByUserId("userid");

        // then
        assertEquals(storeModels.size(), 1);
    }

    @Test
    @DisplayName("3km 이내의 업체 리스트 가져오기 테스트")
    void findAll_1(){
        // given
        DifferenceCoordinateDTO storeSearchDTO = DifferenceCoordinateDTO.builder()
                .differenceCoordinate(3)
                .lettitude(1.1)
                .longtitude(1.1)
                .page(0)
                .build();

        // when
        List<StoreModel> storeModels = queryStoreRepository.findByDifferenceCoordinate(storeSearchDTO);

        // then
        assertNotNull(storeModels);
    }

    @Test
    @DisplayName("3km 이내의 업체 개수 가져오기 테스트")
    void countAll_1(){
        // given
        DifferenceCoordinateDTO storeSearchDTO = DifferenceCoordinateDTO.builder()
                .differenceCoordinate(3)
                .lettitude(1.1)
                .longtitude(1.1)
                .build();

        // when
        assertDoesNotThrow(()->{
            queryStoreRepository.countByDifferenceCoordinate(storeSearchDTO);
        });
    }

    @Test
    @DisplayName("시, 도, 동 기준으로 업체 리스트 가져오기 테스트")
    void findAll_2(){
        // given
        AddressInfoDTO storeSearchDTO = AddressInfoDTO.builder()
                .city("시")
                .province("도")
                .dong("동")
                .page(0)
                .build();

        // when
        List<StoreModel> storeModels = queryStoreRepository.findByAddressInfo(storeSearchDTO);

        // then
        assertNotNull(storeModels);
    }

    @Test
    @DisplayName("시, 도, 동 기준으로 업체 개수 가져오기 테스트")
    void countAll_2(){
        // given
        AddressInfoDTO storeSearchDTO = AddressInfoDTO.builder()
                .city("시")
                .province("도")
                .dong("동")
                .build();

        // when
        assertDoesNotThrow(()->{
            queryStoreRepository.countByAddressInfo(storeSearchDTO);
        });
    }

}
