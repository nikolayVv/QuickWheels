package si.fri.rso.samples.deliveries.models.converters;

import si.fri.rso.samples.deliveries.lib.Delivery;
import si.fri.rso.samples.deliveries.models.entities.DeliveryEntity;

public class DeliveryConverter {
    public static Delivery toDto(DeliveryEntity entity) {

        Delivery dto = new Delivery();
        dto.setDeliveryId(entity.getId());
        dto.setCustomerId(entity.getCustomer());
        dto.setFromAddressId(entity.getFromAddress());
        dto.setToAddressId(entity.getToAddress());
        dto.setTypeId(entity.getType());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLastModified(entity.getLastModified());
        dto.setGeoLat(entity.getGeoLat());
        dto.setGeoLon(entity.getGeoLon());
        dto.setTransportId(entity.getTransport());
        dto.setNote(entity.getNote());

        return dto;
    }

    public static DeliveryEntity toEntity(Delivery dto) {

        DeliveryEntity entity = new DeliveryEntity();
        entity.setCustomer(dto.getCustomerId());
        entity.setFromAddress(dto.getFromAddressId());
        entity.setToAddress(dto.getToAddressId());
        entity.setType(dto.getTypeId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setLastModified(dto.getLastModified());
        entity.setGeoLat(dto.getGeoLat());
        entity.setGeoLon(dto.getGeoLon());
        entity.setTransport(dto.getTransportId());
        entity.setNote(dto.getNote());

        return entity;
    }
}
