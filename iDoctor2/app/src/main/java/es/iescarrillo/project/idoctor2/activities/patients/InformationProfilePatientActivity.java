package es.iescarrillo.project.idoctor2.activities.patients;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.services.PatientService;

public class InformationProfilePatientActivity extends AppCompatActivity {
    private static final String PHONE_NUMBER_PATTERN = "^\\+34\\d{9}$|^34\\d{9}$|^\\d{9}$";
    private PatientService patientService;
    private Patient patient;
    private static final int PICK_IMAGE_REQUEST = 1;
    private CircleImageView civProfile;
    private Uri uriImage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_profile_patient);

        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");

        Button btnEdit = findViewById(R.id.btnEdit);
        ImageView ivBack = findViewById(R.id.ivBack);
        EditText etName = findViewById(R.id.etName);
        EditText etSurname = findViewById(R.id.etSurname);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPhone = findViewById(R.id.etPhone);
        SwitchCompat swInsurance = findViewById(R.id.swInsurance);
        ImageView ivPhoto = findViewById(R.id.ivPhoto);
        civProfile = findViewById(R.id.civProfile);

        SharedPreferences sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "0");

        patientService = new PatientService(getApplicationContext());

        patientService.getPatientById(userId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    patient = snapshot.getValue(Patient.class);

                    etName.setText(patient.getName());
                    etSurname.setText(patient.getSurname());
                    etEmail.setText(patient.getEmail());
                    etPhone.setText(patient.getPhone());
                    swInsurance.setChecked(patient.getHealthInsurance());

                    if (patient.getPhoto() != null) {

                        Uri uri = Uri.parse(patient.getPhoto());
                        Picasso.get().load(uri).into(civProfile);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja errores de lectura de la base de datos si es necesario
                Log.w("Firebase", "Error en la lectura de la base de datos", databaseError.toException());
            }
        });


        btnEdit.setOnClickListener(v -> {
            if (etName.toString().isEmpty()) {
                Toast.makeText(InformationProfilePatientActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                etName.setError("Name number is required");
                etName.requestFocus();
                hideKeyboard();
            } else if (etSurname.toString().isEmpty()) {
                Toast.makeText(InformationProfilePatientActivity.this, "Please enter your surname", Toast.LENGTH_SHORT).show();
                etSurname.setError("Surname is required");
                etSurname.requestFocus();
                hideKeyboard();
            } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
                Toast.makeText(InformationProfilePatientActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                etEmail.setError("Email is required");
                etEmail.requestFocus();
                hideKeyboard();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                Toast.makeText(InformationProfilePatientActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                etEmail.setError("Valid email is required");
                etEmail.requestFocus();
                hideKeyboard();
            } else if (etPhone.getText().toString().isEmpty()) {
                Toast.makeText(InformationProfilePatientActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                etPhone.setError("Phone number is required");
                etPhone.requestFocus();
                hideKeyboard();
            } else if (!etPhone.getText().toString().matches(PHONE_NUMBER_PATTERN)) {
                Toast.makeText(InformationProfilePatientActivity.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                etPhone.setError("Valid phone number is required");
                etPhone.requestFocus();
                hideKeyboard();
            } else {

                patient.setName(etName.getText().toString());
                patient.setSurname(etSurname.getText().toString());
                patient.setEmail(etEmail.getText().toString());
                patient.setPhone(etPhone.getText().toString());
                patient.setHealthInsurance(swInsurance.isChecked());

                uploadPic();
            }
        });

        ivBack.setOnClickListener(v -> finish());
        ivPhoto.setOnClickListener(v -> openFileChooser());
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            civProfile.setImageURI(uriImage);
        }
    }

    private String getFileExtension(Uri uriImage) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uriImage));
    }

    private void uploadPic() {
        if (uriImage != null) {
            storageReference = storageReference.child(patient.getId() + "." + getFileExtension(uriImage));
            storageReference.putFile(uriImage)
                    .addOnSuccessListener(taskSnapshot -> {
                        // La imagen se ha subido con éxito, ahora obtenemos la URL de descarga
                        storageReference.getDownloadUrl().addOnSuccessListener(photoUri -> {
                            // Actualizamos la URL de la foto en el objeto Professional
                            patient.setPhoto(photoUri.toString());
                            // Ahora, después de obtener la URL, actualizamos también el perfil del usuario
                            updatePatientProfile();
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(InformationProfilePatientActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(InformationProfilePatientActivity.this, "No File Selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePatientProfile() {
        patientService.updatePatient(patient);
        finish();
    }
}