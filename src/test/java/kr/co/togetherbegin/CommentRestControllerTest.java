package kr.co.togetherbegin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.togetherbegin.domain.goal.Goal;
import kr.co.togetherbegin.domain.goal.GoalRepository;
import kr.co.togetherbegin.presentation.dto.CommentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CommentRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private GoalRepository goalRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        Long defaultGoalId = 1L;
        boolean goalExists = goalRepository.existsById(defaultGoalId);

        if(!goalExists) {
            Goal newGoal = new Goal();
            newGoal.setId(defaultGoalId);
            newGoal.setTitle("새 목표");
            newGoal.setDescription("새 목표 설명");
            newGoal.setStartedAt(Date.from(Instant.parse("2024-03-18T00:00:00Z")));
            newGoal.setDeadline(Date.from(Instant.parse("2024-05-18T00:00:00Z")));
            newGoal.setCategory("목표 카테고리");
            goalRepository.add(newGoal);
        }
    }
    @Test
    @DisplayName("1-1. 목표 댓글 생성 - 성공")
    void test1_1() throws Exception {

        byte[] body = """
        {
            "comment":"와! 성공하시길 바랍니다!"
        }
        """.getBytes();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/goals/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.goalId").value(1))
                .andExpect(jsonPath("$.comment").value("와! 성공하시길 바랍니다!"));
    }

    @Test
    @DisplayName("1-2. 목표 댓글 생성 - 실패 (목표를 찾지 못함)")
    void test1_2() throws Exception {
        byte[] body = """
        {
            "comment":"와! 성공하시길 바랍니다!"
        }
        """.getBytes();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/goals/999/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                {
                    "message": "목표를 찾지 못했습니다."
                }
                """));
    }

    @Test
    @DisplayName("2-1. 목표 댓글 조회 - 성공")
    void test2_1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/goals/1/comments")
        )
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        List<CommentResponseDto> commentResponseDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<CommentResponseDto>>() {});

        assertNotNull(commentResponseDtos, "댓글 리스트는 null이 아니여야합니다.");
        assertTrue(commentResponseDtos.size() > 0, "현재 댓글 리스트는 비어있지 않아야합니다.");
        assertFalse(commentResponseDtos.isEmpty(), "현재 댓글 리스트가 비어있습니다.");

        CommentResponseDto commentResponseDto = commentResponseDtos.get(0);
        assertTrue(commentResponseDto.getId() instanceof Long, "댓글의 목표 아이디 타입이 잘못되었습니다.");
        assertTrue(commentResponseDto.getGoalId() instanceof Long, "댓글 아이디 타입이 잘못되었습니다.");
        assertTrue(commentResponseDto.getComment() instanceof String, "댓글 타입이 잘못되었습니다.");
    }

    @Test
    @DisplayName("2-2. 목표 댓글 조회 - 실패 (목표를 찾지 못함)")
    void test2_2() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/goals/999/comments")
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                {
                    "message": "목표를 찾지 못했습니다."
                }
                """
        ));
    }

    @Test
    @DisplayName("3-1. 목표 댓글 수정 - 성공")
    void test3_1() throws Exception {
        byte[] body = """
        {
            "comment":"목표가 너무 멋져요!"
        }
        """.getBytes();
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/goals/1/comments/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("목표가 너무 멋져요!"));
    }

    @Test
    @DisplayName("3-2. 목표 댓글 수정 - 실패 (목표를 찾지 못함)")
    void test3_2() throws Exception {
        byte[] body = """
        {
            "comment":"목표가 너무 멋져요!"
        }
        """.getBytes();

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/goals/999/comments/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
            {
                "message": "목표를 찾지 못했습니다."
            }
        """));
    }

    @Test
    @DisplayName("3-3. 목표 댓글 수정 - 실패 (댓글을 찾지 못함)")
    void test3_3() throws Exception {
        byte[] body = """
        {
            "comment":"목표가 너무 멋져요!"
        }
        """.getBytes();

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/goals/1/comments/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
            {
                "message": "해당 댓글을 찾지 못했습니다."
            }
        """));
    }

    @Test
    @DisplayName("4-1. 목표 댓글 삭제 - 성공")
    void test4_1() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/goals/1/comments/1")
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("4-2. 목표 댓글 삭제 - 실패 (목표를 찾지 못함)")
    void test4_2() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/goals/999/comments/1")
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
            {
                "message": "목표를 찾지 못했습니다."
            }
        """));
    }

    @Test
    @DisplayName("4-3. 목표 댓글 삭제 - 실패 (댓글을 찾지 못함)")
    void test4_3() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/goals/1/comments/9999")
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
            {
                "message": "해당 댓글을 찾지 못했습니다."
            }
        """));
    }
}
