package es.iescarrillo.project.idoctor2.activities.patients.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.activities.patients.CreateAssessmentPatientActivity;

public class HomePatientFragment extends Fragment {
    private String name, username, userId;

    public HomePatientFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name", "0");
        username = sharedPreferences.getString("username", "0");
        userId = sharedPreferences.getString("id", "0");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_patient, container, false);

        TextView tvGreetings = view.findViewById(R.id.tvGreetings);
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        CardView cvAssessment = view.findViewById(R.id.cvAssessment);

        cvAssessment.setOnClickListener(v -> {
            Intent AssessmentCreateIntent = new Intent(getContext(), CreateAssessmentPatientActivity.class);
            startActivity(AssessmentCreateIntent);
        });

        tvGreetings.setText("Hi " + name);
        tvUsername.setText("User: " + username);

        return view;
    }
}