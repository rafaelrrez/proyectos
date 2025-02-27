package es.iescarrillo.project.idoctor2.services;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.models.Report;

public class ReportService {
    private final DatabaseReference database;

    public ReportService(Context context) {
        database = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("reports");
    }

    public void insertReport(Report report) {
        DatabaseReference newReference = database.push();
        report.setId(newReference.getKey());

        newReference.setValue(report);
    }

    public void updateReport(Report report) {
        database.child(report.getId()).setValue(report);
    }

    public void deleteReport(String id) {
        database.child(id).removeValue();
    }

    public void getAllReports(ValueEventListener listener){
        database.addValueEventListener(listener);
    }

    public void getReportById(String evaluationId, ValueEventListener listener){
        Query query = database.orderByChild("evaluationId").equalTo(evaluationId);
        query.addValueEventListener(listener);
    }
}

