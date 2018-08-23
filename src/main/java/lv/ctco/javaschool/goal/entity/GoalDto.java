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

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }
}
