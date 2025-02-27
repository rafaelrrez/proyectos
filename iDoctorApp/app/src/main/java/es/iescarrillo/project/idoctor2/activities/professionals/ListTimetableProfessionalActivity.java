package es.iescarrillo.project.idoctor2.activities.professionals;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.ConsultationProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Timetable;
import es.iescarrillo.project.idoctor2.services.ConsultationService;

public class ListTimetableProfessionalActivity extends AppCompatActivity {

    private ConsultationProfessionalAdapter consultationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_timetable_professional);

        SharedPreferences sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "0");
        String username = sharedPreferences.getString("username", "0");
        String professionalId = sharedPreferences.getString("id", "0");

        ListView lvConsultation = findViewById(R.id.lvConsultation);
        ImageView ivBack = findViewById(R.id.ivBack);

        ConsultationService consultationService = new ConsultationService(getApplicationContext());

        List<Consultation> consultationList = new ArrayList<>();

        consultationService.getConsultationByProfessionalId(professionalId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consultationList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Consultation consultation = snapshot.getValue(Consultation.class);
                    consultationList.add(consultation);
                }

                consultationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        consultationAdapter = new ConsultationProfessionalAdapter(getApplicationContext(), consultationList);

        lvConsultation.setAdapter(consultationAdapter);

        lvConsultation.setOnItemClickListener((parent, view1, position, id) -> {

            Consultation selectedConsultation = (Consultation) parent.getItemAtPosition(position);

            Intent listConsultationTimetableIntent = new Intent(getApplicationContext(), ListConsultationTimetableProfessionalActivity.class);
            listConsultationTimetableIntent.putExtra("consultation", selectedConsultation);
            startActivity(listConsultationTimetableIntent);

        });

        ivBack.setOnClickListener(v-> finish());

    }
}