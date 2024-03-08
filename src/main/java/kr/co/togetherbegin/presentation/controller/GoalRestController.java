package kr.co.togetherbegin.presentation.controller;

import kr.co.togetherbegin.application.GoalService;
import kr.co.togetherbegin.presentation.dto.GoalRequestDto;
import kr.co.togetherbegin.presentation.dto.GoalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoalRestController {

    private GoalService goalService;

    @Autowired
    GoalRestController(GoalService goalService) {
        this.goalService = goalService;
    }

    @RequestMapping(value = "/api/v1/goals", method = RequestMethod.POST)
    public ResponseEntity<GoalResponseDto> createGoal(
            @RequestBody GoalRequestDto goalRequestDto
            ) {
        GoalResponseDto goalResponseDto = goalService.createGoal(goalRequestDto);

        return ResponseEntity.ok(goalResponseDto);
    }

    @RequestMapping(value = "/api/v1/goals", method = RequestMethod.GET)
    public ResponseEntity<List<GoalResponseDto>> getGoalsByCategory(
            @RequestParam(required = false) String category
    ) {
        List<GoalResponseDto> goalResponseDtos = goalService.findByCategory(category);

        return ResponseEntity.ok(goalResponseDtos);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}", method = RequestMethod.GET)
    public ResponseEntity<GoalResponseDto> getGoalById(
            @PathVariable Long goalId
    ) {
        GoalResponseDto goalResponseDto = goalService.findById(goalId);

        return ResponseEntity.ok(goalResponseDto);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}", method = RequestMethod.PATCH)
    public ResponseEntity<GoalResponseDto> changeGoal(
            @PathVariable Long goalId,
            @RequestBody GoalRequestDto goalRequestDto
    ) {
        GoalResponseDto goalResponseDto = goalService.changeGoal(goalId, goalRequestDto);

        return ResponseEntity.ok(goalResponseDto);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> cancelGoalById() {
        return ResponseEntity.ok(null);
    }

}
