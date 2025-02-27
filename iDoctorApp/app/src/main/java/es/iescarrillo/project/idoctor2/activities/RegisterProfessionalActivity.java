package es.iescarrillo.project.idoctor2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Professional;
import es.iescarrillo.project.idoctor2.services.ProfessionalService;

public class RegisterProfessionalActivity extends AppCompatActivity {

    private ProfessionalService professionalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_professional);

        EditText etCollegiateNumber = findViewById(R.id.etCollegiateNumber);
        RadioButton rbGeneral = findViewById(R.id.rbGeneral);
        RadioButton rbPhysiotherapy = findViewById(R.id.rbPhysiotherapy);
        RadioButton rbDentistry = findViewById(R.id.rbDentistry);
        EditText etDescription = findViewById(R.id.etDescription);
        ImageView ivBack = findViewById(R.id.ivBack);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        professionalService = new ProfessionalService(getApplicationContext());

        btnSignUp.setOnClickListener(v -> {
            if (etCollegiateNumber.getText().toString().isEmpty()) {
                Toast.makeText(RegisterProfessionalActivity.this, "Please enter your collegiate number", Toast.LENGTH_SHORT).show();
                etCollegiateNumber.setError("Collegiate number is required");
                etCollegiateNumber.requestFocus();
                hideKeyboard();
            } else if (!rbGeneral.isChecked() && !rbPhysiotherapy.isChecked() && !rbDentistry.isChecked()) {
                Toast.makeText(RegisterProfessionalActivity.this, "Please select a profession", Toast.LENGTH_SHORT).show();
            } else if (etDescription.getText().toString().isEmpty()) {
                Toast.makeText(RegisterProfessionalActivity.this, "Please enter your description", Toast.LENGTH_SHORT).show();
                etDescription.setError("Description is required");
                etDescription.requestFocus();
                hideKeyboard();
            } else {

                Professional professional = (Professional) getIntent().getSerializableExtra("professional");

                if (rbGeneral.isChecked()) {

                    professional.setCollegiateNumber(etCollegiateNumber.getText().toString());
                    professional.setSpeciality(Professional.Speciality.GENERAL);
                    professional.setDescription(etDescription.getText().toString());
                    professional.setStars(0.0);
                    professional.setAssessments(0);

                    professionalService.insertProfessional(professional);

                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    Toast.makeText(RegisterProfessionalActivity.this, "Professional was registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(loginIntent);
                    finish();

                } else if (rbPhysiotherapy.isChecked()) {

                    professional.setCollegiateNumber(etCollegiateNumber.getText().toString());
                    professional.setSpeciality(Professional.Speciality.PHYSIOTHERAPY);
                    professional.setDescription(etDescription.getText().toString());
                    professional.setStars(0.0);
                    professional.setAssessments(0);

                    professionalService.insertProfessional(professional);

                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    Toast.makeText(RegisterProfessionalActivity.this, "Professional was registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(loginIntent);
                    finish();

                } else if (rbDentistry.isChecked()) {

                    professional.setCollegiateNumber(etCollegiateNumber.getText().toString());
                    professional.setSpeciality(Professional.Speciality.DENTISTRY);
                    professional.setDescription(etDescription.getText().toString());
                    professional.setStars(0.0);
                    professional.setAssessments(0);

                    professionalService.insertProfessional(professional);

                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    Toast.makeText(RegisterProfessionalActivity.this, "Professional was registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(loginIntent);
                    finish();

                }

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