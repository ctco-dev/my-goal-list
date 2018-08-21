package lv.ctco.javaschool.auth.boundary;

import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.entity.domain.User;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/user")
public class UserResourceApi {
    @Inject
    private UserStore userStore;

    @GET
    @Path("/list")
    @Produces("application/json")
    @RolesAllowed("ADMIN")
    public List<User> getUserList() {
        return userStore.getAllUsers();
    }
}
