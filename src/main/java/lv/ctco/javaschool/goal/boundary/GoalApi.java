package lv.ctco.javaschool.goal.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.goal.control.GoalStore;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.POST;


@Path("/goal")
@Stateless
public class GoalApi {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserStore userStore;
    @Inject
    private GoalStore goalStore;

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public void startPage() {
        /// place for methods on page reload
        /// currently - none
    }
}
