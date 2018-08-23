package lv.ctco.javaschool.goal.entity;


import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Goal goal;

    private String tagMessage;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }

    public String getTagMessage() {
        return tagMessage;
    }
}