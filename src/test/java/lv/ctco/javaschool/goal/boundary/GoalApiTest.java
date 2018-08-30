package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import lv.ctco.javaschool.goal.entity.dto.GoalFormDto;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;

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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
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
    List<UserLoginDto> userDtoList = new ArrayList<>();
    String testLine1, expResult1, testLine4, expResult4;

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
        user1.setId(1L);
        user1.setPassword("12345");
        user1.setPhone("12345678");

        user2.setUsername("admin");
        user2.setEmail("admin@admin.com");
        user2.setId(2L);
        user2.setPassword("12345");
        user2.setPhone("87654321");

        comment1.setGoal(goal);
        comment1.setUser(user1);
        comment1.setCommentMessage("hi");
        comment1.setRegisteredDate(LocalDateTime.now());
        comment1.setId(1L);

        goal.setUser(user1);
        goal.setGoalMessage("abc");
        goal.setRegisteredDate(LocalDateTime.now());
        goal.setDeadlineDate(LocalDate.now().plusDays(1));
        goal.setId((long) 1);
        goal.setTags(null);

        goal2.setUser(user2);
        goal2.setGoalMessage("cde");

        testLine1 = "I will become a programmer this year!";
        expResult1 = "programmer";
        testLine4 = "I will start to learn Java!";
        expResult4 = "learn Java";
    }

    @Test
    @DisplayName("Test getMyGoals(): Current user has No goals returns empty list of dto's")
    void testGetMyGoals() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalsListFor(user1))
                .thenReturn(goalList1);
        assertThat( Objects.equals( goalApi.getMyGoals(), goalDtoList), is(true));
    }

    @Test
    @DisplayName("Test getMyGoals(): Current user has goals returns list of dto's")
    void testGetMyGoals2() {
        goalList1.add(goal);
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalsListFor(user1))
                .thenReturn(goalList1);
        assertThat(goalApi.getMyGoals().get(0).getUsername(), is(goal.getUser().getUsername()));
        assertThat(goalApi.getMyGoals().get(0).getGoalMessage(), is(goal.getGoalMessage()));
        assertThat(goalApi.getMyGoals().get(0).getId(), is(goal.getId()));
    }

    @Test
    @DisplayName("Test getGoalById(): returns dto of goal by id")
    void testGetGoalById() {
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.ofNullable(goal));
        assertThat( goalApi.getGoalDtoByGoalId(1L).getId(), is(1L));
        assertThat( goalApi.getGoalDtoByGoalId(1L).getUsername(), is(user1.getUsername()));
        assertThat( goalApi.getGoalDtoByGoalId(1L).getGoalMessage(), is("abc"));
    }

    @Test
    @DisplayName("Test getGoalById(): returns dto of goal by id")
    void testGetGoalById2() {
        when(goalStore.getGoalById( 1L))
                .thenReturn(java.util.Optional.empty());
        assertEquals(GoalDto.class, goalApi.getGoalById((long) 1).getClass());
        assertThat( goalApi.getGoalDtoByGoalId(1L).getGoalMessage(), nullValue());
        assertThat( goalApi.getGoalDtoByGoalId(1L).getId(), nullValue());
    }


//    @Test
//    @DisplayName("Test parseStringToTags(String value): Checks work of tag list generation from goal message")
//    void testParsingGoalStringToTags() {
//        Tag tag1 = new Tag(expResult1);
//        Set<Tag> tagset1 = new HashSet<>();
//        tagset1.add(tag1);
//        String[] arr = expResult4.split(" ");
//        Tag tag4a = new Tag(arr[0]);
//        Tag tag4b = new Tag(arr[1]);
//        Set<Tag> tagset4 = new HashSet<>();
//        tagset4.add(tag4a);
//        tagset4.add(tag4b);
//        Set<Tag> emptySet = new HashSet<>();
//        doAnswer(invocation -> {
//            String txt = invocation.getArgument(0).toString();
//            if (txt.equals("")) return null;
//            if (txt.equals(expResult1)) return tag1;
//            if (txt.equals(arr[0])) return tag4a;
//            if (txt.equals(arr[1])) return tag4b;
//            return new Tag(txt);
//        }).when(goalStore).addTag(any(String.class));
//        assertThat(Objects.equals(tagset1, goalApi.parseStringToTags(testLine1)), is(true));
//        assertThat(Objects.equals(tagset4, goalApi.parseStringToTags(testLine4)), is(true));
//        assertThat(Objects.equals(tagset1, goalApi.parseStringToTags(testLine4)), is(false));
//        assertThat( goalApi.parseStringToTags("some_text"), notNullValue());
//        assertThat(Objects.equals(emptySet, goalApi.parseStringToTags("")), is(true));
//    }

    @Test
    @DisplayName("Test createNewGoal() : check if persists new Goal")
    void testCreateNewGoal() {
        GoalFormDto goalFormDto = new GoalFormDto();
        goalFormDto.setDeadline("25.10.2018");
        goalFormDto.setGoalMessage("hi");
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        goalApi.createNewGoal(goalFormDto);
        verify(goalStore, times(1)).addGoal(any(Goal.class));
    }

    @Test
    @DisplayName("Test createNewGoal() : check if does not persists new Goal if empty or partial GoalFormDto")
    void testCreateNewGoal2() {
        GoalFormDto goalFormDto = new GoalFormDto();
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        goalApi.createNewGoal(goalFormDto);
        verify(goalStore, times(0)).addGoal(any(Goal.class));
        goalFormDto.setGoalMessage("hi");
        goalApi.createNewGoal(goalFormDto);
        verify(goalStore, times(0)).addGoal(any(Goal.class));
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

    @Test
    @DisplayName("SearchParameters: User has correct input search parameter")
    void getSearchParameters1() {
        userList.add(user1);
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername(user1.getUsername());
        dto.setPhone(user1.getPhone());
        dto.setEmail(user1.getEmail());
        userDtoList.add(dto);
        JsonObject searchDto = Json.createObjectBuilder()
                .add("usersearch", "us")
                .build();
        when(userStore.getUserByUsername("us"))
                .thenReturn(userList);
        when(userStore.convertToDto(userList.get(0)))
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


}
