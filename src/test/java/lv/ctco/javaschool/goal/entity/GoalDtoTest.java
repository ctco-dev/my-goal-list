package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.goal.entity.GoalDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoalDtoTest {
    @Test
    @DisplayName("UserName should be equal")
    void getAndSetUsername() {
        String userName = "test";
        GoalDto dto = new GoalDto();
        dto.setUsername(userName);
        assertEquals(userName, dto.getUsername());
    }

    @Test
    @DisplayName("GoalMessage should be equal")
    void getAndSetGoalMessage() {
        String goalMsg = "test";
        GoalDto dto = new GoalDto();
        dto.setGoalMessage(goalMsg);
        assertEquals(goalMsg, dto.getGoalMessage());
    }

    @Test
    @DisplayName("DeadlineDate should be equal")
    void getAndSetDeadlineDate() {
        LocalDate newDt = LocalDate.now();
        GoalDto dto = new GoalDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dto.setDeadlineDate(newDt.format(formatter));
        assertEquals(newDt, dto.getDeadlineDate());
    }

    @Test
    @DisplayName("RegisteredDate should be equal")
    void getAndSetRegisteredDate() {
        LocalDateTime newDt = LocalDateTime.now();
        GoalDto dto = new GoalDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
        dto.setRegisteredDate(newDt.format(formatter));
        assertEquals(newDt, dto.getRegisteredDate());
    }



}