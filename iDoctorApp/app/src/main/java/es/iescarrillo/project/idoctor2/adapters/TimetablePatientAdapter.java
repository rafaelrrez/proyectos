package es.iescarrillo.project.idoctor2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Timetable;

public class TimetablePatientAdapter extends ArrayAdapter<Timetable> {
    public TimetablePatientAdapter(Context context, List<Timetable> timetableList) {
        super(context, 0, timetableList);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Timetable timetable = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_timetable_consultation_professional_patient, parent , false);
        }

        TextView tvDayOfWeek = convertView.findViewById(R.id.tvDayOfWeek);
        TextView tvStartTime = convertView.findViewById(R.id.tvStartTime);
        TextView tvEndTime = convertView.findViewById(R.id.tvEndTime);

        LocalTime startTime = timetable.getStartTime();
        LocalTime endTime = timetable.getEndTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);

        tvDayOfWeek.setText(timetable.getDayOfWeek());
        tvStartTime.setText(formattedStartTime);
        tvEndTime.setText(formattedEndTime);


        return convertView;
    }
}
