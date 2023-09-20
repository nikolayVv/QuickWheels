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
import si.fri.rso.samples.deliveries.lib.DeliveryStatus;
import si.fri.rso.samples.deliveries.lib.DeliveryType;
import si.fri.rso.samples.deliveries.services.beans.DeliveryStatusBean;
import si.fri.rso.samples.deliveries.services.beans.DeliveryTypeBean;

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
@Tag(name = "status", description = "Everything about delivery statuses")
@Path("/deliveryStatus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryStatusResource {
    private Logger log = Logger.getLogger(DeliveryStatusResource.class.getName());

    @Inject
    private DeliveryStatusBean deliveryStatusBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get a list of all delivery statuses.", summary = "Get all delivery statuses.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of delivery statuses.",
                    content = @Content(schema = @Schema(implementation = DeliveryStatus.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getStatuses() {
        List<DeliveryStatus> deliveryStatusList = deliveryStatusBean.getAllDeliveryStatus();

        return Response.status(Response.Status.OK).entity(deliveryStatusList).build();
    }


    @Operation(description = "Get a chosen delivery status by ID.", summary = "Get delivery status by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery status.",
                    content = @Content(schema = @Schema(implementation = DeliveryStatus.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{statusId}")
    public Response getStatusById(@Parameter(description = "Delivery status ID.", required = true)
                                @PathParam("statusId") Short statusId) {

        DeliveryStatus deliveryStatus = deliveryStatusBean.getDeliveryStatus(statusId);

        if (deliveryStatus == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(deliveryStatus).build();
    }


    @Operation(description = "Create a new delivery status.", summary = "Create delivery status.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Delivery status was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createStatus(@RequestBody(
            description = "DTO object with delivery status with required name.",
            required = true, content = @Content(
            schema = @Schema(implementation = DeliveryStatus.class))) DeliveryStatus deliveryStatus) {

        if ((deliveryStatus.getName() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            deliveryStatus = deliveryStatusBean.createDeliveryStatus(deliveryStatus);
        }

        return Response.status(Response.Status.CONFLICT).entity(deliveryStatus).build();
    }


    @Operation(description = "Update an existing delivery status by ID.", summary = "Update delivery status by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery status was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{statusId}")
    public Response updateStatus(@Parameter(description = "Delivery status ID.", required = true)
                                 @PathParam("statusId") Short statusId,
                                 @RequestBody(
                                         description = "DTO object with delivery status with required name.",
                                         required = true, content = @Content(
                                         schema = @Schema(implementation = DeliveryStatus.class)))
                                         DeliveryStatus deliveryStatus) {

        deliveryStatus = deliveryStatusBean.updateDeliveryStatus(statusId, deliveryStatus);

        if (deliveryStatus == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(deliveryStatus).build();
    }


    @Operation(description = "Delete an existing delivery status by ID.", summary = "Delete delivery status by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery status was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{statusId}")
    public Response deleteStatus(@Parameter(description = "Delivery status ID.", required = true)
                               @PathParam("statusId") Short statusId) {

        boolean deleted = deliveryStatusBean.deleteDeliveryStatus(statusId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
