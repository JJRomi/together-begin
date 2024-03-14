package kr.co.togetherbegin.presentation.dto;

import kr.co.togetherbegin.domain.goal.Goal;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static java.time.LocalDate.*;

public class GoalRequestDto {
    private String title;
    private String description;
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startedAt;

    public GoalRequestDto(String title, String description, String category, Date  deadline, Date startedAt) {
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

    public Date getDeadline() {
        return deadline;
    }

    public Date  getStartedAt() {
        return startedAt;
    }



}
