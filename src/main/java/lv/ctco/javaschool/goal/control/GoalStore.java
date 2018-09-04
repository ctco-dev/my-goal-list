package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Stateless
public class GoalStore {
    @PersistenceContext
    private EntityManager em;

    public List<Goal> getGoalsListFor(User user) {
        return em.createQuery(
                "select g " +
                        "from Goal g " +
                        "where g.user = :user ", Goal.class)
                .setParameter("user", user)
                .getResultList();
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

    public List<Tag> getAllTagList() {
        return em.createQuery("SELECT t FROM Tag t " +
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

}
