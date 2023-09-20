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
import si.fri.rso.samples.deliveries.lib.DeliveryType;
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
@Tag(name = "type", description = "Everything about delivery types")
@Path("/deliveryType")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryTypeResource {
    private Logger log = Logger.getLogger(DeliveryTypeResource.class.getName());

    @Inject
    private DeliveryTypeBean deliveryTypeBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get a list of all delivery types.", summary = "Get all delivery types.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of delivery types.",
                    content = @Content(schema = @Schema(implementation = DeliveryType.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getTypes() {
        List<DeliveryType> deliveryTypeList = deliveryTypeBean.getAllDeliveryType();

        return Response.status(Response.Status.OK).entity(deliveryTypeList).build();
    }


    @Operation(description = "Get a chosen delivery type by ID.", summary = "Get delivery type by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery type.",
                    content = @Content(schema = @Schema(implementation = DeliveryType.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{typeId}")
    public Response getTypeById(@Parameter(description = "Delivery type ID.", required = true)
                                  @PathParam("typeId") Short typeId) {

        DeliveryType deliveryType = deliveryTypeBean.getDeliveryType(typeId);

        if (deliveryType == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(deliveryType).build();
    }


    @Operation(description = "Create a new delivery type.", summary = "Create delivery type.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Delivery type was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createType(@RequestBody(
            description = "DTO object with delivery type with required name.",
            required = true, content = @Content(
            schema = @Schema(implementation = DeliveryType.class))) DeliveryType deliveryType) {

        if ((deliveryType.getName() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            deliveryType = deliveryTypeBean.createDeliveryType(deliveryType);
        }

        return Response.status(Response.Status.CONFLICT).entity(deliveryType).build();
    }


    @Operation(description = "Update an existing delivery type by ID.", summary = "Update delivery type by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery type was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{typeId}")
    public Response updateStatus(@Parameter(description = "Delivery type ID.", required = true)
                                      @PathParam("typeId") Short typeId,
                                      @RequestBody(
                                              description = "DTO object with delivery type with required name.",
                                              required = true, content = @Content(
                                              schema = @Schema(implementation = DeliveryType.class)))
                                              DeliveryType deliveryType) {

        deliveryType = deliveryTypeBean.updateDeliveryType(typeId, deliveryType);

        if (deliveryType == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(deliveryType).build();
    }


    @Operation(description = "Delete an existing delivery type by ID.", summary = "Delete delivery type by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery type was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{typeId}")
    public Response deleteType(@Parameter(description = "Delivery type ID.", required = true)
                                 @PathParam("typeId") Short typeId) {

        boolean deleted = deliveryTypeBean.deleteDeliveryType(typeId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
