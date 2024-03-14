package kr.co.togetherbegin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TogetherBeginApplicationTests {

    @Autowired
    private MockMvc mockMvc;

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
        .andExpect(content().json("""
            {
                "id": 1,
                "title": "목표",
                "description": "설명",
                "startedAt": "2024-03-08",
                "deadline": "2024-05-18",
                "category": "목표 카테고리"
            }
        """));
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
            "title":"목표",
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
                .andExpect(content().json("""
            {
                "id": 1,
                "title": "목표",
                "description": "설명",
                "startedAt": "2024-03-08",
                "deadline": "2024-05-18",
                "category": "목표 카테고리"
            }
        """));
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
                .andExpect(content().json("""
                    {
                        "id": 1,
                        "title": "목표",
                        "description": "설명",
                        "startedAt": "2024-03-08",
                        "deadline": "2024-05-18",
                        "category": "목표 카테고리"
                    }
                    """));

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
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/goals?category=목표")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                [
                    {
                        "id": 1,
                        "title": "목표",
                        "description": "설명",
                        "startedAt": "2024-03-08",
                        "deadline": "2024-05-18",
                        "category": "목표 카테고리"
                    }
                ]
                """));
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
