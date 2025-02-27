package es.iescarrillo.project.idoctor2.services;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Patient;

public class ConsultationService {
    private final DatabaseReference database;

    public ConsultationService(Context context){
        database = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("consultations");
    }

    public void insertConsultation(Consultation consultation) {
        DatabaseReference newReference = database.push();
        consultation.setId(newReference.getKey());

        newReference.setValue(consultation);
    }

    public void updateConsultation(Consultation consultation) {
        database.child(consultation.getId()).setValue(consultation);
    }

    public void deleteConsultation(String id) {
        database.child(id).removeValue();
    }

    public void getConsultationByProfessionalId(String professionalId, ValueEventListener listener){
        Query query = database.orderByChild("professionalId").equalTo(professionalId);
        query.addValueEventListener(listener);
    }

    public void getAllConsultation(ValueEventListener listener) {
        database.addValueEventListener(listener);
    }

    public void getAllConsultationById(String consultationId, ValueEventListener listener) {
        Query query = database.orderByChild("id").equalTo(consultationId);
        query.addValueEventListener(listener);
    }
}
