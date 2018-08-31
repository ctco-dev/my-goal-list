package lv.ctco.javaschool.goal.entity.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidGoalExceptionHandler implements ExceptionMapper<InvalidGoalException> {
    @Override
    public Response toResponse(InvalidGoalException exception) {
        return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}