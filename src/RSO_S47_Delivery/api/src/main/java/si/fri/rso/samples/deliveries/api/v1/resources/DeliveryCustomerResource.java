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
import si.fri.rso.samples.deliveries.lib.DeliveryCustomer;
import si.fri.rso.samples.deliveries.services.beans.DeliveryCustomerBean;

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
@Tag(name = "customer", description = "Everything about delivery customers")
@Path("/deliveryCustomer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryCustomerResource {
    private Logger log = Logger.getLogger(DeliveryCustomerResource.class.getName());

    @Inject
    private DeliveryCustomerBean deliveryCustomerBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get a list of all delivery customers.", summary = "Get all delivery customers.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of delivery customers.",
                    content = @Content(schema = @Schema(implementation = DeliveryCustomer.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getCustomers() {
        List<DeliveryCustomer> deliveryCustomerList = deliveryCustomerBean.getAllDeliveryCustomer();

        return Response.status(Response.Status.OK).entity(deliveryCustomerList).build();
    }


    @Operation(description = "Get a chosen delivery customer by ID.", summary = "Get delivery customer by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery customer.",
                    content = @Content(schema = @Schema(implementation = DeliveryCustomer.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{customerId}")
    public Response getCustomerById(@Parameter(description = "Delivery customerId ID.", required = true)
                                @PathParam("customerId") Long customerId) {

        DeliveryCustomer deliveryCustomer = deliveryCustomerBean.getDeliveryCustomer(customerId);

        if (deliveryCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(deliveryCustomer).build();
    }


    @Operation(description = "Create a new delivery customer.", summary = "Create delivery customer.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Delivery customer was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createCustomer(@RequestBody(
            description = "DTO object with delivery customer with required name, surname, emailAddress, phoneNumber, dateOfBirth.",
            required = true, content = @Content(
            schema = @Schema(implementation = DeliveryCustomer.class))) DeliveryCustomer deliveryCustomer) {

        if ((deliveryCustomer.getName() == null) || (deliveryCustomer.getSurname() == null) || (deliveryCustomer.getEmailAddress() == null) || (deliveryCustomer.getPhoneNumber() == null) || (deliveryCustomer.getDateOfBirth() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            deliveryCustomer = deliveryCustomerBean.createDeliveryCustomer(deliveryCustomer);
        }

        return Response.status(Response.Status.CONFLICT).entity(deliveryCustomer).build();
    }


    @Operation(description = "Update an existing delivery customer by ID.", summary = "Update delivery customer by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery customer was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{customerId}")
    public Response updateCustomer(@Parameter(description = "Delivery customer ID.", required = true)
                                 @PathParam("customerId") Long customerId,
                                 @RequestBody(
                                         description = "DTO object with delivery customer with required name, surname, emailAddress, phoneNumber, dateOfBirth.",
                                         required = true, content = @Content(
                                         schema = @Schema(implementation = DeliveryCustomer.class)))
                                         DeliveryCustomer deliveryCustomer) {

        deliveryCustomer = deliveryCustomerBean.updateDeliveryCustomer(customerId, deliveryCustomer);

        if (deliveryCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(deliveryCustomer).build();
    }


    @Operation(description = "Delete an existing delivery customer by ID.", summary = "Delete delivery customer by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery customer was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{customerId}")
    public Response deleteCustomer(@Parameter(description = "Delivery customer ID.", required = true)
                               @PathParam("customerId") Long customerId) {

        boolean deleted = deliveryCustomerBean.deleteDeliveryCustomer(customerId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
