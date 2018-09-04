package lv.ctco.javaschool.goal.entity.domain;

import lv.ctco.javaschool.auth.entity.domain.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    @OneToMany
    @JoinTable(name = "goal_tags",
            joinColumns = @JoinColumn(name = "goal_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
    private String goalMessage;
    private LocalDate deadlineDate;
    private LocalDateTime registeredDate;

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
}
