package lv.ctco.javaschool.goal.entity.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidUserExceptionHandler implements ExceptionMapper<InvalidGoalException> {
    @Override
    public Response toResponse(InvalidGoalException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}
