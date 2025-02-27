package es.iescarrillo.project.idoctor2.models;

import java.io.Serializable;

public class Patient extends Person implements  Serializable {
    private String DNI;
    private String phone;
    private Boolean healthInsurance;
    private String email;

    public Patient() {
        super();
    }

    public Patient(String name, String surmane, String photo, String DNI, String phone, Boolean healthInsurance, String email){
        super(name, surmane, photo);
        this.DNI = DNI;
        this.phone = phone;
        this.healthInsurance = healthInsurance;
        this.email = email;
    }

    public Patient(String DNI, String phone, Boolean healthInsurance, String email) {
        super();
        this.DNI = DNI;
        this.phone = phone;
        this.healthInsurance = healthInsurance;
        this.email = email;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(Boolean healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "DNI='" + DNI + '\'' +
                ", phone='" + phone + '\'' +
                ", healthInsurance=" + healthInsurance +
                ", email='" + email + '\'' +
                '}';
    }
}
