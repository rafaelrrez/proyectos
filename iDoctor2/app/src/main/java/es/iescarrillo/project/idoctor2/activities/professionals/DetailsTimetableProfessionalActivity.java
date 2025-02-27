package es.iescarrillo.project.idoctor2.activities.professionals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.datepickers.TimePickerHelper;
import es.iescarrillo.project.idoctor2.models.Timetable;
import es.iescarrillo.project.idoctor2.models.TimetableString;
import es.iescarrillo.project.idoctor2.services.TimetableService;

public class DetailsTimetableProfessionalActivity extends AppCompatActivity {

    private final String[] daysOfWeek = new String[]{
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_timetable_professional);

        Timetable timetable = (Timetable) getIntent().getSerializableExtra("timetable");
        TimetableService timetableService = new TimetableService(getApplicationContext());
        EditText etStartTime = findViewById(R.id.etStartTime);
        EditText etEndTime = findViewById(R.id.etEndTime);
        AppCompatSpinner spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        ImageView ivBack = findViewById(R.id.ivBack);

        etStartTime.setText(timetable.getStartTime().toString());
        etEndTime.setText(timetable.getEndTime().toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailsTimetableProfessionalActivity.this, android.R.layout.simple_list_item_1, daysOfWeek);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerDayOfWeek.setAdapter(adapter);

        etStartTime.setOnClickListener(v -> TimePickerHelper.showTimePickerDialog(DetailsTimetableProfessionalActivity.this, 1, (selectedTime, callerId) -> {
            if (callerId == 1) {
                etStartTime.setText(selectedTime.toString());
            }
        }));

        etEndTime.setOnClickListener(v -> TimePickerHelper.showTimePickerDialog(DetailsTimetableProfessionalActivity.this, 2, (selectedTime, callerId) -> {
            if (callerId == 2) {
                etEndTime.setText(selectedTime.toString());
            }
        }));

        spinnerDayOfWeek.setSelection(adapter.getPosition(timetable.getDayOfWeek()));

        btnEdit.setOnClickListener(v -> {
            if (etEndTime.getText().toString().isEmpty() || etStartTime.getText().toString().isEmpty()) {
                Toast.makeText(DetailsTimetableProfessionalActivity.this, "Please enter time", Toast.LENGTH_SHORT).show();
            } else if (LocalTime.parse(etStartTime.getText().toString()).isAfter(LocalTime.parse(etEndTime.getText().toString()))) {
                Toast.makeText(DetailsTimetableProfessionalActivity.this, "Start time must be before end time", Toast.LENGTH_SHORT).show();
            } else {
                String selectedDay = spinnerDayOfWeek.getSelectedItem().toString();
                String originalDay = timetable.getDayOfWeek();

                if (!selectedDay.equals(originalDay)) {
                    timetableService.getAllTimetable(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean timetableExistsInOtherDays = false;

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                TimetableString timetableString = snapshot.getValue(TimetableString.class);
                                Timetable existingTimetable = timetableString.convertToTimeTable();

                                if (existingTimetable.getConsultationId().equals(timetable.getConsultationId())
                                        && existingTimetable.getDayOfWeek().equals(selectedDay)) {

                                    timetableExistsInOtherDays = true;
                                    break;
                                }
                            }

                            if (timetableExistsInOtherDays) {
                                Toast.makeText(DetailsTimetableProfessionalActivity.this, "Timetable already exists for this day in other days", Toast.LENGTH_SHORT).show();
                            } else {
                                timetable.setDayOfWeek(selectedDay);
                                timetable.setStartTime(LocalTime.parse(etStartTime.getText().toString()));
                                timetable.setEndTime(LocalTime.parse(etEndTime.getText().toString()));

                                timetableService.updateTimetable(timetable);

                                Toast.makeText(DetailsTimetableProfessionalActivity.this, "Timetable was successfully updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w("Firebase", "Error reading the database", error.toException());
                        }
                    });
                } else {
                    timetable.setDayOfWeek(originalDay);
                    timetable.setStartTime(LocalTime.parse(etStartTime.getText().toString()));
                    timetable.setEndTime(LocalTime.parse(etEndTime.getText().toString()));

                    timetableService.updateTimetable(timetable);

                    Toast.makeText(DetailsTimetableProfessionalActivity.this, "Timetable was successfully updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        btnDelete.setOnClickListener(v -> {
            timetableService.deleteTimetable(timetable.getId());

            Toast.makeText(DetailsTimetableProfessionalActivity.this, "Timetable was successfully deleted", Toast.LENGTH_SHORT).show();
            finish();
        });

        ivBack.setOnClickListener(v -> finish());
    }
}