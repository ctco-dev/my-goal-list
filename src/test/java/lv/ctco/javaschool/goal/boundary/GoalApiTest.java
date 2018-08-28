package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.Goal;
import lv.ctco.javaschool.goal.entity.GoalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GoalApiTest {
    Goal goal = new Goal();
    Goal goal2 = new Goal();
    User user1 = new User();
    User user2 = new User();
    List<Goal> goalList1 = new ArrayList<>();
    List<Goal> goalList2 = new ArrayList<>();
    List<GoalDto> goalDtoList = new ArrayList<>();
    @Mock
    private UserStore userStore;
    @Mock
    private GoalStore goalStore;
    @InjectMocks
    private GoalApi goalApi;

    @BeforeEach
    void init() {
        user1.setUsername("user");
        user1.setEmail("user@user.com");
        user1.setId((long) 1);
        user1.setPassword("12345");
        user1.setPhone("12345678");

        user2.setUsername("admin");
        user2.setEmail("admin@admin.com");
        user2.setId((long) 1);
        user2.setPassword("12345");
        user2.setPhone("87654321");

        goal.setUser(user1);
        goal.setGoalMessage("abc");
        goal.setRegisteredDate(LocalDateTime.now());
        goal.setDeadlineDate(LocalDate.now().plusDays(1));
        goal.setId((long) 1);
        goal2.setUser(user2);
        goal2.setGoalMessage("cde");
    }

    @Test
    @DisplayName("Current user has No goals returns empty list of dto's")
    void getMyGoals() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalsListFor(user1))
                .thenReturn(goalList1);

        assertEquals(goalDtoList, goalApi.getMyGoals());
    }

    @Test
    @DisplayName("Current user has goals returns list of dto's")
    void getMyGoals2() {
        goalList1.add(goal);
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalsListFor(user1))
                .thenReturn(goalList1);

        assertEquals(goal.getUser().getUsername(), goalApi.getMyGoals().get(0).getUsername());
        assertEquals(goal.getGoalMessage(), goalApi.getMyGoals().get(0).getGoalMessage());
        assertEquals(goal.getId(), (Long) goalApi.getMyGoals().get(0).getId());
    }

    @Test
    @DisplayName("generateTagsList: Checks work of tag list generation from goal message")
    void testGenerationOfTagsList(){
        String testLine1 = "I will become a programmer this year!";
        String expResult1 ="programmer";
        String testLine2 = "I WILL BECOME A PROGRAMMER THIS YEAR!";
        String expResult2 ="PROGRAMMER";
        String testLine3 = "I WILL be two years old!";
        String expResult3 ="";
        String testLine4 = "I will start to learn Java!";
        String expResult4 ="learn Java";

        assertEquals(expResult1, String.join(" ", goalApi.generateTagsList(testLine1)));
        assertEquals(expResult2, String.join(" ", goalApi.generateTagsList(testLine2)));
        assertEquals(expResult3, String.join(" ", goalApi.generateTagsList(testLine3)));
        assertEquals(expResult4, String.join(" ", goalApi.generateTagsList(testLine4)));
        assertFalse(expResult2.equals( String.join(" ", goalApi.generateTagsList(testLine1))));
        assertFalse(expResult1.equals( String.join(" ", goalApi.generateTagsList(testLine2))));
    }
}
