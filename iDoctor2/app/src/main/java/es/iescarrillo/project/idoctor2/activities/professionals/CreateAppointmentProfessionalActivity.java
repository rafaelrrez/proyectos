package es.iescarrillo.project.idoctor2.activities.professionals;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.AppointmentProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.services.AppointmentService;
import es.iescarrillo.project.idoctor2.services.ConsultationService;
import es.iescarrillo.project.idoctor2.services.PatientService;

public class CreateAppointmentProfessionalActivity extends AppCompatActivity {
    private Appointment appointment;
    private AppointmentService appointmentService;
    private ConsultationService consultationService;
    private PatientService patientService;
    private DatabaseReference consultationRef;
    private DatabaseReference patientRef;
    private SharedPreferences sharedPreferences;
    private Consultation selectedConsultation;
    private int selectedEditTextId;
    private Consultation consultation;
    private Patient patient;
    private EditText etAppointmentHour;
    private ArrayAdapter<String> adapterSpinner;
    private AppointmentProfessionalAdapter appointmentProfessionalAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment_professional);
        consultationService = new ConsultationService(getApplicationContext());
        appointmentService = new AppointmentService(getApplicationContext());
        patientService = new PatientService(getApplicationContext());
        sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String professionalId = sharedPreferences.getString("id", "0");

        AppCompatSpinner spinnerConsultation = findViewById(R.id.spinnerConsultation);

        EditText etAppointmentDate = findViewById(R.id.etAppointmentDate);

        etAppointmentHour = findViewById(R.id.etAppointmentHour);

        Button btnCreate = findViewById(R.id.btnCreate);

        ImageView ivBack = findViewById(R.id.ivBack);


        SharedPreferences.Editor editor = sharedPreferences.edit();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        consultationRef = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("consultations");

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

        adapterSpinner = new ArrayAdapter<>(CreateAppointmentProfessionalActivity.this, R.layout.custom_item_spinner, consultationListString);
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

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate input fields
                if (etAppointmentDate.getText().toString().isEmpty()) {
                    Toast.makeText(CreateAppointmentProfessionalActivity.this, "Please enter the appointment's date", Toast.LENGTH_SHORT).show();
                    etAppointmentDate.setError("Date is required");
                    etAppointmentDate.requestFocus();
                } else if (etAppointmentHour.getText().toString().isEmpty()) {
                    Toast.makeText(CreateAppointmentProfessionalActivity.this, "Please enter the appointment's hour", Toast.LENGTH_SHORT).show();
                    etAppointmentHour.setError("Hour is required");
                    etAppointmentHour.requestFocus();
                } else {

                    appointment = new Appointment();
                    appointment.setAppointmentDate(LocalDate.parse(etAppointmentDate.getText().toString()));
                    appointment.setAppointmentTime(LocalTime.parse(etAppointmentHour.getText().toString()));
                    appointment.setActive(true);
                    appointment.setConsultationId(selectedConsultation.getId());
                    appointmentService.insertAppointment(appointment);
                    Toast.makeText(CreateAppointmentProfessionalActivity.this, "Appointment created successfuly", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        ivBack.setOnClickListener(v -> finish());
    }

    //Function to show date picker dialog
    public void showDatePickerDialog(View view) {
        selectedEditTextId = view.getId(); // Almacena el ID del EditText seleccionado

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, dayOfMonth);

                EditText selectedEditText = findViewById(selectedEditTextId);
                if (selectedEditText != null) {
                    selectedEditText.setText(formattedDate);
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        dialog.show();

    }

    public void showTimePickerDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                LocalTime selectedTime = LocalTime.of(hourOfDay, minute);
                etAppointmentHour.setText(selectedTime.toString());
            }
        },
                hourOfDay,
                minute,
                true
        );

        timePickerDialog.show();
    }
}
