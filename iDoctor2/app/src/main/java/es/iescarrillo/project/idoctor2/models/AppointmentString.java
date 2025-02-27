package es.iescarrillo.project.idoctor2.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentString {
    private String id;
    private String appointmentDate;
    private String appointmentTime;
    private String active;
    private String consultationId;
    private String patientId;

    public AppointmentString() {

    }
    public AppointmentString(String id, String appointmentDate, String appointmentTime, String active, String consultationId, String patientId) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.active = active;
        this.consultationId = consultationId;
        this.patientId = patientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
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

    public Appointment convertToAppointment() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        Appointment appointment = new Appointment();
        appointment.setId(this.id);
        appointment.setAppointmentDate(LocalDate.parse(this.appointmentDate, formatterDate));
        appointment.setAppointmentTime(LocalTime.parse(this.appointmentTime, formatterTime));
        appointment.setActive(Boolean.parseBoolean(this.active));
        appointment.setConsultationId(this.consultationId);
        appointment.setPatientId(this.patientId);

        return appointment;
    }

    @Override
    public String toString() {
        return "AppointmentString{" +
                "id='" + id + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", active='" + active + '\'' +
                ", consultationId='" + consultationId + '\'' +
                ", patientId='" + patientId + '\'' +
                '}';
    }
}
