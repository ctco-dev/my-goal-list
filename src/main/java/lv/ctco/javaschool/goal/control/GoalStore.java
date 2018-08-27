package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.Goal;
import lv.ctco.javaschool.goal.entity.Tag;
import lv.ctco.javaschool.goal.entity.TagDto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

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


    public List<TagDto> getAllTagList() {
        return new ArrayList<>( em.createQuery(
                "SELECT new lv.ctco.javaschool.goal.entity.TagDto(t.tagMessage, COUNT(t)) " +
                        "FROM Tag t, Goal g " +
                        "WHERE t MEMBER OF g.tags " +
                        "GROUP BY t.id"
        ).getResultList());
    }

    public Tag addTag( String tagMsg ){
        Optional<Tag> tagFromDB= em.createQuery("select t from Tag t " +
                "where upper(t.tagMessage) = :tagMsg ", Tag.class)
                .setParameter("tagMsg", tagMsg.toUpperCase() )
                .getResultStream()
                .findFirst();
        if (tagFromDB.isPresent()) return tagFromDB.get();
        else {
            Tag tag = new Tag();
            tag.setTagMessage(tagMsg);
            em.persist(tag);
            return tag;
        }
    }

}
