package si.fri.rso.samples.orders.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "orderData")
@NamedQueries(value =
        {
                @NamedQuery(name = "OrderEntity.getAll",
                        query = "SELECT im FROM OrderEntity im")
        })
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long orderId;

    @Column(name = "product")
    private Long product;

    @Column(name = "status")
    private Short status;

    @Column(name = "delivery")
    private Long delivery;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "lastModified")
    private String lastModified;

    @Column(name = "quantity")
    private Integer quantity;

    public Long getId() {
        return orderId;
    }

    public void setId(Long newId) {
        this.orderId = newId;
    }

    public Long getProduct() {
        return this.product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short newStatus) {
        this.status = newStatus;
    }

    public Long getDelivery() {
        return this.delivery;
    }

    public void setDelivery(Long delivery) {
        this.delivery = delivery;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String newTime) {
        this.createdAt = newTime;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(String newTime) {
        this.lastModified = newTime;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer newQuantity) {
        if (newQuantity > 0) {
            this.quantity = newQuantity;
        }
    }
}
