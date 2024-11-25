package exception;

import dto.ExceptionDTO;
import jakarta.ejb.EJBException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {
    @Override
    public Response toResponse(EJBException exception) {
        try {
            throw exception.getCausedByException();
        } catch (IllegalArgumentException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ExceptionDTO("Not valid parameters: " + ex.getMessage()))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } catch (ProcessingException ex) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(new ExceptionDTO("Other service unreachable"))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        } catch (Exception ex) {
            return Response
                    .serverError()
                    .entity(new ExceptionDTO(ex.getMessage()))
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
    }
}
