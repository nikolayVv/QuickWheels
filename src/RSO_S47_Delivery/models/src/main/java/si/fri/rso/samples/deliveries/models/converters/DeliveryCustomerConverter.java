package si.fri.rso.samples.deliveries.models.converters;


import si.fri.rso.samples.deliveries.lib.DeliveryCustomer;
import si.fri.rso.samples.deliveries.models.entities.DeliveryCustomerEntity;

public class DeliveryCustomerConverter {
    public static DeliveryCustomer toDto(DeliveryCustomerEntity entity) {

        DeliveryCustomer dto = new DeliveryCustomer();
        dto.setCustomerId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmailAddress(entity.getEmailAddress());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setNote(entity.getNote());

        return dto;
    }

    public static DeliveryCustomerEntity toEntity(DeliveryCustomer dto) {

        DeliveryCustomerEntity entity = new DeliveryCustomerEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmailAddress(dto.getEmailAddress());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setDateOfBirth(dto.getPhoneNumber());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setNote(dto.getNote());

        return entity;
    }
}
