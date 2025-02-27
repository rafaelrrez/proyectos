package es.iescarrillo.project.idoctor2.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Professional;
import io.woong.shapedimageview.ShapedImageView;

public class ProfessionalPatientAdapter extends ArrayAdapter<Professional> {

    public ProfessionalPatientAdapter(Context context, List<Professional> professionals) {
        super(context, 0, professionals);
    }

    //Get a View that displays the data at the specified position in the data set.
    public View getView(int position, View convertView, ViewGroup parent){

        //Get the Classroom object at the specified position
        Professional professional = getItem(position);

        //Inflate the layout for each item if the convertView is null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_professional_patient, parent , false);
        }

        ShapedImageView shImageProfessional = convertView.findViewById(R.id.shImageProfessional);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvSpeciality = convertView.findViewById(R.id.tvSpeciality);
        TextView tvStars = convertView.findViewById(R.id.tvStars);

        if (professional.getPhoto() != null) {
            Uri uri = Uri.parse(professional.getPhoto());
            Picasso.get().load(uri).into(shImageProfessional);
        } else {
            shImageProfessional.setImageResource(R.drawable.doctor_default_icon);
        }

        tvName.setText(professional.getName());
        tvSpeciality.setText(professional.getSpeciality().toString());
        tvStars.setText(String.format(Locale.getDefault(), "%.1f", professional.getStars()));

        return convertView;
    }
}
