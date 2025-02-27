package es.iescarrillo.project.idoctor2.services;

import android.content.Context;
import android.text.format.Time;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.models.Professional;
import es.iescarrillo.project.idoctor2.models.Timetable;

public class TimetableService {
    private final DatabaseReference database;

    public TimetableService(Context context) {
        database = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("timetables");
    }

    public void insertTimetable(Timetable timetable) {
        DatabaseReference newReference = database.push();
        timetable.setId(newReference.getKey());

        newReference.setValue(timetable.convertToTimetableString());
    }

    public void updateTimetable(Timetable timetable) {
        database.child(timetable.getId()).setValue(timetable.convertToTimetableString());
    }

    public void deleteTimetable(String id) {
        database.child(id).removeValue();
    }

    public void getAllTimetable(ValueEventListener listener) {
        database.addValueEventListener(listener);
    }

    public void getTimetableByConsultationId(String professionalId, ValueEventListener listener) {
        Query query = database.orderByChild("consultationId").equalTo(professionalId);
        query.addListenerForSingleValueEvent(listener);
    }
}
