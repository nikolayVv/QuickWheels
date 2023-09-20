package si.fri.rso.samples.deliveries.models.converters;


import si.fri.rso.samples.deliveries.lib.DeliveryAddress;
import si.fri.rso.samples.deliveries.models.entities.DeliveryAddressEntity;

public class DeliveryAddressConverter {
    public static DeliveryAddress toDto(DeliveryAddressEntity entity) {

        DeliveryAddress dto = new DeliveryAddress();
        dto.setAddressId(entity.getId());
        dto.setZipCode(entity.getZipCode());
        dto.setStreet(entity.getStreet());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());
        dto.setGeoLat(entity.getGeoLat());
        dto.setGeoLon(entity.getGeoLon());

        return dto;
    }

    public static DeliveryAddressEntity toEntity(DeliveryAddress dto) {

        DeliveryAddressEntity entity = new DeliveryAddressEntity();
        entity.setZipCode(dto.getZipCode());
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setGeoLat(dto.getGeoLat());
        entity.setGeoLon(dto.getGeoLon());

        return entity;
    }
}
