package es.iescarrillo.project.idoctor2.activities.professionals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.AppointmentEvaluationAdapter;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.AppointmentString;
import es.iescarrillo.project.idoctor2.services.AppointmentService;

public class ListAppointmentProfessionalActivity extends AppCompatActivity {

    private AppointmentEvaluationAdapter appointmentEvaluationAdapter;
    private List<Appointment> filteredAppointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appointment_professional);

        AppointmentService appointmentService = new AppointmentService(getApplicationContext());
        String consultationId = getIntent().getStringExtra("consultationId");

        ListView lvAppointments = findViewById(R.id.lvAppointments);
        ImageView ivBack = findViewById(R.id.ivBack);

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentService.getAppointmentByConsultationId(consultationId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AppointmentString appointmentString = snapshot.getValue(AppointmentString.class);
                    appointmentList.add(appointmentString.convertToAppointment());
                }

                filteredAppointmentList = new ArrayList<>();
                for (Appointment appointment : appointmentList) {
                    if (appointment.getAppointmentDate().isBefore(LocalDate.now()) && !appointment.getActive()) {
                        filteredAppointmentList.add(appointment);
                    }
                }

                appointmentEvaluationAdapter = new AppointmentEvaluationAdapter(getApplicationContext(), filteredAppointmentList);
                lvAppointments.setAdapter(appointmentEvaluationAdapter);

                appointmentEvaluationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        lvAppointments.setOnItemClickListener((parent, view, position, id) -> {
            Appointment appointment = (Appointment) parent.getItemAtPosition(position);
            Intent createAppointmentEvaluationProfessionalIntent = new Intent(getApplicationContext(), CreateAppointmentEvaluationProfessionalActivity.class);
            createAppointmentEvaluationProfessionalIntent.putExtra("appointment", appointment);
            startActivity(createAppointmentEvaluationProfessionalIntent);
        });

        ivBack.setOnClickListener(v -> finish());
    }
}