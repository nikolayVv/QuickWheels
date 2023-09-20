package si.fri.rso.samples.deliveries.api.v1.resources;

import com.amazonaws.services.location.model.Leg;
import javassist.bytecode.ByteArray;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import si.fri.rso.samples.deliveries.lib.*;
import si.fri.rso.samples.deliveries.services.beans.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.samples.deliveries.services.clients.AmazonLocationClient;
import si.fri.rso.samples.deliveries.services.streaming.EventProducerImpl;

@Log
@ApplicationScoped
@Tag(name = "map", description = "Everything about the map")
@Path("/deliveryMap")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryMapResource {
    private Logger log = Logger.getLogger(DeliveryMapResource.class.getName());

    @Inject
    private AmazonLocationClient amazonLocationClient;

    @Context
    protected UriInfo uriInfo;


    @Operation(description = "Load the map.", summary = "Get a blob of the map.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Body of the style descriptor.",
                    content = @Content(schema = @Schema(implementation = ByteArray.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public void getMapAsync(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation time out").build());
            }
        });

        asyncResponse.setTimeout(20, TimeUnit.SECONDS);

        new Thread(() -> {
            byte[] mapBytes = amazonLocationClient.getMap();
            asyncResponse.resume(Response.status(Response.Status.OK).entity(mapBytes).build());
        }).start();
    }


    @Operation(description = "Calculates route.", summary = "Calculates route between two positions.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Route was successfully calculated."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    @Path("/calcRoute")
    public Response calculateRoute(@RequestBody(
            description = "DTO object with required departureLat, departureLon, destinationLat, destinationLon.",
            required = true, content = @Content(
            schema = @Schema(implementation = RouteRequest.class))) RouteRequest route) throws ParseException {
        if((route.getDepartureLat() == null) || (route.getDepartureLon() == null) || (route.getDestinationLat() == null) || (route.getDestinationLon() == null) ||
                (route.getTransport() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Leg> res = amazonLocationClient.calculateRoute(route);

        return Response.status(Response.Status.OK).entity(res).build();
    }

}
