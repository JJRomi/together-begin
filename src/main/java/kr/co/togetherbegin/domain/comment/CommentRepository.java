package kr.co.togetherbegin.domain.comment;

import java.util.List;

public interface CommentRepository {
   List<Comment> findByGoalId(Long goalId);

   Comment add(Long goalId, Comment comment);

   Comment findByCommentId(Long goalId, Long commentId);

   void delete(Comment comment);

}
