package lk.sugaapps.smartharvest.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.Constant;
import lk.sugaapps.smartharvest.data.remote.model.VegetablePriceDetails;
import lk.sugaapps.smartharvest.databinding.ActivityPriceSummaryBinding;
import lk.sugaapps.smartharvest.ui.adapter.VegetableSummaryAdapter;
import lk.sugaapps.smartharvest.utils.LogUtil;
import lk.sugaapps.smartharvest.viewmodel.VegetableDetailsViewModel;
@AndroidEntryPoint
public class PriceSummaryActivity extends AppCompatActivity {
    private ActivityPriceSummaryBinding binding;
    private VegetableSummaryAdapter vegetableSummaryAdapter;
    private List<VegetablePriceDetails> vegetablePriceDetailsList;

    private VegetableDetailsViewModel vegetableDetailsViewModel;
    private RecyclerView recyclerViewVegetablePrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPriceSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialView();

        String vegetableName = getIntent().getStringExtra(Constant.VEG_NAME);
        String vegetableID = getIntent().getStringExtra(Constant.VEG_ID);

        binding.tvVegName.setText(vegetableName);

        vegetableDetailsViewModel = new ViewModelProvider(this).get(VegetableDetailsViewModel.class);
        vegetableDetailsViewModel.loadVegetableDetailsRepository(vegetableID);

        vegetableDetailsViewModel.getVegetableDetailsRepository().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    binding.priceProgressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.priceProgressBar.setVisibility(View.GONE);
                    vegetableSummaryAdapter = new VegetableSummaryAdapter(resource.getData());
                    recyclerViewVegetablePrice.setAdapter(vegetableSummaryAdapter);
                    for (VegetablePriceDetails item : resource.getData()) {
                        LogUtil.logSuccess(item.getTitle());
                        for (String details : item.getDetails()) {
                            LogUtil.logSuccess(details);
                        }
                    }
                    break;
                case ERROR:

                    break;
            }
        });
        binding.ivBack.setOnClickListener(v -> {
           finish();
        });
    }

    private void initialView() {
        recyclerViewVegetablePrice = binding.recyclerViewVegetablePrice;
        recyclerViewVegetablePrice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}