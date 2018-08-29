package lv.ctco.javaschool.goal.entity;

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

    }

}