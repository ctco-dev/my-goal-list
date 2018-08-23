package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.Goal;
import lv.ctco.javaschool.goal.entity.GoalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoalApiTest {
    @Mock
    private UserStore userStore;
    @Mock
    private GoalStore goalStore;

    @InjectMocks
    private GoalApi goalApi;
    private Goal goal;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getMyGoals() {

        User user = new User();
        user.setUsername("aa");

        goal = new Goal();
        goal.setGoalMessage("goal1");
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
        goal.setRegisteredDate(LocalDateTime.parse("01.02.2018 10:41", dataTimeFormatter));
        goal.setDeadlineDate(LocalDate.parse("10.02.2018", dataFormatter));
        goal.setUser(user);

        List<Goal> goalsList = new ArrayList<>();
        goalsList.add(goal);


        Mockito.when(userStore.getCurrentUser()).thenReturn(user);
        Mockito.when(goalStore.getGoalsListFor(user)).thenReturn(goalsList);
        List<GoalDto> dtos = goalApi.getMyGoals();

        GoalDto dto = dtos.get(0);
        assertEquals(dto.getUsername(), "aa");
        assertEquals(dto.getGoalMessage(), "goal1");
        assertEquals(dto.getRegisteredDate(), "01.02.2018 10:41");
        assertEquals(dto.getDeadlineDate(), "10.02.2018");
    }
}
