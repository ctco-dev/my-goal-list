package lv.ctco.javaschool.goal.entity;


import java.time.LocalDateTime;

public class GoalDto {
    private String username;
    private String goalMessage;
    private LocalDateTime deadlineDate;
    private LocalDateTime registeredDate;

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

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }
}
