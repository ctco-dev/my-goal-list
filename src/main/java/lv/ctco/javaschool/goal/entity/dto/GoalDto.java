package lv.ctco.javaschool.goal.entity.dto;

import lv.ctco.javaschool.goal.entity.domain.Tag;

import java.util.Set;

public class GoalDto {
    private Long id;
    private String username;
    private String goalMessage;
    private String deadlineDate;
    private String registeredDate;
    private Set<Tag> tags;
    private int daysLeft;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoalMessage() {
        return goalMessage;
    }

    public void setGoalMessage(String goalMessage) {
        this.goalMessage = goalMessage;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
