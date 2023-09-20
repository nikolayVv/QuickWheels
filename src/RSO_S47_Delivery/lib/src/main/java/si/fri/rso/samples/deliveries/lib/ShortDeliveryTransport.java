package si.fri.rso.samples.deliveries.lib;

public class ShortDeliveryTransport {
    private Short transportId;

    private String link;

    public ShortDeliveryTransport() {

    }

    public Short getTransportId() {
        return this.transportId;
    }

    public void setTransportId(Short newId) {
        this.transportId = newId;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String newLink) {
        this.link = newLink;
    }
}
