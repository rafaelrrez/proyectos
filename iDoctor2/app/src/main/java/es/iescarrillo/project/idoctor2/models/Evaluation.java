package es.iescarrillo.project.idoctor2.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evaluation implements Serializable {
    private String id;
    private String description;
    private String exploration;
    private String treatment;
    private LocalDateTime evaluationDateTime;
    private String appointmentId;

    public Evaluation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExploration() {
        return exploration;
    }

    public void setExploration(String exploration) {
        this.exploration = exploration;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public LocalDateTime getEvaluationDateTime() {
        return evaluationDateTime;
    }

    public void setEvaluationDateTime(LocalDateTime evaluationDateTime) {
        this.evaluationDateTime = evaluationDateTime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public EvaluationString convertToEvaluationString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        EvaluationString evaluationString = new EvaluationString();
        evaluationString.setId(this.id);
        evaluationString.setDescription(this.description);
        evaluationString.setExploration(this.exploration);
        evaluationString.setTreatment(this.treatment);
        evaluationString.setEvaluationDateTime(this.evaluationDateTime.format(formatter));
        evaluationString.setAppointmentId(this.appointmentId);

        return evaluationString;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", exploration='" + exploration + '\'' +
                ", treatment='" + treatment + '\'' +
                ", evaluationDateTime=" + evaluationDateTime +
                ", appointmentId='" + appointmentId + '\'' +
                '}';
    }
}
