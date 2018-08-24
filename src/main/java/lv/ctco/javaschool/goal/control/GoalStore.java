package lv.ctco.javaschool.goal.control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GoalStore {
    @PersistenceContext
    private EntityManager em;
}
