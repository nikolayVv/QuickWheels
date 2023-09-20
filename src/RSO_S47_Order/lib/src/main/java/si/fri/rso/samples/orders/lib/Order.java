package si.fri.rso.samples.orders.lib;

import java.time.LocalDateTime;

public class Order {

    private Long orderId;

    private Long productId;

    private ShortOrderProduct product;

    private Short statusId;

    private ShortOrderStatus status;

    private Long deliveryId;

    private String createdAt;

    private String lastModified;

    private Integer quantity;

    public Order() {
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long id) {
        this.orderId = id;
    }

    public Long getOrderProductId() {
        return this.productId;
    }

    public void setOrderProductId(Long newProductId) {
        this.productId = newProductId;
    }

    public ShortOrderProduct getOrderProduct() {
        return this.product;
    }

    public void setOrderProduct(ShortOrderProduct newProduct) {
        this.product = newProduct;
    }

    public Short getOrderStatusId() {
        return this.statusId;
    }

    public void setOrderStatusId(Short newStatusId) {
        this.statusId = newStatusId;
    }

    public ShortOrderStatus getOrderStatus() {
        return this.status;
    }

    public void setOrderStatus(ShortOrderStatus newStatus) {
        this.status = newStatus;
    }

    public Long getOrderDeliveryId() {
        return this.deliveryId;
    }

    public void setOrderDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
