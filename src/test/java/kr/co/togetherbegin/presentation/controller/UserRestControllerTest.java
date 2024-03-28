package kr.co.togetherbegin.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.togetherbegin.application.UserService;
import kr.co.togetherbegin.domain.exception.AppException;
import kr.co.togetherbegin.presentation.dto.UserJoinRequestDto;
import kr.co.togetherbegin.presentation.dto.UserLoginRequestDto;
import kr.co.togetherbegin.presentation.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("1-1. 회원가입 성공")
    void test1_1() throws Exception {
        String userName = "jromi158";
        String password = "rororomimi";

        mockMvc.perform(post("/api/v1/users/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequestDto(userName, password))))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("1-2. 회원가입 실패 (userName 중복) ")
    void test1_2() throws Exception {
        String userName = "jromi158";
        String password = "rororomimi";

        mockMvc.perform(post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequestDto(userName, password))))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("2-1. 로그인 성공")
    @WithMockUser
    void test2_1() throws Exception {
        String userName = "jromi158";
        String password = "rororomimi";

        when(userService.login(any(), any()))
                .thenReturn("token");

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequestDto(userName, password))))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("2-2. 로그인 실패 (회원정보 없음)")
    @WithMockUser
    void test2_2() throws Exception {
        String userName = "jromi158";
        String password = "rororomimi";

        when(userService.login(any(), any()))
                .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequestDto(userName, password))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("2-3. 로그인 실패 (패스워드 틀림)")
    @WithMockUser
    void  test2_3() throws Exception {
        String userName = "jromi158";
        String password = "rororoㅇㄴㅇmimi";

        when(userService.login(any(), any()))
                .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequestDto(userName, password))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}