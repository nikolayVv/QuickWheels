package si.fri.rso.samples.deliveries.lib;

public class DeliveryTransport {
    private Short transportId;

    private String name;

    private String description;

    public DeliveryTransport() {

    }

    public Short getTransportId() {
        return this.transportId;
    }

    public void setTransportId(Short newId) {
        this.transportId = newId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
