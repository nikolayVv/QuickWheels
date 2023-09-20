package si.fri.rso.samples.quickwheels.models;

public class OrderProduct {
    private final long id;
    private String description;
    private String name;
    private double price;
    private int quantity;
    private int quantityLeft;

    public OrderProduct(long id, String description, String name, double price, int quantity, int quantityLeft) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.quantityLeft = quantityLeft;
    }

    public long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDescription) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public double getTotalPrice() {
        return this.price * this.quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public int getQuantityLeft() {
        return this.quantityLeft;
    }

    public void setQuantityLeft(int newQuantityLeft) {
        this.quantityLeft = newQuantityLeft;
    }
}
