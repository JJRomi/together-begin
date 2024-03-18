package kr.co.togetherbegin.presentation.dto;

import kr.co.togetherbegin.domain.comment.Comment;

public class CommentResponseDto {

    private Long id;
    private Long goalId;
    private String comment;

    public Long getId() {
        return id;
    }

    public Long getGoalId() {
        return goalId;
    }

    public String getComment() {
        return comment;
    }

    public CommentResponseDto() {
    }

    public CommentResponseDto(Long id, Long goalId, String comment) {
        this.id = id;
        this.goalId = goalId;
        this.comment = comment;
    }

    public static CommentResponseDto toDto(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto(
                comment.getId(),
                comment.getGoalId(),
                comment.getComment()
        );

        return commentResponseDto;
    }
}