package si.fri.rso.samples.deliveries.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "deliveryStatus")
@NamedQueries(value =
        {
                @NamedQuery(name = "DeliveryStatusEntity.getAll",
                        query = "SELECT im FROM DeliveryStatusEntity im")
        })
public class DeliveryStatusEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "statusId")
        private Short statusId;

        @Column(name = "name")
        private String name;

        @Column(name = "description")
        private String description;

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

        public String getDescription() {
                return this.description;
        }

        public void setDescription(String newDescription) {
                this.description = newDescription;
        }
}
