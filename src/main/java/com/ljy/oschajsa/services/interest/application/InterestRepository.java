package com.ljy.oschajsa.services.interest.application;

import com.ljy.oschajsa.services.interest.domain.Interest;
import com.ljy.oschajsa.services.interest.domain.value.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByUserIdAndBusinessInfo_BusinessNumber(UserId userId, String businessNumber);

    List<Interest> findByUserId(UserId userId);
}
