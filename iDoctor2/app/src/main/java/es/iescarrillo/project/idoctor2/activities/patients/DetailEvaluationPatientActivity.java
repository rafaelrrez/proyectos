package es.iescarrillo.project.idoctor2.activities.patients;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Evaluation;
import es.iescarrillo.project.idoctor2.models.Report;
import es.iescarrillo.project.idoctor2.services.ReportService;

public class DetailEvaluationPatientActivity extends AppCompatActivity {

    private Report report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_evaluation_patient);

        Evaluation evaluation = (Evaluation) getIntent().getSerializableExtra("evaluation");

        TextView tvTitleReport = findViewById(R.id.tvTitleReport);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvExploration = findViewById(R.id.tvExploration);
        TextView tvTreatment = findViewById(R.id.tvTreatment);
        ImageView ivBack = findViewById(R.id.ivBack);
        Button btnViewInPDF = findViewById(R.id.btnViewInPDF);

        ReportService reportService = new ReportService(getApplicationContext());

        report = new Report();
        reportService.getReportById(evaluation.getId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    report = snapshot.getValue(Report.class);

                    tvTitleReport.setText(report.getTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvDescription.setText(evaluation.getDescription());
        tvExploration.setText(evaluation.getExploration());
        tvTreatment.setText(evaluation.getTreatment());

        btnViewInPDF.setOnClickListener(v -> {
            Intent viewPdfEvaluationIntent = new Intent(getApplicationContext(), ViewPdfEvaluationPatientActivity.class);
            viewPdfEvaluationIntent.putExtra("link", report.getLink());
            startActivity(viewPdfEvaluationIntent);
        });

        ivBack.setOnClickListener(v -> finish());
    }
}