package si.fri.rso.samples.quickwheels.models;


public class Order {

    private final long id;
    private final String createdAt;
    private final String lastModified;;
    private OrderProduct orderProduct;
    private OrderStatus orderStatus;
    private long deliveryId;

    public Order(long id, String createdAt, String lastModified, OrderProduct orderProduct, OrderStatus orderStatus, long deliveryId) {
        this.id = id;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.orderProduct = orderProduct;
        this.orderStatus = orderStatus;
        this.deliveryId = deliveryId;
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

    public OrderProduct getProduct() {
        return this.orderProduct;
    }

    public void setProduct(OrderProduct newOrderProduct) {
        this.orderProduct = newOrderProduct;
    }

    public OrderStatus getStatus() {
        return this.orderStatus;
    }

    public void setStatus(OrderStatus newOrderStatus) {
        this.orderStatus = newOrderStatus;
    }

    public long getDeliveryId() {
        return this.deliveryId;
    }

    public void setDeliveryId(long newDeliveryId) {
        this.deliveryId = newDeliveryId;
    }
}
