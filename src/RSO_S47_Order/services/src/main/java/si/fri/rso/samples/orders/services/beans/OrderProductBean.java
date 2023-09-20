package si.fri.rso.samples.orders.services.beans;

import si.fri.rso.samples.orders.lib.OrderProduct;
import si.fri.rso.samples.orders.lib.ShortOrderProduct;
import si.fri.rso.samples.orders.models.converters.OrderProductConverter;
import si.fri.rso.samples.orders.models.entities.OrderProductEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class OrderProductBean {
    private Logger log = Logger.getLogger(OrderProductBean.class.getName());

    @Inject
    private EntityManager em;

    public List<OrderProduct> getAllOrderProduct() {
        TypedQuery<OrderProductEntity> query = em.createNamedQuery(
                "OrderProductEntity.getAll", OrderProductEntity.class);
        List<OrderProductEntity> resultList = query.getResultList();

        return resultList.stream().map(OrderProductConverter::toDto).collect(Collectors.toList());
    }

    public OrderProduct getOrderProduct(Long productId) {
        OrderProductEntity orderProductEntity = em.find(OrderProductEntity.class, productId);

        if (orderProductEntity == null) {
            throw new NotFoundException();
        }

        return OrderProductConverter.toDto(orderProductEntity);
    }

    public OrderProduct createOrderProduct(OrderProduct orderProduct) {
        OrderProductEntity orderProductEntity = OrderProductConverter.toEntity(orderProduct);

        try {
            beginTx();
            em.persist(orderProductEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (orderProductEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return OrderProductConverter.toDto(orderProductEntity);
    }

    public OrderProduct updateOrderProduct(Long productId, OrderProduct orderProduct, ShortOrderProduct shortOrderProduct) {
        OrderProductEntity orderProductEntity = em.find(OrderProductEntity.class, productId);

        if (orderProductEntity == null) {
            return null;
        }

        OrderProductEntity updatedOrderProductEntity;

        if (shortOrderProduct != null) {
            updatedOrderProductEntity = orderProductEntity;
            updatedOrderProductEntity.setQuantityLeft(orderProductEntity.getQuantityLeft() - shortOrderProduct.getQuantity());
        } else {
            updatedOrderProductEntity = OrderProductConverter.toEntity(orderProduct);
        }

        try {
            beginTx();
            updatedOrderProductEntity.setId(orderProductEntity.getId());
            updatedOrderProductEntity = em.merge(updatedOrderProductEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return OrderProductConverter.toDto(updatedOrderProductEntity);
    }

    public boolean deleteOrderProduct(Long productId) {
        OrderProductEntity orderProductEntity = em.find(OrderProductEntity.class, productId);


        if (orderProductEntity != null) {
            try {
                beginTx();
                em.remove(orderProductEntity);
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
