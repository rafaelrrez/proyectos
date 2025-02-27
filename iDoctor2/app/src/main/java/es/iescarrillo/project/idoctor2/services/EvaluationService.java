package es.iescarrillo.project.idoctor2.services;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.Evaluation;

public class EvaluationService {
    private final DatabaseReference database;

    public EvaluationService(Context context){
        database = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("evaluations");
    }

    public void insertEvaluation(Evaluation evaluation) {
        DatabaseReference newReference = database.push();
        evaluation.setId(newReference.getKey());

        newReference.setValue(evaluation.convertToEvaluationString());
    }

    public void updateEvaluation(Evaluation evaluation){
        database.child(evaluation.getId()).setValue(evaluation.convertToEvaluationString());
    }

    public void deleteEvaluation(String id) {
        database.child(id).removeValue();
    }

    public void getAllEvaluation(ValueEventListener listener){
        database.addValueEventListener(listener);
    }

    public void getEvaluationByAppointmentId(String appointmentId, ValueEventListener listener){
        Query query = database.orderByChild("appointmentId").equalTo(appointmentId);
        query.addValueEventListener(listener);
    }

}
