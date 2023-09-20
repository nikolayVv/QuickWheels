package si.fri.rso.samples.orders.services.beans;

import si.fri.rso.samples.orders.lib.OrderStatus;
import si.fri.rso.samples.orders.models.converters.OrderStatusConverter;
import si.fri.rso.samples.orders.models.entities.OrderStatusEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.io.NotActiveException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class OrderStatusBean {
    private Logger log = Logger.getLogger(OrderStatusBean.class.getName());

    @Inject
    private EntityManager em;

    public List<OrderStatus> getAllOrderStatus() {
        TypedQuery<OrderStatusEntity> query = em.createNamedQuery(
                "OrderStatusEntity.getAll", OrderStatusEntity.class);

        List<OrderStatusEntity> resultList = query.getResultList();

        return resultList.stream().map(OrderStatusConverter::toDto).collect(Collectors.toList());
    }

    public OrderStatus getOrderStatus(Short orderId) {
        OrderStatusEntity orderStatusEntity = em.find(OrderStatusEntity.class, orderId);

        if (orderStatusEntity == null) {
            throw new NotFoundException();
        }

        return OrderStatusConverter.toDto(orderStatusEntity);
    }

    public OrderStatus createOrderStatus(OrderStatus orderStatus) {
        OrderStatusEntity orderStatusEntity = OrderStatusConverter.toEntity(orderStatus);

        try {
            beginTx();
            em.persist(orderStatusEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (orderStatusEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return OrderStatusConverter.toDto(orderStatusEntity);
    }

    public OrderStatus updateOrderStatus(Short orderId, OrderStatus orderStatus) {
        OrderStatusEntity orderStatusEntity = em.find(OrderStatusEntity.class, orderId);

        if (orderStatusEntity == null) {
            return null;
        }

        OrderStatusEntity updatedOrderStatusEntity = OrderStatusConverter.toEntity(orderStatus);

        try {
            beginTx();
            updatedOrderStatusEntity.setId(orderStatusEntity.getId());
            updatedOrderStatusEntity = em.merge(updatedOrderStatusEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return OrderStatusConverter.toDto(updatedOrderStatusEntity);
    }

    public boolean deleteOrderStatus(Short statusId) {
        OrderStatusEntity orderStatusEntity = em.find(OrderStatusEntity.class, statusId);


        if (orderStatusEntity != null) {
            try {
                beginTx();
                em.remove(orderStatusEntity);
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
