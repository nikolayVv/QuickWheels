package si.fri.rso.samples.deliveries.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "deliveryData")
@NamedQueries(value =
        {
                @NamedQuery(name = "DeliveryEntity.getAll",
                        query = "SELECT im FROM DeliveryEntity im")
        })
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private Long deliveryId;

    @Column(name = "customer")
    private Long customer;

    @Column(name = "fromAddress")
    private Long fromAddress;

    @Column(name = "toAddress")
    private Long toAddress;

    @Column(name = "type")
    private Short type;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "lastModified")
    private String lastModified;

    @Column(name = "geoLat")
    private String geoLat;

    @Column(name = "geoLon")
    private String geoLon;

    @Column(name = "transport")
    private Short transport;

    @Column(name = "note")
    private String note;

    public Long getId() {
        return this.deliveryId;
    }

    public void setId(Long newId) {
        this.deliveryId = newId;
    }

    public Long getCustomer() {
        return this.customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Long getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(Long fromAddress) {
        this.fromAddress = fromAddress;
    }

    public Long getToAddress() {
        return this.toAddress;
    }

    public void setToAddress(Long toAddress) {
        this.toAddress = toAddress;
    }

    public Short getType() {
        return this.type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
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

    public Short getTransport() {
        return this.transport;
    }

    public void setTransport(Short newTransport) {
        this.transport = newTransport;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
