package kr.co.togetherbegin.domain.goal;

import kr.co.togetherbegin.domain.exception.PastDeadlineException;
import kr.co.togetherbegin.presentation.dto.GoalRequestDto;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Builder
public class Goal {
    private Long id;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  startedAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  deadline;
    private String category;
    private Date createdAt;
    private Date updatedAt;

    public Goal(Long id, String title, String description, Date  startedAt, Date  deadline, String category, Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startedAt = startedAt;
        this.deadline = deadline;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public boolean sameCategory(String category) {
        return this.category.contains(category);
    }

    public boolean sameId(Long id) {
        return this.id.equals(id);
    }

    public void changeGoalForce(GoalRequestDto goalRequestDto) {
        this.title = goalRequestDto.getTitle();
        this.description = goalRequestDto.getDescription();
        this.category = goalRequestDto.getCategory();
        this.deadline = goalRequestDto.getDeadline();
        this.startedAt = goalRequestDto.getStartedAt();
    }

    public boolean checkDeadline() {
        Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(this.deadline.before(today)) {
            throw new PastDeadlineException("데드 라인 날짜가 지났습니다.");
        }
        return true;
    }
}
