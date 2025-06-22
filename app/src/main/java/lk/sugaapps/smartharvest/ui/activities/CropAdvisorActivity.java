package lk.sugaapps.smartharvest.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.databinding.ActivityCropAdvisorBinding;
import lk.sugaapps.smartharvest.utils.DialogUtils;
import lk.sugaapps.smartharvest.viewmodel.CropViewModel;

@AndroidEntryPoint
public class CropAdvisorActivity extends AppCompatActivity {
    private ActivityCropAdvisorBinding binding;
    @Inject
    DialogUtils dialogUtils;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCropAdvisorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CropViewModel viewModel = new ViewModelProvider(this).get(CropViewModel.class);

        viewModel.getLux().observe(this, lux -> binding.tvLux.setText(lux+" lumen/m²"));
        viewModel.getCropAdvice().observe(this, binding.tvTips::setText);
        viewModel.isSensorAvailable().observe(this, available -> {
            if (!available) {
                dialogUtils.showErrorDialog(CropAdvisorActivity.this,
                        "Sensor Not Available",
                        "Light sensor is not available on your device. Some features may not work.",
                        "OK", new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });

            }
        });

    }
}