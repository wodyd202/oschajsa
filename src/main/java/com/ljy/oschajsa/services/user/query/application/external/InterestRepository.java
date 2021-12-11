package com.ljy.oschajsa.services.user.query.application.external;

import java.util.List;

public interface InterestRepository {
    List<Interest> getInterests(String userId);
}
