package lk.sugaapps.smartharvest.ui.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.databinding.ActivityPasswordForgetBinding;
import lk.sugaapps.smartharvest.viewmodel.AuthViewModel;

@AndroidEntryPoint
public class PasswordForget extends AppCompatActivity {
    private ActivityPasswordForgetBinding binding;
    private AuthViewModel authViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPasswordForgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setupObservers();
        onClicked();
    }

    private void onClicked() {
        binding.btnRecover.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.txlEmail.getEditText()).getText().toString().trim();
            if (validateInputs(email)) {
                authViewModel.resetPassword(email);
            }
        });
    }

    private void setupObservers() {
        authViewModel.getResetPasswordResult().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    showProgress(true);
                    break;
                case SUCCESS:
                    showProgress(false);
                    Toast.makeText(PasswordForget.this, resource.message, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case ERROR:
                    showProgress(false);
                    Toast.makeText(PasswordForget.this, resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });


    }

    private boolean validateInputs(String email) {

        if (email.isEmpty()) {
            binding.txlEmail.setError("Email is required");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txlEmail.setError("Invalid email format");
            return false;
        }

        return true;
    }
    private void showProgress(boolean show) {
        binding.progressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.btnRecover.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }
}