package lk.sugaapps.smartharvest.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.GeoPoint;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.R;
import lk.sugaapps.smartharvest.data.model.LocationModel;
import lk.sugaapps.smartharvest.databinding.ActivityRegisterBinding;
import lk.sugaapps.smartharvest.viewmodel.AuthViewModel;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity {
    private AuthViewModel viewModel;
    private ActivityRegisterBinding binding;
    private ActivityResultLauncher<Intent> mapPickerLauncher;
    private boolean agricultureAreaSelected = false;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setLauncher();
        setOnClick();
        setupObservers();
    }

    private void setOnClick() {
        binding.btnRegister.setOnClickListener(v -> {
            String name = Objects.requireNonNull(binding.txlName.getEditText()).getText().toString().trim();
            String email = Objects.requireNonNull(binding.txlEmail.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(binding.txlPassword.getEditText()).getText().toString().trim();
            String mobile = Objects.requireNonNull(binding.txlMobileNumber.getEditText()).getText().toString().trim();

            GeoPoint location = new GeoPoint(latitude, longitude);

            if (validateInputs(name, email, password, mobile)) {
                viewModel.registerUser(name, email, password, mobile, location);
            }
        });
        binding.btnBack2.setOnClickListener(v -> finish());
        binding.txSignUp.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
        binding.txlAgricultureArea.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapPickerActivity.class);
            mapPickerLauncher.launch(intent);
        });
    }

    private void setLauncher() {
        mapPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        LocationModel location = data.getParcelableExtra("location_model");
                        if (location != null) {
                            viewModel.setLocationResult(location);
                        }
                    }
                }
        );
    }

    private void setupObservers() {
        viewModel.getRegisterResult().observe(this, resource -> {
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
        viewModel.getLocationResult().observe(this, locationModel -> {
            String selectedAreaName = locationModel.getAreaName();
            latitude = locationModel.getLatitude();
            longitude = locationModel.getLongitude();

            if (selectedAreaName == null || selectedAreaName.trim().isEmpty() || selectedAreaName.toLowerCase().contains("unnamed road")) {
                @SuppressLint("DefaultLocale") String latLngStr = String.format("Lat: %.5f, Lng: %.5f",
                        latitude, longitude);
                binding.tvAgricultureArea.setText(latLngStr);
                binding.tvAgricultureArea.setTextColor(ContextCompat.getColor(this, R.color.black));
                agricultureAreaSelected = true;
            } else {
                binding.tvAgricultureArea.setText(selectedAreaName);
                binding.tvAgricultureArea.setTextColor(ContextCompat.getColor(this, R.color.black));
                agricultureAreaSelected = true;
            }
        });
    }

    private boolean validateInputs(String name, String email, String password, String mobile) {
        boolean isValid = true;

        if (name.isEmpty()) {
            binding.txlName.setError("Name is required");
            isValid = false;
        }

        if (email.isEmpty()) {
            binding.txlEmail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txlEmail.setError("Enter a valid email");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.txlPassword.setError("Password is required");
            isValid = false;
        } else if(!isPasswordSecure(password)){
            binding.txlPassword.setError("Password must be at least 6 chars with upper, lower, digit & symbol");
        }

        if (mobile.isEmpty()) {
            binding.txlMobileNumber.setError("Mobile number is required");
            isValid = false;
        } else if (!isValidSriLankanMobile(mobile)) {
            binding.txlMobileNumber.setError("Enter a valid Sri Lankan mobile number");
            isValid = false;
        }

        if (!agricultureAreaSelected) {
            binding.txlAgricultureArea.setError("Agriculture area is required");
            isValid = false;
        }


        return isValid;
    }
    private boolean isValidSriLankanMobile(String mobile) {
        // Allow: 0712345678 or +94712345678 or 94712345678
        return mobile.matches("^(?:\\+94|94|0)?7[01245678]\\d{7}$");
    }
    private boolean isPasswordSecure(String password) {
        // At least 1 digit, 1 lower, 1 upper, 1 special char, and 6+ chars
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{6,}$";
        return password.matches(passwordPattern);
    }


    private void showProgress(boolean show) {
        binding.progressLogin.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.btnRegister.setEnabled(!show);
    }

}