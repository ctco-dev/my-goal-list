package lv.ctco.javaschool.goal.dto;

import lv.ctco.javaschool.goal.entity.dto.GoalFormDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GoalFormDtoTest {
    @Test
    void testGetAndSetGoal() {
        String newGoalMsg = "goal message";
        GoalFormDto dto = new GoalFormDto();
        dto.setGoalMessage(newGoalMsg);
        assertEquals(newGoalMsg, dto.getGoalMessage());
    }

    @Test
    void testGetAndSetDeadline() {
        String newDeadline = "20.05.2019";
        GoalFormDto dto = new GoalFormDto();
        dto.setDeadline(newDeadline);
        assertEquals(newDeadline, dto.getDeadline());
    }

}