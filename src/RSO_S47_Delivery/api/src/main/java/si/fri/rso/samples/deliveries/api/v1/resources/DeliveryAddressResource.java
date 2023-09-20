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
import si.fri.rso.samples.deliveries.lib.DeliveryAddress;
import si.fri.rso.samples.deliveries.services.beans.DeliveryAddressBean;

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
@Tag(name = "address", description = "Everything about delivery addresses")
@Path("/deliveryAddress")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryAddressResource {
    private Logger log = Logger.getLogger(DeliveryAddressResource.class.getName());

    @Inject
    private DeliveryAddressBean deliveryAddressBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get a list of all delivery addresses.", summary = "Get all delivery addresses.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of delivery addresses.",
                    content = @Content(schema = @Schema(implementation = DeliveryAddress.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getAddress() {
        List<DeliveryAddress> deliveryAddressList = deliveryAddressBean.getAllDeliveryAddress();

        return Response.status(Response.Status.OK).entity(deliveryAddressList).build();
    }


    @Operation(description = "Get a chosen delivery address by ID.", summary = "Get delivery address by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery address.",
                    content = @Content(schema = @Schema(implementation = DeliveryAddress.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{addressId}")
    public Response getAddressById(@Parameter(description = "Delivery address ID.", required = true)
                                @PathParam("addressId") Long addressId) {

        DeliveryAddress deliveryAddress = deliveryAddressBean.getDeliveryAddress(addressId);

        if (deliveryAddress == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(deliveryAddress).build();
    }


    @Operation(description = "Create a new delivery address.", summary = "Create delivery address.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Delivery address was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createAddress(@RequestBody(
            description = "DTO object with delivery address with required zipCode, street, city, country, geoLat and geoLon.",
            required = true, content = @Content(
            schema = @Schema(implementation = DeliveryAddress.class))) DeliveryAddress deliveryAddress) {

        if ((deliveryAddress.getZipCode() == null) || (deliveryAddress.getStreet() == null) || (deliveryAddress.getCity() == null) || (deliveryAddress.getCountry() == null) || (deliveryAddress.getGeoLat() == null) || (deliveryAddress.getGeoLon() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            deliveryAddress = deliveryAddressBean.createDeliveryAddress(deliveryAddress);
        }

        return Response.status(Response.Status.CONFLICT).entity(deliveryAddress).build();
    }


    @Operation(description = "Update an existing delivery address by ID.", summary = "Update delivery address by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery address was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{addressId}")
    public Response updateStatus(@Parameter(description = "Delivery address ID.", required = true)
                                 @PathParam("addressId") Long addressId,
                                 @RequestBody(
                                         description = "DTO object with delivery address with required zipCode, street, city, country, geoLat and geoLon.",
                                         required = true, content = @Content(
                                         schema = @Schema(implementation = DeliveryAddress.class)))
                                         DeliveryAddress deliveryAddress) {

        deliveryAddress = deliveryAddressBean.updateDeliveryAddress(addressId, deliveryAddress);

        if (deliveryAddress == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).entity(deliveryAddress).build();
    }


    @Operation(description = "Delete an existing delivery address by ID.", summary = "Delete delivery address by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery address was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{addressId}")
    public Response deleteType(@Parameter(description = "Delivery address ID.", required = true)
                               @PathParam("addressId") Long addressId) {

        boolean deleted = deliveryAddressBean.deleteDeliveryAddress(addressId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
