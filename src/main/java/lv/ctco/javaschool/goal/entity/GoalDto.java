package lv.ctco.javaschool.goal.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GoalDto {
    private String username;
    private String goalMessage;
    private String deadlineDate;
    private String registeredDate;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setGoalMessage(String goalMessage) {
        this.goalMessage = goalMessage;
    }

    public String getGoalMessage() {
        return goalMessage;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.mm.yyyy");
        this.deadlineDate = deadlineDate.format(formatter);
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
        this.registeredDate = registeredDate.format(formatter);
    }

    public String getRegisteredDate() {
        return registeredDate;
    }
}
