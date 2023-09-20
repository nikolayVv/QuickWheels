package si.fri.rso.samples.orders.lib;

public class OrderProduct {

    private Long productId;

    private String name;

    private String description;

    private Integer quantityLeft;

    private Double price;

    public OrderProduct() {

    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public Integer getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(Integer newQuantity) {
        if (newQuantity >= 0) {
            this.quantityLeft = newQuantity;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double newPrice) {
        if (newPrice > 0) {
            this.price = newPrice;
        }
    }
}
