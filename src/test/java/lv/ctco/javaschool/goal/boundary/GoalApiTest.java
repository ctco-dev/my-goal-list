package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.auth.entity.dto.UserSearchDto;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.json.Json;
import javax.json.JsonObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GoalApiTest {
    Goal goal = new Goal();
    Goal goal2 = new Goal();
    User user1 = new User();
    User user2 = new User();
    Comment comment1 = new Comment();
    List<Goal> goalList1 = new ArrayList<>();
    List<GoalDto> goalDtoList = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();
    List<CommentDto> commentDtos = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<UserSearchDto> userDtoList = new ArrayList<>();

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
        user2.setId((long) 2);
        user2.setPassword("12345");
        user2.setPhone("87654321");

        comment1.setGoal(goal);
        comment1.setUser(user1);
        comment1.setCommentMessage("hi");
        comment1.setRegisteredDate(LocalDateTime.now());
        comment1.setId((long) 1);

        goal.setUser(user1);
        goal.setGoalMessage("abc");
        goal.setRegisteredDate(LocalDateTime.now());
        goal.setDeadlineDate(LocalDate.now().plusDays(1));
        goal.setId((long) 1);
        goal2.setUser(user2);
        goal2.setGoalMessage("cde");
    }

    @Test
    @DisplayName("Test getMyGoals(): Current user has No goals returns empty list of dto's")
    void getMyGoals() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalsListFor(user1))
                .thenReturn(goalList1);

        assertEquals(goalDtoList, goalApi.getMyGoals());
    }

    @Test
    @DisplayName("Test getMyGoals(): Current user has goals returns list of dto's")
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
    @DisplayName("Test getGoalById(): returns dto of goal by id")
    void getGoalById() {
        when(goalStore.getGoalById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(goal));

        assertEquals(1, goalApi.getGoalById((long) 1).getId());
        assertEquals(user1.getUsername(), goalApi.getGoalById((long) 1).getUsername());
        assertEquals("abc", goalApi.getGoalById((long) 1).getGoalMessage());
    }

    @Test
    @DisplayName("SearchParameters: User has correct input search parameter")
    void getSearchParameters1() {
        userList.add(user1);
        UserSearchDto dto = new UserSearchDto();
        dto.setUsername(user1.getUsername());
        dto.setPhone(user1.getPhone());
        dto.setEmail(user1.getEmail());
        userDtoList.add(dto);
        JsonObject searchDto = Json.createObjectBuilder()
                .add("usersearch", "us")
                .build();
        when(userStore.getUserByUsername("us"))
                .thenReturn(userList);
        when(userStore.convertToSearchDto(userList.get(0)))
                .thenReturn(dto);
        assertEquals(userDtoList.get(0).getUsername(), goalApi.getSearchParameters(searchDto).get(0).getUsername());
        assertEquals(userDtoList.get(0).getEmail(), goalApi.getSearchParameters(searchDto).get(0).getEmail());
        assertEquals(userDtoList.get(0).getPhone(), goalApi.getSearchParameters(searchDto).get(0).getPhone());
    }

    @Test
    @DisplayName("SearchParameters: User has no search results for parameter")
    void getSearchParameters2() {
        JsonObject searchDto = Json.createObjectBuilder()
                .add("usersearch", "")
                .build();
        when(userStore.getUserByUsername(""))
                .thenReturn(userList);
        assertTrue(goalApi.getSearchParameters(searchDto).isEmpty());
    }

    @Test
    @DisplayName("Test getGoalById(): returns dto of goal by id")
    void getGoalById2() {
        when(goalStore.getGoalById((long) 1))
                .thenReturn(java.util.Optional.empty());

        assertEquals(GoalDto.class, goalApi.getGoalById((long) 1).getClass());
        assertEquals(null, goalApi.getGoalById((long) 1).getGoalMessage());
        assertEquals(0, goalApi.getGoalById((long) 1).getId());
    }
//    @Test
//    @DisplayName("Test getGoalsByUserId(): returns list of goals by user id")
//    void getGoalsByUserId() {
//        when(userStore.findUserById((long) 1))
//                .thenReturn(user1);
//
//        assertEquals(GoalDto.class, goalApi.getGoalById((long) 1).getClass());
//        assertEquals(null, goalApi.getGoalById((long) 1).getGoalMessage());
//        assertEquals(0, goalApi.getGoalById((long) 1).getId());
//    }

    @Test
    @DisplayName("Test getCommentsForGoalById(): returns Comments dto of goal by id")
    void getCommentsForGoalById() {
        comments.add(comment1);
        when(goalStore.getGoalById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(goal));
        when(goalStore.getCommentsForGoal(goal))
                .thenReturn(comments);

        assertEquals(CommentDto.class, goalApi.getCommentsForGoalById(1).get(0).getClass());
        assertEquals("hi", goalApi.getCommentsForGoalById(1).get(0).getCommentMessage());
        assertEquals("user", goalApi.getCommentsForGoalById(1).get(0).getUsername());
    }

    @Test
    @DisplayName("generateTagsList: Checks work of tag list generation from goal message")
    void testGenerationOfTagsList() {
        String testLine1 = "I will become a programmer this year!";
        String expResult1 = "programmer";
        String testLine2 = "I WILL BECOME A PROGRAMMER THIS YEAR!";
        String expResult2 = "PROGRAMMER";
        String testLine3 = "I WILL be two years old!";
        String expResult3 = "";
        String testLine4 = "I will start to learn Java!";
        String expResult4 = "learn Java";

        assertEquals(expResult1, String.join(" ", goalApi.generateTagsList(testLine1)));
        assertEquals(expResult2, String.join(" ", goalApi.generateTagsList(testLine2)));
        assertEquals(expResult3, String.join(" ", goalApi.generateTagsList(testLine3)));
        assertEquals(expResult4, String.join(" ", goalApi.generateTagsList(testLine4)));
        assertFalse(expResult2.equals(String.join(" ", goalApi.generateTagsList(testLine1))));
        assertFalse(expResult1.equals(String.join(" ", goalApi.generateTagsList(testLine2))));
    }

    @Test
    @DisplayName("getCommentsForGoalById(): returns empty Comments dto of goal")
    void getCommentsForGoalById2() {
        when(goalStore.getGoalById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(goal));
        when(goalStore.getCommentsForGoal(goal))
                .thenReturn(comments);

        assertEquals(0, goalApi.getCommentsForGoalById(1).size());
        assertEquals(commentDtos.getClass(), goalApi.getCommentsForGoalById(1).getClass());
    }

    @Test
    @DisplayName("setCommentForGoalById(): verify if persists Comments")
    void setCommentForGoalById() {
        MessageDto msg = new MessageDto();
        msg.setMessage("hi");
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalById((long) 1))
                .thenReturn(Optional.ofNullable(goal));

        goalApi.setCommentForGoalById(1, msg);

        verify(goalStore, times(1)).addComment(any(Comment.class));
    }

    @Test
    @DisplayName("setCommentForGoalById(): verify if throws exception if optional<goal> isEmpty")
    void setCommentForGoalById2() {
        MessageDto msg = new MessageDto();
        msg.setMessage("hi");
        when(goalStore.getGoalById((long) 1))
                .thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> goalApi.setCommentForGoalById(1, msg));
    }
}
