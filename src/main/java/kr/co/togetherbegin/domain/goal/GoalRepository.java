package kr.co.togetherbegin.domain.goal;


import java.util.List;

public interface GoalRepository {
    Goal add(Goal goal);

    List<Goal> findByCategory(String category);

    Goal findById(Long goalId);
}
