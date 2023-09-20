package si.fri.rso.samples.deliveries.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "deliveryAddress")
@NamedQueries(value =
        {
                @NamedQuery(name = "DeliveryAddressEntity.getAll",
                        query = "SELECT im FROM DeliveryAddressEntity im")
        })
public class DeliveryAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    private Long addressId;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "geoLat")
    private String geoLat;

    @Column(name = "geoLon")
    private String geoLon;

    public Long getId() {
        return this.addressId;
    }

    public void setId(Long newId) {
        this.addressId = newId;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGeoLat() {
        return this.geoLat;
    }

    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    public String getGeoLon() {
        return this.geoLon;
    }

    public void setGeoLon(String geoLon) {
        this.geoLon = geoLon;
    }
}
