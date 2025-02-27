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
import es.iescarrillo.project.idoctor2.services.ConsultationService;

public class ListConsultationAppointmentProfessionalActivity extends AppCompatActivity {

    private ConsultationProfessionalAdapter consultationProfessionalAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_consultation_appointment_professional);

        SharedPreferences sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String professionalId = sharedPreferences.getString("id", "0");

        ConsultationService consultationService = new ConsultationService(getApplicationContext());

        ListView lvConsultation = findViewById(R.id.lvConsultation);
        ImageView ivBack = findViewById(R.id.ivBack);

        List<Consultation> consultationList = new ArrayList<>();
        consultationService.getConsultationByProfessionalId(professionalId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consultationList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Consultation consultation = snapshot.getValue(Consultation.class);
                    consultationList.add(consultation);
                }

                consultationProfessionalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        consultationProfessionalAdapter = new ConsultationProfessionalAdapter(getApplicationContext(), consultationList);

        lvConsultation.setAdapter(consultationProfessionalAdapter);

        lvConsultation.setOnItemClickListener((parent, view, position, id) -> {
            Consultation consultation = (Consultation) parent.getItemAtPosition(position);

            Intent listAppointmentProfessionalIntent = new Intent(getApplicationContext(), ListAppointmentProfessionalActivity.class);
            listAppointmentProfessionalIntent.putExtra("consultationId", consultation.getId());
            startActivity(listAppointmentProfessionalIntent);
        });

        ivBack.setOnClickListener(v-> finish());
    }
}