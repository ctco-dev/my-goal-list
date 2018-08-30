package lv.ctco.javaschool.goal.control;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class DateTimeConverterTest {

    @Test
    @DisplayName("Test convertDateTime (LocalDateTime date): Checks that input dateTime corresponds output string")
    void testConvertDateTime() {
        LocalDateTime dt1 = LocalDateTime.of(2018, 10, 25, 10, 15, 30);
        String resultDt1 = "25.10.2018 10:15";
        String resultDt2 = "10.12.2017 11:30";
        assertThat(DateTimeConverter.convertDateTime(dt1), is(resultDt1));
        assertThat(DateTimeConverter.convertDateTime(dt1), not(resultDt2));
    }

    @Test
    @DisplayName("Test convertDate (LocalDate date): Checks that input date corresponds output string")
    void testConvertDate() {
        LocalDate dt1 = LocalDate.of(2018, 10, 25);
        String resultDt1 = "25.10.2018";
        String resultDt2 = "10.12.2017";
        assertThat(DateTimeConverter.convertDate(dt1), is(resultDt1));
        assertThat(DateTimeConverter.convertDate(dt1), not(resultDt2));
    }

    @Test
    @DisplayName("Test countDaysLeft(LocalDate deadlineDate): Checks that input date corresponds output string")
    void testCountDaysLeft() {
        LocalDate today = LocalDate.now();
        int fewDays = 4;
        LocalDate nextFewDays = LocalDate.now().plusDays(fewDays);
        assertThat(DateTimeConverter.countDaysLeft(nextFewDays), is(fewDays));
        assertThat(DateTimeConverter.countDaysLeft(nextFewDays), not(fewDays +1));
    }
}