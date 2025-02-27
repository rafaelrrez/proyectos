package es.iescarrillo.project.idoctor2.models;

import androidx.navigation.NavType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Appointment implements Serializable {

    private String id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Boolean active;
    private String consultationId;
    private String patientId;

    public Appointment() {
    }

    public Appointment(String id, LocalDate appointmentDate, LocalTime appointmentTime, Boolean active, String consultationId, String patientId) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.active = active;
        this.consultationId = consultationId;
        this.patientId = patientId;
    }

    public Appointment(String id, LocalDate appointmentDate, LocalTime appointmentTime, Boolean active, String consultationId) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.active = active;
        this.consultationId = consultationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public AppointmentString convertToAppointmentString() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        AppointmentString appointmentString = new AppointmentString();
        appointmentString.setId(this.id);
        appointmentString.setAppointmentDate(this.appointmentDate.format(formatterDate));
        appointmentString.setAppointmentTime(this.appointmentTime.format(formatterTime));
        appointmentString.setActive(this.active.toString());
        appointmentString.setConsultationId(this.consultationId);
        appointmentString.setPatientId(this.patientId);

        return appointmentString;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", active=" + active +
                ", consultationId='" + consultationId + '\'' +
                ", patientId='" + patientId + '\'' +
                '}';
    }
}
