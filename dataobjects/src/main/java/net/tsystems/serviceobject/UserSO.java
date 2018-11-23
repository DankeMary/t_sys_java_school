package net.tsystems.serviceobject;

import java.util.Objects;

public class UserSO {
    private int id;
    private String username;
    private String password;
    private String role;
    private PassengerSO passenger;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PassengerSO getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerSO passenger) {
        this.passenger = passenger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSO userSO = (UserSO) o;
        return id == userSO.id &&
                Objects.equals(username, userSO.username) &&
                Objects.equals(password, userSO.password) &&
                Objects.equals(role, userSO.role) &&
                Objects.equals(passenger, userSO.passenger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, passenger);
    }
}
