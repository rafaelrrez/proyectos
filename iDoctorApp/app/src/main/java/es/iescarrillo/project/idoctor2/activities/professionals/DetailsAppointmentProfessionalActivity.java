package es.iescarrillo.project.idoctor2.activities.professionals;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.AppointmentPatientAdapter;
import es.iescarrillo.project.idoctor2.adapters.AppointmentProfessionalAdapter;
import es.iescarrillo.project.idoctor2.adapters.ConsultationProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.services.AppointmentService;

public class DetailsAppointmentProfessionalActivity extends AppCompatActivity {

    private Appointment appointment;
    private int selectedEditTextId;
    private EditText etHour, etDate;
    private Button btnDelete, btnEdit;
    //private AppointmentPatientAdapter appointmentProfessionalAdapter;
    private AppointmentProfessionalAdapter appointmentProfessionalAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_appointment_professional);

        AppointmentService appointmentService = new AppointmentService(getApplicationContext());

        appointment = (Appointment) getIntent().getSerializableExtra("appointment");

        etDate = findViewById(R.id.etDate);
        etHour = findViewById(R.id.etHour);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        etDate.setText(appointment.getAppointmentDate().toString());
        etHour.setText(appointment.getAppointmentTime().toString());

        ImageView ivBack = findViewById(R.id.ivBack);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate input fields
                if (etDate.getText().toString().isEmpty()) {
                    Toast.makeText(DetailsAppointmentProfessionalActivity.this, "Please enter the appointment's date", Toast.LENGTH_SHORT).show();
                    etDate.setError("Date is required");
                    etDate.requestFocus();
                } else if (etHour.getText().toString().isEmpty()) {
                    Toast.makeText(DetailsAppointmentProfessionalActivity.this, "Please enter the appointment's hour", Toast.LENGTH_SHORT).show();
                    etHour.setError("Hour is required");
                    etHour.requestFocus();
                } else {
                    appointment.setAppointmentDate(LocalDate.parse(etDate.getText().toString()));
                    appointment.setAppointmentTime(LocalTime.parse(etHour.getText().toString()));

                    appointmentService.updateAppointment(appointment);

                    Toast.makeText(DetailsAppointmentProfessionalActivity.this, "Appointment has been successfuly edited", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentService.deleteAppointment(appointment.getId());

                Toast.makeText(DetailsAppointmentProfessionalActivity.this, "The appointment has been successfully deleted", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        ivBack.setOnClickListener(v -> finish());
    }

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
                etHour.setText(selectedTime.toString());
            }
        },
                hourOfDay,
                minute,
                true
        );

        timePickerDialog.show();
    }
}