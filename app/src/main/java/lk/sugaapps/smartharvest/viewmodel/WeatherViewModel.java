package lk.sugaapps.smartharvest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lk.sugaapps.smartharvest.data.model.Resource;
import lk.sugaapps.smartharvest.data.remote.model.LocationResultResponse;
import lk.sugaapps.smartharvest.data.remote.model.WeatherResponse;
import lk.sugaapps.smartharvest.data.repo.WeatherRepository;

@HiltViewModel
public class WeatherViewModel extends ViewModel {
    private final WeatherRepository weatherRepository;
    private final MutableLiveData<Resource<WeatherResponse>> weatherData = new MutableLiveData<>() ;
    private final MutableLiveData<Resource<LocationResultResponse>> manuallyWeatherData = new MutableLiveData<>() ;

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }
    public MutableLiveData<Resource<WeatherResponse>> getWeatherData() {
        return weatherData;
    }

    public void callForWeatherData(String apiKey, String location){
        weatherRepository.getCurrentWeather(weatherData,apiKey,location);
    }
    public void callForManuallyWeatherData(String apiKey, String location){
        weatherRepository.getManuallyCurrentWeather(manuallyWeatherData,apiKey,location);
    }
    public LiveData<Resource<LocationResultResponse>> getManuallyWeatherData() {
        return manuallyWeatherData;
    }


    public void refreshWeather(String apiKey, double lat, double lng) {
       // weatherDataweatherRepository.getCurrentWeather(apiKey, lat, lng);
    }
}