package es.iescarrillo.project.idoctor2.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Assessment;
import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.services.PatientService;
import io.woong.shapedimageview.ShapedImageView;

public class AssessmentsProfessionalAdapter extends ArrayAdapter<Assessment> {

    private PatientService patientService;
    private Patient patient;

    public AssessmentsProfessionalAdapter(Context context, List<Assessment> assessment) {
        super(context, 0, assessment);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the Classroom object at the specified position
        Assessment assessment = getItem(position);

        //Inflate the layout for each item if the convertView is null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_consultation_professional_assessments, parent, false);
        }

        ShapedImageView shImageUser = convertView.findViewById(R.id.shImageUser);
        TextView tvUserName = convertView.findViewById(R.id.tvUsername);
        TextView tvtitle = convertView.findViewById(R.id.tvtitle);
        TextView tvStars = convertView.findViewById(R.id.tvStars);
        TextView tvAssessmentDate = convertView.findViewById(R.id.tvAssessmentsDate);
        TextView tvAssessmentTime = convertView.findViewById(R.id.tvAssessmentsTime);

        patientService = new PatientService(getContext());

        tvUserName.setText(assessment.getUsername());
        tvtitle.setText(assessment.getTitle());
        tvStars.setText(assessment.getStars().toString());

        DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter TimeFormat = DateTimeFormatter.ofPattern("HH:mm");

        tvAssessmentDate.setText(assessment.getAssessmentDateTime().format(DateFormat));
        tvAssessmentTime.setText(assessment.getAssessmentDateTime().format(TimeFormat));


        patientService.getPatientByUserName(tvUserName.getText().toString(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    patient = snapshot.getValue(Patient.class);

                    if (patient.getPhoto() != null) {

                        Uri uri = Uri.parse(patient.getPhoto());
                        Picasso.get().load(uri).into(shImageUser);

                    } else {
                        shImageUser.setImageResource(R.drawable.user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja errores de lectura de la base de datos si es necesario
                Log.w("Firebase", "Error reading the database", databaseError.toException());
            }
        });
        return convertView;
    }

}
