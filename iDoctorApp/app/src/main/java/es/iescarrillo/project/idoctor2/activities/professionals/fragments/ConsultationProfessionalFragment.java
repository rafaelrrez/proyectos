package es.iescarrillo.project.idoctor2.activities.professionals.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.activities.professionals.ListConsultationProfessional;
import es.iescarrillo.project.idoctor2.activities.professionals.DetailsConsultationProfessionalActivity;
import es.iescarrillo.project.idoctor2.adapters.ConsultationProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.services.ConsultationService;

public class ConsultationProfessionalFragment extends Fragment {

    private ConsultationProfessionalAdapter consultationAdapter;
    private List<Consultation> consultationList;

    public ConsultationProfessionalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConsultationService consultationService = new ConsultationService(getContext());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "0");

        consultationList = new ArrayList<>();

        consultationService.getConsultationByProfessionalId(userId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consultationList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Consultation consultation = snapshot.getValue(Consultation.class);
                    consultationList.add(consultation);
                }

                consultationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consultation_professional, container, false);

        ListView lvConsultation = view.findViewById(R.id.lvConsultation);

        consultationAdapter = new ConsultationProfessionalAdapter(getContext(), consultationList);

        lvConsultation.setAdapter(consultationAdapter);

        lvConsultation.setOnItemClickListener((parent, view1, position, id) -> {

            Consultation selectedConsultation = (Consultation) parent.getItemAtPosition(position);

            Intent editConsultationIntent = new Intent(getContext(), DetailsConsultationProfessionalActivity.class);
            editConsultationIntent.putExtra("consultation", selectedConsultation);
            startActivity(editConsultationIntent);

        });

        Button btnCreate = view.findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v -> {
            Intent createConsultationIntent = new Intent(getContext(), ListConsultationProfessional.class);
            startActivity(createConsultationIntent);
        });

        return view;
    }
}