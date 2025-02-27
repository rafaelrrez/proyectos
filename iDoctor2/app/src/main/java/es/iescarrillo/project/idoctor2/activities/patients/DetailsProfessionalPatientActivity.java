package es.iescarrillo.project.idoctor2.activities.patients;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.AssessmentsProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Assessment;
import es.iescarrillo.project.idoctor2.models.AssessmentString;
import es.iescarrillo.project.idoctor2.models.Professional;
import es.iescarrillo.project.idoctor2.services.AssessmentService;
import io.woong.shapedimageview.ShapedImageView;

public class DetailsProfessionalPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_professional_patient_activity);

        Professional professional = (Professional) getIntent().getSerializableExtra("professional");
        String professionalID = getIntent().getStringExtra("professionalID");

        TextView tvNameSurname = findViewById(R.id.tvNameSurname);
        TextView tvSpeciality = findViewById(R.id.tvSpeciality);
        TextView tvStars = findViewById(R.id.tvStars);
        TextView tvAssessments = findViewById(R.id.tvAssessments);
        TextView tvDescription = findViewById(R.id.tvDescription);
        ListView lvAssessments = findViewById(R.id.lvAssessments);
        ImageView ivBack = findViewById(R.id.ivBack);
        Button btnBookAppointment = findViewById(R.id.btnBookAppointment);
        ShapedImageView shImageProfessional = findViewById(R.id.shImageProfessional);

        tvNameSurname.setText(professional.getName() + " " + professional.getSurname());
        tvSpeciality.setText(professional.getSpeciality().toString());
        tvStars.setText(String.format(Locale.getDefault(), "%.1f", professional.getStars()));
        tvAssessments.setText(String.format(Locale.getDefault(), "%d", professional.getAssessments()));
        tvDescription.setText(professional.getDescription());

        if (professional.getPhoto() != null) {

            Uri uri = Uri.parse(professional.getPhoto());
            Picasso.get().load(uri).into(shImageProfessional);

        }

        AssessmentService assessmentService = new AssessmentService(getApplicationContext());
        List<Assessment> assessmentList = new ArrayList<>();

        assessmentService.getAllAssessment(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assessmentList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AssessmentString assessmentString = snapshot.getValue(AssessmentString.class);
                    if (assessmentString.getProfessionalId().equals(professionalID)) {
                        assessmentList.add(assessmentString.convertToAssessment());
                    }
                }
                AssessmentsProfessionalAdapter assessmentsProfessionalPatientAdapter = new AssessmentsProfessionalAdapter(getApplicationContext(), assessmentList);
                lvAssessments.setAdapter(assessmentsProfessionalPatientAdapter);

                assessmentsProfessionalPatientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });


        ivBack.setOnClickListener(v -> {
            finish();
        });

        btnBookAppointment.setOnClickListener(v -> {
            Intent intentBookAppoinmentPatient = new Intent(getApplicationContext(), ListConsultationProfessionalPatientActivity.class);
            intentBookAppoinmentPatient.putExtra("professionalID", professionalID);
            startActivity(intentBookAppoinmentPatient);

        });
    }
}