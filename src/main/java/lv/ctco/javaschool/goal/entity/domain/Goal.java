package lv.ctco.javaschool.goal.entity.domain;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    @JoinTable(name = "goal_tags",
            joinColumns = @JoinColumn(name = "goal_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
    private String goalMessage;
    private LocalDate deadlineDate;
    private LocalDateTime registeredDate;
    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    public Goal() {
    }

    public Goal(Long id, User user, Set<Tag> tags, String goalMessage, LocalDate deadlineDate, LocalDateTime registeredDate) {
        this.id = id;
        this.user = user;
        this.tags = tags;
        this.goalMessage = goalMessage;
        this.deadlineDate = deadlineDate;
        this.registeredDate = registeredDate;
    }

    public GoalStatus getStatus() {
        return status;
    }

    public void setStatus(GoalStatus status) {
        this.status = status;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGoalMessage() {
        return goalMessage;
    }

    public void setGoalMessage(String goalMessage) {
        this.goalMessage = goalMessage;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goal goal = (Goal) o;

        if (id != null ? !id.equals(goal.id) : goal.id != null) return false;
        if (user != null ? !user.equals(goal.user) : goal.user != null) return false;
        if (tags != null ? !tags.equals(goal.tags) : goal.tags != null) return false;
        if (goalMessage != null ? !goalMessage.equals(goal.goalMessage) : goal.goalMessage != null) return false;
        if (deadlineDate != null ? !deadlineDate.equals(goal.deadlineDate) : goal.deadlineDate != null) return false;
        if (registeredDate != null ? !registeredDate.equals(goal.registeredDate) : goal.registeredDate != null)
            return false;
        return status == goal.status;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (goalMessage != null ? goalMessage.hashCode() : 0);
        result = 31 * result + (deadlineDate != null ? deadlineDate.hashCode() : 0);
        result = 31 * result + (registeredDate != null ? registeredDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
