package com.ljy.oschajsa.services.user.domain;

import com.ljy.oschajsa.services.user.domain.value.User;
import com.ljy.oschajsa.services.user.domain.value.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
}
