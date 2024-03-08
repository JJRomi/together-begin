package kr.co.togetherbegin.presentation.dto;

import kr.co.togetherbegin.domain.goal.Goal;

import java.util.Date;
import java.util.List;

public class GoalResponseDto {
    private Long id;
    private String title;
    private String description;
    private String startedAt;

    private String deadline;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public String getDeadline() {
        return deadline;
    }

    public GoalResponseDto(Long id, String title, String description, String startedAt, String deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startedAt = startedAt;
        this.deadline = deadline;
    }

    public static GoalResponseDto toDto(Goal goal) {
        GoalResponseDto goalResponseDto = new GoalResponseDto(
                goal.getId(),
                goal.getTitle(),
                goal.getDescription(),
                goal.getStartedAt(),
                goal.getDeadline()
        );

        return goalResponseDto;
    }
}
