package es.iescarrillo.project.idoctor2.activities.patients;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Assessment;
import es.iescarrillo.project.idoctor2.models.Professional;
import es.iescarrillo.project.idoctor2.services.AssessmentService;
import es.iescarrillo.project.idoctor2.services.ProfessionalService;

public class CreateAssessmentPatientActivity extends AppCompatActivity {
    private String name, username, userId;
    private ProfessionalService professionalService;
    private AssessmentService assessmentService;
    private ArrayAdapter<String> adapterSpinner;
    private Professional selectedProfessional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment_patient);
        SharedPreferences sharedPreferences = getSharedPreferences("iDoctor", MODE_PRIVATE);
        name = sharedPreferences.getString("name", "0");
        username = sharedPreferences.getString("username", "0");
        userId = sharedPreferences.getString("id", "0");

        AppCompatSpinner spinneProfessionals = findViewById(R.id.spinneProfessionals);
        ImageView ivBack = findViewById(R.id.ivBack);
        EditText etTitle = findViewById(R.id.etTitle);
        TextView etDescription = findViewById(R.id.etDescription);
        RatingBar rbProfessional = findViewById(R.id.rbProfessional);
        Button btnCreate = findViewById(R.id.btnCreate);

        professionalService = new ProfessionalService(getApplicationContext());
        assessmentService = new AssessmentService(getApplicationContext());
        List<Professional> professionalList = new ArrayList<>();
        List<String> professionalListString = new ArrayList<>();
        List<Assessment> assessmentList = new ArrayList<>();

        // preinicialization of Stars Numbers
        rbProfessional.setNumStars(0);

        professionalService.getAllProfessional(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                professionalList.clear();
                professionalListString.clear();
                int professionalNumber = 1;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Professional professional = snapshot.getValue(Professional.class);
                    professionalList.add(professional);
                    professionalListString.add("Professional " + professionalNumber + " - " + professional.getName() + " " + professional.getSurname());
                    professionalNumber++;
                }
                adapterSpinner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });
        adapterSpinner = new ArrayAdapter<>(CreateAssessmentPatientActivity.this, R.layout.custom_item_spinner, professionalListString);
        adapterSpinner.setDropDownViewResource(R.layout.custom_item_spinner);
        spinneProfessionals.setAdapter(adapterSpinner);

        spinneProfessionals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProfessional = professionalList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.w("Error", "Please select one option");
            }
        });

        btnCreate.setOnClickListener(v -> {
            if (etTitle.getText().toString().isEmpty()) {
                Toast.makeText(CreateAssessmentPatientActivity.this, "Please write a Tilte", Toast.LENGTH_SHORT).show();
                etTitle.setError("Title is required");
                etTitle.requestFocus();
                hideKeyboard();
            } else if (etDescription.getText().toString().isEmpty()) {
                Toast.makeText(CreateAssessmentPatientActivity.this, "Please write a Description", Toast.LENGTH_SHORT).show();
                etDescription.setError("Description is required");
                etDescription.requestFocus();
                hideKeyboard();
            } else {
                LocalDateTime currentLocalDateTime = LocalDateTime.now();

                Assessment assessment = new Assessment();
                assessment.setUsername(username);
                assessment.setProfessionalId(selectedProfessional.getId());
                assessment.setTitle(etTitle.getText().toString());
                assessment.setDescription(etDescription.getText().toString());
                assessment.setAssessmentDateTime(currentLocalDateTime);
                assessment.setStars((double) rbProfessional.getRating());

                assessmentService.insertAssessment(assessment);



                double avgStar = ((selectedProfessional.getStars() * selectedProfessional.getAssessments()) + rbProfessional.getRating());
                avgStar = avgStar / (selectedProfessional.getAssessments()+1);

                selectedProfessional.setStars(avgStar);
                selectedProfessional.setAssessments(selectedProfessional.getAssessments()+1);
                professionalService.updateProfessional(selectedProfessional);

                Toast.makeText(CreateAssessmentPatientActivity.this, "Assessement created successfully", Toast.LENGTH_SHORT).show();
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
