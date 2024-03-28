package kr.co.togetherbegin.application;

import kr.co.togetherbegin.domain.exception.AppException;
import kr.co.togetherbegin.domain.user.User;
import kr.co.togetherbegin.domain.user.UserRepository;
import kr.co.togetherbegin.presentation.exception.ErrorCode;
import kr.co.togetherbegin.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L;


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

    public String login(String userName, String password) {
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userName + "이 없습니다."));

        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드가 틀렸습니다.");
        }

        String token = JwtUtil.createToken(userName, key, expireTimeMs);

        return token;
    }
}
