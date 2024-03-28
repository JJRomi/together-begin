package kr.co.togetherbegin.application;

import kr.co.togetherbegin.domain.exception.AppException;
import kr.co.togetherbegin.domain.exception.ConflictException;
import kr.co.togetherbegin.domain.user.CustomUserRepository;
import kr.co.togetherbegin.domain.user.User;
import kr.co.togetherbegin.domain.user.UserRepository;
import kr.co.togetherbegin.presentation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    public String join(String userName, String password) {

        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userName + "는 이미 있습니다.");
                });

        User user = User.builder()
                        .userName(userName)
                        .password(encoder.encode(password))
                        .build();

        userRepository.save(user);

        return "SUCCESS";
    }
}
