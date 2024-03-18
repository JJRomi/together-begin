package kr.co.togetherbegin.infrastructure;

import kr.co.togetherbegin.domain.exception.EntityNotFoundException;
import kr.co.togetherbegin.domain.goal.Goal;
import kr.co.togetherbegin.domain.goal.GoalRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListGoalRepository implements GoalRepository {

    private List<Goal> goals = new CopyOnWriteArrayList<>();
    private AtomicLong sequence = new AtomicLong(1L);

    @Override
    public Goal add(Goal goal) {
        goal.setId(sequence.getAndAdd(1L));
        goal.checkDeadline();
        goals.add(goal);

        return goal;
    }

    @Override
    public List<Goal> findByCategory(String category) {
        return goals.stream()
                .filter(goal -> category == null || goal.sameCategory(category))
                .toList();
    }

    @Override
    public Goal findById(Long id) {
        return goals.stream()
                .filter(goal -> goal.sameId(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("목표를 찾지 못했습니다."));
    }

    @Override
    public boolean existsById(Long id) {
        return goals.stream()
                .anyMatch(goal -> goal.sameId(id));
    }

    @Override
    public void delete(Goal goal) {
        goals.remove(goal);
    }
}
