package si.fri.rso.samples.orders.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "orderStatus")
@NamedQueries(value =
        {
                @NamedQuery(name = "OrderStatusEntity.getAll",
                        query = "SELECT im FROM OrderStatusEntity im")
        })
public class OrderStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusId")
    private Short statusId;

    @Column(name = "name")
    private String name;

    public Short getId() {
        return this.statusId;
    }

    public void setId(Short newId) {
        this.statusId = newId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
