package es.iescarrillo.project.idoctor2.models;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timetable implements Serializable {

    private String id;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String consultationId;

    public Timetable() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public TimetableString convertToTimetableString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        TimetableString timetableString = new TimetableString();
        timetableString.setId(this.id);
        timetableString.setDayOfWeek(this.dayOfWeek);
        timetableString.setStartTime(this.startTime.format(formatter));
        timetableString.setEndTime(this.endTime.format(formatter));
        timetableString.setConsultationId(this.consultationId);

        return timetableString;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "id='" + id + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", consultationId='" + consultationId + '\'' +
                '}';
    }
}
