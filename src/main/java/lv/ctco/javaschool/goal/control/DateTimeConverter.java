package lv.ctco.javaschool.goal.control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public class DateTimeConverter {

    public static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");

    public static int countDaysLeft(LocalDate deadlineDate) {
        LocalDate localDate = LocalDate.now();
        return ((int) DAYS.between(localDate, deadlineDate));
    }

    public static String convertDate(LocalDate date) {
        return date.format(DateTimeConverter.formatterDate);
    }

    public static String convertDateTime(LocalDateTime date) {
        return date.format(DateTimeConverter.formatterDateTime);
    }


}
