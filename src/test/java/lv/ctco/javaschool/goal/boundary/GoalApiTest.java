package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.control.TagParser;
import lv.ctco.javaschool.goal.entity.*;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.CommentDto;
import lv.ctco.javaschool.goal.entity.dto.MessageDto;
import lv.ctco.javaschool.goal.entity.exception.InvalidGoalException;
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
import java.util.*;

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
    List<UserLoginDto> userDtoList = new ArrayList<>();
    String testLine1, expResult1, testLine2, expResult2, testLine3, expResult3, testLine4, expResult4;

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
        goal.setId(1L);
        goal.setTags(null);

        goal2.setUser(user2);
        goal2.setGoalMessage("cde");

        testLine1 = "I will become a programmer this year!";
        expResult1 = "programmer";
        testLine2 = "I WILL BECOME A PROGRAMMER THIS YEAR!";
        expResult2 = "PROGRAMMER";
        testLine3 = "I WILL be two years old!";
        expResult3 = "";
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
        assertThat(goalApi.getMyGoals(), equalTo(goalDtoList));
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
    @DisplayName("Test getGoalById(): throws InvalidGoalException")
    void testGetGoalById2() {
        when(goalStore.getGoalById( 1L))
                .thenReturn(java.util.Optional.empty());
        assertThrows(InvalidGoalException.class, () -> goalApi.getGoalDtoByGoalId(1L));
    }

    @Test
    @DisplayName("Test parseStringToTags(String value): Checks work of tag list generation from goal message")
    void testParsingGoalStringToTags() {
        Tag tag1 = new Tag(expResult1);
        Set<Tag> tagset1 = new HashSet<>();
        tagset1.add(tag1);
        String[] arr = expResult4.split(" ");
        Tag tag4a = new Tag(arr[0]);
        Tag tag4b = new Tag(arr[1]);
        Set<Tag> tagset4 = new HashSet<>();
        tagset4.add(tag4a);
        tagset4.add(tag4b);
        Set<Tag> emptySet = new HashSet<>();
        doAnswer(invocation -> {
            String txt = invocation.getArgument(0).toString();
            if (txt.equals("")) return null;
            if (txt.equals(expResult1)) return tag1;
            if (txt.equals(arr[0])) return tag4a;
            if (txt.equals(arr[1])) return tag4b;
            return new Tag(txt);
        }).when(goalStore).addTag(any(String.class));
        assertThat(goalApi.parseStringToTags(testLine1), equalTo(tagset1));
        assertThat(goalApi.parseStringToTags(testLine4), equalTo(tagset4));
        assertThat(goalApi.parseStringToTags(testLine4), not(equalTo(tagset1)));
        assertThat(goalApi.parseStringToTags("some_text"), notNullValue());
        assertThat(goalApi.parseStringToTags(""), equalTo(emptySet));
    }

    @Test
    @DisplayName("generateTagsList: Checks work of tag list generation from goal message")
    void testGenerationOfTagsList() {
        assertThat(String.join(" ", TagParser.generateTagsList(testLine1)), is(expResult1));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine2)), is(expResult2));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine3)), is(expResult3));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine4)), is(expResult4));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine1)), not(expResult2));
        assertThat(String.join(" ", TagParser.generateTagsList(testLine2)), not(expResult1));
    }

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
    @DisplayName("Test createNewGoal() : check if throws exception if empty fields of dto object")
    void testCreateNewGoal2() {
        GoalFormDto goalFormDto = new GoalFormDto();
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        assertThrows(InvalidGoalException.class, () -> goalApi.createNewGoal(goalFormDto));
    }

    @Test
    @DisplayName("Test returnAllCommentsForGoalById(): returns Comments dto of goal by id")
    void returnAllCommentsForGoalById() {
        comments.add(comment1);
        when(goalStore.getGoalById(1l))
                .thenReturn(java.util.Optional.ofNullable(goal));
        when(goalStore.getCommentsForGoal(goal))
                .thenReturn(comments);
        assertThat(goalApi.returnAllCommentsForGoalById(1L).get(0).getClass(), equalTo(CommentDto.class));
        assertThat(goalApi.returnAllCommentsForGoalById(1L).get(0).getCommentMessage(), is("hi"));
        assertThat(goalApi.returnAllCommentsForGoalById(1L).get(0).getUsername(), is("user"));
    }

    @Test
    @DisplayName("Test receiveCommentsForGoalById(): returns empty Comments dto of goal")
    void returnAllCommentsForGoalById2() {
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.ofNullable(goal));
        when(goalStore.getCommentsForGoal(goal))
                .thenReturn(comments);
        assertThat(goalApi.returnAllCommentsForGoalById(1L).size(), is(0));
        assertThat(goalApi.returnAllCommentsForGoalById(1L).getClass(), equalTo(commentDtos.getClass()));
    }

    @Test
    @DisplayName("Test saveNewCommentsForGoalById(): verify if persists Comments")
    void saveNewCommentsForGoalById() {
        MessageDto msg = new MessageDto();
        msg.setMessage("hi");
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalById(1L))
                .thenReturn(Optional.ofNullable(goal));
        goalApi.saveNewCommentsForGoalById(1L, msg);
        verify(goalStore, times(1)).addComment(any(Comment.class));
    }

    @Test
    @DisplayName("Test saveNewCommentsForGoalById(): verify if throws exception if optional<goal> isEmpty")
    void saveNewCommentsForGoalById2() {
        MessageDto msg = new MessageDto();
        msg.setMessage("hi");
        when(goalStore.getGoalById(1L))
                .thenReturn(Optional.empty());
        assertThrows(InvalidGoalException.class, () -> goalApi.saveNewCommentsForGoalById(1L, msg));
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
