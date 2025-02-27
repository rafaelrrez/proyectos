package es.iescarrillo.project.idoctor2.adapters;

import android.content.Context;
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

import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Appointment;
import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.services.PatientService;

public class AppointmentEvaluationAdapter extends ArrayAdapter<Appointment> {

    public AppointmentEvaluationAdapter(Context context, List<Appointment> appointments){
        super(context, 0, appointments);
    }
    public View getView(int position, View convertView, ViewGroup parent){

        //Get the Appointment object at the specified position
        Appointment appointment = getItem(position);

        //Inflate the layout for each item if the convertView is null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_appointment_evaluation, parent , false);
        }

        PatientService patientService = new PatientService(getContext());
        TextView tvPatientNameSurname = convertView.findViewById(R.id.tvPatientNameSurname);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvHour = convertView.findViewById(R.id.tvHour);

        patientService.getPatientById(appointment.getPatientId(), new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Patient patient = snapshot.getValue(Patient.class);

                    tvPatientNameSurname.setText(patient.getName() + " " + patient.getSurname());
                    tvDate.setText(appointment.getAppointmentDate().toString());
                    tvHour.setText(appointment.getAppointmentTime().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        return convertView;
    }
}
