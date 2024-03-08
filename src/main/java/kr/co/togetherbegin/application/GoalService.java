package kr.co.togetherbegin.application;

import kr.co.togetherbegin.domain.goal.Goal;
import kr.co.togetherbegin.domain.goal.GoalRepository;
import kr.co.togetherbegin.presentation.dto.GoalRequestDto;
import kr.co.togetherbegin.presentation.dto.GoalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    private GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<GoalResponseDto> findByCategory(String category) {
        List<Goal> goals = goalRepository.findByCategory(category);

        List<GoalResponseDto> goalResponseDtos = goals
                .stream()
                .map(goal -> GoalResponseDto.toDto(goal))
                .toList();

        return goalResponseDtos;

    }

    public GoalResponseDto createGoal(GoalRequestDto goalRequestDto) {
        Goal goal = goalRepository.add(GoalRequestDto.toEntity(goalRequestDto));
        GoalResponseDto goalResponseDto = GoalResponseDto.toDto(goal);

        return goalResponseDto;
    }

    public GoalResponseDto findById(Long goalId) {
        Goal goal = goalRepository.findById(goalId);

        GoalResponseDto goalResponseDto = GoalResponseDto.toDto(goal);

        return goalResponseDto;
    }

    public GoalResponseDto changeGoal(Long goalId, GoalRequestDto goalRequestDto) {
        Goal goal = goalRepository.findById(goalId);
        goal.changeGoalForce(goalRequestDto);

        GoalResponseDto goalResponseDto = GoalResponseDto.toDto(goal);

        return goalResponseDto;
    }

    public void deleteGoal(Long goalId) {
        Goal goal = goalRepository.findById(goalId);
        goalRepository.delete(goal);
    }
}
