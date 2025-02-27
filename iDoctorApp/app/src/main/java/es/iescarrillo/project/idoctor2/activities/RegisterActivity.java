package es.iescarrillo.project.idoctor2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.models.Person;
import es.iescarrillo.project.idoctor2.models.Professional;

import org.mindrot.jbcrypt.BCrypt;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnContinue = findViewById(R.id.btnContinue);
        RadioButton rbProfessional = findViewById(R.id.rbProfessional);
        RadioButton rbPatient = findViewById(R.id.rbPatient);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etName = findViewById(R.id.etName);
        EditText etSurname = findViewById(R.id.etSurname);
        ImageView ivBack = findViewById(R.id.ivBack);

        ColorStateList colorSelected = ContextCompat.getColorStateList(this, R.color.colorSelected);
        ColorStateList colorUnselected = ContextCompat.getColorStateList(this, R.color.colorUnselected);

        setupRadioButton(rbPatient, colorSelected, colorUnselected);
        setupRadioButton(rbProfessional, colorSelected, colorUnselected);

        btnContinue.setOnClickListener(v -> {
            if (etUsername.getText().toString().isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                etUsername.setError("Username is required");
                etUsername.requestFocus();
                hideKeyboard();
            } else if (etPassword.getText().toString().isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                etPassword.setError("Password is required");
                etPassword.requestFocus();
                hideKeyboard();
            } else if (etPassword.getText().toString().length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
                etPassword.setError("Password too weak");
                etPassword.requestFocus();
                hideKeyboard();
            } else if (etName.getText().toString().isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                etName.setError("Name number is required");
                etName.requestFocus();
                hideKeyboard();
            } else if (etSurname.getText().toString().isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please enter your surname", Toast.LENGTH_SHORT).show();
                etSurname.setError("Surname is required");
                etSurname.requestFocus();
                hideKeyboard();
            } else if (!rbPatient.isChecked() && !rbProfessional.isChecked()) {
                Toast.makeText(RegisterActivity.this, "Please select account type", Toast.LENGTH_SHORT).show();
                hideKeyboard();
            } else {

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("users");

                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean userFound = false;

                        for (DataSnapshot userSnapshot : snapshot.child("patients").getChildren()) {

                            String username = userSnapshot.child("username").getValue(String.class);

                            if (username.equals(etUsername.getText().toString())) {
                                userFound = true;
                                break;
                            }
                        }

                        for (DataSnapshot userSnapshot : snapshot.child("professionals").getChildren()) {
                            String username = userSnapshot.child("username").getValue(String.class);

                            if (username.equals(etUsername.getText().toString())) {
                                userFound = true;
                                break;
                            }
                        }

                        if (!userFound) {

                            if (rbProfessional.isChecked()) {

                                Professional professional = new Professional();

                                professional.setUserRole(Person.UserRole.PROFESSIONAL);
                                professional.setUsername(etUsername.getText().toString());
                                professional.setPassword(BCrypt.hashpw(etPassword.getText().toString(), BCrypt.gensalt(5)));
                                professional.setName(etName.getText().toString());
                                professional.setSurname(etSurname.getText().toString());

                                Intent registerProfessionalIntent = new Intent(getApplicationContext(), RegisterProfessionalActivity.class);
                                registerProfessionalIntent.putExtra("professional", professional);
                                startActivity(registerProfessionalIntent);

                            } else {

                                Patient patient = new Patient();

                                patient.setUserRole(Person.UserRole.PATIENT);
                                patient.setUsername(etUsername.getText().toString());
                                patient.setPassword(BCrypt.hashpw(etPassword.getText().toString(), BCrypt.gensalt(5)));
                                patient.setName(etName.getText().toString());
                                patient.setSurname(etSurname.getText().toString());

                                Intent registerPatientIntent = new Intent(RegisterActivity.this, RegisterPatientActivity.class);
                                registerPatientIntent.putExtra("patient", patient);
                                startActivity(registerPatientIntent);
                            }

                        } else {

                            Toast.makeText(RegisterActivity.this, "Sorry, this username is already taken. Please choose a different one.", Toast.LENGTH_SHORT).show();
                            etUsername.setError("Username already in use");
                            etUsername.requestFocus();
                            hideKeyboard();

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

    private void setupRadioButton(RadioButton radioButton, ColorStateList colorSelected, ColorStateList colorUnselected) {
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> buttonView.setButtonTintList(isChecked ? colorSelected : colorUnselected));
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}