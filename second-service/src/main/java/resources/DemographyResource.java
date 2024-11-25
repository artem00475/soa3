package resources;

import dto.ResultDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import itmo.tuchin.nikitin.ejb.DemographyBeanRemoteTest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


@Path("/demography")
@Produces(MediaType.APPLICATION_JSON)
public class DemographyResource {

    private static DemographyBeanRemoteTest lookupEJB() throws NamingException {
        Context ctx = new InitialContext();
        return (DemographyBeanRemoteTest) ctx.lookup("java:global/second-service/DemographyBean!itmo.tuchin.nikitin.ejb.DemographyBeanRemoteTest");
    }

    @GET
    @Path("/hair-color/{hair-color}/percentage")
    public Response getPercentageByHairColor(@PathParam("hair-color") String color) throws NamingException {
        System.out.println("color");
        return Response.ok(
                new ResultDTO(String.valueOf(lookupEJB().getPercentageByHairColor(color)))
        ).build();
    }

    @GET
    @Path("/nationality/{nationality}/hair-color")
    public Response getCountByNationalityAndHairColor(
            @PathParam("nationality") String nationality,
            @QueryParam("hair-color") String color
    ) throws NamingException {
        System.out.println("nationality");
        return Response.ok(
                new ResultDTO(String.valueOf(lookupEJB().getCountByNationalityAndHairColor(nationality, color)))
        ).build();
    }
}
