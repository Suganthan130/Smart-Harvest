package lk.sugaapps.smartharvest.di.hilt;

import android.app.Application;
import android.location.Geocoder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LocationModule {

    @Provides
    @Singleton
    public static Geocoder provideGeocoder(Application application) {
        return new Geocoder(application, Locale.getDefault());
    }

    @Provides
    @Singleton
    public static FusedLocationProviderClient provideFusedLocationClient(Application application) {
        return LocationServices.getFusedLocationProviderClient(application);
    }
}