package es.iescarrillo.project.idoctor2.activities.patients;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.EvaluationAppointmentAdapter;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.AppointmentString;
import es.iescarrillo.project.idoctor2.models.Evaluation;
import es.iescarrillo.project.idoctor2.models.EvaluationString;
import es.iescarrillo.project.idoctor2.services.AppointmentService;
import es.iescarrillo.project.idoctor2.services.EvaluationService;

public class ListEvaluationAppoinmentPatientActivity extends AppCompatActivity {
    private EvaluationAppointmentAdapter evaluationAppoinmentAdapter;
    List<Evaluation> evaluationList;
    List<Appointment> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_evaluation_appoinment_patient);

        SharedPreferences sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String patientId = sharedPreferences.getString("id", "0");

        ImageView ivBack = findViewById(R.id.ivBack);
        ListView lvEvaluation = findViewById(R.id.lvEvaluation);

        EvaluationService evaluationService = new EvaluationService(getApplicationContext());
        AppointmentService appointmentService = new AppointmentService(getApplicationContext());

        evaluationList = new ArrayList<Evaluation>();
        appointmentList = new ArrayList<Appointment>();
        appointmentService.getAppointmentByPatientlId(patientId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AppointmentString appointmentString = snapshot.getValue(AppointmentString.class);

                    appointmentList.add(appointmentString.convertToAppointment());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        evaluationService.getAllEvaluation(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    EvaluationString evaluationString = snapshot.getValue(EvaluationString.class);
                    for (Appointment appointment : appointmentList) {
                        if (appointment.getId().equalsIgnoreCase(evaluationString.getAppointmentId())) {
                            evaluationList.add(evaluationString.convertToEvaluation());
                        }
                    }
                }

                evaluationAppoinmentAdapter = new EvaluationAppointmentAdapter(getApplicationContext(), evaluationList);

                lvEvaluation.setAdapter(evaluationAppoinmentAdapter);
                evaluationAppoinmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });




        lvEvaluation.setOnItemClickListener((parent, view, position, id) -> {
            Evaluation selecEvaluation = (Evaluation) parent.getItemAtPosition(position);
            Intent detailEvaluationPatientIntent = new Intent(getApplicationContext(), DetailEvaluationPatientActivity.class);
            detailEvaluationPatientIntent.putExtra("evaluation", selecEvaluation);
            startActivity(detailEvaluationPatientIntent);
        });

        ivBack.setOnClickListener(v -> finish());
    }
}