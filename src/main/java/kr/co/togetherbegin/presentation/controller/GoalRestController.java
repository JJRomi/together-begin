package kr.co.togetherbegin.presentation.controller;

import kr.co.togetherbegin.application.GoalService;
import kr.co.togetherbegin.presentation.dto.GoalRequestDto;
import kr.co.togetherbegin.presentation.dto.GoalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getGoalsByState() {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}", method = RequestMethod.GET)
    public ResponseEntity<?> getGoalById() {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> changeGoal() {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/api/v1/goals/{goalId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> cancelGoalById() {
        return ResponseEntity.ok(null);
    }

}
