package si.fri.rso.samples.deliveries.models.converters;

import si.fri.rso.samples.deliveries.lib.DeliveryType;
import si.fri.rso.samples.deliveries.models.entities.DeliveryTypeEntity;

public class DeliveryTypeConverter {
    public static DeliveryType toDto(DeliveryTypeEntity entity) {

        DeliveryType dto = new DeliveryType();
        dto.setTypeId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public static DeliveryTypeEntity toEntity(DeliveryType dto) {

        DeliveryTypeEntity entity = new DeliveryTypeEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        return entity;
    }
}
