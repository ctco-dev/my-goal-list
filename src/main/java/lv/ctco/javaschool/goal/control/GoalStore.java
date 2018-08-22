package lv.ctco.javaschool.goal.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.goal.entity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GoalStore {
    @PersistenceContext
    private EntityManager em;


}
