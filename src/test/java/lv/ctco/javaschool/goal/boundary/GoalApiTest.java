package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.control.GoalStore;
import lv.ctco.javaschool.goal.entity.Goal;
import lv.ctco.javaschool.goal.entity.GoalDto;
import lv.ctco.javaschool.goal.entity.GoalFormDto;
import lv.ctco.javaschool.goal.entity.Tag;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
        assertEquals(goalDtoList, goalApi.getMyGoals());
    }

    @Test
    @DisplayName("Test getMyGoals(): Current user has goals returns list of dto's")
    void testGetMyGoals2() {
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
    void testGetGoalById2() {
        when(goalStore.getGoalById((long) 1))
                .thenReturn(java.util.Optional.empty());
        assertEquals(GoalDto.class, goalApi.getGoalDtoByGoalId((long) 1).getClass());
        assertEquals(null, goalApi.getGoalDtoByGoalId((long) 1).getGoalMessage());
        assertEquals(null, goalApi.getGoalDtoByGoalId((long) 1).getId());
    }

    @Test
    @DisplayName("Test getGoalById(): returns dto of goal by id")
    void testGetGoalById() {
        when(goalStore.getGoalById(1L))
                .thenReturn(java.util.Optional.ofNullable(goal));
        assertEquals(new Long(1), goalApi.getGoalDtoByGoalId((long) 1).getId());
        assertEquals(user1.getUsername(), goalApi.getGoalDtoByGoalId((long) 1).getUsername());
        assertEquals("abc", goalApi.getGoalDtoByGoalId((long) 1).getGoalMessage());
    }

    @Test
    @DisplayName("Test generateTagsList: Checks work of tag list generation from goal message")
    void testGenerationOfTagsList() {
        String testLine2 = "I WILL BECOME A PROGRAMMER THIS YEAR!";
        String expResult2 = "PROGRAMMER";
        String testLine3 = "I WILL be two years old!";
        String expResult3 = "";
        assertEquals(expResult1, String.join(" ", goalApi.generateTagsList(testLine1)));
        assertEquals(expResult2, String.join(" ", goalApi.generateTagsList(testLine2)));
        assertEquals(expResult3, String.join(" ", goalApi.generateTagsList(testLine3)));
        assertEquals(expResult4, String.join(" ", goalApi.generateTagsList(testLine4)));
        assertFalse(expResult2.equals(String.join(" ", goalApi.generateTagsList(testLine1))));
        assertFalse(expResult1.equals(String.join(" ", goalApi.generateTagsList(testLine2))));
    }

    public static boolean equals(Set<?> set1, Set<?> set2) {
        if (set1 == null || set2 == null) {
            return false;
        }
        if (set1.size() != set2.size()) {
            return false;
        }
        return set1.containsAll(set2);
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
        assertThat(equals(tagset1, goalApi.parseStringToTags(testLine1)), is(true));
        assertThat(equals(tagset4, goalApi.parseStringToTags(testLine4)), is(true));
        assertThat(equals(tagset1, goalApi.parseStringToTags(testLine4)), is(false));
        assertNotNull(goalApi.parseStringToTags("some_text"));
        assertThat(equals(emptySet, goalApi.parseStringToTags("")), is(true));
    }

    @Test
    @DisplayName("Test convertDateTime (LocalDateTime date): Checks that input dateTime corresponds output string")
    void testConvertDateTime() {
        LocalDateTime dt1 = LocalDateTime.of(2018, 10, 25, 10, 15, 30);
        String resultDt1 = "25.10.2018 10:15";
        String resultDt2 = "10.12.2017 11:30";
        assertEquals(resultDt1, goalApi.convertDateTime(dt1));
        assertNotEquals(resultDt2, goalApi.convertDateTime(dt1));
    }

    @Test
    @DisplayName("Test convertDate (LocalDate date): Checks that input date corresponds output string")
    void testConvertDate() {
        LocalDate dt1 = LocalDate.of(2018, 10, 25);
        String resultDt1 = "25.10.2018";
        String resultDt2 = "10.12.2017";
        assertEquals(resultDt1, goalApi.convertDate(dt1));
        assertNotEquals(resultDt2, goalApi.convertDate(dt1));
    }

    @Test
    @DisplayName("Test countDaysLeft(LocalDate deadlineDate): Checks that input date corresponds output string")
    void testCountDaysLeft() {
        LocalDate today = LocalDate.now();
        long fewDays = 4L;
        LocalDate nextFewDays = LocalDate.now().plusDays(fewDays);
        assertEquals(fewDays, goalApi.countDaysLeft(nextFewDays));
        assertNotEquals(fewDays + 1L, goalApi.countDaysLeft(nextFewDays));
    }

    @Test
    @DisplayName("Test convertToDto(Goal goal): Checks that goal and goalDto contains same data (except List<Tag>)")
    void testConvertToDto() {
        GoalDto dto = goalApi.convertToDto(goal);
        assertEquals(goal.getUser().getUsername(), dto.getUsername());
        assertEquals(goal.getGoalMessage(), dto.getGoalMessage());
        assertEquals(goalApi.convertDate(goal.getDeadlineDate()), dto.getDeadlineDate());
        assertEquals(goal.getId(), dto.getId());
        assertEquals(goalApi.convertDateTime(goal.getRegisteredDate()), dto.getRegisteredDate());
        assertEquals(goalApi.countDaysLeft(goal.getDeadlineDate()), dto.getDaysLeft());
        assertNull(dto.getTagList());
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
