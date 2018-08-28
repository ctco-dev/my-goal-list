package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    List<User> userList = new ArrayList<>();
    List<UserLoginDto> userDtoList = new ArrayList<>();
    UserLoginDto dto = new UserLoginDto();
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
    @DisplayName("getSearchedUser(String searchedUserName): User has correct input search parameter")
    void getSearchedUser1() {
        userList.add(user1);
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername(user1.getUsername());
        dto.setPhone(user1.getPhone());
        dto.setEmail(user1.getEmail());
        userDtoList.add(dto);
        when(userStore.getUserByUsername("us"))
                .thenReturn(userList);
        when(userStore.convertToDto(userList.get(0)))
                .thenReturn(dto);
        assertEquals(userDtoList.get(0).getUsername(), goalApi.getSearchedUser("us").get(0).getUsername());
        assertEquals(userDtoList.get(0).getEmail(), goalApi.getSearchedUser("us").get(0).getEmail());
        assertEquals(userDtoList.get(0).getPhone(), goalApi.getSearchedUser("us").get(0).getPhone());
    }
    @Test
    @DisplayName("getSearchedUser(String searchedUserName): User has no search results")
    void getSearchedUser2() {
        when(userStore.getUserByUsername("us"))
                .thenReturn(userList);
        assertTrue(goalApi.getSearchedUser("us").isEmpty());
    }

}
