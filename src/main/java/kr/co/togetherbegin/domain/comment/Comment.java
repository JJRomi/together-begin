package kr.co.togetherbegin.domain.comment;

import jakarta.persistence.Column;
import kr.co.togetherbegin.domain.exception.PastDeadlineException;
import kr.co.togetherbegin.presentation.dto.CommentRequestDto;
import kr.co.togetherbegin.presentation.dto.GoalRequestDto;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Builder
public class Comment {
    private Long id;
    @Column(name = "goal_id")
    private Long goalId;
    private String comment;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public Comment(Long id, Long goalId, String comment, Date createdAt, Date updatedAt) {
        this.id = id;
        this.goalId = goalId;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public Long getId() {
        return id;
    }
    public Long getGoalId() {
        return goalId;
    }
    public String getComment() {
        return comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public boolean sameGoalId(Long goalId) {
        return this.goalId.equals(goalId);
    }

    public boolean sameCommentId(Long commentId) {
        return this.id.equals(commentId);
    }

    public void changeCommentForce(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }



}
