package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.*;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


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
    @DisplayName("Test getGoalById(): returns dto of goal by id")
    void getGoalById2() {
        when(goalStore.getGoalById((long) 1))
                .thenReturn(java.util.Optional.empty());

        assertEquals(GoalDto.class, goalApi.getGoalById((long) 1).getClass());
        assertEquals(null, goalApi.getGoalById((long) 1).getGoalMessage());
        assertEquals(0, goalApi.getGoalById((long) 1).getId());
    }

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
