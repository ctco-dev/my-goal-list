package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.control.TagParser;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;
import lv.ctco.javaschool.goal.entity.dto.GoalFormDto;
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
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@ExtendWith(MockitoExtension.class)
class GoalApiTest {
    Goal goal = new Goal();
    Goal goal2 = new Goal();
    User user1 = new User();
    User user2 = new User();
    List<Goal> goalList1 = new ArrayList<>();
    List<Goal> goalList2 = new ArrayList<>();
    List<GoalDto> goalDtoList = new ArrayList<>();
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
        assertThat( TagParser.isEqualLists( goalApi.getMyGoals(),goalDtoList), is(true));
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
    void testGetGoalById2() {
        when(goalStore.getGoalById( 1L))
                .thenReturn(java.util.Optional.empty());
        assertThat( goalApi.getGoalDtoByGoalId( 1L ).getClass() == GoalDto.class, is(true));
        assertThat( goalApi.getGoalDtoByGoalId(1L).getGoalMessage()==null, is(true));
        assertThat( goalApi.getGoalDtoByGoalId(1L).getId()==null, is(true));
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
        assertThat(TagParser.isEqualSets(tagset1, goalApi.parseStringToTags(testLine1)), is(true));
        assertThat(TagParser.isEqualSets(tagset4, goalApi.parseStringToTags(testLine4)), is(true));
        assertThat(TagParser.isEqualSets(tagset1, goalApi.parseStringToTags(testLine4)), is(false));
        assertThat( goalApi.parseStringToTags("some_text")!=null, is(true));
        assertThat(TagParser.isEqualSets(emptySet, goalApi.parseStringToTags("")), is(true));
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
}
