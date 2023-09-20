package si.fri.rso.samples.deliveries.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "deliveryType")
@NamedQueries(value =
        {
                @NamedQuery(name = "DeliveryTypeEntity.getAll",
                        query = "SELECT im FROM DeliveryTypeEntity im")
        })
public class DeliveryTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "typeId")
    private Short typeId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Short getId() {
        return this.typeId;
    }

    public void setId(Short newId) {
        this.typeId = newId;
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
