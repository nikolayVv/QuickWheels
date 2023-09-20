package si.fri.rso.samples.orders.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "orderProduct")
@NamedQueries(value =
        {
                @NamedQuery(name = "OrderProductEntity.getAll",
                        query = "SELECT im FROM OrderProductEntity im")
        })
public class OrderProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "quantityLeft")
    private Integer quantityLeft;

    @Column(name = "price")
    private Double price;

    public Long getId() {
        return this.productId;
    }

    public void setId(Long newId) {
        this.productId = newId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public Integer getQuantityLeft() {
        return this.quantityLeft;
    }

    public void setQuantityLeft(Integer newQuantityLeft) {
        if (newQuantityLeft >= 0) {
            this.quantityLeft = newQuantityLeft;
        }
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double newPrice) {
        if (newPrice > 0) {
            this.price = newPrice;
        }
    }
}
