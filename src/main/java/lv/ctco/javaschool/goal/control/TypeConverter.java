package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.dto.GoalDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public class TypeConverter {

    public static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");

    public static long countDaysLeft(LocalDate deadlineDate) {
        LocalDate localDate = LocalDate.now();
        return DAYS.between(localDate, deadlineDate);
    }

    public static String convertDate(LocalDate date) {
        return date.format(TypeConverter.formatterDate);
    }

    public static String convertDateTime(LocalDateTime date) {
        return date.format(TypeConverter.formatterDateTime);
    }

    public static GoalDto convertGoalToGoalDto(Goal goal) {
        GoalDto dto = new GoalDto();
        dto.setUsername(goal.getUser().getUsername());
        dto.setGoalMessage(goal.getGoalMessage());
        dto.setDeadlineDate(convertDate(goal.getDeadlineDate()));
        dto.setRegisteredDate(convertDateTime(goal.getRegisteredDate()));
        dto.setDaysLeft(countDaysLeft(goal.getDeadlineDate()));
        dto.setId(goal.getId());
        return dto;
    }

}
