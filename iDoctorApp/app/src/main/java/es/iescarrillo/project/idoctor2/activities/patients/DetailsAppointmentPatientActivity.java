package es.iescarrillo.project.idoctor2.activities.patients;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.services.AppointmentService;
import es.iescarrillo.project.idoctor2.services.ConsultationService;

public class DetailsAppointmentPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_appointment_patient);

        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvCity = findViewById(R.id.tvCity);
        TextView tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        TextView tvAppointmentTime = findViewById(R.id.tvAppointmentTime);
        Button btnCancel = findViewById(R.id.btnCancel);
        ImageView ivBack = findViewById(R.id.ivBack);

        Appointment appointment = (Appointment) getIntent().getSerializableExtra("appointment");

        AppointmentService appointmentService = new AppointmentService(getApplicationContext());
        ConsultationService consultationService = new ConsultationService(getApplicationContext());

        consultationService.getAllConsultationById(appointment.getConsultationId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Consultation consultation = snapshot.getValue(Consultation.class);

                    tvAddress.setText(consultation.getAddress());
                    tvCity.setText(consultation.getCity());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvAppointmentDate.setText(appointment.getAppointmentDate().toString());
        tvAppointmentTime.setText(appointment.getAppointmentTime().toString());

        btnCancel.setOnClickListener(v -> {

            if (appointment.getAppointmentDate().isAfter(LocalDate.now())) {

                appointment.setPatientId("");
                appointment.setActive(true);

                appointmentService.updateAppointment(appointment);
                Toast.makeText(getApplicationContext(), "This appointment was successfully cancelled", Toast.LENGTH_LONG).show();

                finish();
            } else {

                Toast.makeText(getApplicationContext(), "This appointment cannot be cancelled", Toast.LENGTH_LONG).show();
            }
        });

        ivBack.setOnClickListener(v -> finish());
    }
}