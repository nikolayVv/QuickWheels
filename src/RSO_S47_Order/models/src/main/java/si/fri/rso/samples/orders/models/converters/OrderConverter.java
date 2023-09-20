package si.fri.rso.samples.orders.models.converters;


import si.fri.rso.samples.orders.lib.Order;
import si.fri.rso.samples.orders.models.entities.OrderEntity;


public class OrderConverter {

    public static Order toDto(OrderEntity entity) {

        Order dto = new Order();
        dto.setOrderId(entity.getId());
        dto.setOrderProductId(entity.getProduct());
        dto.setOrderStatusId(entity.getStatus());
        dto.setOrderDeliveryId(entity.getDelivery());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModified(entity.getLastModified());
        dto.setQuantity(entity.getQuantity());

        return dto;

    }

    public static OrderEntity toEntity(Order dto) {

        OrderEntity entity = new OrderEntity();
        entity.setProduct(dto.getOrderProductId());
        entity.setStatus(dto.getOrderStatusId());
        entity.setDelivery(dto.getOrderDeliveryId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setLastModified(dto.getLastModified());
        entity.setQuantity(dto.getQuantity());

        return entity;

    }
}
