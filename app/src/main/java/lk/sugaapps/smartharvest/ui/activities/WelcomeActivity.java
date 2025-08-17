package lk.sugaapps.smartharvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import lk.sugaapps.smartharvest.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClick();

    }
    private void setOnClick() {

        binding.btnSignIn.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this,LoginActivity.class)));

        binding.btnSignUp.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this,RegisterActivity.class)));
    }
}