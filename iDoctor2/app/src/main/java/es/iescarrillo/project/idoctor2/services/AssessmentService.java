package es.iescarrillo.project.idoctor2.services;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.models.Assessment;

public class AssessmentService {

    private final DatabaseReference database;

    public AssessmentService(Context context) {
        database = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("assessments");
    }

    public void insertAssessment(Assessment assessment) {
        DatabaseReference newReference = database.push();
        assessment.setId(newReference.getKey());

        newReference.setValue(assessment.convertToAssessmentString());
    }

    public void updateAssessment(Assessment assessment) {
        database.child(assessment.getId()).setValue(assessment);
    }

    public void deleteAssessment(String id) {
        database.child(id).removeValue();
    }

    public void getAllAssessment(ValueEventListener listener){
        database.addValueEventListener(listener);
    }

    public void getAssessmentsByProfessionalId(String professionalId, ValueEventListener listener){
        Query query = database.orderByChild("professionalId").equalTo(professionalId);
        query.addValueEventListener(listener);
    }
}
