package es.iescarrillo.project.idoctor2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Evaluation;

public class EvaluationAppointmentAdapter extends ArrayAdapter<Evaluation> {
    public EvaluationAppointmentAdapter(Context context, List<Evaluation> evaluations){
        super(context, 0, evaluations);
    }
        public View getView(int position, View convertView, ViewGroup parent){
            Evaluation evaluation = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_evaluation_appoinment_patient, parent , false);
            }

            TextView tvDescription = convertView.findViewById(R.id.tvDescription);
            TextView tvExploration = convertView.findViewById(R.id.tvExploration);
            TextView tvTreatment = convertView.findViewById(R.id.tvTreatment);
            TextView tvEvaluationDateTime = convertView.findViewById(R.id.tvEvaluationDateTime);

            tvDescription.setText(evaluation.getDescription());
            tvExploration.setText(evaluation.getExploration());
            tvTreatment.setText(evaluation.getTreatment());

            DateTimeFormatter DateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            tvEvaluationDateTime.setText(evaluation.getEvaluationDateTime().format(DateTimeFormat));

            return convertView;
        }

}
