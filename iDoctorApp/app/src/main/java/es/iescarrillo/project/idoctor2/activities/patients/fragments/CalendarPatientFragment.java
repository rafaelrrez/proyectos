package es.iescarrillo.project.idoctor2.activities.patients.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.activities.MainActivity;
import es.iescarrillo.project.idoctor2.activities.patients.DetailsAppointmentPatientActivity;
import es.iescarrillo.project.idoctor2.adapters.AppointmentPatientAdapter;
import es.iescarrillo.project.idoctor2.adapters.AppointmentProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.AppointmentString;
import es.iescarrillo.project.idoctor2.services.AppointmentService;


public class CalendarPatientFragment extends Fragment {

    private String userId;
    private AppointmentPatientAdapter appointmentPatientAdapter;
    private AppointmentService appointmentService;

    public CalendarPatientFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("iDoctor", Context.MODE_PRIVATE);

        userId = sharedPreferences.getString("id", "0");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar_patient, container, false);

        appointmentService = new AppointmentService(getContext());

        ListView lvAppointments = view.findViewById(R.id.lvAppointments);
        CalendarView calendar = view.findViewById(R.id.calendarView);

        List<Appointment> appointmentList = new ArrayList<>();

        appointmentService.getAppointmentByPatientlId(userId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointmentList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AppointmentString appointment = snapshot.getValue(AppointmentString.class);
                    appointmentList.add(appointment.convertToAppointment());
                }

                appointmentPatientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        appointmentPatientAdapter = new AppointmentPatientAdapter(getContext(), appointmentList);

        lvAppointments.setAdapter(appointmentPatientAdapter);

        lvAppointments.setOnItemClickListener((parent, view1, position, id) -> {
            Appointment appointment = (Appointment) parent.getItemAtPosition(position);

            Intent detailsAppoinmentIntent = new Intent(getContext(), DetailsAppointmentPatientActivity.class);
            detailsAppoinmentIntent.putExtra("appointment", appointment);
            startActivity(detailsAppoinmentIntent);
        });

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        return view;
    }
}