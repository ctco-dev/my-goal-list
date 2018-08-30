package lv.ctco.javaschool.goal.entity;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GoalTest {

    @Test
    void testGetAndSetId() {
        Long newId=123456789L;
        Goal goal = new Goal();
        goal.setId(newId);
        assertThat(goal.getId(), is(newId));
    }

    @Test
    void testGetAndSetUser() {
        User user = new User();
        user.setId(123456789L);
        user.setPassword("12346");
        user.setEmail("qqq@qqq.qqq");
        user.setPhone("+37123456789");
        Goal goal = new Goal();
        goal.setUser(user);
        assertThat(goal.getUser(), is(user));
    }

    @Test
    void testGetAndSetGoalMessage() {
        String newGoalMsg = "goal message";
        Goal goal = new Goal();
        goal.setGoalMessage(newGoalMsg);
        assertThat(goal.getGoalMessage(), is(newGoalMsg));
    }

    @Test
    void testGetAndSetDeadlineDate() {
        LocalDate newDt = LocalDate.now();
        Goal goal = new Goal();
        goal.setDeadlineDate(newDt);
        assertThat(goal.getDeadlineDate(), is(newDt) );
    }

    @Test
    void testGetAndSetRegisteredDate() {
        LocalDateTime newDt = LocalDateTime.now();
        Goal goal = new Goal();
        goal.setRegisteredDate(newDt);
        assertThat(goal.getRegisteredDate(), is(newDt) );
    }

    @Test
    void testGetAndSetTagsSet() {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add( new Tag("tag1")  );
        tagSet.add( new Tag("tag2")  );
        tagSet.add( new Tag("tag3")  );
        tagSet.add( new Tag("tag1")  );
        Goal goal = new Goal();
        goal.setTags(tagSet);
        assertThat(goal.getTags(), equalTo(tagSet));
    }
}