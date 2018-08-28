package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.goal.entity.GoalDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoalDtoTest {
    @Test
    void testGetAndSetUsername() {
        String userName = "test";
        GoalDto dto = new GoalDto();
        dto.setUsername(userName);
        assertEquals(userName, dto.getUsername());
    }

    @Test
    void testGetAndSetGoalMessage() {
        String goalMsg = "test";
        GoalDto dto = new GoalDto();
        dto.setGoalMessage(goalMsg);
        assertEquals(goalMsg, dto.getGoalMessage());
    }

    @Test
    void testGetAndSetDeadlineDate() {
        LocalDate newDt = LocalDate.now();
        GoalDto dto = new GoalDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dto.setDeadlineDate(newDt.format(formatter));
        assertEquals(newDt.format(formatter), dto.getDeadlineDate());
    }

    @Test
    void testGetAndSetRegisteredDate() {
        LocalDateTime newDt = LocalDateTime.now();
        GoalDto dto = new GoalDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
        dto.setRegisteredDate(newDt.format(formatter));
        assertEquals(newDt.format(formatter), dto.getRegisteredDate());
    }

    @Test
    void testGetAndSetDaysLeft() {
        long daysCnt = 123456L;
        GoalDto dto = new GoalDto();
        dto.setDaysLeft(daysCnt);
        assertEquals(daysCnt, dto.getDaysLeft());
    }

    @Test
    void testGetAndSetId() {
        Long l = 123456789L;
        GoalDto dto = new GoalDto();
        dto.setId(l);
        assertEquals(l, dto.getId());
    }

    @Test
    void testGetAndSetTagList() {
        List<String> newList = new ArrayList<>();
        newList.add("tag1");
        newList.add("tag2");
        newList.add("tag3");
        GoalDto dto = new GoalDto();
        dto.setTagList(newList);
        assertEquals(newList, dto.getTagList());
    }
}