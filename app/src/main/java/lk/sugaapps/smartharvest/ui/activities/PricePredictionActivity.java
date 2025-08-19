package lk.sugaapps.smartharvest.ui.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.Constant;
import lk.sugaapps.smartharvest.databinding.ActivityPricePredictionBinding;
import lk.sugaapps.smartharvest.ui.adapter.VegetablePredicateAdapter;
import lk.sugaapps.smartharvest.viewmodel.VegetableDetailsViewModel;

@AndroidEntryPoint

public class PricePredictionActivity extends AppCompatActivity {
    ActivityPricePredictionBinding binding;
    @Inject
    FirebaseFirestore db;
    String TAG = "PricePredictionActivity";

    private VegetableDetailsViewModel vegetableDetailsViewModel;
    private VegetablePredicateAdapter vegetablePredicateAdapter;
    private RecyclerView recyclerViewVegetablePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPricePredictionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerViewVegetablePrice = binding.recyclerViewVegetablePrice;
        recyclerViewVegetablePrice.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        vegetableDetailsViewModel = new ViewModelProvider(this).get(VegetableDetailsViewModel.class);
        String vegetableName = getIntent().getStringExtra(Constant.VEG_NAME);
        String vegetableID = getIntent().getStringExtra(Constant.VEG_ID);

        binding.tvVegName.setText(vegetableName);

        vegetableDetailsViewModel.loadPricePredicatedRepository(vegetableID);


        vegetableDetailsViewModel.getPricePredicatedRepository().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    binding.priceProgressBar.setVisibility(VISIBLE);
                    binding.recyclerViewVegetablePrice.setVisibility(GONE);

                    break;
                case SUCCESS:
                    binding.priceProgressBar.setVisibility(GONE);
                    binding.recyclerViewVegetablePrice.setVisibility(VISIBLE);
                    vegetablePredicateAdapter = new VegetablePredicateAdapter(resource.getData());
                    recyclerViewVegetablePrice.setAdapter(vegetablePredicateAdapter);
                    break;
                case ERROR:
                    binding.priceProgressBar.setVisibility(GONE);
                    binding.recyclerViewVegetablePrice.setVisibility(VISIBLE);

                    break;
            }
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

    }
}