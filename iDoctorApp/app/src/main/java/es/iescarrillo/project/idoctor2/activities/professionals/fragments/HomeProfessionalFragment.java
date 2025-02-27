package es.iescarrillo.project.idoctor2.activities.professionals.fragments;

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
import es.iescarrillo.project.idoctor2.activities.patients.ListAppointmentConsultationProfessionalPatientActivity;
import es.iescarrillo.project.idoctor2.activities.professionals.GenerateAppointmentsProfessionalActivity;
import es.iescarrillo.project.idoctor2.activities.professionals.ListAppointmentProfessionalActivity;
import es.iescarrillo.project.idoctor2.activities.professionals.ListConsultationAppointmentProfessionalActivity;
import es.iescarrillo.project.idoctor2.activities.professionals.ListTimetableProfessionalActivity;

public class HomeProfessionalFragment extends Fragment {
    private String name, username, userId;

    public HomeProfessionalFragment() {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_professional, container, false);

        TextView tvGreetings = view.findViewById(R.id.tvGreetings);
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        CardView cvManageSchedule = view.findViewById(R.id.cvManageSchedule);
        CardView cvGenerateSchedule = view.findViewById(R.id.cvGenerateSchedule);
        CardView cvGenerateEvaluation = view.findViewById(R.id.cvGenerateEvaluation);

        cvManageSchedule.setOnClickListener(v -> {
            Intent listTimetableIntent = new Intent(getContext(), ListTimetableProfessionalActivity.class);
            startActivity(listTimetableIntent);
        });

        cvGenerateSchedule.setOnClickListener(v -> {
            Intent generateAppointmentsIntent = new Intent(getContext(), GenerateAppointmentsProfessionalActivity.class);
            startActivity(generateAppointmentsIntent);
        });

        cvGenerateEvaluation.setOnClickListener(v -> {
            Intent listAppointmentConsultationProfessionalIntent = new Intent(getContext(), ListConsultationAppointmentProfessionalActivity.class);
            startActivity(listAppointmentConsultationProfessionalIntent);
        });

        tvGreetings.setText("Hi " + name);
        tvUsername.setText("User: " + username);

        return view;
    }
}