package com.ljy.oschajsa.services.interest.application;

import com.ljy.oschajsa.services.interest.application.external.Store;
import com.ljy.oschajsa.services.interest.application.external.StoreRepository;
import com.ljy.oschajsa.services.interest.domain.Interest;
import com.ljy.oschajsa.services.interest.domain.model.InterestModel;
import com.ljy.oschajsa.services.interest.domain.value.BusinessInfo;
import com.ljy.oschajsa.services.interest.domain.value.UserId;
import lombok.AllArgsConstructor;
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

    public void interest(String businessNumber, UserId userId){
        Optional<Interest> optionalInterest = interestRepository.findByUserIdAndBusinessInfo_BusinessNumber(userId, businessNumber);
        // 이미 존재할 경우 제거
        if(optionalInterest.isPresent()){
            interestRepository.deleteById(optionalInterest.get().getSeq());
        }else{
            Store store = storeRepository.getStore(businessNumber);

            Interest interest = Interest.builder()
                    .businessInfo(new BusinessInfo(store.getBusinessNumber(), store.getBusinessName()))
                    .userId(userId)
                    .build();

            interestRepository.save(interest);
        }
    }

    public List<InterestModel> getInterestModels(UserId userId) {
        return interestRepository.findByUserId(userId).stream().map(Interest::toModel).collect(Collectors.toList());
    }
}
