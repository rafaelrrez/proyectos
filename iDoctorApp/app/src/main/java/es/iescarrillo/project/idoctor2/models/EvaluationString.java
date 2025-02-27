package es.iescarrillo.project.idoctor2.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EvaluationString {

    private String id;
    private String description;
    private String exploration;
    private String treatment;
    private String evaluationDateTime;
    private String appointmentId;

    public EvaluationString() {

    }

    public EvaluationString(String id, String description, String exploration, String treatment, String evaluationDateTime, String appointmentId) {
        this.id = id;
        this.description = description;
        this.exploration = exploration;
        this.treatment = treatment;
        this.evaluationDateTime = evaluationDateTime;
        this.appointmentId = appointmentId;
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

    public String getEvaluationDateTime() {
        return evaluationDateTime;
    }

    public void setEvaluationDateTime(String evaluationDateTime) {
        this.evaluationDateTime = evaluationDateTime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Evaluation convertToEvaluation() {
        DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Evaluation evaluation = new Evaluation();
        evaluation.setId(this.id);
        evaluation.setDescription(this.description);
        evaluation.setExploration(this.exploration);
        evaluation.setTreatment(this.treatment);
        evaluation.setEvaluationDateTime(LocalDateTime.parse(this.evaluationDateTime, formatterDateTime));
        evaluation.setAppointmentId(this.appointmentId);

        return evaluation;
    }
    @Override
    public String toString() {
        return "EvaluationString{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", exploration='" + exploration + '\'' +
                ", treatment='" + treatment + '\'' +
                ", evaluationDateTime='" + evaluationDateTime + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                '}';
    }
}
