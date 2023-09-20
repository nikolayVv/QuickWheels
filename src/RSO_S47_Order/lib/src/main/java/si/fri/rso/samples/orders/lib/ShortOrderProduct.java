package si.fri.rso.samples.orders.lib;

public class ShortOrderProduct {
    private Long productId;

    private String link;

    private Integer quantity;

    public ShortOrderProduct() {

    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long newId) {
        this.productId = newId;
    }

    public void setLink(String newLink) {
       this.link = newLink;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer newQuantity) {
        this.quantity = newQuantity;
    }

    public String getLink() {
        return link;
    }
}
