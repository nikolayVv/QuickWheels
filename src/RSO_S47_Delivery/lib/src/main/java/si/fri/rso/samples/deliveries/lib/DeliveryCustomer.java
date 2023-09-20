package si.fri.rso.samples.deliveries.lib;

public class DeliveryCustomer {
    private Long customerId;

    private String name;

    private String surname;

    private String emailAddress;

    private String phoneNumber;

    private String dateOfBirth;

    private String note;

    public DeliveryCustomer() {

    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long newId) {
        this.customerId = newId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String newEmailAddress) {
        this.emailAddress = newEmailAddress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }
}
