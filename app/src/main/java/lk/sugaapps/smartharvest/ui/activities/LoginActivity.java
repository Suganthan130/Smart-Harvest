package lk.sugaapps.smartharvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.databinding.ActivityLoginBinding;
import lk.sugaapps.smartharvest.viewmodel.AuthViewModel;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setupObservers();
        binding.btnLogin.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.txlEmail.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(binding.txlPassword.getEditText()).getText().toString().trim();
            if (validateInputs(email, password)) {
                authViewModel.login(email, password);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.txSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }
    private void setupObservers() {
        authViewModel.getLoginResult().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showProgress(true);
                    break;
                case SUCCESS:
                    showProgress(false);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    break;
                case ERROR:
                    showProgress(false);
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty()) {
            binding.txlEmail.setError("Email is required");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.txlPassword.setError("Password is required");
            isValid = false;
        }

        return isValid;
    }

    private void showProgress(boolean show) {
        binding.progressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.btnLogin.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }
}
