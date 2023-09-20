package si.fri.rso.samples.quickwheels.responses;

public final class OrderResponse {

    private final String createdAt;
    private final String lastModified;
    private final long orderId;
    //private final OrderProductResponse;

    public OrderResponse(String createdAt, String lastModified, long orderId) {
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.orderId = orderId;

    }
}
