package es.iescarrillo.project.idoctor2.models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AssessmentString {
    private String id;
    private String username;
    private String title;
    private String description;
    private String stars;
    private String assessmentDateTime;
    private String professionalId;

    public AssessmentString() {

    }

    public AssessmentString(String id, String username, String title, String description, String stars, String assessmentDateTime, String professionalId) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.description = description;
        this.stars = stars;
        this.assessmentDateTime = assessmentDateTime;
        this.professionalId = professionalId;
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

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getAssessmentDateTime() {
        return assessmentDateTime;
    }

    public void setAssessmentDateTime(String assessmentDateTime) {
        this.assessmentDateTime = assessmentDateTime;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public Assessment convertToAssessment() {
        DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Assessment assessment = new Assessment();
        assessment.setId(this.id);
        assessment.setUsername(this.username);
        assessment.setTitle(this.title);
        assessment.setDescription(this.description);
        assessment.setStars(Double.parseDouble(this.stars));
        assessment.setAssessmentDateTime(LocalDateTime.parse(this.assessmentDateTime, formatterDateTime));
        assessment.setProfessionalId(this.professionalId);

        return assessment;
    }

    @Override
    public String toString() {
        return "AssessmentString{" +
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
