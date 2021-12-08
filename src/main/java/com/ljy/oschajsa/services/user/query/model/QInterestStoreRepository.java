package com.ljy.oschajsa.services.user.query.model;

import java.util.Set;

public interface QInterestStoreRepository {

    void interest(String businessNumber, String userId);

    void deInterest(String businessNumber, String userId);

    Set<String> findByUserId(String userId);
}
