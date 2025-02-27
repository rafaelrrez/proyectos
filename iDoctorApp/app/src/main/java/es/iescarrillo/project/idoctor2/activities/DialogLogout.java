package es.iescarrillo.project.idoctor2.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import es.iescarrillo.project.idoctor2.R;

public class DialogLogout {

    public static void showLogoutDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        View dialogView = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.log_out_pop_up, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        ((Activity) context).getWindow().findViewById(R.id.alertDialog).setVisibility(View.VISIBLE);
        alertDialog.show();

        // Find buttons from the custom layout
        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Set onClickListeners for buttons in the custom layout
        btnYes.setOnClickListener(v -> handleLogout(context, alertDialog));

        btnCancel.setOnClickListener(v -> {
            // Perform actions for 'Cancel' button click
            alertDialog.dismiss(); // Dismiss the dialog if needed
            ((Activity) context).getWindow().findViewById(R.id.alertDialog).setVisibility(View.INVISIBLE);
        });

    }

    private static void handleLogout(Context context, AlertDialog alertDialog) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("iDoctor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Perform actions for 'Yes' button click (Logout)
        alertDialog.dismiss(); // Dismiss the dialog if needed

        Intent startActivityIntent = new Intent(context, StartActivity.class);
        context.startActivity(startActivityIntent);

        // Finish the activity containing the fragment
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }

        editor.clear();
        editor.apply();
    }
}
