package kr.co.togetherbegin.infrastructure;

import kr.co.togetherbegin.domain.comment.Comment;
import kr.co.togetherbegin.domain.comment.CommentRepository;
import kr.co.togetherbegin.domain.exception.EntityNotFoundException;
import kr.co.togetherbegin.domain.goal.Goal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListCommentRepository implements CommentRepository {
    private List<Comment> comments = new CopyOnWriteArrayList<>();
    private AtomicLong sequence = new AtomicLong(1L);

    @Override
    public List<Comment> findByGoalId(Long goalId) {
       return comments.stream()
               .filter(comment -> comment.sameGoalId(goalId))
               .toList();
    }

    @Override
    public Comment add(Long goalId, Comment comment) {
        comment.setId(sequence.getAndAdd(1L));
        comment.setGoalId(goalId);
        comments.add(comment);

        return comment;
    }

    @Override
    public Comment findByCommentId(Long goalId, Long commentId) {
        return comments.stream()
                .filter(comment -> comment.sameGoalId(goalId) && comment.sameCommentId(commentId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾지 못했습니다."));
    }

    @Override
    public void delete(Comment comment) {
        comments.remove(comment);
    }
}
