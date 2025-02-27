package es.iescarrillo.project.idoctor2.models;

import java.io.Serializable;

public class Consultation implements Serializable {
    private String id;
    private String address;
    private String city;
    private String email;
    private String phone;
    private String phoneAux;
    private String professionalId;

    public Consultation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneAux() {
        return phoneAux;
    }

    public void setPhoneAux(String phoneAux) {
        this.phoneAux = phoneAux;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneAux='" + phoneAux + '\'' +
                ", professionalId='" + professionalId + '\'' +
                '}';
    }

    public String toStringConsultation() {
        return  address;
    }
}
