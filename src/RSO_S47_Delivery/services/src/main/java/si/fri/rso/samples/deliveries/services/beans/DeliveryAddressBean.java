package si.fri.rso.samples.deliveries.services.beans;

import javassist.compiler.ast.Pair;
import si.fri.rso.samples.deliveries.lib.DeliveryAddress;
import si.fri.rso.samples.deliveries.models.converters.DeliveryAddressConverter;
import si.fri.rso.samples.deliveries.models.entities.DeliveryAddressEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class DeliveryAddressBean {
    private Logger log = Logger.getLogger(DeliveryAddressBean.class.getName());

    @Inject
    private EntityManager em;

    public List<DeliveryAddress> getAllDeliveryAddress() {
        TypedQuery<DeliveryAddressEntity> query = em.createNamedQuery(
                "DeliveryAddressEntity.getAll", DeliveryAddressEntity.class);

        List<DeliveryAddressEntity> resultList = query.getResultList();

        return resultList.stream().map(DeliveryAddressConverter::toDto).collect(Collectors.toList());
    }

    public DeliveryAddress getDeliveryAddress(Long addressId) {
        DeliveryAddressEntity deliveryAddressEntity = em.find(DeliveryAddressEntity.class, addressId);

        if (deliveryAddressEntity == null) {
            throw new NotFoundException();
        }

        return DeliveryAddressConverter.toDto(deliveryAddressEntity);
    }

    public DeliveryAddress createDeliveryAddress(DeliveryAddress deliveryAddress) {
        DeliveryAddressEntity deliveryAddressEntity = DeliveryAddressConverter.toEntity(deliveryAddress);

        try {
            beginTx();
            em.persist(deliveryAddressEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (deliveryAddressEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return DeliveryAddressConverter.toDto(deliveryAddressEntity);
    }

    public DeliveryAddress updateDeliveryAddress(Long addressId, DeliveryAddress deliveryAddress) {
        DeliveryAddressEntity deliveryAddressEntity = em.find(DeliveryAddressEntity.class, addressId);

        if (deliveryAddressEntity == null) {
            return null;
        }

        DeliveryAddressEntity updatedDeliveryAddressEntity = DeliveryAddressConverter.toEntity(deliveryAddress);

        try {
            beginTx();
            updatedDeliveryAddressEntity.setId(deliveryAddressEntity.getId());
            updatedDeliveryAddressEntity = em.merge(updatedDeliveryAddressEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return DeliveryAddressConverter.toDto(updatedDeliveryAddressEntity);
    }

    public boolean deleteDeliveryAddress(Long addressId) {
        DeliveryAddressEntity deliveryAddressEntity = em.find(DeliveryAddressEntity.class, addressId);


        if (deliveryAddressEntity != null) {
            try {
                beginTx();
                em.remove(deliveryAddressEntity);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else {
            return false;
        }

        return true;
    }

    public String getLonLat(Long addressId, String type) {
        DeliveryAddressEntity deliveryAddressEntity = em.find(DeliveryAddressEntity.class, addressId);

        if (deliveryAddressEntity == null) {
            throw new NotFoundException();
        }

        if (type.equals("lat")) {
            return deliveryAddressEntity.getGeoLat();
        }
        return deliveryAddressEntity.getGeoLon();
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
