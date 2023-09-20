package si.fri.rso.samples.deliveries.api.v1.resources;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.samples.deliveries.services.clients.AmazonLocationClient;
import si.fri.rso.samples.deliveries.services.streaming.EventProducerImpl;

@Log
@ApplicationScoped
@Tag(name = "delivery", description = "Everything about deliveries")
@Path("/delivery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryResource {
    private Logger log = Logger.getLogger(DeliveryResource.class.getName());

    @Inject
    private AmazonLocationClient amazonLocationClient;

    @Inject
    private DeliveryBean deliveryBean;

    @Inject
    private DeliveryAddressBean deliveryAddressBean;

    @Inject
    private DeliveryCustomerBean deliveryCustomerBean;

    @Context
    protected UriInfo uriInfo;

    @Inject
    private EventProducerImpl eventProducer;

    @Operation(description = "Get a list of all deliveries.", summary = "Get all deliveries.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of deliveries.",
                    content = @Content(schema = @Schema(implementation = Delivery.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list.")}
            ),
    })
    @GET
    public Response getDeliveries() {
        List<Delivery> deliveryList = deliveryBean.getAllDelivery();

        for (Delivery delivery : deliveryList) {
            convertToShort(delivery);
        }

        return Response.status(Response.Status.OK).entity(deliveryList).build();
    }

    @Timed(name="timed_getting_delivery_by_id")
    @Operation(description = "Get a chosen delivery by ID.", summary = "Get delivery by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery.",
                    content = @Content(schema = @Schema(implementation = Delivery.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @GET
    @Path("/{deliveryId}")
    public Response getDeliveryById(@Parameter(description = "Delivery ID.", required = true)
                                 @PathParam("deliveryId") Long deliveryId) {

        Delivery delivery = deliveryBean.getDelivery(deliveryId);

        if (delivery == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        convertToShort(delivery);

        return Response.status(Response.Status.OK).entity(delivery).build();
    }


    @Operation(description = "Create a new delivery.", summary = "Create delivery.")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Delivery was successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Bad Request."
            )
    })
    @POST
    public Response createDelivery(@RequestBody(
            description = "DTO object with required delivery customerId, fromAddressId, toAddressId, typeId, transportId.",
            required = true, content = @Content(
            schema = @Schema(implementation = Delivery.class))) Delivery delivery) {

        if((delivery.getCustomerId() == null) || (delivery.getFromAddressId() == null) || (delivery.getToAddress() == null) || (delivery.getTypeId() == null) || (delivery.getTransportId() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        delivery = deliveryBean.createDelivery(delivery);

        if (delivery == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        convertToShort(delivery);

        return Response.status(Response.Status.CONFLICT).entity(delivery).build();
    }


    @Operation(description = "Update an existing delivery by ID.", summary = "Update delivery by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery was successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @PUT
    @Path("/{deliveryId}")
    public Response updateDelivery(@Parameter(description = "Delivery ID.", required = true)
                                @PathParam("deliveryId") Long deliveryId,
                                @RequestBody(
                                        description = "DTO object with required delivery customerId, fromAddressId, toAddressId, typeId, transportId.",
                                        required = true, content = @Content(
                                        schema = @Schema(implementation = Delivery.class)))
                                        Delivery delivery) {

        delivery = deliveryBean.updateDelivery(deliveryId, delivery);
        if (delivery == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        convertToShort(delivery);

        return Response.status(Response.Status.NOT_MODIFIED).entity(delivery).build();
    }


    @Operation(description = "Delete an existing delivery by ID.", summary = "Delete delivery by ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delivery was successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("/{deliveryId}")
    public Response deleteDelivery(@Parameter(description = "Delivery ID.", required = true)
                                @PathParam("deliveryId") Long deliveryId) {

        boolean deleted = deliveryBean.deleteDelivery(deliveryId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    private void convertToShort(Delivery delivery) {
        ShortDeliveryStatus status = new ShortDeliveryStatus();
        status.setStatusId(delivery.getStatusId());
        status.setLink(uriInfo.getBaseUri() + "deliveryStatus/" + delivery.getStatusId());
        delivery.setStatusId(null);

        ShortDeliveryType type = new ShortDeliveryType();
        type.setTypeId(delivery.getTypeId());
        type.setLink(uriInfo.getBaseUri() + "deliveryType/" + delivery.getTypeId());
        delivery.setTypeId(null);

        ShortDeliveryTransport transport = new ShortDeliveryTransport();
        transport.setTransportId(delivery.getTransportId());
        transport.setLink(uriInfo.getBaseUri() + "deliveryTransport/" + delivery.getTransportId());
        delivery.setTransportId(null);

        ShortDeliveryCustomer customer = new ShortDeliveryCustomer();
        customer.setCustomerId(delivery.getCustomerId());
        customer.setFullName(deliveryCustomerBean.getFullName(delivery.getCustomerId()));
        customer.setLink(uriInfo.getBaseUri() + "deliveryCustomer/" + delivery.getCustomerId());
        delivery.setCustomerId(null);

        ShortDeliveryAddress fromAddress = new ShortDeliveryAddress();
        fromAddress.setAddressId(delivery.getFromAddressId());
        fromAddress.setGeoLat(deliveryAddressBean.getLonLat(delivery.getFromAddressId(), "lat"));
        fromAddress.setGeoLon(deliveryAddressBean.getLonLat(delivery.getFromAddressId(), "lon"));
        fromAddress.setLink(uriInfo.getBaseUri() + "deliveryAddress/" + delivery.getFromAddressId());
        delivery.setFromAddressId(null);

        ShortDeliveryAddress toAddress = new ShortDeliveryAddress();
        toAddress.setAddressId(delivery.getToAddressId());
        toAddress.setGeoLat(deliveryAddressBean.getLonLat(delivery.getToAddressId(), "lat"));
        toAddress.setGeoLon(deliveryAddressBean.getLonLat(delivery.getToAddressId(), "lon"));
        toAddress.setLink(uriInfo.getBaseUri() + "deliveryAddress/" + delivery.getToAddressId());
        delivery.setToAddressId(null);

        delivery.setStatus(status);
        delivery.setType(type);
        delivery.setTransport(transport);
        delivery.setCustomer(customer);
        delivery.setFromAddress(fromAddress);
        delivery.setToAddress(toAddress);
    }
}
