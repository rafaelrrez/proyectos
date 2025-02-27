package es.iescarrillo.project.idoctor2.models;

import java.io.Serializable;

public abstract class Person extends DomainEntity implements Serializable {

    private String name;
    private String surname;
    private String photo;
    private String username;
    private String password;
    private UserRole userRole;

    public Person(String name, String surmane, String photo) {
        this.name = name;
        this.surname = surmane;
        this.photo = photo;
    }

    public enum UserRole {
        PATIENT,
        PROFESSIONAL
    }

    public Person() {
        super();
    }

    public Person(String id, String name, String surname, String photo, String username, String password, UserRole userRole) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.photo = photo;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", photo='" + photo + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
