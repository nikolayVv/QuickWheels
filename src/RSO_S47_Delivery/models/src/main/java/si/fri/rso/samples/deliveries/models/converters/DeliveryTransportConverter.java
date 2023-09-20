package si.fri.rso.samples.deliveries.models.converters;


import si.fri.rso.samples.deliveries.lib.DeliveryTransport;
import si.fri.rso.samples.deliveries.models.entities.DeliveryTransportEntity;

public class DeliveryTransportConverter {
    public static DeliveryTransport toDto(DeliveryTransportEntity entity) {

        DeliveryTransport dto = new DeliveryTransport();
        dto.setTransportId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public static DeliveryTransportEntity toEntity(DeliveryTransport dto) {

        DeliveryTransportEntity entity = new DeliveryTransportEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }
}
