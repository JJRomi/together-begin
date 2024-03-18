package kr.co.togetherbegin.application;

import kr.co.togetherbegin.domain.comment.Comment;
import kr.co.togetherbegin.domain.comment.CommentRepository;
import kr.co.togetherbegin.domain.goal.Goal;
import kr.co.togetherbegin.domain.goal.GoalRepository;
import kr.co.togetherbegin.presentation.dto.CommentRequestDto;
import kr.co.togetherbegin.presentation.dto.CommentResponseDto;
import kr.co.togetherbegin.presentation.dto.GoalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private GoalRepository goalRepository;
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(GoalRepository goalRepository, CommentRepository commentRepository) {
        this.goalRepository = goalRepository;
        this.commentRepository = commentRepository;
    }

    public List<CommentResponseDto> findByGoalId(Long goalId) {
        goalRepository.findById(goalId);
        List<Comment> comments = commentRepository.findByGoalId(goalId);

        List<CommentResponseDto> commentResponseDtos = comments
                .stream()
                .map(comment -> CommentResponseDto.toDto(comment))
                .toList();

        return commentResponseDtos;
    }

    public CommentResponseDto createGoalComment(Long goalId, CommentRequestDto commentRequestDto) {
        goalRepository.findById(goalId);
        Comment comment = commentRepository.add(goalId, CommentRequestDto.toEntity(commentRequestDto));
        CommentResponseDto commentResponseDto = CommentResponseDto.toDto(comment);

        return commentResponseDto;
    }

    public CommentResponseDto findByCommentId(Long goalId, Long commentId) {
        goalRepository.findById(goalId);
        commentRepository.findByCommentId(goalId, commentId);
        Comment comment = commentRepository.findByCommentId(goalId, commentId);

        CommentResponseDto commentResponseDto = CommentResponseDto.toDto(comment);

        return commentResponseDto;
    }

    public CommentResponseDto changeGoalComment(Long goalId, Long commentId, CommentRequestDto commentRequestDto) {
        goalRepository.findById(goalId);
        Comment comment = commentRepository.findByCommentId(goalId, commentId);
        comment.changeCommentForce(commentRequestDto);

        CommentResponseDto commentResponseDto = CommentResponseDto.toDto(comment);

        return commentResponseDto;
    }

    public void deleteGoalComment(Long goalId, Long commentId) {
        goalRepository.findById(goalId);
        Comment comment = commentRepository.findByCommentId(goalId, commentId);
        commentRepository.delete(comment);
    }

}
