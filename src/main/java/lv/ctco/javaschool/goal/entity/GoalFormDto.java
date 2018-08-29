package lv.ctco.javaschool.goal.entity;

/**
 * Created by pavel.tihomirov01 on 8/29/2018.
 */
public class GoalFormDto {
    private String goal = "";
    private String deadline = "";

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
