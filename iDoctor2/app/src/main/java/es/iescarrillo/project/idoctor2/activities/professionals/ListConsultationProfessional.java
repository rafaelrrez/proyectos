package es.iescarrillo.project.idoctor2.activities.professionals;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.services.ConsultationService;

public class ListConsultationProfessional extends AppCompatActivity {

    private static final String PHONE_NUMBER_PATTERN = "^\\+34\\d{9}$|^34\\d{9}$|^\\d{9}$";
    private ConsultationService consultationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_consultation_professional);

        consultationService = new ConsultationService(getApplicationContext());

        SharedPreferences sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "0");

        EditText etAddress = findViewById(R.id.etAddress);
        EditText etCity = findViewById(R.id.etCity);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etPhoneAux = findViewById(R.id.etPhoneAux);
        Button btnCreate = findViewById(R.id.btnCreate);
        ImageView ivBack = findViewById(R.id.ivBack);

        btnCreate.setOnClickListener(v -> {
            if (etAddress.getText().toString().isEmpty()) {
                Toast.makeText(ListConsultationProfessional.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                etAddress.setError("Address is required");
                etAddress.requestFocus();
                hideKeyboard();
            } else if (etCity.getText().toString().isEmpty()) {
                Toast.makeText(ListConsultationProfessional.this, "Please enter your city", Toast.LENGTH_SHORT).show();
                etCity.setError("City is required");
                etCity.requestFocus();
                hideKeyboard();
            } else if (etEmail.getText().toString().isEmpty()) {
                Toast.makeText(ListConsultationProfessional.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                etEmail.setError("Email is required");
                etEmail.requestFocus();
                hideKeyboard();
            } else if (etPhone.getText().toString().isEmpty()) {
                Toast.makeText(ListConsultationProfessional.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                etPhone.setError("Phone number is required");
                etPhone.requestFocus();
                hideKeyboard();
            } else if (etPhoneAux.getText().toString().isEmpty()) {
                Toast.makeText(ListConsultationProfessional.this, "Please enter your auxiliar number", Toast.LENGTH_SHORT).show();
                etPhoneAux.setError("Phone number is required");
                etPhoneAux.requestFocus();
                hideKeyboard();
            } else if (!etPhone.getText().toString().matches(PHONE_NUMBER_PATTERN)) {
                Toast.makeText(ListConsultationProfessional.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                etPhone.setError("Valid phone number is required");
                etPhone.requestFocus();
                hideKeyboard();
            } else if (!etPhoneAux.getText().toString().matches(PHONE_NUMBER_PATTERN)) {
                Toast.makeText(ListConsultationProfessional.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                etPhoneAux.setError("Valid phone number is required");
                etPhoneAux.requestFocus();
                hideKeyboard();
            } else {

                Consultation consultation = new Consultation();
                consultation.setAddress(etAddress.getText().toString());
                consultation.setCity(etCity.getText().toString());
                consultation.setEmail(etEmail.getText().toString());
                consultation.setPhone(etPhone.getText().toString());
                consultation.setPhoneAux(etPhoneAux.getText().toString());
                consultation.setProfessionalId(userId);

                consultationService.insertConsultation(consultation);

                Toast.makeText(ListConsultationProfessional.this, "Consultation created successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
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