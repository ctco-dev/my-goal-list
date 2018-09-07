package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserSearchDto;
import lv.ctco.javaschool.goal.control.DtoConverter;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.control.TagParser;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.GoalStatus;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.CommentDto;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import lv.ctco.javaschool.goal.entity.dto.GoalFormDto;
import lv.ctco.javaschool.goal.entity.dto.MessageDto;
import lv.ctco.javaschool.goal.entity.dto.TagDto;
import lv.ctco.javaschool.goal.entity.dto.UserDto;
import lv.ctco.javaschool.goal.entity.exception.InvalidGoalException;
import lv.ctco.javaschool.goal.entity.exception.InvalidUserException;
import lv.ctco.javaschool.goal.entity.exception.ValidationException;
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
import java.util.Collections;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoalApiTest {
    private Goal goal;
    private User user1;
    private User user2;
    private Comment comment1;
    private List<Goal> goalList1;
    private List<GoalDto> goalDtoList;
    private List<Comment> comments;
    private List<CommentDto> commentDtoList;
    private Set<Tag> tags;
    private List<Tag> tagList;
    private GoalFormDto goalFormDto;
    private MessageDto msg;

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
        Goal goal2 = new Goal();
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Tag tag3 = new Tag();
        goal = new Goal();
        user1 = new User();
        user2 = new User();
        comment1 = new Comment();
        goalList1 = new ArrayList<>();
        goalDtoList = new ArrayList<>();
        comments = new ArrayList<>();
        commentDtoList = new ArrayList<>();
        tags = new HashSet<>();
        tagList = new ArrayList<>();
        goalFormDto = setupGoalFormDto();
        msg = new MessageDto();
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
        goal.setStatus(GoalStatus.OPEN);
        goal2.setUser(user2);
        goal2.setGoalMessage("cde");
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
        msg.setMessage("hi");
    }

    @Test
    @DisplayName("Test getUserById(): returns user if User exists")
    void testGetUserByIdReturnsUserDtoIfUserExists() {
        goalList1.add(goal);
        UserDto userDto = DtoConverter.convertToUserDto(user1, goalList1);
        when(userStore.getUserById(1L))
                .thenReturn(Optional.of(user1));
        when(goalStore.getGoalsByUser(user1))
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
        when(userStore.getUserById(1L))
                .thenReturn(Optional.empty());
        assertThrows(InvalidUserException.class, () -> goalApi.getUserById(1L));
    }

    @Test
    @DisplayName("Test findGoalsForCurrentUser(): Current user has No goals returns empty list of dto's")
    void testFindGoalsForCurrentUser() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalsByUser(user1))
                .thenReturn(goalList1);
        assertThat(goalApi.findGoalsForCurrentUser(), equalTo(goalDtoList));
    }

    @Test
    @DisplayName("Test findGoalsForCurrentUser(): Current user has goals returns list of dto's")
    void testFindGoalsForCurrentUserToReturnListOfDto() {
        goalList1.add(goal);
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalsByUser(user1))
                .thenReturn(goalList1);
        assertThat(goalApi.findGoalsForCurrentUser().get(0).getUsername(), is(goal.getUser().getUsername()));
        assertThat(goalApi.findGoalsForCurrentUser().get(0).getGoalMessage(), is(goal.getGoalMessage()));
        assertThat(goalApi.findGoalsForCurrentUser().get(0).getId(), is(goal.getId()));
    }

    @Test
    @DisplayName("Test getGoalById(): returns dto of goal by id")
    void testGetGoalById() {
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.ofNullable(goal));
        assertThat(goalApi.findGoalDtoByGoalId(1L).getId(), is(1L));
        assertThat(goalApi.findGoalDtoByGoalId(1L).getUsername(), is(user1.getUsername()));
        assertThat(goalApi.findGoalDtoByGoalId(1L).getGoalMessage(), is("abc"));
    }

    @Test
    @DisplayName("Test getGoalById(): throws InvalidGoalException")
    void testGetGoalByIdException() {
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.empty());
        assertThrows(InvalidGoalException.class, () -> goalApi.findGoalDtoByGoalId(1L));
    }

    @Test
    @DisplayName("Test saveGoal(): check if persists new Goal")
    void testSaveGoal() {
        goalFormDto.setDeadline(LocalDate.of(9018, 10, 25));
        goalFormDto.setGoalMessage("hi");
        goalFormDto.setTags("qwjye|iwefyg|ksdgf");
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(tagParser.parseStringToTags(goalFormDto.getTags()))
                .thenReturn(tagList);
        when(goalStore.checkIfTagExistsOrPersist(tagList))
                .thenReturn(tags);
        goalApi.saveGoal(goalFormDto);
        verify(goalStore, times(1)).addGoal(any(Goal.class));
    }

    @Test
    @DisplayName("Test saveGoal(): check if throws exception if empty fields of dto object")
    void testSaveGoalException() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        assertThrows(InvalidGoalException.class, () -> goalApi.saveGoal(new GoalFormDto()));
    }

    @Test
    @DisplayName("Test saveGoal(): check if throws exception if empty message inserted")
    void testSaveGoalEmptyMessage() {
        GoalFormDto testGoalFormDto = new GoalFormDto();
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        assertThrows(InvalidGoalException.class, () -> goalApi.saveGoal(testGoalFormDto));
        goalFormDto.setGoalMessage("");
        assertThrows(InvalidGoalException.class, () -> goalApi.saveGoal(testGoalFormDto));
        goalFormDto.setGoalMessage(" ");
        assertThrows(InvalidGoalException.class, () -> goalApi.saveGoal(testGoalFormDto));
        goalFormDto.setGoalMessage("  ");
        assertThrows(InvalidGoalException.class, () -> goalApi.saveGoal(testGoalFormDto));
    }

    @Test
    @DisplayName("Test saveGoal(): check if throws exception if there is invalid deadline date of dto object")
    void testSaveGoalInvalidDeadline() {
        goalFormDto.setDeadline(LocalDate.of(1,1,1));
        goalFormDto.setGoalMessage("hi");
        goalFormDto.setTags("qwjye|iwefyg|ksdgf");
        when(userStore.getCurrentUser())
                .thenReturn(user1);

        assertThrows(InvalidGoalException.class, () -> goalApi.saveGoal(goalFormDto));
    }

    @Test
    @DisplayName("Test findCommentsForGoalById(): returns Comments dto of goal by id")
    void testFindCommentsForGoalById() {
        comments.add(comment1);
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.ofNullable(goal));
        when(goalStore.getCommentsForGoal(goal))
                .thenReturn(comments);
        assertThat(goalApi.findCommentsForGoalById(1L).get(0).getClass(), equalTo(CommentDto.class));
        assertThat(goalApi.findCommentsForGoalById(1L).get(0).getCommentMessage(), is("hi"));
        assertThat(goalApi.findCommentsForGoalById(1L).get(0).getUsername(), is("user"));
    }

    @Test
    @DisplayName("Test findCommentsForGoalById(): returns empty Comments dto of goal")
    void testFindCommentsForGoalByIdEmptyCommentsDto() {
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.ofNullable(goal));
        when(goalStore.getCommentsForGoal(goal))
                .thenReturn(comments);
        assertThat(goalApi.findCommentsForGoalById(1L).size(), is(0));
        assertThat(goalApi.findCommentsForGoalById(1L).getClass(), equalTo(commentDtoList.getClass()));
    }

    @Test
    @DisplayName("Test saveCommentsForGoalById(): verify if persists Comments")
    void testSaveCommentsForGoalById() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getGoalById(1L))
                .thenReturn(Optional.ofNullable(goal));
        goalApi.saveCommentsForGoalById(1L, msg);
        verify(goalStore, times(1)).addComment(any(Comment.class));
    }

    @Test
    @DisplayName("Test saveCommentsForGoalById(): verify if throws exception if optional<goal> isEmpty")
    void testSaveCommentsForGoalByIdException() {
        when(goalStore.getGoalById(1L))
                .thenReturn(Optional.empty());
        assertThrows(InvalidGoalException.class, () -> goalApi.saveCommentsForGoalById(1L, msg));
    }

    @Test
    @DisplayName("Test isCurrentUsersGoal(Long goalId): verify if goal is for current user is returned true")
    void testIsCurrentUsersGoalForCurrentUser() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getUnachievedUserGoalById(user1, 1L))
                .thenReturn(Optional.of(goal));
        assertThat(goalApi.isCurrentUsersGoal(1L), is(true));
    }

    @Test
    @DisplayName("Test isCurrentUsersGoal(Long goalId): verify if goal is for other user not current, then returned false")
    void testIsCurrentUsersGoalForDifferentUser() {
        when(userStore.getCurrentUser())
                .thenReturn(user2);
        when(goalStore.getUnachievedUserGoalById(user2, 1L))
                .thenReturn(Optional.empty());
        assertThat(goalApi.isCurrentUsersGoal(1L), is(false));
    }

    @Test
    @DisplayName("Test editGoal(Long goalId, GoalFormDto newGoalDto): verify that goal is edited, if user created goal")
    void testEditGoalForGoalOwner() {
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getUnachievedUserGoalById(user1, 1L))
                .thenReturn(Optional.of(goal));
        goalApi.editGoal(1L, goalFormDto);
        assertThat(goal.getGoalMessage(), is(goalFormDto.getGoalMessage()));
        assertThat(goal.getDeadlineDate(), is(goalFormDto.getDeadline()));
        assertThat(goal.getStatus(), is(GoalStatus.OPEN));
    }

    @Test
    @DisplayName("Test editGoal(Long goalId, GoalFormDto newGoalDto): verify that goal is not edited, if user did not create goal")
    void testEditGoalForDifferentUser() {
        String messageBeforeEdit = goal.getGoalMessage();
        LocalDate deadlineDateBeforeEdit = goal.getDeadlineDate();
        when(userStore.getCurrentUser())
                .thenReturn(user2);
        when(goalStore.getUnachievedUserGoalById(user2, 1L))
                .thenReturn(Optional.empty());
        goalApi.editGoal(1L, goalFormDto);
        assertThat(goal.getGoalMessage(), is(messageBeforeEdit));
        assertThat(goal.getDeadlineDate(), is(deadlineDateBeforeEdit));
    }


    @Test
    @DisplayName("Test editGoal(Long goalId, GoalFormDto newGoalDto): verify that goal is not edited, if goal is achieved")
    void testEditGoalForAchievedGoal() {
        goal.setStatus(GoalStatus.ACHIEVED);
        String messageBeforeEdit = goal.getGoalMessage();
        LocalDate deadlineDateBeforeEdit = goal.getDeadlineDate();
        when(userStore.getCurrentUser())
                .thenReturn(user2);
        when(goalStore.getUnachievedUserGoalById(user2, 1L))
                .thenReturn(Optional.empty());
        goalApi.editGoal(1L, goalFormDto);
        assertThat(goal.getGoalMessage(), is(messageBeforeEdit));
        assertThat(goal.getDeadlineDate(), is(deadlineDateBeforeEdit));
        assertThat(goal.getStatus(), is(GoalStatus.ACHIEVED));
    }

    @Test
    @DisplayName("Test editGoal(Long goalId, GoalFormDto newGoalDto): verify that goal status is changed, if overdue goal deadline date is edited")
    void testEditGoalForOverdueGoal() {
        goal.setStatus(GoalStatus.OVERDUE);
        when(userStore.getCurrentUser())
                .thenReturn(user1);
        when(goalStore.getUnachievedUserGoalById(user1,1L))
                .thenReturn(Optional.of(goal));
        goalApi.editGoal(1L, goalFormDto);
        assertThat(goal.getGoalMessage(), is(goalFormDto.getGoalMessage()));
        assertThat(goal.getDeadlineDate(), is(goalFormDto.getDeadline()));
        assertThat(goal.getStatus(), is(GoalStatus.OPEN));
    }

    @Test
    @DisplayName("Test editGoal(Long goalId, GoalFormDto newGoalDto): verify that goal is edited, if users new goal is empty")
    void testEditGoalWithoutInput() {

        when(userStore.getCurrentUser())
                .thenReturn(user1);
        assertThrows(ValidationException.class, () -> goalApi.editGoal(1L, new GoalFormDto()));
    }

    @Test
    @DisplayName("Test editGoal(Long goalId, GoalFormDto newGoalDto): verify that goal is edited, if user input invalid deadline date")
    void testEditGoalInvalidDeadline() {
        goalFormDto.setGoalMessage("123");
        goalFormDto.setDeadline(LocalDate.of(1, 1, 1));
        assertThrows(ValidationException.class, () -> goalApi.editGoal(1L, goalFormDto));
    }

    @Test
    @DisplayName("Test findAllTags(): verify if throws exception if optional<goal> isEmpty")
    void testFindAllTags() {
        when(goalStore.getAllTags()).thenReturn(tagList);
        List<TagDto> dtoList = goalApi.findAllTags();
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
        Tag tag1 = new Tag("test1");
        Tag tag2 = new Tag("test2");
        Collections.addAll(tags, tag1, tag2);
        List<Goal> goals = new ArrayList<>();
        Goal goal1 = new Goal(1L, user1, new HashSet<>(tags), "abc", LocalDate.now().plusDays(1), LocalDateTime.now());
        Goal goal2 = new Goal(2L, user2, new HashSet<>(tags), "bcd", LocalDate.now().plusDays(1), LocalDateTime.now());
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
    @DisplayName("Test getUsersByUsername(JsonObject searchDto): returns list of UserSearchDto and calling userStore.getUsersByUsername() method")
    void testGetUsersByUsername() {
        List<User> users = new ArrayList<>();
        Collections.addAll(users, user1, user2);
        when(userStore.getUsersByUsername(anyString())).thenReturn(users);
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
        verify(userStore, times(1)).getUsersByUsername(anyString());
    }

    private GoalFormDto setupGoalFormDto() {
        GoalFormDto result = new GoalFormDto();
        result.setGoalMessage("123");
        result.setDeadline(LocalDate.of(2918, 1, 1));
        return result;
    }
}
