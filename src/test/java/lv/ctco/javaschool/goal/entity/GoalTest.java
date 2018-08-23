package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.Goal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;


import static org.junit.jupiter.api.Assertions.*;


public class GoalTest {


    @Test
    @DisplayName("Ids should be equal")
    void getAndSetId() {
        Long l=123456789L;
        Goal goal = new Goal();
        goal.setId(l);
        assertEquals(l,goal.getId());
    }

    @Test
    @DisplayName("Users should be equal")
    void getAndSetUser() {
        User user = new User();
        user.setId(123456789L);
        user.setPassword("12346");
        user.setEmail("qqq@qqq.qqq");
        user.setPhone("+37123456789");
        Goal goal = new Goal();
        goal.setUser(user);
        assertEquals(user, goal.getUser());
    }

    @Test
    @DisplayName("GoalMessages should be equal")
    public void getAndSetGoalMessage() {
        String newGoalMsg = "goal message";
        Goal goal = new Goal();
        goal.setGoalMessage(newGoalMsg);
        assertEquals(newGoalMsg, goal.getGoalMessage());
    }

    @Test
    @DisplayName("DeadlineDate should be equal")
    void getAndSetDeadlineDate() {
        LocalDate newDt = LocalDate.now();
        Goal goal = new Goal();
        goal.setDeadlineDate(newDt);
        assertEquals(newDt, goal.getDeadlineDate() );
    }

    @Test
    @DisplayName("RegisteredDate should be equal")
    void getAndSetRegisteredDate() {
        LocalDateTime newDt = LocalDateTime.now();
        Goal goal = new Goal();
        goal.setRegisteredDate(newDt);
        assertEquals(newDt, goal.getRegisteredDate() );
    }
}