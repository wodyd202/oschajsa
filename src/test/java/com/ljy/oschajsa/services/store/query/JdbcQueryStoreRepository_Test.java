package com.ljy.oschajsa.services.store.query;

import com.ljy.oschajsa.core.application.AddressHelper;
import com.ljy.oschajsa.services.store.StoreAPITest;
import com.ljy.oschajsa.services.store.StoreFixture;
import com.ljy.oschajsa.services.store.domain.StoreTest;
import com.ljy.oschajsa.services.store.domain.model.StoreModel;
import com.ljy.oschajsa.services.store.domain.value.BusinessNumber;
import com.ljy.oschajsa.services.store.domain.value.OwnerId;
import com.ljy.oschajsa.services.store.query.application.QueryStoreRepository;
import com.ljy.oschajsa.services.store.query.application.StoreSearchDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    @DisplayName("주변 업체 리스트 조회시 좌표값 및 차이를 모두 입력해야함")
    void findAll_invalidCoordinate(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .differenceCoordinate(3)
                .page(0)
                .build();

        // when
        assertThrows(Exception.class,()->{
            queryStoreRepository.findAll(storeSearchDTO);
        });
    }

    @Test
    @DisplayName("3km 이내의 업체 리스트 가져오기 테스트")
    void findAll_1(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .differenceCoordinate(3)
                .lettitude(1.1)
                .longtitude(1.1)
                .page(0)
                .build();

        // when
        List<StoreModel> storeModels = queryStoreRepository.findAll(storeSearchDTO);

        // then
        assertNotNull(storeModels);
    }

    @Test
    @DisplayName("3km 이내의 업체 개수 가져오기 테스트")
    void countAll_1(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .differenceCoordinate(3)
                .lettitude(1.1)
                .longtitude(1.1)
                .build();

        // when
        assertDoesNotThrow(()->{
            queryStoreRepository.countAll(storeSearchDTO);
        });
    }

    @Test
    @DisplayName("시 기준으로 업체 리스트 가져오기 테스트")
    void findAll_2(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .city("시")
                .page(0)
                .build();

        // when
        List<StoreModel> storeModels = queryStoreRepository.findAll(storeSearchDTO);

        // then
        assertNotNull(storeModels);
    }

    @Test
    @DisplayName("시 기준으로 업체 개수 가져오기 테스트")
    void countAll_2(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .city("시")
                .build();

        // when
        assertDoesNotThrow(()->{
            queryStoreRepository.countAll(storeSearchDTO);
        });
    }

    @Test
    @DisplayName("도 기준으로 업체 리스트 가져오기 테스트")
    void findAll_3(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .province("도")
                .page(0)
                .build();

        // when
        List<StoreModel> storeModels = queryStoreRepository.findAll(storeSearchDTO);

        // then
        assertNotNull(storeModels);
    }

    @Test
    @DisplayName("도 기준으로 업체 개수 가져오기 테스트")
    void countAll_3(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .province("도")
                .build();

        // when
        assertDoesNotThrow(()->{
            queryStoreRepository.countAll(storeSearchDTO);
        });
    }

    @Test
    @DisplayName("동 기준으로 업체 리스트 가져오기 테스트")
    void findAll_4(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .dong("동")
                .page(0)
                .build();

        // when
        List<StoreModel> storeModels = queryStoreRepository.findAll(storeSearchDTO);

        // then
        assertNotNull(storeModels);
    }

    @Test
    @DisplayName("동 기준으로 업체 개수 가져오기 테스트")
    void countAll_4(){
        // given
        StoreSearchDTO storeSearchDTO = StoreSearchDTO.builder()
                .dong("동")
                .build();

        // when
        assertDoesNotThrow(()->{
            queryStoreRepository.countAll(storeSearchDTO);
        });
    }
}
