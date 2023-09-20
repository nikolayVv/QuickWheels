package si.fri.rso.samples.quickwheels.models;

import java.util.List;

public class Delivery {
    public final long id;
    private final String createdAt;
    private final String lastModified;
    private final DeliveryCustomer customer;
    private final String fromLon;
    private final String fromLat;
    private final String toLon;
    private final String toLat;
    private String currLon;
    private String currLat;
    private DeliveryStatus status;
    private final DeliveryType type;
    private DeliveryTransport transport;
    private final List<String> ordersList;
    private String note;

    public Delivery(long id, String createdAt, String lastModified, DeliveryCustomer customer, String fromLon, String fromLat, String toLon, String toLat, String currLon, String currLat, DeliveryStatus status, DeliveryType type, DeliveryTransport transport, String note, List<String> ordersList) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.customer = customer;
        this.fromLon = fromLon;
        this.fromLat = fromLat;
        this.toLon = toLon;
        this.toLat = toLat;
        this.currLon = currLon;
        this.currLat = currLat;
        this.status = status;
        this.type = type;
        this.transport = transport;
        this.ordersList = ordersList;
        this.note = note;
    }

    public long getId() {
        return this.id;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public DeliveryCustomer getCustomer() {
        return this.customer;
    }

    public String getFromLon() {
        return this.fromLon;
    }

    public String getFromLat() {
        return this.fromLat;
    }

    public String getToLon() {
        return this.toLon;
    }

    public String getToLat() {
        return this.toLat;
    }

    public String getCurrLon() {
        return this.currLon;
    }

    public void setCurrLon(String newCurrLon) {
        this.currLon = newCurrLon;
    }

    public String getCurrLat() {
        return this.currLat;
    }

    public void setCurrLat(String newCurrLat) {
        this.currLat = newCurrLat;
    }

    public DeliveryStatus getStatus() {
        return this.status;
    }

    public void setStatus(DeliveryStatus newStatus) {
        this.status = newStatus;
    }

    public DeliveryType getType() {
        return this.type;
    }

    public DeliveryTransport getTransport() {
        return this.transport;
    }

    public void setTransport(DeliveryTransport newTransport) {
        this.transport = newTransport;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }

    public String getOrders() {
        String result = "";
        for (String order : ordersList) {
            result += order + ", ";
        }

        return result.substring(0, result.length() - 2);

    }
}
