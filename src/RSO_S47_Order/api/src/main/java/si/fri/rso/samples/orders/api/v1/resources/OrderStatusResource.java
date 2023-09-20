package si.fri.rso.samples.orders.api.v1.resources;

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
import si.fri.rso.samples.orders.lib.OrderStatus;
import si.fri.rso.samples.orders.services.beans.OrderStatusBean;

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
@Tag(name = "status", description = "Everything about order statuses")
@Path("/orderStatus")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderStatusResource {
    private Logger log = Logger.getLogger(OrderStatusResource.class.getName());

    @Inject
    private OrderStatusBean orderStatusBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get a list of all order statuses.", summary = "Get all order statuses.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of order statuses.",
                    content = @Content(schema = @Schema(implementation = OrderStatus.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getStatuses() {
        List<OrderStatus> orderStatusList = orderStatusBean.getAllOrderStatus();

        return Response.status(Response.Status.OK).entity(orderStatusList).build();
    }


    @Operation(description = "Get a chosen order status by ID.", summary = "Get order status by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order status.",
                    content = @Content(schema = @Schema(implementation = OrderStatus.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{statusId}")
    public Response getStatusById(@Parameter(description = "Order status ID.", required = true)
                                  @PathParam("statusId") Short statusId) {

        OrderStatus orderStatus = orderStatusBean.getOrderStatus(statusId);

        if (orderStatus == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(orderStatus).build();
    }


    @Operation(description = "Create a new order status.", summary = "Create order status.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Order status was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createStatus(@RequestBody(
            description = "DTO object with order status with required name.",
            required = true, content = @Content(
            schema = @Schema(implementation = OrderStatus.class))) OrderStatus orderStatus) {

        if ((orderStatus.getName() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            orderStatus = orderStatusBean.createOrderStatus(orderStatus);
        }

        return Response.status(Response.Status.CONFLICT).entity(orderStatus).build();
    }


    @Operation(description = "Update an existing order status by ID.", summary = "Update order status by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order status was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{statusId}")
    public Response updateStatus(@Parameter(description = "Order status ID.", required = true)
                                 @PathParam("statusId") Short statusId,
                                 @RequestBody(
                                         description = "DTO object with order status with required name.",
                                         required = true, content = @Content(
                                         schema = @Schema(implementation = OrderStatus.class)))
                                         OrderStatus orderStatus) {

        orderStatus = orderStatusBean.updateOrderStatus(statusId, orderStatus);

        if (orderStatus == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(orderStatus).build();
    }


    @Operation(description = "Delete an existing order status by ID.", summary = "Delete order status by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order status was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{statusId}")
    public Response deleteStatus(@Parameter(description = "Order status ID.", required = true)
                                      @PathParam("statusId") Short statusId) {

        boolean deleted = orderStatusBean.deleteOrderStatus(statusId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
