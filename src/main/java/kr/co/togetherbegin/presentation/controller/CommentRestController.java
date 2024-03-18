package kr.co.togetherbegin.presentation.controller;

import kr.co.togetherbegin.application.CommentService;
import kr.co.togetherbegin.presentation.dto.CommentRequestDto;
import kr.co.togetherbegin.presentation.dto.CommentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentRestController {

    private CommentService commentService;
    @Autowired
    CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}/comments", method = RequestMethod.GET)
    public ResponseEntity<List<CommentResponseDto>> getGoalCommentsByGoalId(
            @PathVariable Long goalId
    ) {
        List<CommentResponseDto> commentResponseDtos = commentService.findByGoalId(goalId);

        return ResponseEntity.ok(commentResponseDtos);
    }


    @RequestMapping(value = "/api/v1/goals/{goalId}/comments", method = RequestMethod.POST)
    public ResponseEntity<CommentResponseDto> createGoalComment(
            @PathVariable Long goalId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        CommentResponseDto commentResponseDto = commentService.createGoalComment(goalId, commentRequestDto);

        return ResponseEntity.ok(commentResponseDto);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}/comments/{commentId}", method = RequestMethod.GET)
    public ResponseEntity<CommentResponseDto> getGoalCommentByCommentId(
            @PathVariable Long goalId,
            @PathVariable Long commentId
    ) {
        CommentResponseDto commentResponseDto = commentService.findByCommentId(goalId, commentId);

        return ResponseEntity.ok(commentResponseDto);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}/comments/{commentId}", method = RequestMethod.PATCH)
    public ResponseEntity<CommentResponseDto> changeGoalComment(
            @PathVariable Long goalId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        CommentResponseDto commentResponseDto = commentService.changeGoalComment(goalId, commentId, commentRequestDto);

        return ResponseEntity.ok(commentResponseDto);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}/comments/{commentId}", method = RequestMethod.DELETE)
    public void deleteGoalCommentById(
            @PathVariable Long goalId,
            @PathVariable Long commentId
    ) {
        commentService.deleteGoalComment(goalId, commentId);
    }
}
