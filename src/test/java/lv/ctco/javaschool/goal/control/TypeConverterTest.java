package lv.ctco.javaschool.goal.control;


import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


class TypeConverterTest {

    @Test
    @DisplayName("Test convertDateTime (LocalDateTime date): Checks that input dateTime corresponds output string")
    void testConvertDateTime() {
        LocalDateTime dt1 = LocalDateTime.of(2018, 10, 25, 10, 15, 30);
        String resultDt1 = "25.10.2018 10:15";
        String resultDt2 = "10.12.2017 11:30";
        assertThat(TypeConverter.convertDateTime(dt1), is(resultDt1));
        assertThat(TypeConverter.convertDateTime(dt1), not(resultDt2));
    }

    @Test
    @DisplayName("Test convertDate (LocalDate date): Checks that input date corresponds output string")
    void testConvertDate() {
        LocalDate dt1 = LocalDate.of(2018, 10, 25);
        String resultDt1 = "25.10.2018";
        String resultDt2 = "10.12.2017";
        assertThat(TypeConverter.convertDate(dt1), is(resultDt1));
        assertThat(TypeConverter.convertDate(dt1), not(resultDt2));
    }

    @Test
    @DisplayName("Test countDaysLeft(LocalDate deadlineDate): Checks that input date corresponds output string")
    void testCountDaysLeft() {
        LocalDate today = LocalDate.now();
        long fewDays = 4L;
        LocalDate nextFewDays = LocalDate.now().plusDays(fewDays);
        assertThat(TypeConverter.countDaysLeft(nextFewDays), is(fewDays));
        assertThat(TypeConverter.countDaysLeft(nextFewDays), not(fewDays + 1L));
    }

    @Test
    @DisplayName("Test convertGoalToGoalDto(Goal goal): Checks that goal and goalDto contains same data (List<Tag> excluded)")
    void testconvertGoalToGoalDto() {
        User user1 = new User();
        user1.setUsername("user");
        user1.setEmail("user@user.com");
        user1.setId(1L);
        user1.setPassword("12345");
        user1.setPhone("12345678");
        Goal goal = new Goal();
        goal.setUser(user1);
        goal.setGoalMessage("abc");
        goal.setRegisteredDate(LocalDateTime.now());
        goal.setDeadlineDate(LocalDate.now().plusDays(1));
        goal.setId(1L);
        goal.setTags(null);
        GoalDto dto = TypeConverter.convertGoalToGoalDto(goal);

        assertThat( dto.getUsername(), is(goal.getUser().getUsername()));
        assertThat( dto.getGoalMessage(), is(goal.getGoalMessage()));
        assertThat( dto.getDeadlineDate(), is(TypeConverter.convertDate(goal.getDeadlineDate())));
        assertThat( dto.getId(), is(goal.getId()));
        assertThat( dto.getRegisteredDate(), is(TypeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat( dto.getRegisteredDate(), is(TypeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat( dto.getRegisteredDate(), is(TypeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat(dto.getTagList()==null, is(true));
    }
}