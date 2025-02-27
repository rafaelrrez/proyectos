package es.iescarrillo.project.idoctor2.activities.professionals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shawnlin.numberpicker.NumberPicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Timetable;
import es.iescarrillo.project.idoctor2.models.TimetableString;
import es.iescarrillo.project.idoctor2.services.AppointmentService;
import es.iescarrillo.project.idoctor2.services.ConsultationService;
import es.iescarrillo.project.idoctor2.services.TimetableService;

public class GenerateAppointmentsProfessionalActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapterSpinner;
    private Consultation selectedConsultation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_appointments_professional);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String professionalId = sharedPreferences.getString("id", "0");

        AppCompatSpinner spinnerConsultation = findViewById(R.id.spinnerConsultation);
        RadioButton rbOneWeek = findViewById(R.id.rbOneWeek);
        RadioButton rbOneMonth = findViewById(R.id.rbOneMonth);
        NumberPicker durationPicker = findViewById(R.id.durationPicker);
        ImageView ivBack = findViewById(R.id.ivBack);
        Button btnGenerate = findViewById(R.id.btnGenerate);

        ConsultationService consultationService = new ConsultationService(getApplicationContext());
        List<Consultation> consultationList = new ArrayList<>();
        List<String> consultationListString = new ArrayList<>();

        consultationService.getConsultationByProfessionalId(professionalId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consultationList.clear();
                consultationListString.clear();

                int consultationNumber = 1;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Consultation consultation = snapshot.getValue(Consultation.class);
                    consultationList.add(consultation);
                    consultationListString.add("Consultation " + consultationNumber + " - " + consultation.toStringConsultation());
                    consultationNumber++;
                }

                adapterSpinner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        adapterSpinner = new ArrayAdapter<>(GenerateAppointmentsProfessionalActivity.this, R.layout.custom_item_spinner, consultationListString);
        adapterSpinner.setDropDownViewResource(R.layout.custom_item_spinner);
        spinnerConsultation.setAdapter(adapterSpinner);

        spinnerConsultation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedConsultation = consultationList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.w("Error", "Please select one option");
            }
        });

        btnGenerate.setOnClickListener(v -> {
            int duration = durationPicker.getValue();
            LocalDate currentDate = LocalDate.now();

            if (!rbOneWeek.isChecked() && !rbOneMonth.isChecked()) {

                Toast.makeText(GenerateAppointmentsProfessionalActivity.this, "Please select one option", Toast.LENGTH_SHORT).show();

            } else {

                if (rbOneWeek.isChecked()) {
                    LocalDate futureDate = currentDate.plusDays(7);
                    generateAppointments(currentDate, futureDate, duration);
                } else if (rbOneMonth.isChecked()) {
                    LocalDate futureDate = currentDate.plusDays(30);
                    generateAppointments(currentDate, futureDate, duration);
                }
            }
        });

        ivBack.setOnClickListener(v -> finish());
    }

    private void generateAppointments(LocalDate startDate, LocalDate endDate, int duration) {
        TimetableService timetableService = new TimetableService(getApplicationContext());
        timetableService.getTimetableByConsultationId(selectedConsultation.getId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Timetable> timetableList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TimetableString timetableString = snapshot.getValue(TimetableString.class);
                    timetableList.add(timetableString.convertToTimeTable());
                }

                List<LocalDateTime> generatedAppointments = new ArrayList<>();

                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                    for (Timetable timetable : timetableList) {
                        LocalTime startTime = timetable.getStartTime();
                        LocalTime endTime = timetable.getEndTime();

                        startTime = startTime.plusMinutes((duration - (startTime.getMinute() % duration)) % duration);

                        if (timetable.getDayOfWeek().equalsIgnoreCase(date.getDayOfWeek().toString())) {
                            while (startTime.plusMinutes(duration).isBefore(endTime) || (startTime.plusMinutes(duration).equals(endTime))) {
                                LocalDateTime appointmentDateTime = LocalDateTime.of(date, startTime);
                                generatedAppointments.add(appointmentDateTime);
                                startTime = startTime.plusMinutes(duration);
                            }
                        }
                    }
                }

                insertAppointments(selectedConsultation.getId(), generatedAppointments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });
    }


    private void insertAppointments(String consultationId, List<LocalDateTime> appointmentList) {
        AppointmentService appointmentService = new AppointmentService(getApplicationContext());

        for (LocalDateTime appointmentDateTime : appointmentList) {
            Appointment appointment = new Appointment();
            appointment.setActive(true);
            appointment.setConsultationId(consultationId);
            appointment.setAppointmentDate(appointmentDateTime.toLocalDate());
            appointment.setAppointmentTime(appointmentDateTime.toLocalTime());

            appointmentService.insertAppointment(appointment);
        }

        Toast.makeText(GenerateAppointmentsProfessionalActivity.this, "Appointments was successfully generated", Toast.LENGTH_SHORT).show();
    }
}