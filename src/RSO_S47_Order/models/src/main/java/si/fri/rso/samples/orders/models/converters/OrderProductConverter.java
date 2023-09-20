package si.fri.rso.samples.orders.models.converters;

import si.fri.rso.samples.orders.lib.OrderProduct;
import si.fri.rso.samples.orders.models.entities.OrderProductEntity;

public class OrderProductConverter {
    public static OrderProduct toDto(OrderProductEntity entity) {

        OrderProduct dto = new OrderProduct();
        dto.setProductId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setQuantityLeft(entity.getQuantityLeft());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public static OrderProductEntity toEntity(OrderProduct dto) {

        OrderProductEntity entity = new OrderProductEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setQuantityLeft(dto.getQuantityLeft());
        entity.setPrice(dto.getPrice());

        return entity;
    }
}
