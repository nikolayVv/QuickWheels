package si.fri.rso.samples.deliveries.services.beans;


import si.fri.rso.samples.deliveries.lib.DeliveryTransport;
import si.fri.rso.samples.deliveries.models.converters.DeliveryTransportConverter;
import si.fri.rso.samples.deliveries.models.entities.DeliveryTransportEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class DeliveryTransportBean {
    private Logger log = Logger.getLogger(DeliveryTransportBean.class.getName());

    @Inject
    private EntityManager em;

    public List<DeliveryTransport> getAllDeliveryTransport() {
        TypedQuery<DeliveryTransportEntity> query = em.createNamedQuery(
                "DeliveryTransportEntity.getAll", DeliveryTransportEntity.class);

        List<DeliveryTransportEntity> resultList = query.getResultList();

        return resultList.stream().map(DeliveryTransportConverter::toDto).collect(Collectors.toList());
    }

    public DeliveryTransport getDeliveryTransport(Short transportId) {
        DeliveryTransportEntity deliveryTransportEntity = em.find(DeliveryTransportEntity.class, transportId);

        if (deliveryTransportEntity == null) {
            throw new NotFoundException();
        }

        return DeliveryTransportConverter.toDto(deliveryTransportEntity);
    }

    public DeliveryTransport createDeliveryTransport(DeliveryTransport deliveryTransport) {
        DeliveryTransportEntity deliveryTransportEntity = DeliveryTransportConverter.toEntity(deliveryTransport);

        try {
            beginTx();
            em.persist(deliveryTransportEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (deliveryTransportEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return DeliveryTransportConverter.toDto(deliveryTransportEntity);
    }

    public DeliveryTransport updateDeliveryTransport(Short transportId, DeliveryTransport deliveryTransport) {
        DeliveryTransportEntity deliveryTransportEntity = em.find(DeliveryTransportEntity.class, transportId);

        if (deliveryTransportEntity == null) {
            return null;
        }

        DeliveryTransportEntity updatedDeliveryTransportEntity = DeliveryTransportConverter.toEntity(deliveryTransport);

        try {
            beginTx();
            updatedDeliveryTransportEntity.setId(deliveryTransportEntity.getId());
            updatedDeliveryTransportEntity = em.merge(updatedDeliveryTransportEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return DeliveryTransportConverter.toDto(updatedDeliveryTransportEntity);
    }

    public boolean deleteDeliveryTransport(Short transportId) {
        DeliveryTransportEntity deliveryTransportEntity = em.find(DeliveryTransportEntity.class, transportId);


        if (deliveryTransportEntity != null) {
            try {
                beginTx();
                em.remove(deliveryTransportEntity);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else {
            return false;
        }

        return true;
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
