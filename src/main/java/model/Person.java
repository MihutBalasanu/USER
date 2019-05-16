package model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "persons")
public class Person extends BaseModel {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "first_name", length = 16)
    private String firstName;

    @Column(name = "last_name", length = 16, nullable = false)
    private String lastName;

    @Column(name = "email", length = 16)
    private String email;

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId()) &&
                Objects.equals(getUser(), person.getUser()) &&
                Objects.equals(getAddress(), person.getAddress()) &&
                Objects.equals(getFirstName(), person.getFirstName()) &&
                Objects.equals(getLastName(), person.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getAddress(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", user=" + user +
                ", address='" + address + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
