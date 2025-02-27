package es.iescarrillo.project.idoctor2.activities.patients;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.AppointmentPatientAdapter;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.AppointmentString;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Timetable;
import es.iescarrillo.project.idoctor2.services.AppointmentService;

public class ListAppointmentConsultationProfessionalPatientActivity extends AppCompatActivity {
    private AppointmentPatientAdapter appointmentPatientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appointment_consultation_professional_patient);


        AppointmentService appointmentService = new AppointmentService(getApplicationContext());
        Consultation consultation = (Consultation) getIntent().getSerializableExtra("consultation");
        String professionalID = getIntent().getStringExtra("professionalID");
        ListView lvAppointments = findViewById(R.id.lvAppointments);
        ImageView ivBack = findViewById(R.id.ivBack);

        List<Appointment> appointmentsList = new ArrayList<>();

        appointmentService.getAppointmentByConsultationId(consultation.getId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AppointmentString appointmentString = snapshot.getValue(AppointmentString.class);
                    if (appointmentString.getActive().equalsIgnoreCase("TRUE")) {
                        appointmentsList.add(appointmentString.convertToAppointment());
                    }
                }

                appointmentPatientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        appointmentPatientAdapter = new AppointmentPatientAdapter(getApplicationContext(), appointmentsList);

        lvAppointments.setAdapter(appointmentPatientAdapter);

        lvAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment appointment = (Appointment) parent.getItemAtPosition(position);

                Intent BookAppointmentIntent = new Intent(getApplicationContext(), BookAppointmentPatientActivity.class);
                BookAppointmentIntent.putExtra("appointment", appointment);
                BookAppointmentIntent.putExtra("consultation", consultation);
                BookAppointmentIntent.putExtra("professionalID", professionalID);
                startActivity(BookAppointmentIntent);
            }
        });


        ivBack.setOnClickListener(v -> finish());
    }

}