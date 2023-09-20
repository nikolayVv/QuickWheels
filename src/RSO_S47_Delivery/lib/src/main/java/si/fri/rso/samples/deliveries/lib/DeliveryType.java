package si.fri.rso.samples.deliveries.lib;

public class DeliveryType {
    private Short typeId;

    private String name;

    private String description;

    public DeliveryType() {

    }


    public Short getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Short newId) {
        this.typeId = newId;
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
