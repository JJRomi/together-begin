package kr.co.togetherbegin.infrastructure;

import kr.co.togetherbegin.domain.user.CustomUserRepository;
import kr.co.togetherbegin.domain.user.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @Override
    public Optional<User> findByUsername(String userName) {
        return Optional.empty();
    }
}
