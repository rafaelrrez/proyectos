package es.iescarrillo.project.idoctor2.activities.professionals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.services.ConsultationService;

public class DetailsConsultationProfessionalActivity extends AppCompatActivity {

    private static final String PHONE_NUMBER_PATTERN = "^\\+34\\d{9}$|^34\\d{9}$|^\\d{9}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_consultation_professional);

        ConsultationService consultationService = new ConsultationService(getApplicationContext());

        Consultation consultation = (Consultation) getIntent().getSerializableExtra("consultation");

        EditText etAddress = findViewById(R.id.etAddress);
        EditText etCity = findViewById(R.id.etCity);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etPhoneAux = findViewById(R.id.etPhoneAux);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        ImageView ivBack = findViewById(R.id.ivBack);

        etAddress.setText(consultation.getAddress().toString());
        etCity.setText(consultation.getCity().toString());
        etEmail.setText(consultation.getEmail().toString());
        etPhone.setText(consultation.getPhone().toString());
        etPhoneAux.setText(consultation.getPhoneAux().toString());

        btnEdit.setOnClickListener(v -> {

            if (etAddress.getText().toString().isEmpty()) {
                Toast.makeText(DetailsConsultationProfessionalActivity.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                etAddress.setError("Address is required");
                etAddress.requestFocus();
                hideKeyboard();
            } else if (etCity.getText().toString().isEmpty()) {
                Toast.makeText(DetailsConsultationProfessionalActivity.this, "Please enter your city", Toast.LENGTH_SHORT).show();
                etCity.setError("City is required");
                etCity.requestFocus();
                hideKeyboard();
            } else if (etEmail.getText().toString().isEmpty()) {
                Toast.makeText(DetailsConsultationProfessionalActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                etEmail.setError("Email is required");
                etEmail.requestFocus();
                hideKeyboard();
            } else if (etPhone.getText().toString().isEmpty()) {
                Toast.makeText(DetailsConsultationProfessionalActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                etPhone.setError("Phone number is required");
                etPhone.requestFocus();
                hideKeyboard();
            } else if (etPhoneAux.getText().toString().isEmpty()) {
                Toast.makeText(DetailsConsultationProfessionalActivity.this, "Please enter your auxiliar number", Toast.LENGTH_SHORT).show();
                etPhoneAux.setError("Phone number is required");
                etPhoneAux.requestFocus();
                hideKeyboard();
            } else if (!etPhone.getText().toString().matches(PHONE_NUMBER_PATTERN)) {
                Toast.makeText(DetailsConsultationProfessionalActivity.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                etPhone.setError("Valid phone number is required");
                etPhone.requestFocus();
                hideKeyboard();
            } else if (!etPhoneAux.getText().toString().matches(PHONE_NUMBER_PATTERN)) {
                Toast.makeText(DetailsConsultationProfessionalActivity.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                etPhoneAux.setError("Valid phone number is required");
                etPhoneAux.requestFocus();
                hideKeyboard();
            } else {

                consultation.setAddress(etAddress.getText().toString());
                consultation.setCity(etCity.getText().toString());
                consultation.setEmail(etEmail.getText().toString());
                consultation.setPhone(etPhone.getText().toString());
                consultation.setPhoneAux(etPhoneAux.getText().toString());

                consultationService.updateConsultation(consultation);

                Toast.makeText(DetailsConsultationProfessionalActivity.this, "The consultation was successfully updated", Toast.LENGTH_SHORT).show();

                finish();

            }

        });

        btnDelete.setOnClickListener(v -> {

            consultationService.deleteConsultation(consultation.getId());

            Toast.makeText(DetailsConsultationProfessionalActivity.this, "The consultation was successfully deleted", Toast.LENGTH_SHORT).show();

            finish();

        });

        ivBack.setOnClickListener(v -> finish());
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}