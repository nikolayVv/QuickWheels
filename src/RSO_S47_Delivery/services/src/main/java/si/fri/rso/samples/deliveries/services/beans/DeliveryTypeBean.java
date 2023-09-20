package si.fri.rso.samples.deliveries.services.beans;

import si.fri.rso.samples.deliveries.lib.DeliveryType;
import si.fri.rso.samples.deliveries.models.converters.DeliveryTypeConverter;
import si.fri.rso.samples.deliveries.models.entities.DeliveryTypeEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class DeliveryTypeBean {
    private Logger log = Logger.getLogger(DeliveryTypeBean.class.getName());

    @Inject
    private EntityManager em;

    public List<DeliveryType> getAllDeliveryType() {
        TypedQuery<DeliveryTypeEntity> query = em.createNamedQuery(
                "DeliveryTypeEntity.getAll", DeliveryTypeEntity.class);

        List<DeliveryTypeEntity> resultList = query.getResultList();

        return resultList.stream().map(DeliveryTypeConverter::toDto).collect(Collectors.toList());
    }

    public DeliveryType getDeliveryType(Short typeId) {
        DeliveryTypeEntity deliveryTypeEntity = em.find(DeliveryTypeEntity.class, typeId);

        if (deliveryTypeEntity == null) {
            throw new NotFoundException();
        }

        return DeliveryTypeConverter.toDto(deliveryTypeEntity);
    }

    public DeliveryType createDeliveryType(DeliveryType deliveryType) {
        DeliveryTypeEntity deliveryTypeEntity = DeliveryTypeConverter.toEntity(deliveryType);

        try {
            beginTx();
            em.persist(deliveryTypeEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (deliveryTypeEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return DeliveryTypeConverter.toDto(deliveryTypeEntity);
    }

    public DeliveryType updateDeliveryType(Short typeId, DeliveryType deliveryType) {
        DeliveryTypeEntity deliveryTypeEntity = em.find(DeliveryTypeEntity.class, typeId);

        if (deliveryTypeEntity == null) {
            return null;
        }

        DeliveryTypeEntity updatedDeliveryTypeEntity = DeliveryTypeConverter.toEntity(deliveryType);

        try {
            beginTx();
            updatedDeliveryTypeEntity.setId(deliveryTypeEntity.getId());
            updatedDeliveryTypeEntity = em.merge(updatedDeliveryTypeEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return DeliveryTypeConverter.toDto(updatedDeliveryTypeEntity);
    }

    public boolean deleteDeliveryType(Short typeId) {
        DeliveryTypeEntity deliveryTypeEntity = em.find(DeliveryTypeEntity.class, typeId);


        if (deliveryTypeEntity != null) {
            try {
                beginTx();
                em.remove(deliveryTypeEntity);
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
