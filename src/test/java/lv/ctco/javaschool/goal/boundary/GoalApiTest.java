//package lv.ctco.javaschool.goal.boundary;
//
//import lv.ctco.javaschool.auth.control.UserStore;
//import lv.ctco.javaschool.auth.entity.domain.User;
//import lv.ctco.javaschool.goal.control.GoalStore;
//import lv.ctco.javaschool.goal.entity.Goal;
//import lv.ctco.javaschool.goal.entity.GoalDto;
//import lv.ctco.javaschool.goal.entity.Tag;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.Month;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//class GoalApiTest {
////    @Test
////    void createNewGoal() {
////    }
////    @Mock
//    EntityManager em;
//    @Mock
//    private UserStore userStore;
//    @Mock
//    private GoalStore goalStore;
//
//    @InjectMocks
//    private GoalApi goalApi;
//    private Goal goal;
//
//    @BeforeEach
//    void init() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void getMyGoals() {
//
//        User user = new User();
//        user.setUsername("aa");
//
//        goal = new Goal();
//        goal.setGoalMessage("goal1");
//        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
//        goal.setRegisteredDate(LocalDateTime.parse("01.02.2018 10:41", dataTimeFormatter));
//        goal.setDeadlineDate(LocalDate.parse("10.02.2018", dataFormatter));
//        goal.setUser(user);
//
//        List<Goal> goalsList = new ArrayList<>();
//        goalsList.add(goal);
//
//
//        Mockito.when(userStore.getCurrentUser()).thenReturn(user);
//        Mockito.when(goalStore.getGoalsListFor(user)).thenReturn(goalsList);
//        List<GoalDto> dtos = goalApi.getMyGoals();
//
//        GoalDto dto = dtos.get(0);
//        assertEquals(dto.getUsername(), "aa");
//        assertEquals(dto.getGoalMessage(), "goal1");
//        assertEquals(dto.getRegisteredDate(), "01.02.2018 10:41");
//        assertEquals(dto.getDeadlineDate(), "10.02.2018");
//    }

//
//    @Test
//    @DisplayName("Check crate new Goal from dto")
//    void testConvertDtoToGoal() {
//        User user = new User();
//        user.setUsername("aa");
//        List<String> tagList = Arrays.asList("tag", "tag2", "tag3", "tag1", "tag2");
//        Set<Tag> tagSet = new HashSet<Tag>(tagList);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
//        LocalDateTime registeredDateTime = LocalDateTime.parse("10.01.2018 15:00", formatter);
//        Goal goal = new Goal(user, "goal-mwessage", LocalDate.parse("10.02.2018"), registeredDateTime,tagList);
//        GoalDto dto = new GoalDto();
//
//
//
//
//        dto.setGoalMessage("goal-mwessage");
//        dto.setDeadlineDate("10.02.2018");
//        dto.setTagList(tagList);
//        dto.setRegisteredDate("10.01.2018");
////        LocalTime regTime = LocalTime.now();
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
////        String regTimeText = regTime.format(formatter);
////        dto.setRegisteredDate(regTimeText);
//
////
////        Mockito.when(userStore.getCurrentUser()).thenReturn(user);
////
////        assertEquals(goal, goalApi.convertDtoToGoal(dto));
//////
////
////        assertEquals(user.getUsername(), "aa");
////        assertEquals(goal.getGoalMessage(), "goal-mwessage");
////
////        regTime = LocalTime.now();
////        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
////        regTimeText = regTime.format(formatter);
////        assertEquals(goal.getRegisteredDate(), regTimeText);
////        assertEquals(goal.getDeadlineDate(), "10.02.2018");
//    }
//
//}
