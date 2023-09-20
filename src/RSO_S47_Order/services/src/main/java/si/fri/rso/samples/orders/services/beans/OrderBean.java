package si.fri.rso.samples.orders.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.eclipse.microprofile.metrics.annotation.Timed;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import javassist.compiler.ast.Pair;
import si.fri.rso.samples.orders.lib.Order;
import si.fri.rso.samples.orders.lib.OrderProduct;
import si.fri.rso.samples.orders.lib.ShortOrderProduct;
import si.fri.rso.samples.orders.models.converters.OrderConverter;
import si.fri.rso.samples.orders.models.converters.OrderProductConverter;
import si.fri.rso.samples.orders.models.entities.OrderEntity;
import si.fri.rso.samples.orders.models.entities.OrderProductEntity;
import si.fri.rso.samples.orders.models.entities.OrderStatusEntity;
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
public class OrderBean {

    private Logger log = Logger.getLogger(OrderBean.class.getName());

    @Inject
    private OrderBean orderBeanProxy;

    @Inject
    private EntityManager em;

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrl = "http://localhost:8080"; // only for demonstration
    }

    @Timed
    public List<Order> getAllOrder() {
        TypedQuery<OrderEntity> query = em.createNamedQuery(
                "OrderEntity.getAll", OrderEntity.class);
        List<OrderEntity> resultList = query.getResultList();

        return resultList.stream().map(OrderConverter::toDto).collect(Collectors.toList());
    }

    public Order getOrder(Long orderId) {
        OrderEntity orderEntity = em.find(OrderEntity.class, orderId);

        if (orderEntity == null) {
            throw new NotFoundException();
        }

        return OrderConverter.toDto(orderEntity);
    }

    public Order createOrder(ShortOrderProduct orderProduct) {
        OrderProductEntity orderProductEntity = em.find(OrderProductEntity.class, orderProduct.getProductId());

        if (orderProductEntity == null) {
            throw new NotFoundException();
        }
        if (orderProductEntity.getQuantityLeft() < orderProduct.getQuantity()) {
            throw new BadRequestException();
        }


        Order newOrder = new Order();
        String time = getCurrentTime();

        newOrder.setOrderProductId(orderProduct.getProductId());
        newOrder.setOrderStatusId((short) 1);
        newOrder.setCreatedAt(time);
        newOrder.setLastModified(time);
        newOrder.setQuantity(orderProduct.getQuantity());

        OrderEntity orderEntity = OrderConverter.toEntity(newOrder);

        try {
            beginTx();
            em.persist(orderEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        if (orderEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return OrderConverter.toDto(orderEntity);
    }

    public Order updateOrder(Long orderId, Order order) {
        OrderEntity orderEntity = em.find(OrderEntity.class, orderId);

        if (orderEntity == null) {
            return null;
        }

        OrderEntity updatedOrderEntity = OrderConverter.toEntity(order);

        if (updatedOrderEntity.getProduct() != null && !orderEntity.getProduct().equals(updatedOrderEntity.getProduct())) {
            OrderProductEntity orderProductEntity = em.find(OrderProductEntity.class, orderEntity.getProduct());

            if (orderProductEntity == null) {
                throw new NotFoundException();
            }
        }

        if (updatedOrderEntity.getStatus() != null && !orderEntity.getStatus().equals(updatedOrderEntity.getStatus())) {
            OrderStatusEntity orderStatusEntity = em.find(OrderStatusEntity.class, orderEntity.getStatus());

            if (orderStatusEntity == null) {
                throw new NotFoundException();
            }
        }

        if (!orderEntity.getQuantity().equals(updatedOrderEntity.getQuantity())) {
            if (orderEntity.getQuantity() < updatedOrderEntity.getQuantity()) {
                OrderProductEntity orderProductEntity = em.find(OrderProductEntity.class, orderEntity.getProduct());

                if (orderProductEntity.getQuantityLeft() < (updatedOrderEntity.getQuantity() - orderEntity.getQuantity())) {
                    throw new BadRequestException();
                }
            }
        }

        try {
            beginTx();
            updatedOrderEntity.setId(orderEntity.getId());
            updatedOrderEntity.setLastModified(getCurrentTime());
            updatedOrderEntity.setCreatedAt(orderEntity.getCreatedAt());
            updatedOrderEntity = em.merge(updatedOrderEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return OrderConverter.toDto(updatedOrderEntity);
    }

    public Integer getQuantityDiff(Long orderId, Order order) {
        OrderEntity orderEntity = em.find(OrderEntity.class, orderId);

        return order.getQuantity() - orderEntity.getQuantity();
    }

    public boolean deleteOrder(Long orderId) {
        OrderEntity orderEntity = em.find(OrderEntity.class, orderId);


        if (orderEntity != null) {
            try {
                beginTx();
                em.remove(orderEntity);
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

    public Integer getCommentCountFallback(Integer imageId) {
        return null;
    }

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
