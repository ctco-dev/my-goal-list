package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
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
import lv.ctco.javaschool.goal.entity.exception.InvalidGoalException;
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
}
