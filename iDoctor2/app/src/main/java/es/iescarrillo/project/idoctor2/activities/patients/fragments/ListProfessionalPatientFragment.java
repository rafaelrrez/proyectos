package es.iescarrillo.project.idoctor2.activities.patients.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.activities.patients.DetailsProfessionalPatientActivity;
import es.iescarrillo.project.idoctor2.adapters.ProfessionalPatientAdapter;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Professional;
import es.iescarrillo.project.idoctor2.services.ConsultationService;
import es.iescarrillo.project.idoctor2.services.ProfessionalService;

public class ListProfessionalPatientFragment extends Fragment {

    private ProfessionalPatientAdapter professionalPatientAdapter;
    private List<Professional> professionalList;
    private List<Professional> fullProfessionalList;
    private Spinner spinnerFilter;
    private String professionalID;
    private final String[] filterOptions = {"Filter by city", "Filter by specialty", "Filter by name"};

    public ListProfessionalPatientFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "0");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_professional_patient, container, false);

        ListView lvProfessional = view.findViewById(R.id.lvDoctors);
        spinnerFilter = view.findViewById(R.id.spinnerFilter);
        EditText etSearchProfessionals = view.findViewById(R.id.etSearchProfessionals);
        ProfessionalService professionalService = new ProfessionalService(getContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext().getApplicationContext(), R.layout.item_spinner, R.id.text1, filterOptions) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView icon = view.findViewById(R.id.spinnerIcon);
                setIconForOption(icon, position);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ImageView icon = view.findViewById(R.id.spinnerIcon);
                setIconForOption(icon, position);
                return view;
            }
        };

        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinnerFilter.setAdapter(adapter);

        professionalList = new ArrayList<>();
        fullProfessionalList = new ArrayList<>(); // Inicializa la lista completa

        professionalService.getAllProfessional(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                professionalList.clear();
                fullProfessionalList.clear(); // Limpia la lista completa antes de cargarla nuevamente

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Professional professional = snapshot.getValue(Professional.class);
                    professionalList.add(professional);
                    fullProfessionalList.add(professional);
                }

                professionalPatientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        professionalPatientAdapter = new ProfessionalPatientAdapter(getContext(), professionalList);
        lvProfessional.setAdapter(professionalPatientAdapter);

        lvProfessional.setOnItemClickListener((parent, view1, position, id) -> {
            Professional selectedProfessional = (Professional) parent.getItemAtPosition(position);
            professionalID = selectedProfessional.getId();
            Intent detailsProfessionalIntent = new Intent(getContext(), DetailsProfessionalPatientActivity.class);
            detailsProfessionalIntent.putExtra("professional", selectedProfessional);
            detailsProfessionalIntent.putExtra("professionalID", professionalID);
            startActivity(detailsProfessionalIntent);
        });

        etSearchProfessionals.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int selectedFilterPosition = spinnerFilter.getSelectedItemPosition();
                switch (selectedFilterPosition) {
                    case 0:
                        filterByCity(s.toString());
                        break;
                    case 1:
                        filterBySpecialty(s.toString());
                        break;
                    case 2:
                        filterByName(s.toString());
                        break;
                    default:
                        Toast.makeText(getContext(), "Please introduce a valid password", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    private void setIconForOption(ImageView icon, int position) {
        if (position == 0) {
            icon.setImageResource(R.drawable.city_icon);
        } else if (position == 1) {
            icon.setImageResource(R.drawable.speciality_icon);
        } else if (position == 2) {
            icon.setImageResource(R.drawable.doctor_icon);
        }
    }

    private void filterByCity(String city) {
        List<Professional> filteredList = new ArrayList<>();

        ConsultationService consultationService = new ConsultationService(getContext());
        consultationService.getAllConsultation(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                professionalList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Consultation consultation = snapshot.getValue(Consultation.class);
                    if (consultation.getCity() != null && consultation.getCity().toLowerCase().contains(city.toLowerCase())) {
                        for (Professional professional : fullProfessionalList) {
                            if (professional.getId().equals(consultation.getProfessionalId()) && !filteredList.contains(professional)) {
                                filteredList.add(professional);
                            }
                        }
                    }
                }

                if (city.isEmpty()) {
                    professionalList.addAll(fullProfessionalList);
                } else {
                    professionalList.addAll(filteredList);
                }

                professionalPatientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });
    }



    private void filterBySpecialty(String specialty) {
        professionalList.clear();

        for (Professional professional : fullProfessionalList) {
            if (professional.getSpeciality().toString().toLowerCase().contains(specialty.toLowerCase())) {
                professionalList.add(professional);
            }
        }

        professionalPatientAdapter.notifyDataSetChanged();
    }


    private void filterByName(String name) {
        professionalList.clear();

        for (Professional professional : fullProfessionalList) {
            if (professional.getName().toLowerCase().contains(name.toLowerCase())) {
                professionalList.add(professional);
            }
        }

        professionalPatientAdapter.notifyDataSetChanged();
    }
}
