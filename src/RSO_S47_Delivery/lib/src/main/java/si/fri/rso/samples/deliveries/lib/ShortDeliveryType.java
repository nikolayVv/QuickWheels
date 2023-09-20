package si.fri.rso.samples.deliveries.lib;

public class ShortDeliveryType {
    private Short typeId;

    private String link;

    public ShortDeliveryType() {

    }

    public Short getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Short newId) {
        this.typeId = newId;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String newLink) {
        this.link = newLink;
    }
}
