package es.iescarrillo.project.idoctor2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Appointment;


public class AppointmentProfessionalAdapter extends ArrayAdapter<Appointment> {

    public AppointmentProfessionalAdapter(Context context, List<Appointment> appointments){
        super(context, 0, appointments);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        //Get the Appointment object at the specified position
        Appointment appointment = getItem(position);

        //Inflate the layout for each item if the convertView is null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_appointment_professional, parent , false);
        }

        TextView tvDate = convertView.findViewById(R.id.tvDate);
        TextView tvHour = convertView.findViewById(R.id.tvHour);

        tvDate.setText(appointment.getAppointmentDate().toString());
        tvHour.setText(appointment.getAppointmentTime().toString());

        return convertView;
    }
}
