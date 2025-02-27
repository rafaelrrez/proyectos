package es.iescarrillo.project.idoctor2.activities.professionals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.AssessmentsProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Assessment;
import es.iescarrillo.project.idoctor2.models.AssessmentString;
import es.iescarrillo.project.idoctor2.services.AssessmentService;

public class ListAssessmentsProfessionalActivity extends AppCompatActivity {

    private AssessmentsProfessionalAdapter assessmentsProfessionalAdapter;
    private List<Assessment> assessmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_assessments_professional);

        ImageView ivBack = findViewById(R.id.ivBack);
        ListView lvAssessments = findViewById(R.id.lvAssessments);

        AssessmentService assessmentService = new AssessmentService(getApplicationContext());

        assessmentList = new ArrayList<Assessment>();
        assessmentService.getAllAssessment(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assessmentList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AssessmentString assessment = snapshot.getValue(AssessmentString.class);
                    assessmentList.add(assessment.convertToAssessment());

                }

                assessmentsProfessionalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        assessmentsProfessionalAdapter = new AssessmentsProfessionalAdapter(getApplicationContext(), assessmentList);
        lvAssessments.setAdapter(assessmentsProfessionalAdapter);

        lvAssessments.setOnItemClickListener((parent, view, position, id) -> {
            Assessment selectAssessment = (Assessment) parent.getItemAtPosition(position);
            Intent intentDetailAssessmentProfessional = new Intent(getApplicationContext(), DetailsAssessmentProfessionalActivity.class);
            intentDetailAssessmentProfessional.putExtra("assessment", selectAssessment);
            startActivity(intentDetailAssessmentProfessional);
        });

        ivBack.setOnClickListener(v -> finish());
    }
}