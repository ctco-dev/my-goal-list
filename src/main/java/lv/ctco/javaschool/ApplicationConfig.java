package lv.ctco.javaschool;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
@Consumes("application/json")
@Produces("application/json")
@DeclareRoles({"USER", "ADMIN"})
public class ApplicationConfig extends Application {
}
