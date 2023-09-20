package si.fri.rso.samples.deliveries.services.beans;

import si.fri.rso.samples.deliveries.lib.DeliveryCustomer;
import si.fri.rso.samples.deliveries.models.converters.DeliveryCustomerConverter;
import si.fri.rso.samples.deliveries.models.entities.DeliveryCustomerEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class DeliveryCustomerBean {
    private Logger log = Logger.getLogger(DeliveryCustomerBean.class.getName());

    @Inject
    private EntityManager em;

    public List<DeliveryCustomer> getAllDeliveryCustomer() {
        TypedQuery<DeliveryCustomerEntity> query = em.createNamedQuery(
                "DeliveryCustomerEntity.getAll", DeliveryCustomerEntity.class);

        List<DeliveryCustomerEntity> resultList = query.getResultList();

        return resultList.stream().map(DeliveryCustomerConverter::toDto).collect(Collectors.toList());
    }

    public DeliveryCustomer getDeliveryCustomer(Long customerId) {
        DeliveryCustomerEntity deliveryCustomerEntity = em.find(DeliveryCustomerEntity.class, customerId);

        if (deliveryCustomerEntity == null) {
            throw new NotFoundException();
        }

        return DeliveryCustomerConverter.toDto(deliveryCustomerEntity);
    }

    public DeliveryCustomer createDeliveryCustomer(DeliveryCustomer deliveryCustomer) {
        DeliveryCustomerEntity deliveryCustomerEntity = DeliveryCustomerConverter.toEntity(deliveryCustomer);

        try {
            beginTx();
            em.persist(deliveryCustomerEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (deliveryCustomerEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return DeliveryCustomerConverter.toDto(deliveryCustomerEntity);
    }

    public DeliveryCustomer updateDeliveryCustomer(Long customerId, DeliveryCustomer deliveryCustomer) {
        DeliveryCustomerEntity deliveryCustomerEntity = em.find(DeliveryCustomerEntity.class, customerId);

        if (deliveryCustomerEntity == null) {
            return null;
        }

        DeliveryCustomerEntity updatedDeliveryCustomerEntity = DeliveryCustomerConverter.toEntity(deliveryCustomer);

        try {
            beginTx();
            updatedDeliveryCustomerEntity.setId(deliveryCustomerEntity.getId());
            updatedDeliveryCustomerEntity = em.merge(updatedDeliveryCustomerEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return DeliveryCustomerConverter.toDto(updatedDeliveryCustomerEntity);
    }

    public boolean deleteDeliveryCustomer(Long customerId) {
        DeliveryCustomerEntity deliveryCustomerEntity = em.find(DeliveryCustomerEntity.class, customerId);


        if (deliveryCustomerEntity != null) {
            try {
                beginTx();
                em.remove(deliveryCustomerEntity);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else {
            return false;
        }

        return true;
    }

    public String getFullName(Long customerId) {
        DeliveryCustomerEntity deliveryCustomerEntity = em.find(DeliveryCustomerEntity.class, customerId);

        if (deliveryCustomerEntity == null) {
            throw new NotFoundException();
        }

        return deliveryCustomerEntity.getName() + " " + deliveryCustomerEntity.getSurname();
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
