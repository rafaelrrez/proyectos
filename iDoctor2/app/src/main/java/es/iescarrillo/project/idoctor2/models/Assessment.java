package es.iescarrillo.project.idoctor2.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Assessment implements Serializable {
    private String id;
    private String username;
    private String title;
    private String description;
    private Double stars;
    private LocalDateTime assessmentDateTime;
    private String professionalId;

    public Assessment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public LocalDateTime getAssessmentDateTime() {
        return assessmentDateTime;
    }

    public void setAssessmentDateTime(LocalDateTime assessmentDateTime) {
        this.assessmentDateTime = assessmentDateTime;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public AssessmentString convertToAssessmentString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        AssessmentString assessmentString = new AssessmentString();
        assessmentString.setId(this.id);
        assessmentString.setUsername(this.username);
        assessmentString.setTitle(this.title);
        assessmentString.setDescription(this.description);
        assessmentString.setStars(String.valueOf(this.stars));
        assessmentString.setAssessmentDateTime(this.assessmentDateTime.format(formatter));
        assessmentString.setProfessionalId(this.professionalId);

        return assessmentString;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", stars=" + stars +
                ", assessmentDateTime=" + assessmentDateTime +
                ", professionalId='" + professionalId + '\'' +
                '}';
    }
}
