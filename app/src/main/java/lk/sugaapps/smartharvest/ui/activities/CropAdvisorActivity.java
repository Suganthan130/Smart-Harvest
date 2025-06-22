package lk.sugaapps.smartharvest.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.databinding.ActivityCropAdvisorBinding;
import lk.sugaapps.smartharvest.viewmodel.CropViewModel;

@AndroidEntryPoint
public class CropAdvisorActivity extends AppCompatActivity {
    private ActivityCropAdvisorBinding binding;
    private CropViewModel viewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCropAdvisorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CropViewModel.class);

        viewModel.getLux().observe(this, lux -> binding.tvLux.setText(lux+"lumen/m²"));
        viewModel.getCropAdvice().observe(this, binding.tvTips::setText);

    }
}