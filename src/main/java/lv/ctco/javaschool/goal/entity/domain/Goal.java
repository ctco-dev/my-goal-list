package lv.ctco.javaschool.goal.entity.domain;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Goal {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    private String tags;
    private String goalMessage;
    private LocalDate deadlineDate;
    private LocalDateTime registeredDate;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
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
}
