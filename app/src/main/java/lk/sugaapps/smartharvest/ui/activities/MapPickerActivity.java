package lk.sugaapps.smartharvest.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.R;
import lk.sugaapps.smartharvest.data.model.LocationModel;
import lk.sugaapps.smartharvest.databinding.ActivityMapPickerBinding;
import lk.sugaapps.smartharvest.viewmodel.MapPickerViewModel;
@AndroidEntryPoint
public class MapPickerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityMapPickerBinding binding;

    private MapPickerViewModel viewModel;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    private LatLng selectedLatLng;
    private String selectedAreaName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMapPickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MapPickerViewModel.class);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        viewModel.getAreaNameLiveData().observe(this, name -> {
            selectedAreaName = name;

            if (name == null || name.trim().isEmpty() || name.toLowerCase().contains("unnamed road")) {
                if (selectedLatLng != null) {
                    @SuppressLint("DefaultLocale") String latLngStr = String.format("Lat: %.5f, Lng: %.5f",
                            selectedLatLng.latitude, selectedLatLng.longitude);
                    binding.tvAreaName.setText(latLngStr);
                    selectedAreaName = latLngStr;
                } else {
                    binding.tvAreaName.setText("Unknown location");
                }
            } else {
                binding.tvAreaName.setText(name);
            }
        });

        binding.btnConfirm.setOnClickListener(v -> {
            if (selectedLatLng != null && !selectedAreaName.isEmpty()) {
                Intent resultIntent = new Intent();
                LocationModel location = new LocationModel(selectedAreaName, selectedLatLng.latitude, selectedLatLng.longitude);
                resultIntent.putExtra("location_model", location);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show();
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        binding.btnBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }

        mMap.setOnMapClickListener(latLng -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            selectedLatLng = latLng;
            viewModel.fetchAreaName(latLng.latitude, latLng.longitude);
        });
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) return;

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 16f));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @androidx.annotation.NonNull String[] permissions,
                                           @androidx.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
        }
    }
}
