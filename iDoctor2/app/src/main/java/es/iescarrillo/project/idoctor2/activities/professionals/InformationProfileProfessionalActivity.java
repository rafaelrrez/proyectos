package es.iescarrillo.project.idoctor2.activities.professionals;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Professional;
import es.iescarrillo.project.idoctor2.services.ProfessionalService;

public class InformationProfileProfessionalActivity extends AppCompatActivity {

    private ProfessionalService professionalService;
    private Professional professional;
    private static final int PICK_IMAGE_REQUEST = 1;
    private CircleImageView civProfile;
    private Uri uriImage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_professional_profile);

        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");

        Button btnEdit = findViewById(R.id.btnEdit);
        ImageView ivBack = findViewById(R.id.ivBack);
        EditText etName = findViewById(R.id.etName);
        EditText etSurname = findViewById(R.id.etSurname);
        EditText etDescription = findViewById(R.id.etDescription);
        ImageView ivPhoto = findViewById(R.id.ivPhoto);
        civProfile = findViewById(R.id.civProfile);

        SharedPreferences sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "0");

        professionalService = new ProfessionalService(getApplicationContext());

        professionalService.getProfessionalById(userId, new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    professional = snapshot.getValue(Professional.class);

                    etName.setText(professional.getName());
                    etSurname.setText(professional.getSurname());
                    etDescription.setText(professional.getDescription());

                    if (professional.getPhoto() != null) {

                        Uri uri = Uri.parse(professional.getPhoto());
                        Picasso.get().load(uri).into(civProfile);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.w("Firebase", "Error en la lectura de la base de datos", databaseError.toException());

            }
        });

        btnEdit.setOnClickListener(v -> {
            if (etName.toString().isEmpty()) {
                Toast.makeText(InformationProfileProfessionalActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                etName.setError("Name number is required");
                etName.requestFocus();
                hideKeyboard();
            } else if (etSurname.toString().isEmpty()) {
                Toast.makeText(InformationProfileProfessionalActivity.this, "Please enter your surname", Toast.LENGTH_SHORT).show();
                etSurname.setError("Surname is required");
                etSurname.requestFocus();
                hideKeyboard();
            } else if (etDescription.toString().isEmpty()) {
                Toast.makeText(InformationProfileProfessionalActivity.this, "Please enter your descriptrion", Toast.LENGTH_SHORT).show();
                etDescription.setError("Description is required");
                etDescription.requestFocus();
                hideKeyboard();
            } else {
                professional.setName(etName.getText().toString());
                professional.setSurname(etSurname.getText().toString());
                professional.setDescription(etDescription.getText().toString());

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
            storageReference = storageReference.child(professional.getId() + "." + getFileExtension(uriImage));
            storageReference.putFile(uriImage)
                    .addOnSuccessListener(taskSnapshot -> {

                        storageReference.getDownloadUrl().addOnSuccessListener(photoUri -> {

                            professional.setPhoto(photoUri.toString());

                            updateProfessionalProfile();
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(InformationProfileProfessionalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(InformationProfileProfessionalActivity.this, "No File Selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProfessionalProfile() {
        professionalService.updateProfessional(professional);
        finish();
    }
}