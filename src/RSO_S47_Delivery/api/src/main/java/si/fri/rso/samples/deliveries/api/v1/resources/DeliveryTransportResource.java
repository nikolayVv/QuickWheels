package si.fri.rso.samples.deliveries.api.v1.resources;

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
import si.fri.rso.samples.deliveries.lib.DeliveryTransport;
import si.fri.rso.samples.deliveries.services.beans.DeliveryTransportBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Tag(name = "transport", description = "Everything about delivery transports")
@Path("/deliveryTransport")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryTransportResource {
    private Logger log = Logger.getLogger(DeliveryTransportResource.class.getName());

    @Inject
    private DeliveryTransportBean deliveryTransportBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get a list of all delivery transports.", summary = "Get all delivery transports.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of delivery transports.",
                    content = @Content(schema = @Schema(implementation = DeliveryTransport.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getTransports() {
        List<DeliveryTransport> deliveryTransportList = deliveryTransportBean.getAllDeliveryTransport();

        return Response.status(Response.Status.OK).entity(deliveryTransportList).build();
    }


    @Operation(description = "Get a chosen delivery transport by ID.", summary = "Get delivery transport by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery transport.",
                    content = @Content(schema = @Schema(implementation = DeliveryTransport.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{transportId}")
    public Response getTransportById(@Parameter(description = "Delivery transport ID.", required = true)
                                @PathParam("transportId") Short transportId) {

        DeliveryTransport deliveryTransport = deliveryTransportBean.getDeliveryTransport(transportId);

        if (deliveryTransport == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(deliveryTransport).build();
    }


    @Operation(description = "Create a new delivery transport.", summary = "Create delivery transport.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Delivery transport was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createTransport(@RequestBody(
            description = "DTO object with delivery transport with required name.",
            required = true, content = @Content(
            schema = @Schema(implementation = DeliveryTransport.class))) DeliveryTransport deliveryTransport) {

        if ((deliveryTransport.getName() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            deliveryTransport = deliveryTransportBean.createDeliveryTransport(deliveryTransport);
        }

        return Response.status(Response.Status.CONFLICT).entity(deliveryTransport).build();
    }


    @Operation(description = "Update an existing delivery transport by ID.", summary = "Update delivery transport by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery transport was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{transportId}")
    public Response updateStatus(@Parameter(description = "Delivery transport ID.", required = true)
                                 @PathParam("transportId") Short transportId,
                                 @RequestBody(
                                         description = "DTO object with delivery transport with required name.",
                                         required = true, content = @Content(
                                         schema = @Schema(implementation = DeliveryTransport.class)))
                                         DeliveryTransport deliveryTransport) {

        deliveryTransport = deliveryTransportBean.updateDeliveryTransport(transportId, deliveryTransport);

        if (deliveryTransport == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(deliveryTransport).build();
    }


    @Operation(description = "Delete an existing delivery transport by ID.", summary = "Delete delivery transport by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery transport was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{transportId}")
    public Response deleteType(@Parameter(description = "Delivery transport ID.", required = true)
                               @PathParam("transportId") Short transportId) {

        boolean deleted = deliveryTransportBean.deleteDeliveryTransport(transportId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
