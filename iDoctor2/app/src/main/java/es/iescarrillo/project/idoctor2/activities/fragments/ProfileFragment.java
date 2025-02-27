package es.iescarrillo.project.idoctor2.activities.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.activities.DialogLogout;
import es.iescarrillo.project.idoctor2.activities.patients.InformationProfilePatientActivity;
import es.iescarrillo.project.idoctor2.activities.patients.ListEvaluationAppoinmentPatientActivity;
import es.iescarrillo.project.idoctor2.activities.professionals.InformationProfileProfessionalActivity;
import es.iescarrillo.project.idoctor2.activities.professionals.ListAssessmentsProfessionalActivity;

public class ProfileFragment extends Fragment {
    private String role;
    private String name;
    private String userName;
    private String userId;
    private String photo;

    private CircleImageView civProfile;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = Objects.requireNonNull(requireContext()).getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "0");
        userName = sharedPreferences.getString("username", "0");
        userId = sharedPreferences.getString("id", "0");
        role = sharedPreferences.getString("role", "0");
        photo = sharedPreferences.getString("photo", "");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvEditProfile = view.findViewById(R.id.tvEditProfile);
        TextView tvLogOut = view.findViewById(R.id.tvLogOut);
        TextView tvOption = view.findViewById(R.id.tvOption);
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        civProfile = view.findViewById(R.id.civProfile);
        View view2 = view.findViewById(R.id.view2);

        tvUsername.setText(userName);

        // Agregar un timestamp para evitar la caché
        if (!photo.isEmpty()) {
            Uri uri = Uri.parse(photo + "?time=" + System.currentTimeMillis());
            Picasso.get().load(uri).into(civProfile);
        }

        tvEditProfile.setOnClickListener(v -> {
            if (role.equals("PROFESSIONAL")) {
                Intent intentEditProfileProfessional = new Intent(getActivity().getApplication().getApplicationContext(), InformationProfileProfessionalActivity.class);
                startActivity(intentEditProfileProfessional);
            } else {
                Intent intentEditProfilePatient = new Intent(getActivity().getApplication().getApplicationContext(), InformationProfilePatientActivity.class);
                startActivity(intentEditProfilePatient);
            }
        });

        if (role.equals("PATIENT")) {
            tvOption.setText("My Evaluations");
            Drawable drawableEvaluation = getResources().getDrawable(R.drawable.evaluation_icon_round);
            Drawable drawableArrow = getResources().getDrawable(R.drawable.arrow_icon);
            tvOption.setCompoundDrawablesWithIntrinsicBounds(drawableEvaluation, null, drawableArrow, null);

            tvOption.setOnClickListener(v -> {
                Intent intentListEvaluation = new Intent(getActivity().getApplication().getApplicationContext(), ListEvaluationAppoinmentPatientActivity.class);
                startActivity(intentListEvaluation);
            });
        } else {
            tvOption.setOnClickListener(v -> {
                Intent intentConsultationAssessments = new Intent(getActivity().getApplication().getApplicationContext(), ListAssessmentsProfessionalActivity.class);
                startActivity(intentConsultationAssessments);
            });
        }

        tvLogOut.setOnClickListener(v -> DialogLogout.showLogoutDialog(requireContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!photo.isEmpty()) {
            Uri uri = Uri.parse(photo + "?time=" + System.currentTimeMillis());
            Picasso.get().load(uri).into(civProfile);
        }
    }
}
