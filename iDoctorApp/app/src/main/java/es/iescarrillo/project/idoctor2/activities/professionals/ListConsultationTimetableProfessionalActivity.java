package es.iescarrillo.project.idoctor2.activities.professionals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.adapters.TimetableProfessionalAdapter;
import es.iescarrillo.project.idoctor2.models.Consultation;
import es.iescarrillo.project.idoctor2.models.Timetable;
import es.iescarrillo.project.idoctor2.models.TimetableString;
import es.iescarrillo.project.idoctor2.services.TimetableService;

public class ListConsultationTimetableProfessionalActivity extends AppCompatActivity {
    private TimetableProfessionalAdapter timetableProfessionalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_consultation_timetable_professional);

        Consultation consultation = (Consultation) getIntent().getSerializableExtra("consultation");

        TimetableService timetableService = new TimetableService(getApplicationContext());

        ListView lvTimetable = findViewById(R.id.lvTimetable);
        Button btnCreate = findViewById(R.id.btnCreate);
        ImageView ivBack = findViewById(R.id.ivBack);

        List<Timetable> timetableList = new ArrayList<>();

        timetableService.getAllTimetable(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timetableList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    TimetableString timetable = snapshot.getValue(TimetableString.class);

                    if (timetable.getConsultationId().equals(consultation.getId())) {
                        timetableList.add(timetable.convertToTimeTable());
                    }
                }

                timetableProfessionalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Error reading the database", error.toException());
            }
        });

        timetableProfessionalAdapter = new TimetableProfessionalAdapter(getApplicationContext(), timetableList);

        lvTimetable.setAdapter(timetableProfessionalAdapter);

        lvTimetable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Timetable timetable = (Timetable) parent.getItemAtPosition(position);

                Intent editTimetableIntent = new Intent(getApplicationContext(), DetailsTimetableProfessionalActivity.class);
                editTimetableIntent.putExtra("timetable", timetable);
                startActivity(editTimetableIntent);
            }
        });

        btnCreate.setOnClickListener(v -> {
            Intent createTimetableIntent = new Intent(getApplicationContext(), CreateTimetableConsultationProfessionalActivity.class);
            createTimetableIntent.putExtra("consultation", consultation);
            startActivity(createTimetableIntent);
        });

        ivBack.setOnClickListener(v -> finish());
    }
}