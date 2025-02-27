package es.iescarrillo.project.idoctor2.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mindrot.jbcrypt.BCrypt;

import es.iescarrillo.project.idoctor2.R;
import es.iescarrillo.project.idoctor2.models.Patient;
import es.iescarrillo.project.idoctor2.models.Professional;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView ivBack = findViewById(R.id.ivBack);
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvSignUp = findViewById(R.id.tvSignUp);

        btnLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etUsername.getText().toString())) {
                Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                etUsername.setError("Username is required");
                etUsername.requestFocus();
                hideKeyboard();
            } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                etPassword.setError("Password is required");
                etPassword.requestFocus();
                hideKeyboard();
            } else {

                sharedPreferences = getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("iDoctor").child("users");

                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean userFound = false;

                        for (DataSnapshot userSnapshot : snapshot.child("patients").getChildren()) {

                            String username = userSnapshot.child("username").getValue(String.class);

                            if (username.equals(etUsername.getText().toString())) {
                                userFound = true;

                                Patient patient = userSnapshot.getValue(Patient.class);

                                boolean checkPassword = BCrypt.checkpw(etPassword.getText().toString(), patient.getPassword());

                                if (checkPassword) {

                                    editor.putString("name", patient.getName());
                                    editor.putString("username", patient.getUsername());
                                    editor.putString("id", patient.getId());
                                    editor.putString("role", patient.getUserRole().toString());
                                    editor.putString("photo", patient.getPhoto());

                                    editor.apply();

                                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                    return;

                                } else {

                                    Toast.makeText(LoginActivity.this, "Please introduce a valid password", Toast.LENGTH_SHORT).show();
                                    etPassword.setError("Wrong password");
                                    etPassword.requestFocus();

                                }
                            }
                        }

                        for (DataSnapshot userSnapshot : snapshot.child("professionals").getChildren()) {
                            String username = userSnapshot.child("username").getValue(String.class);

                            if (username.equals(etUsername.getText().toString())) {
                                userFound = true;

                                Professional professional = userSnapshot.getValue(Professional.class);

                                boolean checkPassword = BCrypt.checkpw(etPassword.getText().toString(), professional.getPassword());

                                if (checkPassword) {

                                    editor.putString("name", professional.getName());
                                    editor.putString("username", professional.getUsername());
                                    editor.putString("id", professional.getId());
                                    editor.putString("role", professional.getUserRole().toString());
                                    editor.putString("photo", professional.getPhoto());

                                    editor.apply();

                                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                    return;

                                } else {

                                    Toast.makeText(LoginActivity.this, "Please introduce a valid password", Toast.LENGTH_SHORT).show();
                                    etPassword.setError("Wrong password");
                                    etPassword.requestFocus();

                                }
                            }
                        }

                        if (!userFound) {
                            Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            hideKeyboard();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("Firebase", "Error reading the database", error.toException());
                    }
                });
            }
        });

        tvSignUp.setOnClickListener(v -> {
            Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(registerIntent);
            finish();
        });

        ivBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}