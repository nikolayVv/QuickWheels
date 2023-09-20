package si.fri.rso.samples.deliveries.lib;

public class DeliveryAddress {
    private Long addressId;

    private String zipCode;

    private String street;

    private String city;

    private String country;

    private String geoLat;

    private String geoLon;

    public DeliveryAddress() {

    }

    public Long getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Long newId) {
        this.addressId = newId;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String newZipCode) {
        this.zipCode = newZipCode;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String newStreet) {
        this.street = newStreet;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String newCity) {
        this.city = newCity;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String newCountry) {
        this.country = newCountry;
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
}
