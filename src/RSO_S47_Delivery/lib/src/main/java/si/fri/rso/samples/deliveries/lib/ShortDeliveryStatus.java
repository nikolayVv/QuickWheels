package si.fri.rso.samples.deliveries.lib;

public class ShortDeliveryStatus {
    private Short statusId;

    private String link;

    public ShortDeliveryStatus() {

    }

    public Short getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Short newId) {
        this.statusId = newId;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String newLink) {
        this.link = newLink;
    }
}
