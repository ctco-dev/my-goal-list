package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.domain.Comment;
import lv.ctco.javaschool.goal.entity.domain.Goal;
import lv.ctco.javaschool.goal.entity.domain.Tag;
import lv.ctco.javaschool.goal.entity.dto.TagDto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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

    public Tag addTag(String tagMsg) {
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

    public void checkIfTagExistsOrPersist(String[] tagList, Set<Tag> tagSet) {
        for (String item : tagList) {
            Tag tag;
            tag = this.addTag(item);
            if (tag != null) {
                tagSet.add(tag);
            }
        }
    }

    public List<Tag> getAllTagsForGoal(Goal goal) {
        return em.createQuery(
                "select t " +
                        "from Tag t, Goal g " +
                        "where g = :goal and t member of g.tags", Tag.class)
                .setParameter("goal", goal)
                .getResultList();
    }

    public List<Goal> getGoalsByTag(Tag tag){
        return em.createQuery("SELECT g FROM Goal AS g WHERE :tag MEMBER OF g.tags", Goal.class)
                .setParameter("tag", tag)
                .getResultList();
    }

    public Optional<Tag> getTagByMessage(String message){
        return em.createQuery("select t from Tag t where t.tagMessage = :message", Tag.class)
                .setParameter("message", message)
                .getResultStream()
                .findFirst();
    }

    public List<TagDto> getTagList() {
        return new ArrayList<>( em.createQuery(
                "SELECT new lv.ctco.javaschool.goal.entity.TagDto(t.tagMessage, COUNT(t)) " +
                        "FROM Tag t, Goal g " +
                        "WHERE t MEMBER OF g.tags " +
                        "GROUP BY t.id").getResultList());
    }



}
