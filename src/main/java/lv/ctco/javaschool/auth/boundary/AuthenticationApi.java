package lv.ctco.javaschool.auth.boundary;

import lv.ctco.javaschool.auth.control.exceptions.InvalidPasswordException;
import lv.ctco.javaschool.auth.control.exceptions.InvalidUsernameException;
import lv.ctco.javaschool.auth.control.UserStore;
import lv.ctco.javaschool.auth.control.exceptions.UsernameAlreadyExistsException;
import lv.ctco.javaschool.auth.entity.domain.Role;
import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.auth.entity.dto.UserLoginDto;
import lv.ctco.javaschool.auth.entity.dto.ErrorDto;

import javax.inject.Inject;
import javax.json.Json;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@Path("/auth")
public class AuthenticationApi {
    private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Inject
    @SuppressWarnings("CdiInjectionPointsInspection")
    private SecurityContext securityContext;
    @Inject
    private UserStore userStore;

    @POST
    @Path("/login")
    public Response login(UserLoginDto userLogin, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        String username = userLogin.getUsername();
        Credential credential = new UsernamePasswordCredential(username, userLogin.getPassword());
        AuthenticationStatus status = securityContext.authenticate(request, response,
                withParams()
                        .credential(credential)
                        .newAuthentication(true)
                        .rememberMe(false));
        if (status == AuthenticationStatus.SUCCESS) {
            return Response.ok(Json.createObjectBuilder().add("username", username).build()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/register")
    public Response register(UserLoginDto userLogin, @Context HttpServletRequest request, @Context HttpServletResponse response) {
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();
        String errorCode = "UNKNOWN";
        Response.Status status = Response.Status.BAD_REQUEST;
        try {
            User user = userStore.createUser(username, password, Role.USER);
            log.info(String.format("User is registered %s", user));
            return login(userLogin, request, response);
        } catch (UsernameAlreadyExistsException e) {
            errorCode = "CONFLICT";
            status = Response.Status.CONFLICT;
        } catch (InvalidUsernameException e) {
            errorCode = "BAD_USERNAME";
        } catch (InvalidPasswordException e) {
            errorCode = "BAD_PASSWORD";
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorCode(errorCode);
        return Response
                .status(status)
                .entity(errorDto)
                .build();
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request) {
        try {
            request.logout();
            return Response.ok().build();
        } catch (ServletException e) {
            log.log(Level.WARNING, "Cannot logout", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
