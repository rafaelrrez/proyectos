package es.iescarrillo.project.idoctor2.services;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.models.Professional;

public class ProfessionalService {

    private final DatabaseReference database;

    public ProfessionalService(Context context) {
        database = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("users").child("professionals");
    }

    public void insertProfessional(Professional professional) {
        DatabaseReference newReference = database.push();
        professional.setId(newReference.getKey());

        newReference.setValue(professional);
    }

    public void updateProfessional(Professional professional) {
        database.child(professional.getId()).setValue(professional);
    }

    public void deleteProfessional(String id) {
        database.child(id).removeValue();
    }

    public void getProfessionalById(String professionalId, ValueEventListener listener) {
        Query query = database.orderByChild("id").equalTo(professionalId);
        query.addValueEventListener(listener);
    }

    public void getAllProfessional(ValueEventListener listener) {
        database.addValueEventListener(listener);
    }
}
