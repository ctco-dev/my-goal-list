package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.GoalStatus;
import lv.ctco.javaschool.goal.entity.domain.Tag;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Stateless
public class GoalStore {
    @PersistenceContext
    private EntityManager em;

    public List<Goal> getGoalsByUser(User user) {
        List<Goal> goals = em.createQuery("select g from Goal g " +
                "where g.user = :user ", Goal.class)
                .setParameter("user", user)
                .getResultList();
        return changeGoalStatus(goals);
    }

    private List<Goal> changeGoalStatus(List<Goal> goals) {
        List<Goal> goalsToReturn = new ArrayList<>();
        LocalDate localDateNow = LocalDate.now();
        for (Goal goal : goals) {
            if ((goal.getDeadlineDate().isBefore(localDateNow)
                    || goal.getDeadlineDate().isEqual(localDateNow))
                    && goal.getStatus() != GoalStatus.ACHIEVED) {
                goal.setStatus(GoalStatus.OVERDUE);
            }
            goalsToReturn.add(goal);
        }
        return goalsToReturn;
    }

    public void addGoal(Goal goal) {
        em.persist(goal);
    }

    public Tag addTagIfNotExists(String tagMsg) {
        if (tagMsg.equals("")) return null;
        Optional<Tag> tagFromDB = em.createQuery("select t from Tag t " +
                "where upper(t.tagMessage) = :tagMsg ", Tag.class)
                .setParameter("tagMsg", tagMsg.toUpperCase())
                .getResultStream()
                .findFirst();
        if (tagFromDB.isPresent()) {
            return tagFromDB.get();
        }
        Tag tag = new Tag();
        tag.setTagMessage(tagMsg);
        em.persist(tag);
        return tag;
    }

    public Optional<Goal> getGoalById(Long goalId) {
        return em.createQuery("select g from Goal g " +
                "where g.id = :id ", Goal.class)
                .setParameter("id", goalId)
                .getResultStream()
                .findFirst();
    }

    public List<Comment> getCommentsForGoal(Goal goal) {
        return em.createQuery("select c from Comment c " +
                "where c.goal = :goal", Comment.class)
                .setParameter("goal", goal)
                .getResultList();
    }

    public void addComment(Comment comment) {
        em.persist(comment);
    }

    public List<Tag> getAllTags() {
        return em.createQuery("select t from Tag t " +
                "order by t.tagMessage", Tag.class)
                .getResultList();
    }

    public Set<Tag> checkIfTagExistsOrPersist(List<Tag> tags) {
        Set<Tag> tagSet = new HashSet<>();
        for (Tag t : tags) {
            Tag tag = this.addTagIfNotExists(t.getTagMessage());
            if (tag != null) {
                tagSet.add(tag);
            }
        }
        return tagSet;
    }

    public List<Goal> getGoalsByTag(Tag tag) {
        return em.createQuery("select g from Goal as g " +
                "where :tag member of g.tags", Goal.class)
                .setParameter("tag", tag)
                .getResultList();
    }

    public Optional<Tag> getTagByMessage(String message) {
        return em.createQuery("select t from Tag t " +
                "where t.tagMessage = :message", Tag.class)
                .setParameter("message", message)
                .getResultStream()
                .findFirst();
    }

    public List<Tag> getTagsByMessage(String message) {
        return em.createQuery("select t from Tag t " +
                "where lower(t.tagMessage) like lower(:message)", Tag.class)
                .setParameter("message", "%" + message + "%")
                .getResultList();
    }

    public Optional<Goal> getUnachievedUserGoalById(User user, Long goalId) {
        return em.createQuery("select g from Goal g where g.user=:user and g.id=:id and g.status<>:status", Goal.class)
                .setParameter("user", user)
                .setParameter("id", goalId)
                .setParameter("status",GoalStatus.ACHIEVED)
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }
}
