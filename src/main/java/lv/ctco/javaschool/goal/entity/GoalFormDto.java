package lv.ctco.javaschool.goal.entity;

public class GoalFormDto {
    private String goalMessage = "";
    private String deadline = "";

    public String getGoalMessage() {
        return goalMessage;
    }

    public void setGoalMessage(String goalMessage) {
        this.goalMessage = goalMessage;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
