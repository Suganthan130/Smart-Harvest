package lk.sugaapps.smartharvest.data.remote.model;

import java.util.List;

public class WeatherForecastResponse {
    public Forecast forecast;
    public Location location;
    public Current current;

    public class Forecast {
        public List<ForecastDay> forecastday;
    }

    public class ForecastDay {
        public String date;
        public List<Hour> hour;
    }

    public class Hour {
        public String time;
        public int chance_of_rain;
        public Condition condition;
    }

    public class Condition {
        public String text;
        public String icon;
    }

    public class Location {
        public String name;
        public String localtime;
    }

    public class Current {
        public double temp_c;
        public Condition condition;
    }
}