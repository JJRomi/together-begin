package kr.co.togetherbegin.domain.user;

import java.util.Optional;

public interface CustomUserRepository {
    Optional<User> findByUsername(String userName);

}
