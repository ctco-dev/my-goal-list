package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.auth.entity.dto.UserSearchDto;
import lv.ctco.javaschool.goal.control.DtoConverter;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.control.TagParser;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.CommentDto;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import lv.ctco.javaschool.goal.entity.dto.GoalFormDto;
import lv.ctco.javaschool.goal.entity.dto.MessageDto;
import lv.ctco.javaschool.goal.entity.dto.TagDto;
import lv.ctco.javaschool.goal.entity.dto.UserDto;
import lv.ctco.javaschool.goal.entity.exception.InvalidGoalException;
import lv.ctco.javaschool.goal.entity.exception.InvalidUserException;
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
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoalApiTest {
    Goal goal = new Goal();
    Goal goal2 = new Goal();
    Tag tag1 = new Tag();
    Tag tag2 = new Tag();
    Tag tag3 = new Tag();
    User user1 = new User();
    User user2 = new User();
    Comment comment1 = new Comment();
    List<Goal> goalList1 = new ArrayList<>();
    List<GoalDto> goalDtoList = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();
    List<CommentDto> commentDtos = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<UserLoginDto> userDtoList = new ArrayList<>();
    Set<Tag> tags = new HashSet<>();
    List<Tag> tagList = new ArrayList<>();
    String testLine1, expResult1, testLine2, expResult2, testLine3, expResult3, testLine4, expResult4;

    @Mock
    private UserStore userStore;
    @Mock
    private GoalStore goalStore;
    @Mock
    private TagParser tagParser;

    @InjectMocks
    private GoalApi goalApi;

    @BeforeEach
    void init() {
        user1.setUsername("user");
        user1.setEmail("user@user.com");
        user1.setId(1L);
        user1.setPassword("12345");
        user1.setPhone("12345678");
        tag1.setId((long) 1);
        tag2.setId((long) 2);
        tag3.setId((long) 3);
        tag1.setTagMessage("qwer");
        tag2.setTagMessage("qwkeughsdf");
        tag3.setTagMessage("asdlgn");

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

        tagList.add(new Tag("tag1"));
        tagList.add(new Tag("tag2"));
        tagList.add(new Tag("tag3"));
        tagList.add(new Tag("tag4"));

        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);
    }

    @Test
    @DisplayName("Test getUserById(): returns user if User exists")
    void testGetUserByIdReturnsUserDtoIfUserExists() {
        goalList1.add(goal);
        UserDto userDto = DtoConverter.convertToUserDto(user1, goalList1);
        when(userStore.findUserById(1L))
                .thenReturn(Optional.of(user1));
        when(goalStore.getGoalsListFor(user1))
                .thenReturn(goalList1);
        assertThat(goalApi.getUserById(1L).getClass(), equalTo(userDto.getClass()));
        assertThat(goalApi.getUserById(1L).getEmail(), equalTo(userDto.getEmail()));
        assertThat(goalApi.getUserById(1L).getId(), equalTo(userDto.getId()));
        assertThat(goalApi.getUserById(1L).getGoalList().get(0).getDaysLeft(), equalTo(userDto.getGoalList().get(0).getDaysLeft()));
        assertThat(goalApi.getUserById(1L).getPhone(), equalTo(userDto.getPhone()));
    }

    @Test
    @DisplayName("Test getUserById(): throws InvalidUserException if user does not exists(wrong id)")
    void testGetUserByIdThrowsError() {
        when(userStore.findUserById(1L))
                .thenReturn(Optional.empty());
        assertThrows(InvalidUserException.class, () -> goalApi.getUserById(1L));
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
    void testGetMyGoalsToReturnListOfDto() {
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
        assertThat(goalApi.getGoalDtoByGoalId(1L).getId(), is(1L));
        assertThat(goalApi.getGoalDtoByGoalId(1L).getUsername(), is(user1.getUsername()));
        assertThat(goalApi.getGoalDtoByGoalId(1L).getGoalMessage(), is("abc"));
    }

    @Test
    @DisplayName("Test getGoalById(): throws InvalidGoalException")
    void testGetGoalByIdException() {
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.empty());
        assertThrows(InvalidGoalException.class, () -> goalApi.getGoalDtoByGoalId(1L));
    }

    @Test
    @DisplayName("Test createNewGoal() : check if persists new Goal")
    void testCreateNewGoal() {
        GoalFormDto goalFormDto = new GoalFormDto();
        goalFormDto.setDeadline("25.10.2018");
        goalFormDto.setGoalMessage("hi");
        goalFormDto.setTags("qwjye|iwefyg|ksdgf");
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(tagParser.parseStringToTags(goalFormDto.getTags()))
                .thenReturn(tagList);
        when(goalStore.checkIfTagExistsOrPersist(tagList))
                .thenReturn(tags);
        goalApi.createNewGoal(goalFormDto);
        verify(goalStore, times(1)).addGoal(any(Goal.class));
    }

    @Test
    @DisplayName("Test createNewGoal() : check if throws exception if empty fields of dto object")
    void testCreateNewGoalException() {
        GoalFormDto goalFormDto = new GoalFormDto();
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        assertThrows(InvalidGoalException.class, () -> goalApi.createNewGoal(goalFormDto));
    }

    @Test
    @DisplayName("Test returnAllCommentsForGoalById(): returns Comments dto of goal by id")
    void testReturnAllCommentsForGoalById() {
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
    void testReturnAllCommentsForGoalByIdEmptyCommentsDto() {
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.ofNullable(goal));
        when(goalStore.getCommentsForGoal(goal))
                .thenReturn(comments);
        assertThat(goalApi.returnAllCommentsForGoalById(1L).size(), is(0));
        assertThat(goalApi.returnAllCommentsForGoalById(1L).getClass(), equalTo(commentDtos.getClass()));
    }

    @Test
    @DisplayName("Test saveNewCommentsForGoalById(): verify if persists Comments")
    void testSaveNewCommentsForGoalById() {
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
    void testSaveNewCommentsForGoalByIdException() {
        MessageDto msg = new MessageDto();
        msg.setMessage("hi");
        when(goalStore.getGoalById(1L))
                .thenReturn(Optional.empty());
        assertThrows(InvalidGoalException.class, () -> goalApi.saveNewCommentsForGoalById(1L, msg));
    }

    @Test
    @DisplayName("Test returnAllTags(): verify if throws exception if optional<goal> isEmpty")
    void testReturnAllTags() {
        when(goalStore.getAllTagList()).thenReturn(tagList);
        List<TagDto> dtoList = goalApi.returnAllTags();
        assertThat(dtoList.size(), is(tagList.size()));
        for (int i = 0; i < dtoList.size(); i++) {
            assertThat(dtoList.get(i).getTagMessage(), is(tagList.get(i).getTagMessage()));
        }
        assertThat(dtoList.get(0).getTagMessage(), is(not(tagList.get(2).getTagMessage())));
    }

    @Test
    @DisplayName("Test getGoalsByTag(JsonObject searchDto): returns list of GoalDto by Tag")
    void testGetGoalsByTag() {
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setTagMessage("test1");
        Tag tag2 = new Tag();
        tag2.setTagMessage("test2");
        Collections.addAll(tags, tag1);
        List<Goal> goals = new ArrayList<>();
        Goal goal1 = new Goal();
        goal1.setUser(user1);
        goal1.setGoalMessage("abc");
        goal1.setRegisteredDate(LocalDateTime.now());
        goal1.setDeadlineDate(LocalDate.now().plusDays(1));
        goal1.setId(1L);
        goal1.setTags(new HashSet<>(tags));
        Goal goal2 = new Goal();
        goal2.setUser(user2);
        goal2.setGoalMessage("bcd");
        goal2.setRegisteredDate(LocalDateTime.now());
        goal2.setDeadlineDate(LocalDate.now().plusDays(1));
        goal2.setId(2L);
        goal2.setTags(new HashSet<>(tags));
        Collections.addAll(goals, goal1, goal2);
        JsonObject jsonObject = Json.createObjectBuilder().build();
        for (Tag tag : tags) {
            Json.createObjectBuilder(jsonObject).add("tagsearch", tag.getTagMessage());
            List<GoalDto> dtoList = goalApi.getGoalsByTag(jsonObject);
            for (int i = 0; i < dtoList.size(); i++) {
                assertThat(dtoList.get(i).getId(), is(goals.get(i).getId()));
                assertThat(dtoList.get(i).getUsername(), is(goals.get(i).getUser().getUsername()));
                assertThat(dtoList.get(i).getGoalMessage(), is(goals.get(i).getGoalMessage()));
                assertThat(dtoList.get(i).getRegisteredDate(), is(goals.get(i).getRegisteredDate()));
                assertThat(dtoList.get(i).getTags(), is(goals.get(i).getTags()));
            }
        }
    }

    @Test
    @DisplayName("Test getUsersByUsername(JsonObject searchDto): returns list of UserSearchDto and calling userStore.getUserByUsername() method")
    void testGetUsersByUsername() {
        List<User> users = new ArrayList<>();
        Collections.addAll(users, user1, user2);
        when(userStore.getUserByUsername(anyString())).thenReturn(users);
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("usersearch", "user")
                .add("usersearch", "admin")
                .build();
        List<UserSearchDto> dtoList = goalApi.getUsersByUsername(jsonObject);
        for (int i = 0; i < dtoList.size(); i++) {
            assertThat(dtoList.get(i).getId(), is(users.get(i).getId()));
            assertThat(dtoList.get(i).getUsername(), is(users.get(i).getUsername()));
            assertThat(dtoList.get(i).getEmail(), is(users.get(i).getEmail()));
            assertThat(dtoList.get(i).getPhone(), is(users.get(i).getPhone()));
        }
        verify(userStore, times(1)).getUserByUsername(anyString());
    }
}
