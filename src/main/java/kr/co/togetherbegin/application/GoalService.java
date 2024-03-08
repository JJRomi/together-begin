package kr.co.togetherbegin.application;

import kr.co.togetherbegin.domain.goal.Goal;
import kr.co.togetherbegin.domain.goal.GoalRepository;
import kr.co.togetherbegin.presentation.dto.GoalRequestDto;
import kr.co.togetherbegin.presentation.dto.GoalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public GoalResponseDto createGoal(GoalRequestDto goalRequestDto) {
        Goal goal = goalRepository.add(GoalRequestDto.toEntity(goalRequestDto));
        GoalResponseDto goalResponseDto = GoalResponseDto.toDto(goal);

        return goalResponseDto;
    }
}
