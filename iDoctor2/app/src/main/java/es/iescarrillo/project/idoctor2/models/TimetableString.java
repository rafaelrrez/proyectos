package es.iescarrillo.project.idoctor2.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimetableString {
    private String id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String consultationId;

    public TimetableString() {

    }

    public TimetableString(String id, String dayOfWeek, String startTime, String endTime, String consultationId) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.consultationId = consultationId;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    public Timetable convertToTimeTable() {
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        Timetable timetable = new Timetable();
        timetable.setId(this.id);
        timetable.setDayOfWeek(this.dayOfWeek);
        timetable.setStartTime(LocalTime.parse(this.startTime, formatterTime));
        timetable.setEndTime(LocalTime.parse(this.endTime, formatterTime));
        timetable.setConsultationId(this.consultationId);

        return timetable;
    }
    @Override
    public String toString() {
        return "TimetableString{" +
                "id='" + id + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", consultationId='" + consultationId + '\'' +
                '}';
    }
}
