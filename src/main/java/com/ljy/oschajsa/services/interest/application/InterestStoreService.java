package com.ljy.oschajsa.services.interest.application;

import com.ljy.oschajsa.services.interest.application.external.StoreRepository;
import com.ljy.oschajsa.services.interest.domain.Interest;
import com.ljy.oschajsa.services.interest.domain.model.InterestModel;
import com.ljy.oschajsa.services.interest.domain.value.Registrant;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 관심 업체 등록 및 삭제 서비스
 */
@Service
@Transactional
@AllArgsConstructor
public class InterestStoreService {
    private StoreRepository storeRepository;
    private InterestRepository interestRepository;

    @CacheEvict(value = "store-interest", key = "#userId.get()")
    public void interest(String businessNumber, Registrant userId){
        Optional<Interest> optionalInterest = interestRepository.findByUserIdAndBusinessNumber(userId, businessNumber);
        // 이미 존재할 경우 제거
        if(optionalInterest.isPresent()){
            interestRepository.deleteById(optionalInterest.get().getSeq());
        }else{
            Interest interest = Interest.builder()
                    .storeInfo(storeRepository.getStore(businessNumber))
                    .registrant(userId)
                    .build();
            interestRepository.save(interest);
        }
    }

    @Cacheable(value = "store-interest", key = "#userId.get()")
    public List<InterestModel> getInterestModels(Registrant userId) {
        return interestRepository.findByUserId(userId).stream().map(Interest::toModel).collect(Collectors.toList());
    }
}
