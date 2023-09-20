package si.fri.rso.samples.deliveries.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "deliveryTransport")
@NamedQueries(value =
        {
                @NamedQuery(name = "DeliveryTransportEntity.getAll",
                        query = "SELECT im FROM DeliveryTransportEntity im")
        })
public class DeliveryTransportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transportId")
    private Short transportId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Short getId() {
        return this.transportId;
    }

    public void setId(Short newId) {
        this.transportId = newId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
