package user;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUserId(UserId userId);
}
