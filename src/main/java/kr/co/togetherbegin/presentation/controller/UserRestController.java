package kr.co.togetherbegin.presentation.controller;

import kr.co.togetherbegin.application.UserService;
import kr.co.togetherbegin.presentation.dto.UserJoinRequestDto;
import kr.co.togetherbegin.presentation.dto.UserLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        userService.join(userJoinRequestDto.getUserName(), userJoinRequestDto.getPassword());
        return ResponseEntity.ok("회원가입이 성공했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        String token = userService.login(userLoginRequestDto.getUserName(), userLoginRequestDto.getPassword());
        return ResponseEntity.ok().body(token);
    }
}
