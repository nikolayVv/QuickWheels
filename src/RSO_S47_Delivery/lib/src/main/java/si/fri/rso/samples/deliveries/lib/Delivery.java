package si.fri.rso.samples.deliveries.lib;

public class Delivery {
    private Long deliveryId;

    private Long customerId;

    private ShortDeliveryCustomer customer;

    private Long fromAddressId;

    private ShortDeliveryAddress fromAddress;

    private Long toAddressId;

    private ShortDeliveryAddress toAddress;

    private Short statusId;

    private ShortDeliveryStatus status;

    private Short typeId;

    private ShortDeliveryType type;

    private String createdAt;

    private String lastModified;

    private String geoLat;

    private String geoLon;

    private Short transportId;

    private ShortDeliveryTransport transport;

    private String note;

    public Delivery() {

    }

    public Long getDeliveryId() {
        return this.deliveryId;
    }

    public void setDeliveryId(Long newId) {
        this.deliveryId = newId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long newCustomerId) {
        this.customerId = newCustomerId;
    }

    public ShortDeliveryCustomer getCustomer() {
        return this.customer;
    }

    public void setCustomer(ShortDeliveryCustomer newCustomer) {
        this.customer = newCustomer;
    }

    public Long getFromAddressId() {
        return this.fromAddressId;
    }

    public void setFromAddressId(Long newFromAddressId) {
        this.fromAddressId = newFromAddressId;
    }

    public ShortDeliveryAddress getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(ShortDeliveryAddress newFromAddress) {
        this.fromAddress = newFromAddress;
    }

    public Long getToAddressId() {
        return this.toAddressId;
    }

    public void setToAddressId(Long newToAddressId) {
        this.toAddressId = newToAddressId;
    }

    public ShortDeliveryAddress getToAddress() {
        return this.toAddress;
    }

    public void setToAddress(ShortDeliveryAddress newToAddress) {
        this.toAddress = newToAddress;
    }

    public Short getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Short newStatusId) {
        this.statusId = newStatusId;
    }

    public ShortDeliveryStatus getStatus() {
        return this.status;
    }

    public void setStatus(ShortDeliveryStatus newStatus) {
        this.status = newStatus;
    }

    public Short getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Short newTypeId) {
        this.typeId = newTypeId;
    }

    public ShortDeliveryType getType() {
        return this.type;
    }

    public void setType(ShortDeliveryType newType) {
        this.type = newType;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String newCreatedAt) {
        this.createdAt = newCreatedAt;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(String newLastModified) {
        this.lastModified = newLastModified;
    }

    public String getGeoLat() {
        return this.geoLat;
    }

    public void setGeoLat(String newGeoLat) {
        this.geoLat = newGeoLat;
    }

    public String getGeoLon() {
        return this.geoLon;
    }

    public void setGeoLon(String newGeoLon) {
        this.geoLon = newGeoLon;
    }

    public Short getTransportId() {
        return this.transportId;
    }

    public void setTransportId(Short newTransportId) {
        this.transportId = newTransportId;
    }

    public ShortDeliveryTransport getTransport() {
        return this.transport;
    }

    public void setTransport(ShortDeliveryTransport newTransport) {
        this.transport = newTransport;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }
}
