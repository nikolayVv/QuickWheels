package si.fri.rso.samples.quickwheels.models;

public class DeliveryTransport {
    private final int id;
    private final String name;
    private final String description;

    public DeliveryTransport(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
