package si.fri.rso.samples.orders.models.converters;

import si.fri.rso.samples.orders.lib.OrderStatus;
import si.fri.rso.samples.orders.models.entities.OrderStatusEntity;

public class OrderStatusConverter {
    public static OrderStatus toDto(OrderStatusEntity entity) {

        OrderStatus dto = new OrderStatus();
        dto.setStatusId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    public static OrderStatusEntity toEntity(OrderStatus dto) {

        OrderStatusEntity entity = new OrderStatusEntity();
        entity.setName(dto.getName());

        return entity;
    }
}
