package es.iescarrillo.project.idoctor2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.services.PatientService;

public class RegisterPatientActivity extends AppCompatActivity {

    private static final String DNI_PATTERN = "\\d{8}[A-HJ-NP-TV-Z]";
    private static final String PHONE_NUMBER_PATTERN = "^\\+34\\d{9}$|^34\\d{9}$|^\\d{9}$";
    private PatientService patientService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etDni = findViewById(R.id.etDni);
        EditText etPhone = findViewById(R.id.etPhone);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        SwitchCompat switchInsurance = findViewById(R.id.switchInsurance);
        ImageView ivBack = findViewById(R.id.ivBack);

        patientService = new PatientService(getApplicationContext());

        btnSignUp.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etEmail.getText().toString())) {
                Toast.makeText(RegisterPatientActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                etEmail.setError("Email is required");
                etEmail.requestFocus();
                hideKeyboard();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                Toast.makeText(RegisterPatientActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                etEmail.setError("Valid email is required");
                etEmail.requestFocus();
                hideKeyboard();
            } else if (etDni.getText().toString().isEmpty()) {
                Toast.makeText(RegisterPatientActivity.this, "Please enter your DNI", Toast.LENGTH_SHORT).show();
                etDni.setError("DNI is required");
                etDni.requestFocus();
                hideKeyboard();
            } else if (!etDni.getText().toString().matches(DNI_PATTERN)) {
                Toast.makeText(RegisterPatientActivity.this, "Invalid DNI format", Toast.LENGTH_SHORT).show();
                etDni.setError("Valid DNI is required");
                etDni.requestFocus();
                hideKeyboard();
            } else if (etPhone.getText().toString().isEmpty()) {
                Toast.makeText(RegisterPatientActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                etPhone.setError("Phone number is required");
                etPhone.requestFocus();
                hideKeyboard();
            } else if (!etPhone.getText().toString().matches(PHONE_NUMBER_PATTERN)) {
                Toast.makeText(RegisterPatientActivity.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                etPhone.setError("Valid phone number is required");
                etPhone.requestFocus();
                hideKeyboard();
            } else {

                patientService.getAllPatients(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean dniInUse = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Patient patient = snapshot.getValue(Patient.class);

                            if (patient != null && patient.getDNI().equals(etDni.getText().toString())) {
                                dniInUse = true;
                                break;
                            }
                        }

                        if (dniInUse) {
                            Toast.makeText(RegisterPatientActivity.this, "DNI is already in use", Toast.LENGTH_SHORT).show();
                            etDni.setError("DNI is in use");
                            etDni.requestFocus();
                            hideKeyboard();
                        } else {

                            Patient newPatient = (Patient) getIntent().getSerializableExtra("patient");
                            newPatient.setDNI(etDni.getText().toString());
                            newPatient.setPhone(etPhone.getText().toString());
                            newPatient.setHealthInsurance(switchInsurance.isChecked());
                            newPatient.setEmail(etEmail.getText().toString());


                            patientService.insertPatient(newPatient);

                            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            Toast.makeText(RegisterPatientActivity.this, "Patient was registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(loginIntent);
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

        ivBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}