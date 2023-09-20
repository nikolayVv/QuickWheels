package si.fri.rso.samples.deliveries.lib;

public class ShortDeliveryCustomer {
    private Long customerId;

    private String fullName;

    private String link;

    public ShortDeliveryCustomer() {

    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long newId) {
        this.customerId = newId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String newLink) {
        this.link = newLink;
    }
}
