package si.fri.rso.samples.deliveries.services.beans;

import si.fri.rso.samples.deliveries.lib.DeliveryStatus;
import si.fri.rso.samples.deliveries.models.converters.DeliveryStatusConverter;
import si.fri.rso.samples.deliveries.models.entities.DeliveryStatusEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class DeliveryStatusBean {
    private Logger log = Logger.getLogger(DeliveryStatusBean.class.getName());

    @Inject
    private EntityManager em;

    public List<DeliveryStatus> getAllDeliveryStatus() {
        TypedQuery<DeliveryStatusEntity> query = em.createNamedQuery(
                "DeliveryStatusEntity.getAll", DeliveryStatusEntity.class);

        List<DeliveryStatusEntity> resultList = query.getResultList();

        return resultList.stream().map(DeliveryStatusConverter::toDto).collect(Collectors.toList());
    }

    public DeliveryStatus getDeliveryStatus(Short statusId) {
        DeliveryStatusEntity deliveryStatusEntity = em.find(DeliveryStatusEntity.class, statusId);

        if (deliveryStatusEntity == null) {
            throw new NotFoundException();
        }

        return DeliveryStatusConverter.toDto(deliveryStatusEntity);
    }

    public DeliveryStatus createDeliveryStatus(DeliveryStatus deliveryStatus) {
        DeliveryStatusEntity deliveryStatusEntity = DeliveryStatusConverter.toEntity(deliveryStatus);

        try {
            beginTx();
            em.persist(deliveryStatusEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (deliveryStatusEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return DeliveryStatusConverter.toDto(deliveryStatusEntity);
    }

    public DeliveryStatus updateDeliveryStatus(Short statusId, DeliveryStatus deliveryStatus) {
        DeliveryStatusEntity deliveryStatusEntity = em.find(DeliveryStatusEntity.class, statusId);

        if (deliveryStatusEntity == null) {
            return null;
        }

        DeliveryStatusEntity updatedDeliveryStatusEntity = DeliveryStatusConverter.toEntity(deliveryStatus);

        try {
            beginTx();
            updatedDeliveryStatusEntity.setId(deliveryStatusEntity.getId());
            updatedDeliveryStatusEntity = em.merge(updatedDeliveryStatusEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return DeliveryStatusConverter.toDto(updatedDeliveryStatusEntity);
    }

    public boolean deleteDeliveryStatus(Short statusId) {
        DeliveryStatusEntity deliveryStatusEntity = em.find(DeliveryStatusEntity.class, statusId);


        if (deliveryStatusEntity != null) {
            try {
                beginTx();
                em.remove(deliveryStatusEntity);
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
