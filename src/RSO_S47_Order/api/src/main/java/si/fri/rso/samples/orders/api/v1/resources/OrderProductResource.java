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
import si.fri.rso.samples.orders.lib.OrderProduct;
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

@ApplicationScoped
@Tag(name = "product", description = "Everything about order products")
@Path("/orderProduct")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderProductResource {
    private Logger log = Logger.getLogger(OrderProductResource.class.getName());

    @Inject
    private OrderProductBean orderProductBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get a list of all order products.", summary = "Get all order products.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of order products.",
                    content = @Content(schema = @Schema(implementation = OrderProduct.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getProducts() {
        List<OrderProduct> orderProductList = orderProductBean.getAllOrderProduct();

        return Response.status(Response.Status.OK).entity(orderProductList).build();
    }


    @Operation(description = "Get a chosen order product by ID.", summary = "Get order product by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order product.",
                    content = @Content(schema = @Schema(implementation = OrderProduct.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{productId}")
    public Response getStatusById(@Parameter(description = "Order product ID.", required = true)
                                  @PathParam("productId") Long productId) {

        OrderProduct orderProduct = orderProductBean.getOrderProduct(productId);

        if (orderProduct == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(orderProduct).build();
    }


    @Operation(description = "Create a new order product.", summary = "Create order product.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Order product was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createProduct(@RequestBody(
            description = "DTO object with order product with required name, quantityLeft (bigger or equal 0) and price (bigger than 0.00).",
            required = true, content = @Content(
            schema = @Schema(implementation = OrderProduct.class))) OrderProduct orderProduct) {

        if ((orderProduct.getName() == null) || (orderProduct.getQuantityLeft() == null) || (orderProduct.getQuantityLeft() < 0) || (orderProduct.getPrice() == null) || (orderProduct.getPrice() <= 0)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            orderProduct = orderProductBean.createOrderProduct(orderProduct);
        }

        return Response.status(Response.Status.CONFLICT).entity(orderProduct).build();
    }


    @Operation(description = "Update an existing order product by ID.", summary = "Update order product by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order product was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{productId}")
    public Response updateProduct(@Parameter(description = "Order product ID.", required = true)
                                      @PathParam("productId") Long productId,
                                      @RequestBody(
                                              description = "DTO object with order product with required name, quantityLeft (bigger or equal 0) and price (bigger than 0.00).",
                                              required = true, content = @Content(
                                              schema = @Schema(implementation = OrderProduct.class)))
                                              OrderProduct orderProduct) {

        orderProduct = orderProductBean.updateOrderProduct(productId, orderProduct, null);

        if (orderProduct == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(orderProduct).build();
    }


    @Operation(description = "Delete an existing order product by ID.", summary = "Delete order product by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Order product was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{productId}")
    public Response deleteProduct(@Parameter(description = "Order product ID.", required = true)
                                      @PathParam("productId") Long productId) {

        boolean deleted = orderProductBean.deleteOrderProduct(productId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
