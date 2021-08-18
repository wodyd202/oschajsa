package user;

import com.ljy.oschajsa.oschajsa.user.domain.Password;
import com.ljy.oschajsa.oschajsa.user.domain.User;
import com.ljy.oschajsa.oschajsa.user.domain.UserId;
import com.ljy.oschajsa.oschajsa.user.service.UserRepository;

import static com.ljy.oschajsa.oschajsa.user.service.UserServiceHelper.findByUserId;

final public class WithdrawalService {

    private final UserRepository userRepository;

    public WithdrawalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void withdrawal(WithdrawalUser withdrawalUser, UserId userid) {
        User user = findByUserId(userRepository, userid);
        user.withdrawal(Password.of(withdrawalUser.getOriginPassword()));
    }
}
