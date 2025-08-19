package lk.sugaapps.smartharvest.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dagger.hilt.android.AndroidEntryPoint;
import lk.sugaapps.smartharvest.R;
import lk.sugaapps.smartharvest.data.model.LocationModel;
import lk.sugaapps.smartharvest.databinding.ActivityMapPickerBinding;
import lk.sugaapps.smartharvest.viewmodel.MapPickerViewModel;

@AndroidEntryPoint
public class MapPickerActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private ActivityMapPickerBinding binding;
    private MapPickerViewModel viewModel;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    private LatLng selectedLatLng;
    private String selectedAreaName = "";
    private Marker draggableMarker;

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
                    if (draggableMarker != null) {
                        draggableMarker.setTitle("Selected Location");
                        draggableMarker.setSnippet(latLngStr);
                    }
                } else {
                    binding.tvAreaName.setText("Unknown location");
                }
            } else {
                binding.tvAreaName.setText(name);
                 if (draggableMarker != null) {
                    draggableMarker.setTitle("Selected Location");
                    draggableMarker.setSnippet(name);
                }
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
        binding.btnBack3.setOnClickListener(v -> finish());
    }

    private void updateSelectedLocation(LatLng latLng, String title) {
        selectedLatLng = latLng;
        viewModel.fetchAreaName(latLng.latitude, latLng.longitude);

        if (draggableMarker == null) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .draggable(true);
            draggableMarker = mMap.addMarker(markerOptions);
        } else {
            draggableMarker.setPosition(latLng);
            draggableMarker.setTitle(title);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerDragListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }

        mMap.setOnMapClickListener(latLng -> {
            updateSelectedLocation(latLng, "Selected Location");
        });
    }

    @SuppressLint("MissingPermission")
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Consider calling ActivityCompat#requestPermissions here to request the missing permissions
            return;
        }

        mMap.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                updateSelectedLocation(current, "Current Location");
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 16f));
            } else {
                // Handle case where last location is null, maybe set a default or prompt user
                Toast.makeText(this, "Could not get current location. Please select one manually.", Toast.LENGTH_LONG).show();;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123 && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            Toast.makeText(this, "Location permission required to show current location.", Toast.LENGTH_SHORT).show();
            // Optionally, place a default marker if permission is denied
            // updateSelectedLocation(new LatLng(0, 0), "Default Location"); // Example default
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0,0), 2f));
        }
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {
        // Optional: Called when a marker starts being dragged.
        // You can add visual feedback here if needed.
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {
        // Optional: Called repeatedly while a marker is being dragged.
        // You could update UI elements in real-time here, but it might be resource-intensive.
        // For now, we only care about the final position.
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        updateSelectedLocation(marker.getPosition(), "Selected Location");
        // Optionally move camera to the new marker position
        // mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
    }
}
