package si.fri.rso.samples.quickwheels.responses;

public class ProductResponseShort {
    private final String link;
    private final int productId;
    private final int quantity;

    public ProductResponseShort(String link, int productId, int quantity) {
        this.link = link;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getLink() {
        return this.link;
    }

    public int getProductId() {
        return this.productId;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
