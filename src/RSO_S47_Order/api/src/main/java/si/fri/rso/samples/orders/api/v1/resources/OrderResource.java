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
import si.fri.rso.samples.orders.lib.*;
import si.fri.rso.samples.orders.services.beans.OrderBean;
import si.fri.rso.samples.orders.services.beans.OrderProductBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.samples.orders.services.streaming.EventProducerImpl;

@Log
@ApplicationScoped
@Tag(name = "order", description = "Everything about orders")
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private Logger log = Logger.getLogger(OrderResource.class.getName());

    @Inject
    private OrderBean orderBean;

    @Inject
    private OrderProductBean orderProductBean;

    @Context
    protected UriInfo uriInfo;

    @Inject
    private EventProducerImpl eventProducer;

    @Operation(description = "Get a list of all orders.", summary = "Get all orders.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of orders.",
                    content = @Content(schema = @Schema(implementation = Order.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getOrders() {
        List<Order> orderList = orderBean.getAllOrder();

        for (Order order : orderList) {
            convertToShort(order);
        }

        return Response.status(Response.Status.OK).entity(orderList).build();
    }


    @Operation(description = "Get a chosen order by ID.", summary = "Get order by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order.",
                    content = @Content(schema = @Schema(implementation = Order.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{orderId}")
    public Response getOrderById(@Parameter(description = "Order ID.", required = true)
                                  @PathParam("orderId") Long orderId) {

        Order order = orderBean.getOrder(orderId);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        convertToShort(order);

        return Response.status(Response.Status.OK).entity(order).build();
    }


    @Operation(description = "Create a new order.", summary = "Create order.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Order was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createOrder(@RequestBody(
            description = "DTO object with required order productId and quantity (bigger than 0).",
            required = true, content = @Content(
            schema = @Schema(implementation = ShortOrderProduct.class))) ShortOrderProduct orderProduct) {

        if((orderProduct.getProductId() == null) || (orderProduct.getQuantity() == null) || (orderProduct.getQuantity() <= 0)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Order order = orderBean.createOrder(orderProduct);

        OrderProduct updatedOrderProduct = orderProductBean.updateOrderProduct(order.getOrderProductId(), null, orderProduct);

        if (updatedOrderProduct == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        convertToShort(order);

        return Response.status(Response.Status.CONFLICT).entity(order).build();
    }


    @Operation(description = "Update an existing order by ID.", summary = "Update order by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{orderId}")
    public Response updateOrder(@Parameter(description = "Order ID.", required = true)
                                       @PathParam("orderId") Long orderId,
                                       @RequestBody(
                                               description = "DTO object with existing order with new values.",
                                               required = true, content = @Content(
                                               schema = @Schema(implementation = Order.class)))
                                               Order order) {

        if (order.getQuantity() != null) {
            if (order.getQuantity() < 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if (order.getQuantity() == 0) {
                boolean deleted = orderBean.deleteOrder(orderId);

                if (deleted) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
            }
        }

        Integer quantityDiff = orderBean.getQuantityDiff(orderId, order);

        order = orderBean.updateOrder(orderId, order);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (quantityDiff != 0) {
            ShortOrderProduct orderProduct = new ShortOrderProduct();
            orderProduct.setProductId(order.getOrderProductId());
            orderProduct.setQuantity(quantityDiff);

            OrderProduct updatedOrderProduct = orderProductBean.updateOrderProduct(orderId, null, orderProduct);

            if (updatedOrderProduct == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        convertToShort(order);

        return Response.status(Response.Status.NOT_MODIFIED).entity(order).build();
    }


    @Operation(description = "Delete an existing order by ID.", summary = "Delete order by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{orderId}")
    public Response deleteOrder(@Parameter(description = "Order ID.", required = true)
                                       @PathParam("orderId") Long orderId) {

        boolean deleted = orderBean.deleteOrder(orderId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private void convertToShort(Order order) {
        ShortOrderStatus status = new ShortOrderStatus();
        status.setStatusId(order.getOrderStatusId());
        status.setLink(uriInfo.getBaseUri() + "orderStatus/" + order.getOrderStatusId());
        order.setOrderStatusId(null);

        ShortOrderProduct product = new ShortOrderProduct();
        product.setProductId(order.getOrderProductId());
        product.setQuantity(order.getQuantity());
        product.setLink(uriInfo.getBaseUri() + "orderProduct/" + order.getOrderProductId());
        order.setOrderProductId(null);
        order.setQuantity(null);

        order.setOrderStatus(status);
        order.setOrderProduct(product);
    }
}
