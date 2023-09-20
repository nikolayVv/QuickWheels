package si.fri.rso.samples.deliveries.models.converters;

import si.fri.rso.samples.deliveries.lib.DeliveryStatus;
import si.fri.rso.samples.deliveries.models.entities.DeliveryStatusEntity;

public class DeliveryStatusConverter {
    public static DeliveryStatus toDto(DeliveryStatusEntity entity) {

        DeliveryStatus dto = new DeliveryStatus();
        dto.setStatusId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public static DeliveryStatusEntity toEntity(DeliveryStatus dto) {

        DeliveryStatusEntity entity = new DeliveryStatusEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }
}
