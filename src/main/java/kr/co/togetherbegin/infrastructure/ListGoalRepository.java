package kr.co.togetherbegin.infrastructure;

import kr.co.togetherbegin.domain.goal.Goal;
import kr.co.togetherbegin.domain.goal.GoalRepository;
import kr.co.togetherbegin.presentation.dto.GoalRequestDto;
import org.springframework.core.annotation.Order;
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
        // TODO :: 디비에 입력 부분 추가
        goals.add(goal);

        return goal;
    }

}
