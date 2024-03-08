package kr.co.togetherbegin.presentation.dto;

import kr.co.togetherbegin.domain.goal.Goal;

import java.util.Date;

public class GoalRequestDto {
    private String title;
    private String description;
    private String category;
    private String deadline;
    private String startedAt;

    public GoalRequestDto(String title, String description, String category, String  deadline, String startedAt) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
        this.startedAt = startedAt;
    }

    public static Goal toEntity(GoalRequestDto goalRequestDto) {
        return Goal.builder()
                .title(goalRequestDto.getTitle())
                .description(goalRequestDto.getDescription())
                .category(goalRequestDto.getCategory())
                .deadline(goalRequestDto.getDeadline())
                .startedAt(goalRequestDto.getStartedAt())
                .build();
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDeadline() {
        return deadline;
    }

    public String  getStartedAt() {
        return startedAt;
    }

}
