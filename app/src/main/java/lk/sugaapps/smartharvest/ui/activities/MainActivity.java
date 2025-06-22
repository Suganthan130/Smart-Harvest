package lk.sugaapps.smartharvest.ui.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.BuildConfig;
import lk.sugaapps.smartharvest.data.model.CropHandBookModel;
import lk.sugaapps.smartharvest.data.model.UserModel;
import lk.sugaapps.smartharvest.data.model.WeatherItemModel;
import lk.sugaapps.smartharvest.data.remote.model.VegetableResponse;
import lk.sugaapps.smartharvest.data.remote.model.WeatherResponse;
import lk.sugaapps.smartharvest.databinding.ActivityMainBinding;
import lk.sugaapps.smartharvest.ui.adapter.CropHandBookAdapter;
import lk.sugaapps.smartharvest.ui.adapter.VegetablePriceAdapter;
import lk.sugaapps.smartharvest.ui.adapter.WeatherAdapter;
import lk.sugaapps.smartharvest.utils.DialogUtils;
import lk.sugaapps.smartharvest.utils.LogUtil;
import lk.sugaapps.smartharvest.viewmodel.FirebaseViewModel;
import lk.sugaapps.smartharvest.viewmodel.UserViewModel;
import lk.sugaapps.smartharvest.viewmodel.VegetableDetailsViewModel;
import lk.sugaapps.smartharvest.viewmodel.WeatherViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding ;
    private WeatherViewModel weatherViewModel;
    private FirebaseViewModel firebaseViewModel;
    private UserViewModel userViewModel;
    private List<CropHandBookModel> cropHandBookModelList;
    @Inject
    DialogUtils dialogUtils;
    private RecyclerView recyclerViewCopHandBooks;
    private CropHandBookAdapter cropHandBookAdapter;
    private VegetableDetailsViewModel vegetableDetailsViewModel;
    private RecyclerView recyclerViewVegetables;
    private VegetablePriceAdapter vegetablePriceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialView();
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        vegetableDetailsViewModel = new ViewModelProvider(this).get(VegetableDetailsViewModel.class);
        observeData();
        onClick();


        firebaseViewModel.callForGetUserDetails();
        firebaseViewModel.callForGetCropHandBook();
        firebaseViewModel.callForGetVegetablesData();
       // vegetableDetailsViewModel.loadVegetableDetailsRepository();

        vegetableDetailsViewModel.getVegetableDetailsRepository().observe(this, resource -> {
            switch (resource.getStatus()) {
                case LOADING:
                    // show progress bar
                    break;
                case SUCCESS:
                    LogUtil.logInfo(String.valueOf(resource.getData().get(0).getDetails()));

                    break;
                case ERROR:
                    LogUtil.logError("Error :"+resource.getMessage());
                    Toast.makeText(this, "Error: " + resource.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });


    }

    private void onClick() {
        binding.buttonCropAdvisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CropAdvisorActivity.class));
            }
        });
    }

    private void initialView() {
        recyclerViewCopHandBooks = binding.recyclerViewCropHandbooks;
        recyclerViewCopHandBooks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewVegetables = binding.recyclerViewDailyPrices;
        recyclerViewVegetables.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    private void observeData() {

        weatherViewModel.getManuallyWeatherData().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    binding.progressBarLocation.setVisibility(View.VISIBLE);
                    binding.addLocation.setEnabled(false);
                    break;
                case SUCCESS:
                    binding.progressBarLocation.setVisibility(View.GONE);
                    binding.addLocation.setEnabled(true);
                    dialogUtils.showOkCancelDialog(
                            MainActivity.this,
                            "Location Detected",
                            "your searched location is " + resource.getData().getName(),
                            "Correct. Add the Location", () -> {
                                if (resource.getData()!=null){
                                    weatherViewModel.callForWeatherData(BuildConfig.WEATHER_API_KEY, resource.getData().getName());
                                    userViewModel.insertLocation(resource.getData());
                                }

                            }, "No, Try Again", () -> {

                            }
                    );
                    binding.shimmerWeather.setVisibility(View.GONE);
                    binding.shimmerWeather.stopShimmer();
                    binding.constraintLayoutWeather.setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    binding.progressBarLocation.setVisibility(View.GONE);
                    binding.addLocation.setEnabled(true);
                    if (resource.getStatusCode()==400){
                        binding.txlLocation.setError("Location Not Detected");
                    }

                    break;
            }
        });

        weatherViewModel.getWeatherData()
                .observe(this, resource -> {
                    switch (resource.status) {
                        case LOADING:
                            binding.shimmerWeather.setVisibility(View.VISIBLE);
                            binding.shimmerWeather.startShimmer();
                            binding.constraintLayoutWeather.setVisibility(View.INVISIBLE);
                            break;
                        case SUCCESS:
                            showWeatherData(resource.getData());
                            binding.shimmerWeather.setVisibility(View.GONE);
                            binding.shimmerWeather.stopShimmer();
                            binding.constraintLayoutWeather.setVisibility(View.VISIBLE);
                            break;
                        case ERROR:
                            if (resource.getStatusCode()==400){
                                dialogUtils.showErrorDialog(
                                        MainActivity.this,
                                        "Location Not Detected",
                                        "We couldn’t access your current location to show weather details. You can manually enter your near city location instead.",
                                        "Enter Near City Manually", new Runnable() {
                                            @Override
                                            public void run() {

                                            }
                                        }
                                );
                                showNotFoundWeatherData();
                            }
                            binding.shimmerWeather.setVisibility(View.GONE);
                            binding.shimmerWeather.stopShimmer();
                            binding.constraintLayoutWeather.setVisibility(View.VISIBLE);
                            break;
                    }
                });
        firebaseViewModel.getUserInfoResult().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:

                    break;
                case SUCCESS:
                    fetchWeatherData(resource.getData());
                    userViewModel.saveUser(resource.getData());
                    break;
                case ERROR:
                   dialogUtils.showErrorDialog(this,"Failed to Load User Information",
                           "We couldn’t retrieve your account details from the server. Please check your internet connection and try again."
                           , "Retry", (Runnable) () -> {

                           });

                    break;
            }
        });

        firebaseViewModel.getCropHandBookResult().observe(this, resource -> {

            switch (resource.status) {
                case LOADING:
                    binding.recyclerViewCropHandbooks.setVisibility(View.GONE);
                    binding.shimmerCropHandBook.setVisibility(View.VISIBLE);
                    binding.shimmerCropHandBook.startShimmer();
                    break;
                case SUCCESS:
                    cropHandBookAdapter = new CropHandBookAdapter(resource.getData());
                    recyclerViewCopHandBooks.setAdapter(cropHandBookAdapter);
                    binding.recyclerViewCropHandbooks.setVisibility(View.VISIBLE);
                    binding.shimmerCropHandBook.setVisibility(View.GONE);
                    binding.shimmerCropHandBook.stopShimmer();
                    break;
                case ERROR:
                    binding.recyclerViewCropHandbooks.setVisibility(View.VISIBLE);
                    binding.shimmerCropHandBook.setVisibility(View.GONE);
                    binding.shimmerCropHandBook.stopShimmer();
                    break;
            }

        });

        firebaseViewModel.getVegetableData().observe(this, resource -> {

            switch (resource.status) {
                case LOADING:
              //      binding.recyclerViewCropHandbooks.setVisibility(View.GONE);
             ///       binding.shimmerCropHandBook.setVisibility(View.VISIBLE);
             //       binding.shimmerCropHandBook.startShimmer();
                    break;
                case SUCCESS:
                //    cropHandBookAdapter = new CropHandBookAdapter(resource.getData());
              //      recyclerViewCopHandBooks.setAdapter(cropHandBookAdapter);
            //        binding.recyclerViewCropHandbooks.setVisibility(View.VISIBLE);
            //        binding.shimmerCropHandBook.setVisibility(View.GONE);
            //        binding.shimmerCropHandBook.stopShimmer();
                    vegetablePriceAdapter = new VegetablePriceAdapter(resource.getData(), new VegetablePriceAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(VegetableResponse item) {

                        }
                    });
                    recyclerViewVegetables.setAdapter(vegetablePriceAdapter);


                    break;
                case ERROR:
                  //  binding.recyclerViewCropHandbooks.setVisibility(View.VISIBLE);
                  //  binding.shimmerCropHandBook.setVisibility(View.GONE);
                  //  binding.shimmerCropHandBook.stopShimmer();
                    break;
            }

        });

    }


    private void showNotFoundWeatherData() {
        binding.txlLocation.setVisibility(View.VISIBLE);
        binding.addLocation.setVisibility(View.VISIBLE);
        binding.recyclerViewWeather.setVisibility(View.GONE);
        binding.addLocation.setOnClickListener(v -> {
            if (!Objects.requireNonNull(binding.txlLocation.getEditText()).getText().toString().isEmpty()){
                weatherViewModel.callForManuallyWeatherData(BuildConfig.WEATHER_API_KEY, binding.txlLocation.getEditText().getText().toString());
            }else {
                binding.txlLocation.setError("Please Enter your location");
            }
        });
    }

    private void fetchWeatherData(UserModel data) {
        if (userViewModel.getLocationList().isEmpty()){
            weatherViewModel.callForWeatherData(BuildConfig.WEATHER_API_KEY, String.valueOf(data.getLocation().getLatitude()/data.getLocation().getLongitude()));
        }else {
            weatherViewModel.callForWeatherData(BuildConfig.WEATHER_API_KEY, userViewModel.getLocationList().get(0).getName());
        }

    }
    private void showLoadingState() {

    }

    @SuppressLint("DefaultLocale")
    private void showWeatherData(WeatherResponse weather) {
        if (weather.getCurrent()!=null){
            binding.txlLocation.setVisibility(View.GONE);
            binding.addLocation.setVisibility(View.GONE);
            binding.recyclerViewWeather.setVisibility(View.VISIBLE);
            List<WeatherItemModel> weatherItemModelList = new ArrayList<>();
            binding.txvLocation.setText(weather.getLocation().getName());
            weatherItemModelList.add(new WeatherItemModel(weather.getCurrent().getCondition().getText(),weather.getCurrent().getCondition().getText(),weather.getCurrent().getCondition().getIcon(),weather.getCurrent().getTempC()+" C"));
            weatherItemModelList.add(new WeatherItemModel("Wind", "Wind",  "wind.png", weather.getCurrent().getWindKph() + " Kph"));
            weatherItemModelList.add(new WeatherItemModel("Humidity", "Humidity", "humidity.png", weather.getCurrent().getHumidity() + " %"));
            WeatherAdapter weatherAdapter = new WeatherAdapter(MainActivity.this, weatherItemModelList);
            int spanCount = 3;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
            binding.recyclerViewWeather.setLayoutManager(gridLayoutManager);
            binding.recyclerViewWeather.setAdapter(weatherAdapter);
        }

    }

    private void showErrorState(String message) {

    }
 /*   private void showChart() {
        ArrayList<BarEntry> entriesPlace1 = new ArrayList<>();
        ArrayList<BarEntry> entriesPlace2 = new ArrayList<>();
        ArrayList<BarEntry> entriesPlace3 = new ArrayList<>();

        for (int i = 0; i < dates.length; i++) {
            // Group bars at same x position with different offsets
            entriesPlace1.add(new BarEntry(i, Float.parseFloat(place1[i])));
            entriesPlace2.add(new BarEntry(i, Float.parseFloat(place2[i])));
            entriesPlace3.add(new BarEntry(i, Float.parseFloat(place3[i])));
        }

        BarDataSet dataSet1 = new BarDataSet(entriesPlace1, "Place 1");
        dataSet1.setColor(Color.RED);

        BarDataSet dataSet2 = new BarDataSet(entriesPlace2, "Place 2");
        dataSet2.setColor(Color.BLUE);

        BarDataSet dataSet3 = new BarDataSet(entriesPlace3, "Place 3");
        dataSet3.setColor(Color.GREEN);

        BarData data = new BarData(dataSet1, dataSet2, dataSet3);

        // Make sure all bars fit
        float groupSpace = 0.2f;
        float barSpace = 0.02f;
        float barWidth = 0.25f;

        data.setBarWidth(barWidth);
        barChart.setData(data);
        barChart.groupBars(0f, groupSpace, barSpace); // Group the bars
        barChart.invalidate();

        // X Axis config
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(0 + data.getGroupWidth(groupSpace, barSpace) * dates.length);

        // Y Axis config
        barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        // Other configs
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.setVisibleXRangeMaximum(4);
        barChart.animateY(1000);
    }*/
}