package es.iescarrillo.project.idoctor2.activities.patients;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import es.iescarrillo.project.idoctor2.R;

public class ViewPdfEvaluationPatientActivity extends AppCompatActivity {
    private static final String FILENAME = "sample.pdf";
    private int pageIndex;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor parcelFileDescriptor;

    private ImageView imageViewPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf_evaluation_patient);

        imageViewPdf = findViewById(R.id.pdf_image);
        ImageView ivBack = findViewById(R.id.ivBack);

        String link = Objects.requireNonNull(getIntent().getStringExtra("link"));

        new DownloadPdfTask().execute(link);

        ivBack.setOnClickListener(v -> finish());
    }

    private class DownloadPdfTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference pdfRef = storage.getReferenceFromUrl(params[0]);
                File localFile = new File(getCacheDir(), FILENAME);

                pdfRef.getFile(localFile).addOnSuccessListener(taskSnapshot -> {

                    openRenderer(localFile);
                }).addOnFailureListener(e -> {
                    Log.e("DownloadPdfTask", "Error downloading PDF: " + e.getMessage());
                    e.printStackTrace();
                });
            } catch (Exception e) {
                Log.e("DownloadPdfTask", "Error: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    private void openRenderer(File file) {
        try {
            parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);

            showPage(pageIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPage(int index) {
        if (index < 0 || index >= pdfRenderer.getPageCount()) {
            return;
        }

        if (currentPage != null) {
            currentPage.close();
        }

        currentPage = pdfRenderer.openPage(index);

        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        imageViewPdf.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        if (currentPage != null) {
            currentPage.close();
        }
        if (pdfRenderer != null) {
            pdfRenderer.close();
        }
        try {
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}