package si.fri.rso.samples.orders.lib;

public class ShortOrderStatus {
    private Short statusId;

    private String link;

    public ShortOrderStatus() {

    }

    public void setStatusId(Short newId) {
        this.statusId = newId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Short getStatusId() {
        return statusId;
    }
}
