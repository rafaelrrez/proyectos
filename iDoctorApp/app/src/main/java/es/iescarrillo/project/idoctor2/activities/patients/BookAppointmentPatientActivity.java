package es.iescarrillo.project.idoctor2.activities.patients;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.activities.MainActivity;
import es.iescarrillo.project.idoctor2.activities.RegisterActivity;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Professional;
import es.iescarrillo.project.idoctor2.models.Timetable;
import es.iescarrillo.project.idoctor2.services.AppointmentService;
import es.iescarrillo.project.idoctor2.services.ProfessionalService;

public class BookAppointmentPatientActivity extends AppCompatActivity {
    ProfessionalService professionalService;
    AppointmentService appointmentService;
    Professional professional;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment_patient);

        sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "0");


        Appointment appointment = (Appointment) getIntent().getSerializableExtra("appointment");
        Consultation consultation = (Consultation) getIntent().getSerializableExtra("consultation");
        String professionalID = getIntent().getStringExtra("professionalID");

        TextView tvProfessional = findViewById(R.id.tvProfessional);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvCity = findViewById(R.id.tvCity);
        TextView tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        TextView tvAppointmentTime = findViewById(R.id.tvAppointmentTime);
        ImageView ivBack = findViewById(R.id.ivBack);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        professionalService = new ProfessionalService(getApplicationContext());
        appointmentService = new AppointmentService(getApplicationContext());

        professionalService.getProfessionalById(professionalID, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    professional = snapshot.getValue(Professional.class);
                }

                String professionalName = professional.getName() + " " + professional.getSurname();
                tvProfessional.setText(professionalName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase", "Error reading the database", databaseError.toException());
            }
        });

        tvAddress.setText(consultation.getAddress());
        tvCity.setText(consultation.getCity());
        tvAppointmentDate.setText(appointment.getAppointmentDate().toString());
        tvAppointmentTime.setText(appointment.getAppointmentTime().toString());

        btnConfirm.setOnClickListener(v -> {
            appointment.setPatientId(userId);
            appointment.setActive(false);

            appointmentService.updateAppointment(appointment);

            Toast.makeText(BookAppointmentPatientActivity.this, "Appointment confirmed", Toast.LENGTH_SHORT).show();
            finish();
        });

        ivBack.setOnClickListener(v -> finish());
    }
}