package si.fri.rso.samples.deliveries.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "deliveryCustomer")
@NamedQueries(value =
        {
                @NamedQuery(name = "DeliveryCustomerEntity.getAll",
                        query = "SELECT im FROM DeliveryCustomerEntity im")
        })
public class DeliveryCustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "emailAddress")
    private String emailAddress;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "note")
    private String note;

    public Long getId() {
        return this.customerId;
    }

    public void setId(Long newId) {
        this.customerId = newId;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
