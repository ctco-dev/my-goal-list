package lv.ctco.javaschool.goal.dto;

import lv.ctco.javaschool.goal.entity.dto.GoalFormDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class GoalFormDtoTest {
    @Test
    void testGetAndSetGoal() {
        String newGoalMsg = "goal message";
        GoalFormDto dto = new GoalFormDto();
        dto.setGoalMessage(newGoalMsg);
        assertThat( dto.getGoalMessage(), is(newGoalMsg));
    }

    @Test
    void testGetAndSetDeadline() {
        String newDeadline = "20.05.2019";
        GoalFormDto dto = new GoalFormDto();
        dto.setDeadline(newDeadline);
        assertThat( dto.getDeadline(), is(newDeadline));
    }

}