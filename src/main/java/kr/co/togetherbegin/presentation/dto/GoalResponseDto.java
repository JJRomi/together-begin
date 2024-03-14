package kr.co.togetherbegin.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.togetherbegin.domain.goal.Goal;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class GoalResponseDto {
    private Long id;
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date deadline;

    private String category;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getCategory() {
        return category;
    }

    public GoalResponseDto(Long id, String title, String description, Date startedAt, Date deadline, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startedAt = startedAt;
        this.deadline = deadline;
        this.category = category;
    }

    public static GoalResponseDto toDto(Goal goal) {
        GoalResponseDto goalResponseDto = new GoalResponseDto(
                goal.getId(),
                goal.getTitle(),
                goal.getDescription(),
                goal.getStartedAt(),
                goal.getDeadline(),
                goal.getCategory()
        );

        return goalResponseDto;
    }
}
