package es.iescarrillo.project.idoctor2.services;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.Consultation;

public class AppointmentService {
    private final DatabaseReference database;

    public AppointmentService(Context context){
        database = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("appointments");
    }

    public void insertAppointment(Appointment appointment) {
        DatabaseReference newReference = database.push();
        appointment.setId(newReference.getKey());

        newReference.setValue(appointment.convertToAppointmentString());
    }

    public void updateAppointment(Appointment appointment){
        database.child(appointment.getId()).setValue(appointment.convertToAppointmentString());
    }

    public void deleteAppointment(String id) {
        database.child(id).removeValue();
    }

    public void getAppointmentByConsultationId(String consultationId, ValueEventListener listener){
        Query query = database.orderByChild("consultationId").equalTo(consultationId);
        query.addValueEventListener(listener);
    }

    public void getAppointmentByPatientlId(String patientId, ValueEventListener listener){
        Query query = database.orderByChild("patientId").equalTo(patientId);
        query.addValueEventListener(listener);
    }

    public void getAllAppointments(ValueEventListener listener) {
        database.addValueEventListener(listener);
    }
}
