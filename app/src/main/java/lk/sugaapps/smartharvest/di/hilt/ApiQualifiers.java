package lk.sugaapps.smartharvest.di.hilt;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

public class ApiQualifiers {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface WeatherApi {}
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PriceDetailsApi {}

}