package kr.co.togetherbegin.presentation.dto;

import kr.co.togetherbegin.domain.comment.Comment;

public class CommentRequestDto {
    private String comment;

    public String getComment() {
        return comment;
    }

    public CommentRequestDto() {
    }

    public CommentRequestDto(String comment) {
        this.comment = comment;
    }

    public static Comment toEntity(CommentRequestDto commentRequestDto) {
        return Comment.builder()
                .comment(commentRequestDto.getComment())
                .build();
    }


}
