package lv.ctco.javaschool.goal.dto;

import lv.ctco.javaschool.goal.control.DateTimeConverter;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class GoalDtoTest {
    @Test
    void testGetAndSetUsername() {
        String userName = "test";
        GoalDto dto = new GoalDto();
        dto.setUsername(userName);
        assertThat(dto.getUsername(), is(userName));
    }

    @Test
    void testGetAndSetGoalMessage() {
        String goalMsg = "test";
        GoalDto dto = new GoalDto();
        dto.setGoalMessage(goalMsg);
        assertThat(dto.getGoalMessage(), is(goalMsg));
    }

    @Test
    void testGetAndSetDeadlineDate() {
        LocalDate newDt = LocalDate.now();
        GoalDto dto = new GoalDto();
        dto.setDeadlineDate(newDt.format(DateTimeConverter.formatterDate));
        assertThat(dto.getDeadlineDate(), is(newDt.format(DateTimeConverter.formatterDate)));
    }

    @Test
    void testGetAndSetRegisteredDate() {
        LocalDateTime newDt = LocalDateTime.now();
        GoalDto dto = new GoalDto();
        dto.setRegisteredDate(newDt.format(DateTimeConverter.formatterDateTime));
        assertThat(dto.getRegisteredDate(), is(newDt.format(DateTimeConverter.formatterDateTime)));
    }

    @Test
    void testGetAndSetDaysLeft() {
        int daysCnt = 35;
        GoalDto dto = new GoalDto();
        dto.setDaysLeft(daysCnt);
        assertThat(dto.getDaysLeft(), is(daysCnt));
    }

    @Test
    void testGetAndSetId() {
        Long newId = 123456789L;
        GoalDto dto = new GoalDto();
        dto.setId(newId);
        assertThat( dto.getId(), is(newId));
    }

    @Test
    void testGetAndSetTagList() {
        List<String> newList = new ArrayList<>();
        newList.add("tag1");
        newList.add("tag2");
        newList.add("tag3");
        GoalDto dto = new GoalDto();
        dto.setTagList(newList);
        assertThat(Objects.equals( dto.getTagList(), newList), is(true));
    }
}