package si.fri.rso.samples.deliveries.lib;

public class ShortDeliveryAddress {
    private Long addressId;

    private String geoLat;

    private String geoLon;

    private String link;

    public ShortDeliveryAddress() {

    }

    public Long getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Long newId) {
        this.addressId = newId;
    }

    public String getGeoLat() {
        return this.geoLat;
    }

    public void setGeoLat(String newGeoLat) {
        this.geoLat = newGeoLat;
    }

    public String getGeoLon() {
        return this.geoLon;
    }

    public void setGeoLon(String newGeoLon) {
        this.geoLon = newGeoLon;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String newLink) {
        this.link = newLink;
    }
}
