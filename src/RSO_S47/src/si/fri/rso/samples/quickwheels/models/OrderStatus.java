package si.fri.rso.samples.quickwheels.models;

public class OrderStatus {

    private final int id;
    private final String name;

    public OrderStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
