package com.ljy.oschajsa.services.interest.application;

import com.ljy.oschajsa.services.interest.domain.Interest;
import com.ljy.oschajsa.services.interest.domain.value.Registrant;

import java.util.List;
import java.util.Optional;

public interface InterestRepository {
    Optional<Interest> findByUserIdAndBusinessNumber(Registrant userId, String businessNumber);
    List<Interest> findByUserId(Registrant userId);
    void deleteById(Long seq);
    void save(Interest interest);
}
