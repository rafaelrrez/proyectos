package es.iescarrillo.project.idoctor2.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.activities.patients.fragments.CalendarPatientFragment;
import es.iescarrillo.project.idoctor2.activities.professionals.fragments.AppointmentProfessionalFragment;
import es.iescarrillo.project.idoctor2.activities.professionals.fragments.ConsultationProfessionalFragment;
import es.iescarrillo.project.idoctor2.activities.patients.fragments.HomePatientFragment;
import es.iescarrillo.project.idoctor2.activities.professionals.fragments.HomeProfessionalFragment;
import es.iescarrillo.project.idoctor2.activities.patients.fragments.ListProfessionalPatientFragment;
import es.iescarrillo.project.idoctor2.activities.fragments.ProfileFragment;
import es.iescarrillo.project.idoctor2.models.Person;
import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimatedBottomBar bottomBar = findViewById(R.id.bottom_bar);

        sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "0");
        String userName = sharedPreferences.getString("username", "0");
        String userId = sharedPreferences.getString("id", "0");
        String role = sharedPreferences.getString("role", "0");
        String photo = sharedPreferences.getString("photo", "0");

        if (role.equals(Person.UserRole.PATIENT.toString())) {

            replace(new HomePatientFragment());

        } else {

            replace(new HomeProfessionalFragment());

        }

        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {

                if (tab1.getId() == R.id.tab_home) {
                    if (role.equals(Person.UserRole.PATIENT.toString())) {

                        replace(new HomePatientFragment());

                    } else {

                        replace(new HomeProfessionalFragment());

                    }
                } else if (tab1.getId() == R.id.tab_profile) {
                    replace(new ProfileFragment());
                } else if (tab1.getId() == R.id.tab_consultation) {

                    if (role.equals(Person.UserRole.PATIENT.toString())) {

                        replace(new ListProfessionalPatientFragment());

                    } else {

                        replace(new ConsultationProfessionalFragment());

                    }

                } else if (tab1.getId() == R.id.tab_calendar) {

                    if (role.equals(Person.UserRole.PATIENT.toString())) {
                        replace(new CalendarPatientFragment());
                    } else {
                        replace(new AppointmentProfessionalFragment());
                    }

                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();
    }
}