package si.fri.rso.samples.orders.lib;


public class OrderStatus {
    private Short statusId;

    private String name;

    public OrderStatus() {

    }

    public Short getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Short statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
