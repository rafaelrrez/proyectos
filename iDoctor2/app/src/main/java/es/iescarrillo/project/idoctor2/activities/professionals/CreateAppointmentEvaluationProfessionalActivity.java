package es.iescarrillo.project.idoctor2.activities.professionals;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.Evaluation;
import es.iescarrillo.project.idoctor2.models.Report;
import es.iescarrillo.project.idoctor2.services.EvaluationService;
import es.iescarrillo.project.idoctor2.services.ReportService;

public class CreateAppointmentEvaluationProfessionalActivity extends AppCompatActivity {

    private StorageReference storageReference;
    private  Appointment appointment;
    private Evaluation evaluation;
    private EditText etTitleReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment_evaluation_professional);

        appointment = (Appointment) getIntent().getSerializableExtra("appointment");

        EvaluationService evaluationService = new EvaluationService(getApplicationContext());
        storageReference = FirebaseStorage.getInstance().getReference();

        etTitleReport = findViewById(R.id.etTitleReport);
        EditText etDescription = findViewById(R.id.etDescription);
        EditText etExploration = findViewById(R.id.etExploration);
        EditText etTreatment = findViewById(R.id.etTreatment);
        ImageView ivBack = findViewById(R.id.ivBack);
        Button btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v -> {
            if (etDescription.getText().toString().isEmpty()) {
                Toast.makeText(CreateAppointmentEvaluationProfessionalActivity.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                etDescription.setError("Description is required");
                etDescription.requestFocus();
                hideKeyboard();
            } else if (etExploration.getText().toString().isEmpty()) {
                Toast.makeText(CreateAppointmentEvaluationProfessionalActivity.this, "Please enter an exploration", Toast.LENGTH_SHORT).show();
                etExploration.setError("Exploration is required");
                etExploration.requestFocus();
                hideKeyboard();
            } else if (etTreatment.getText().toString().isEmpty()) {
                Toast.makeText(CreateAppointmentEvaluationProfessionalActivity.this, "Please enter a treatment", Toast.LENGTH_SHORT).show();
                etTreatment.setError("Treatment is required");
                etTreatment.requestFocus();
                hideKeyboard();
            }else if (etTitleReport.getText().toString().isEmpty()) {
                Toast.makeText(CreateAppointmentEvaluationProfessionalActivity.this, "Please enter a report title", Toast.LENGTH_SHORT).show();
                etTitleReport.setError("Report title is required");
                etTitleReport.requestFocus();
                hideKeyboard();
            } else {

                evaluation = new Evaluation();

                evaluation.setAppointmentId(appointment.getId());
                evaluation.setDescription(etDescription.getText().toString());
                evaluation.setExploration(etExploration.getText().toString());
                evaluation.setTreatment(etTreatment.getText().toString());

                LocalDateTime currentDateTime = LocalDateTime.now();

                DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String formattedDateTime = currentDateTime.format(formatterDateTime);

                evaluation.setEvaluationDateTime(LocalDateTime.parse(formattedDateTime, formatterDateTime));

                generateAndUploadPdf();

                evaluationService.insertEvaluation(evaluation);
                Toast.makeText(CreateAppointmentEvaluationProfessionalActivity.this, "Evaluation was successfully created", Toast.LENGTH_SHORT).show();
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

    private void generateAndUploadPdf() {
        try {
            File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "sample_report.pdf");

            PdfWriter pdfWriter = new PdfWriter(pdfFile);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_launcher_round, null);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            try {
                Image logo = new Image(ImageDataFactory.create(stream.toByteArray()));
                logo.scaleAbsolute(100, 100);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);

                document.add(logo);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Paragraph title = new Paragraph(etTitleReport.getText().toString())
                    .setFontSize(18f)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("Description: " + evaluation.getDescription()));
            document.add(new Paragraph("Exploration: " + evaluation.getExploration()));
            document.add(new Paragraph("Treatment: " + evaluation.getTreatment()));

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedDateTime = currentDateTime.format(formatterDateTime);

            document.add(new Paragraph("Date and Time: " + formattedDateTime));

            document.close();

            uploadPdfToFirebase(pdfFile);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating or uploading PDF", Toast.LENGTH_SHORT).show();
        }
    }


    private void uploadPdfToFirebase(File pdfFile) {
        StorageReference pdfRef = storageReference.child("Reports/" + System.currentTimeMillis() + ".pdf");

        pdfRef.putFile(Uri.fromFile(pdfFile))
                .addOnSuccessListener(taskSnapshot -> pdfRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    String pdfLink = downloadUri.toString();
                    Toast.makeText(CreateAppointmentEvaluationProfessionalActivity.this, "PDF uploaded successfully", Toast.LENGTH_SHORT).show();

                    ReportService reportService = new ReportService(getApplicationContext());

                    Report report = new Report();

                    report.setEvaluationId(evaluation.getId());

                    report.setLink(pdfLink);

                    report.setTitle(etTitleReport.getText().toString());

                    reportService.insertReport(report);
                }))
                .addOnFailureListener(e -> Toast.makeText(CreateAppointmentEvaluationProfessionalActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

}