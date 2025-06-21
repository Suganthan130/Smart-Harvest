package lk.sugaapps.smartharvest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lk.sugaapps.smartharvest.ui.activities.MainActivity;
import lk.sugaapps.smartharvest.ui.activities.WelcomeActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = (currentUser != null) ?
                    new Intent(this, MainActivity.class) :
                    new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}