package kr.co.togetherbegin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.togetherbegin.presentation.dto.GoalResponseDto;
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

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TogetherBeginApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("1-1. 목표 생성 - 성공")
    void test1_1() throws Exception {
        byte[] body = """
        {
            "title":"목표",
            "description": "설명",
            "startedAt": "2024-03-08",
            "deadline": "2024-05-18",
            "category": "목표 카테고리"
        }
        """.getBytes();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("목표"))
                .andExpect(jsonPath("$.description").value("설명"))
                .andExpect(jsonPath("$.startedAt").value("2024-03-08"))
                .andExpect(jsonPath("$.deadline").value("2024-05-18"))
                .andExpect(jsonPath("$.category").value("목표 카테고리"));
    }

    @Test
    @DisplayName("1-2. 목표 생성 - 실패(데드라인 날짜가 맞지 않음)")
    void test1_2() throws Exception {
        byte[] body = """
        {
            "title":"목표",
            "description": "설명",
            "startedAt": "2024-03-08",
            "deadline": "2024-03-08",
            "category": "목표 카테고리"
        }
        """.getBytes();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/goals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("""
            {
               "message": "데드 라인 날짜가 지났습니다."
            }
        """));
    }

    @Test
    @DisplayName("2-1. 목표 수정 - 성공")
    void test2_1() throws Exception {
        byte[] body = """
        {
            "title":"목표3",
            "description": "설명",
            "startedAt": "2024-03-08",
            "deadline": "2024-05-18",
            "category": "목표 카테고리"
        }
        """.getBytes();

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/goals/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("목표3"))
                .andExpect(jsonPath("$.description").value("설명"))
                .andExpect(jsonPath("$.startedAt").value("2024-03-08"))
                .andExpect(jsonPath("$.deadline").value("2024-05-18"))
                .andExpect(jsonPath("$.category").value("목표 카테고리"));

    }

    @Test
    @DisplayName("2-2. 목표 수정 - 실패 (목표 찾지 못함)")
    void test2_2() throws Exception {
        byte[] body = """
        {
            "title":"목표",
            "description": "설명",
            "startedAt": "2024-03-08T15:45:01Z",
            "deadline": "2024-05-18T15:45:01Z",
            "category": "목표 카테고리"
        }
        """.getBytes();

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/v1/goals/999")
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
    @DisplayName("3-1. 목표 아이디 조회 - 성공")
    void test3_1() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/goals/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("목표3"))
                .andExpect(jsonPath("$.description").value("설명"))
                .andExpect(jsonPath("$.startedAt").value("2024-03-08"))
                .andExpect(jsonPath("$.deadline").value("2024-05-18"))
                .andExpect(jsonPath("$.category").value("목표 카테고리"));

    }

    @Test
    @DisplayName("3-2. 목표 아이디 조회 - 실패 (목표 찾지 못함)")
    void test3_2() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/goals/999")
                )
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                {
                    "message": "목표를 찾지 못했습니다."
                }
                """));
    }

    @Test
    @DisplayName("4-1. 목표 카테고리로 조회 - 성공")
    void test4_1() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/goals?category=목표")
                )
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        List<GoalResponseDto> goalResponseDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<GoalResponseDto>>() {});

        assertNotNull(goalResponseDtos, "목표 리스트는 null 이 아니여야합니다.");
        assertTrue(goalResponseDtos.size() > 0, "현재 목표 리스트는 비어있지 않아야합니다.");
        assertFalse(goalResponseDtos.isEmpty(), "현재 목표 리스트가 비어있습니다.");

        GoalResponseDto goalResponseDto = goalResponseDtos.get(0);

        assertTrue(goalResponseDto.getId() instanceof Long, " 목표 아이디 타입이 잘못되었습니다.");
        assertTrue(goalResponseDto.getTitle() instanceof String, "목표 제목 타입이 잘못되었습니다.");
        assertTrue(goalResponseDto.getDescription() instanceof String, "목표 설명 타입이 잘못되었습니다.");
        assertTrue(goalResponseDto.getCategory() instanceof String, "목표 카테고리 타입이 잘못되었습니다.");
        assertTrue(goalResponseDto.getStartedAt() instanceof Date, "목표 시작일자 타입이 잘못되었습니다.");
        assertTrue(goalResponseDto.getDeadline() instanceof Date, "목표 목표일자 타입이 잘못되었습니다.");

    }

    @Test
    @DisplayName("4-2. 목표 상태로 조회 - 실패 (검색 결과 없음, 빈 배열)")
    void test4_2() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/goals?category=없음")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
						[]
						"""));
    }

    @Test
    @DisplayName("5-1. 목표 취소 - 성공")
    void test5_1() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/goals/1")
                )
                .andExpect(status().isOk());
    }
}
