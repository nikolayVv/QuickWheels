package si.fri.rso.samples.deliveries.lib;

public class DeliveryStatus {
    private Short statusId;

    private String name;

    private String description;

    public DeliveryStatus() {

    }


    public Short getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Short newId) {
        this.statusId = newId;
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
