package kr.co.togetherbegin.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.togetherbegin.application.UserService;
import kr.co.togetherbegin.presentation.dto.UserJoinRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequestDto(userName, password))))
                .andDo(print())
                .andExpect(status().isConflict());

    }
}