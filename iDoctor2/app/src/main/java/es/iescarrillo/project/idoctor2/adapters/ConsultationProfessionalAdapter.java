package es.iescarrillo.project.idoctor2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Consultation;

public class ConsultationProfessionalAdapter extends ArrayAdapter<Consultation> {

    //Custom adapter for displaying manager classrooms in a ListView.
    public ConsultationProfessionalAdapter(Context context, List<Consultation> consultations) {
        super(context, 0, consultations);
    }

    //Get a View that displays the data at the specified position in the data set.
    public View getView(int position, View convertView, ViewGroup parent){

        //Get the Classroom object at the specified position
        Consultation consultation = getItem(position);

        //Inflate the layout for each item if the convertView is null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_consultation_professional, parent , false);
        }

        //Find the TextView for displaying classroom names
        TextView tvAddress = convertView.findViewById(R.id.tvAddress);
        TextView tvCity = convertView.findViewById(R.id.tvCity);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);
        TextView tvPhoneAux = convertView.findViewById(R.id.tvPhoneAux);


        //Set the text of the TextView to the name of the current classroom
        tvAddress.setText(consultation.getAddress());
        tvCity.setText(consultation.getCity());
        tvEmail.setText(consultation.getEmail());
        tvPhone.setText(consultation.getPhone());
        tvPhoneAux.setText(consultation.getPhoneAux());

        return convertView;
    }
}
