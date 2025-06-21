package lk.sugaapps.smartharvest.di.hilt;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import lk.sugaapps.smartharvest.data.remote.api.PriceDetailsApiService;
import lk.sugaapps.smartharvest.data.remote.api.WeatherApiService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    private static final String WEATHER_URL = "https://api.weatherapi.com/v1/";
    private static final String AGINFOHUB_URL = "https://aginfohub.de/";

    @Provides
    @Singleton
    public static HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .header("User-Agent", "SmartHarvest/1.0")
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @ApiQualifiers.WeatherApi
    public static Retrofit provideWeatherRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(WEATHER_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @ApiQualifiers.PriceDetailsApi
    public static Retrofit providePriceDeatilsRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(AGINFOHUB_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public static PriceDetailsApiService providePriceDetailsApiService(@ApiQualifiers.PriceDetailsApi Retrofit retrofit) {
        return retrofit.create(PriceDetailsApiService.class);
    }

    @Provides
    @Singleton
    public static WeatherApiService provideWeatherApiService(@ApiQualifiers.WeatherApi Retrofit retrofit) {
        return retrofit.create(WeatherApiService.class);
    }

}