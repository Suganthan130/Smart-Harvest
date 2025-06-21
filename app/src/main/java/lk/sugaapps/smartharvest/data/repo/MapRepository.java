package lk.sugaapps.smartharvest.data.repo;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class MapRepository {
    private final Geocoder geocoder;

    @Inject
    public MapRepository(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public String getAreaName(double lat, double lng) {

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            return addresses.isEmpty() ? "Unknown Area" : addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            return null;
        }
    }
}
