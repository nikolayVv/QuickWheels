package si.fri.rso.samples.deliveries.services.beans;

import si.fri.rso.samples.deliveries.lib.Delivery;
import si.fri.rso.samples.deliveries.models.converters.DeliveryConverter;
import si.fri.rso.samples.deliveries.models.entities.*;
import org.eclipse.microprofile.metrics.annotation.Timed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.time.temporal.ChronoUnit;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
@RequestScoped
public class DeliveryBean {
    private Logger log = Logger.getLogger(DeliveryBean.class.getName());

    @Inject
    private DeliveryBean deliveryBeanProxy;

    @Inject
    private EntityManager em;

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrl = "http://localhost:8081"; // only for demonstration
    }

    @Timed
    public List<Delivery> getAllDelivery() {
        TypedQuery<DeliveryEntity> query = em.createNamedQuery(
                "DeliveryEntity.getAll", DeliveryEntity.class);
        List<DeliveryEntity> resultList = query.getResultList();

        return resultList.stream().map(DeliveryConverter::toDto).collect(Collectors.toList());
    }

    public Delivery getDelivery(Long deliveryId) {
        DeliveryEntity deliveryEntity = em.find(DeliveryEntity.class, deliveryId);

        if (deliveryEntity == null) {
            throw new NotFoundException();
        }

        return DeliveryConverter.toDto(deliveryEntity);
    }

    public Delivery createDelivery(Delivery delivery) {
        DeliveryCustomerEntity deliveryCustomerEntity = em.find(DeliveryCustomerEntity.class, delivery.getCustomerId());
        DeliveryAddressEntity deliveryFromAddressEntity = em.find(DeliveryAddressEntity.class, delivery.getFromAddressId());
        DeliveryAddressEntity deliveryToAddressEntity = em.find(DeliveryAddressEntity.class, delivery.getToAddressId());
        DeliveryTypeEntity deliveryTypeEntity = em.find(DeliveryTypeEntity.class, delivery.getTypeId());
        DeliveryTransportEntity deliveryTransportEntity = em.find(DeliveryTransportEntity.class, delivery.getTransportId());

        if ((deliveryCustomerEntity == null) || (deliveryFromAddressEntity == null) || (deliveryToAddressEntity == null) || (deliveryTypeEntity == null) || (deliveryTransportEntity == null)) {
            throw new NotFoundException();
        }

        String time = getCurrentTime();

        DeliveryEntity deliveryEntity = DeliveryConverter.toEntity(delivery);
        delivery.setGeoLon(deliveryFromAddressEntity.getGeoLon());
        delivery.setGeoLat(deliveryFromAddressEntity.getGeoLat());
        delivery.setCreatedAt(time);
        delivery.setLastModified(time);

        try {
            beginTx();
            em.persist(deliveryEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (deliveryEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return DeliveryConverter.toDto(deliveryEntity);
    }

    public Delivery updateDelivery(Long deliveryId, Delivery delivery) {
        DeliveryEntity deliveryEntity = em.find(DeliveryEntity.class, deliveryId);

        if (deliveryEntity == null) {
            return null;
        }

        DeliveryEntity updatedDeliveryEntity = DeliveryConverter.toEntity(delivery);

        if (updatedDeliveryEntity.getCustomer() != null) {
            updatedDeliveryEntity.setCustomer(deliveryEntity.getCustomer());
        }
        if (updatedDeliveryEntity.getFromAddress() != null) {
            updatedDeliveryEntity.setFromAddress(deliveryEntity.getFromAddress());
        }
        if (updatedDeliveryEntity.getToAddress() != null) {
            updatedDeliveryEntity.setToAddress(deliveryEntity.getToAddress());
        }
        if (updatedDeliveryEntity.getType() != null) {
            updatedDeliveryEntity.setType(deliveryEntity.getType());
        }
        if (updatedDeliveryEntity.getTransport() != null) {
            updatedDeliveryEntity.setTransport(deliveryEntity.getTransport());
        }

        try {
            beginTx();
            updatedDeliveryEntity.setId(deliveryEntity.getId());
            updatedDeliveryEntity.setLastModified(getCurrentTime());
            updatedDeliveryEntity.setCreatedAt(deliveryEntity.getCreatedAt());
            updatedDeliveryEntity = em.merge(updatedDeliveryEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return DeliveryConverter.toDto(updatedDeliveryEntity);
    }

    public boolean deleteDelivery(Long deliveryId) {
        DeliveryEntity deliveryEntity = em.find(DeliveryEntity.class, deliveryId);


        if (deliveryEntity != null) {
            try {
                beginTx();
                em.remove(deliveryEntity);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else {
            return false;
        }

        return true;
    }

    //    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    //    @CircuitBreaker(requestVolumeThreshold = 3)
    //    @Fallback(fallbackMethod = "getCommentCountFallback")
    //    public Integer getCommentCount(Integer imageId) {
    //
    //        log.info("Calling comments service: getting comment count.");
    //
    //        try {
    //            return httpClient
    //                    .target(baseUrl + "/v1/comments/count")
    //                    .queryParam("imageId", imageId)
    //                    .request().get(new GenericType<Integer>() {
    //                    });
    //        }
    //        catch (WebApplicationException | ProcessingException e) {
    //            log.severe(e.getMessage());
    //            throw new InternalServerErrorException(e);
    //        }
    //    }

    private String getCurrentTime() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        return time.format(formatter);
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
