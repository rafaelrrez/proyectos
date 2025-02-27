package es.iescarrillo.project.idoctor2.activities.professionals;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Assessment;
import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.services.PatientService;
import io.woong.shapedimageview.ShapedImageView;

public class DetailsAssessmentProfessionalActivity extends AppCompatActivity {

    private PatientService patientService;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_IDoctor2);
        setContentView(R.layout.activity_details_assessment_professional);

        Assessment assessment = (Assessment) getIntent().getSerializableExtra("assessment");
        patientService = new PatientService(getApplicationContext());

        TextView tvUserName = findViewById(R.id.tvUsername);
        TextView tvStart = findViewById(R.id.tvStars);
        TextView tvDate = findViewById(R.id.tvDate);
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvDescription = findViewById(R.id.tvDescription);
        ImageView ivBack = findViewById(R.id.ivBack);
        ShapedImageView shImagePatient = findViewById(R.id.shImagePatient);

        tvUserName.setText(assessment.getUsername().toString());
        tvStart.setText(assessment.getStars().toString());

        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter TimeFormat = DateTimeFormatter.ofPattern("HH:mm");

        tvDate.setText(assessment.getAssessmentDateTime().format(DateFormat));
        tvTime.setText(assessment.getAssessmentDateTime().format(TimeFormat));
        tvTitle.setText(assessment.getTitle().toString());
        tvDescription.setText(assessment.getDescription().toString());

        patientService.getPatientByUserName(tvUserName.getText().toString(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    patient = snapshot.getValue(Patient.class);

                    if (patient.getPhoto() != null) {

                        Uri uri = Uri.parse(patient.getPhoto());
                        Picasso.get().load(uri).into(shImagePatient);

                    } else {
                        shImagePatient.setImageResource(R.drawable.user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja errores de lectura de la base de datos si es necesario
                Log.w("Firebase", "Error reading the database", databaseError.toException());
            }
        });

        ivBack.setOnClickListener(v -> finish());
    }
}