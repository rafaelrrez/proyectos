package es.iescarrillo.project.idoctor2.models;

public class Professional extends Person{
    private String collegiateNumber;
    private Speciality speciality;
    private String description;
    private Double stars;
    private Integer assessments;

    public enum Speciality {
        GENERAL,
        PHYSIOTHERAPY,
        DENTISTRY,
    }

    public Professional() {
        super();
    }

    public String getCollegiateNumber() {
        return collegiateNumber;
    }

    public void setCollegiateNumber(String collegiateNumber) {
        this.collegiateNumber = collegiateNumber;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
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

    public Integer getAssessments() {
        return assessments;
    }

    public void setAssessments(Integer assessments) {
        this.assessments = assessments;
    }

    @Override
    public String toString() {
        return "Professional{" +
                "collegiateNumber='" + collegiateNumber + '\'' +
                ", speciality='" + speciality + '\'' +
                ", description='" + description + '\'' +
                ", stars=" + stars +
                ", assessments=" + assessments +
                '}';
    }
}
