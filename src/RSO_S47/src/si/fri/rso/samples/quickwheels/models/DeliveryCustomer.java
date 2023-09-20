package si.fri.rso.samples.quickwheels.models;

public class DeliveryCustomer {
    private final long id;
    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final String dateOfBirth;
    private String note;

    public DeliveryCustomer(long id, String name, String surname, String email, String phone, String dateOfBirth, String note) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.note = note;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    public String getEmail() {
        return this.email;
    }


    public String getPhone() {
        return this.phone;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String newNote) {
        this.note = newNote;
    }
}
