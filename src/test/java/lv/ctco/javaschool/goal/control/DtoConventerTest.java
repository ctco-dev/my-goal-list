package lv.ctco.javaschool.goal.control;


import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

class DtoConventerTest {
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
        GoalDto dto = DtoConventer.convertGoalToGoalDto(goal);

        assertThat( dto.getUsername(), is(goal.getUser().getUsername()));
        assertThat( dto.getGoalMessage(), is(goal.getGoalMessage()));
        assertThat( dto.getDeadlineDate(), is(DateTimeConverter.convertDate(goal.getDeadlineDate())));
        assertThat( dto.getId(), is(goal.getId()));
        assertThat( dto.getRegisteredDate(), is(DateTimeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat( dto.getRegisteredDate(), is(DateTimeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat( dto.getRegisteredDate(), is(DateTimeConverter.convertDateTime(goal.getRegisteredDate())));
        assertThat(dto.getTagList(), nullValue());
    }

    @Test
    void testConvertUserToUserLoginDto() {
        User user = new User();
        user.setUsername("aa");
        user.setPhone("1234567");
        user.setEmail("a@b.com");
        UserLoginDto dto = DtoConventer.convertUserToUserLoginDto(user);
        assertThat( dto.getUsername(), is(user.getUsername()));
        assertThat( dto.getEmail(), is(user.getEmail()));
        assertThat( dto.getPhone(), is(user.getPhone()));
    }
}