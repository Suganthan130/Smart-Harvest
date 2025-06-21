package lk.sugaapps.smartharvest.data.repo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import lk.sugaapps.smartharvest.data.model.Resource;
import lk.sugaapps.smartharvest.data.remote.api.WeatherApiService;
import lk.sugaapps.smartharvest.data.remote.model.LocationResultResponse;
import lk.sugaapps.smartharvest.data.remote.model.WeatherForecastResponse;
import lk.sugaapps.smartharvest.data.remote.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import javax.inject.Inject;

public class WeatherRepository {
    private final WeatherApiService weatherApiService;

    @Inject
    public WeatherRepository(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }
    public interface MidnightHourCallback {
        void onResult(@Nullable WeatherForecastResponse.Hour hour);
    }

    public void getMidnightHour(String location, String apiKey, MidnightHourCallback callback) {
        weatherApiService.getForecast(apiKey, location, 1, "no", "no")
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherForecastResponse> call, @NonNull Response<WeatherForecastResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            for (WeatherForecastResponse.Hour hour : response.body().forecast.forecastday.get(0).hour) {
                                if (hour.time.endsWith("00:00")) {
                                    callback.onResult(hour);
                                    return;
                                }
                            }
                        }
                        callback.onResult(null);
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherForecastResponse> call, @NonNull Throwable t) {
                        callback.onResult(null);
                    }
                });
    }

    public void getCurrentWeather(MutableLiveData<Resource<WeatherResponse>> weatherData, String apiKey, String location) {

        weatherData.setValue(Resource.loading(null));

        weatherApiService.getCurrentWeather(apiKey, location).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherData.setValue(Resource.success(response.body(),response.code()));
                } else {
                    weatherData.setValue(Resource.error("Error: " + response.code(), null,response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                weatherData.setValue(Resource.error(t.getMessage(), null,500));
            }
        });

    }

    public void getManuallyCurrentWeather(MutableLiveData<Resource<LocationResultResponse>> manuallyWeatherData, String apiKey, String location) {
        manuallyWeatherData.setValue(Resource.loading(null));

        weatherApiService.searchLocation(apiKey, location).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<LocationResultResponse>> call, Response<List<LocationResultResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()){
                        manuallyWeatherData.setValue(Resource.error("Error: " + response.code(), null,400));
                    }else {
                        manuallyWeatherData.setValue(Resource.success(response.body().get(0),response.code()));
                    }

                } else {
                    manuallyWeatherData.setValue(Resource.error("Error: " + response.code(), null,response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<LocationResultResponse>> call, Throwable t) {
                manuallyWeatherData.setValue(Resource.error(t.getMessage(), null,500));
            }

        });

    }
}