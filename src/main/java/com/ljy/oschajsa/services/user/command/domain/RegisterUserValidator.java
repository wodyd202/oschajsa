package com.ljy.oschajsa.services.user.command.domain;

import com.ljy.oschajsa.services.user.command.domain.exception.AlreadyExistUserException;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserValidator {
    private final UserRepository userRepository;
    public RegisterUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final String ALREADY_EXIST_USER = "already exist user";
    public void validation(UserId userId){
        if(userRepository.findByUserId(userId).isPresent()){
            throw new AlreadyExistUserException(ALREADY_EXIST_USER);
        }
    }
}
