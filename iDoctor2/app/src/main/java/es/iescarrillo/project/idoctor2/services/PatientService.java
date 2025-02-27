package es.iescarrillo.project.idoctor2.services;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.models.Patient;

public class PatientService {
    private final DatabaseReference database;

    public PatientService(Context context){
        database = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("users").child("patients");
    }

    public void insertPatient(Patient patient) {
        DatabaseReference newReference = database.push();
        patient.setId(newReference.getKey());

        newReference.setValue(patient);
    }

    public void updatePatient(Patient patient) {
        database.child(patient.getId()).setValue(patient);
    }

    public void deletePatient(String id) {
        database.child(id).removeValue();
    }

    public void getPatientById(String idPatient, ValueEventListener listener){
        Query query = database.orderByChild("id").equalTo(idPatient);
        query.addValueEventListener(listener);
    }

    public void getAllNamePatients(ValueEventListener listener){
        Query query = database.orderByChild("name");
        query.addValueEventListener(listener);
    }


    public void getPatientByUserName(String userName, ValueEventListener listener){
        Query query = database.orderByChild("username").equalTo(userName);
        query.addValueEventListener(listener);
    }

    public void getAllPatients(ValueEventListener listener){
        database.addListenerForSingleValueEvent(listener);
    }
}
