package es.iescarrillo.project.idoctor2.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import es.iescarrillo.project.idoctor2.R;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Load the animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Set the animation on the ImageView
        ImageView splashLogo = findViewById(R.id.splash_logo);
        splashLogo.startAnimation(fadeIn);

        // Set a listener to start the main activity when the animation finishes
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation start
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation end, start the main activity
                startActivity(new Intent(StartActivity.this, WelcomeActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeat
            }
        });
    }
}