package kr.co.togetherbegin.domain.goal;

import lombok.Builder;

import java.util.Date;

@Builder
public class Goal {
    private Long id;
    private String title;
    private String description;
    private String  startedAt;
    private String  deadline;
    private String category;

    private Date createdAt;
    private Date updatedAt;

    public Goal(Long id, String title, String description, String  startedAt, String  deadline, String category, Date createdAt, Date updatedAt) {
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

    public String getStartedAt() {
        return startedAt;
    }

    public String getDeadline() {
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
}