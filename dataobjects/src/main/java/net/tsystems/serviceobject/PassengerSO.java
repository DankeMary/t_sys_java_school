package net.tsystems.serviceobject;


import java.util.Date;
import java.util.Objects;

public class PassengerSO {

    private int id;
    private String firstName;
    private String lastName;
    private Date birthday;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerSO that = (PassengerSO) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(birthday, that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthday);
    }
}
