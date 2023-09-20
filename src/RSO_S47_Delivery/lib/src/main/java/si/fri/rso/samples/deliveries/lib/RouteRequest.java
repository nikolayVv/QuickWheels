package si.fri.rso.samples.deliveries.lib;

public class RouteRequest {
    private String transport;
    private Double departureLat;
    private Double departureLon;
    private Double destinationLat;
    private Double destinationLon;
    private String departureTime;
    private String distanceUnit;

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Double getDepartureLat() {
        return departureLat;
    }

    public void setDepartureLat(Double departureLat) {
        this.departureLat = departureLat;
    }

    public Double getDepartureLon() {
        return departureLon;
    }

    public void setDepartureLon(Double departureLon) {
        this.departureLon = departureLon;
    }

    public Double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(Double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public Double getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(Double destinationLon) {
        this.destinationLon = destinationLon;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }
}
