package lv.ctco.javaschool.goal.control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public class DateTimeConverter {

    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");

    public static int countDaysLeft(LocalDate deadlineDate) {
        LocalDate localDate = LocalDate.now();
        return ((int) DAYS.between(localDate, deadlineDate));
    }

    public static String convertDate(LocalDate date) {
        return date.format(DateTimeConverter.FORMATTER_DATE);
    }

    public static String convertDateTime(LocalDateTime date) {
        return date.format(DateTimeConverter.FORMATTER_DATE_TIME);
    }

}
