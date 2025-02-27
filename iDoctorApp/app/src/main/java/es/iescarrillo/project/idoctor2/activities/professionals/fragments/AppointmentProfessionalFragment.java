package es.iescarrillo.project.idoctor2.activities.professionals.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.activities.professionals.CreateAppointmentProfessionalActivity;
import es.iescarrillo.project.idoctor2.activities.professionals.DetailsAppointmentProfessionalActivity;
import es.iescarrillo.project.idoctor2.adapters.AppointmentProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.AppointmentString;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.services.AppointmentService;
import es.iescarrillo.project.idoctor2.services.ConsultationService;

public class AppointmentProfessionalFragment extends Fragment {
    private ListView lvAppointments;
    private List<Appointment> appointmentList, filteredAppointmentList;
    private List<Consultation> consultationList;
    private AppointmentProfessionalAdapter appointmentProfessionalAdapter;

    public AppointmentProfessionalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_professional, container, false);

        AppointmentService appointmentService = new AppointmentService(getContext());
        ConsultationService consultationService = new ConsultationService(getContext());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String professionalId = sharedPreferences.getString("id", "0");

        consultationList = new ArrayList<>();
        appointmentList = new ArrayList<>();

        consultationService.getConsultationByProfessionalId(professionalId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consultationList.clear();
                for (DataSnapshot consultationSnapshot : dataSnapshot.getChildren()) {
                    Consultation consultation = consultationSnapshot.getValue(Consultation.class);
                    consultationList.add(consultation);
                    System.out.println(consultation.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        filteredAppointmentList = new ArrayList<>();

        appointmentService.getAllAppointments(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentList.clear();
                filteredAppointmentList.clear();
                for (DataSnapshot consultationSnapshot : dataSnapshot.getChildren()) {
                    AppointmentString appointment = consultationSnapshot.getValue(AppointmentString.class);
                    appointmentList.add(appointment.convertToAppointment());
                }

                for (Consultation consultation : consultationList){
                    for(Appointment appointment : appointmentList){
                        if(consultation.getId().equals(appointment.getConsultationId()) && appointment.getActive() == true){
                            filteredAppointmentList.add(appointment);
                            appointmentProfessionalAdapter = new AppointmentProfessionalAdapter(getContext(), filteredAppointmentList);
                            lvAppointments.setAdapter(appointmentProfessionalAdapter);
                        }
                    }
                }
                appointmentProfessionalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        lvAppointments = view.findViewById(R.id.lvAppointments);
        Button btnCreate = view.findViewById(R.id.btnCreate);

        lvAppointments.setOnItemClickListener((parent, view1, position, id) -> {

            Appointment selectedAppointment = (Appointment) parent.getItemAtPosition(position);

            Intent detailsAppointmentIntent = new Intent(getContext(), DetailsAppointmentProfessionalActivity.class);
            detailsAppointmentIntent.putExtra("appointment", selectedAppointment);
            startActivity(detailsAppointmentIntent);
        });

        btnCreate.setOnClickListener(v -> {
            Intent createAppointmentIntent = new Intent(getContext(), CreateAppointmentProfessionalActivity.class);
            startActivity(createAppointmentIntent);
        });

        return view;
    }
}