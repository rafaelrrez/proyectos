package es.iescarrillo.project.idoctor2.activities.professionals;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.datepickers.TimePickerHelper;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Timetable;
import es.iescarrillo.project.idoctor2.models.TimetableString;
import es.iescarrillo.project.idoctor2.services.TimetableService;

public class CreateTimetableConsultationProfessionalActivity extends AppCompatActivity {

    private EditText etEndTime, etStartTime;
    private final String[] daysOfWeek = new String[]{
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timetable_consultation_professional);

        String professionalId = getIntent().getStringExtra("professionalId");

        etEndTime = findViewById(R.id.etEndTime);
        etStartTime = findViewById(R.id.etStartTime);
        ImageView ivBack = findViewById(R.id.ivBack);
        Button btnCreate = findViewById(R.id.btnCreate);
        AppCompatSpinner spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);

        TimetableService timetableService = new TimetableService(getApplicationContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateTimetableConsultationProfessionalActivity.this, android.R.layout.simple_list_item_1, daysOfWeek);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerDayOfWeek.setAdapter(adapter);

        etStartTime.setOnClickListener(v -> TimePickerHelper.showTimePickerDialog(CreateTimetableConsultationProfessionalActivity.this, 1, (selectedTime, callerId) -> {
            if (callerId == 1) {
                etStartTime.setText(selectedTime.toString());
            }
        }));

        etEndTime.setOnClickListener(v -> TimePickerHelper.showTimePickerDialog(CreateTimetableConsultationProfessionalActivity.this, 2, (selectedTime, callerId) -> {
            if (callerId == 2) {
                etEndTime.setText(selectedTime.toString());
            }
        }));

        btnCreate.setOnClickListener(v -> {
            if (etEndTime.getText().toString().isEmpty() || etStartTime.getText().toString().isEmpty()) {
                Toast.makeText(CreateTimetableConsultationProfessionalActivity.this, "Please enter time", Toast.LENGTH_SHORT).show();
            } else if (LocalTime.parse(etStartTime.getText().toString()).isAfter(LocalTime.parse(etEndTime.getText().toString()))) {
                Toast.makeText(CreateTimetableConsultationProfessionalActivity.this, "Start time must be before end time", Toast.LENGTH_SHORT).show();
            } else {
                Consultation selectedConsultation = (Consultation) getIntent().getSerializableExtra("consultation");

                timetableService.getAllTimetable(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean timetableExists = false;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            TimetableString timetableString = snapshot.getValue(TimetableString.class);
                            Timetable existingTimetable = timetableString.convertToTimeTable();

                            if (existingTimetable.getConsultationId().equals(selectedConsultation.getId())
                                    && existingTimetable.getDayOfWeek().equals(spinnerDayOfWeek.getSelectedItem().toString())) {

                                timetableExists = true;
                                break;
                            }
                        }

                        if (timetableExists) {
                            Toast.makeText(CreateTimetableConsultationProfessionalActivity.this, "Timetable already exists for this day", Toast.LENGTH_SHORT).show();
                        } else {
                            Timetable timetable = new Timetable();
                            timetable.setConsultationId(selectedConsultation.getId());
                            timetable.setDayOfWeek(spinnerDayOfWeek.getSelectedItem().toString());
                            timetable.setStartTime(LocalTime.parse(etStartTime.getText().toString()));
                            timetable.setEndTime(LocalTime.parse(etEndTime.getText().toString()));

                            timetableService.insertTimetable(timetable);

                            Toast.makeText(CreateTimetableConsultationProfessionalActivity.this, "Timetable was successfully created", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Firebase", "Error reading the database", error.toException());
                    }
                });
            }
        });

        ivBack.setOnClickListener(v -> finish());
    }
}