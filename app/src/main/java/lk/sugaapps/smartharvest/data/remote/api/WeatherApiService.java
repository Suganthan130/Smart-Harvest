package lk.sugaapps.smartharvest.data.remote.api;

import com.google.android.gms.location.LocationResult;

import java.util.List;

import lk.sugaapps.smartharvest.data.remote.model.LocationResultResponse;
import lk.sugaapps.smartharvest.data.remote.model.WeatherForecastResponse;
import lk.sugaapps.smartharvest.data.remote.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherApiService {
    @Headers("Accept: application/json")
    @GET("current.json")
    Call<WeatherResponse> getCurrentWeather(
            @Query("key") String key,
            @Query("q") String location
    );
    @GET("search.json")
    @Headers("Accept: application/json")
    Call<List<LocationResultResponse>> searchLocation(
            @Query("key") String apiKey,
            @Query("q") String query
    );
    @GET("v1/forecast.json")
    Call<WeatherForecastResponse> getForecast(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("days") int days,
            @Query("aqi") String aqi,
            @Query("alerts") String alerts
    );
}
