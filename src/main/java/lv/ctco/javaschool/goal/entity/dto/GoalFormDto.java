package lv.ctco.javaschool.goal.entity.dto;

import java.time.LocalDate;

public class GoalFormDto {
    private String goalMessage = "";
    private LocalDate deadline;

    public String getGoalMessage() {
        return goalMessage;
    }

    public void setGoalMessage(String goalMessage) {
        this.goalMessage = goalMessage;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
