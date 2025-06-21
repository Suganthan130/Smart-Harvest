package lk.sugaapps.smartharvest.data.remote.model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("location")
    public Location location;

    @SerializedName("current")
    public Current current;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public static class Location {
        @SerializedName("name")
        public String name;

        @SerializedName("region")
        public String region;

        @SerializedName("country")
        public String country;

        @SerializedName("lat")
        public double lat;

        @SerializedName("lon")
        public double lon;

        @SerializedName("tz_id")
        public String timeZoneId;

        @SerializedName("localtime_epoch")
        public long localtimeEpoch;

        @SerializedName("localtime")
        public String localtime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getTimeZoneId() {
            return timeZoneId;
        }

        public void setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
        }

        public long getLocaltimeEpoch() {
            return localtimeEpoch;
        }

        public void setLocaltimeEpoch(long localtimeEpoch) {
            this.localtimeEpoch = localtimeEpoch;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }
    }

    public static class Current {
        @SerializedName("last_updated_epoch")
        public long lastUpdatedEpoch;

        @SerializedName("last_updated")
        public String lastUpdated;

        @SerializedName("temp_c")
        public float tempC;

        @SerializedName("temp_f")
        public float tempF;

        @SerializedName("is_day")
        public int isDay;

        @SerializedName("condition")
        public Condition condition;

        @SerializedName("wind_mph")
        public float windMph;

        @SerializedName("wind_kph")
        public float windKph;

        @SerializedName("wind_degree")
        public int windDegree;

        @SerializedName("wind_dir")
        public String windDir;

        @SerializedName("pressure_mb")
        public float pressureMb;

        @SerializedName("pressure_in")
        public float pressureIn;

        @SerializedName("precip_mm")
        public float precipMm;

        @SerializedName("precip_in")
        public float precipIn;

        @SerializedName("humidity")
        public int humidity;

        @SerializedName("cloud")
        public int cloud;

        @SerializedName("feelslike_c")
        public float feelsLikeC;

        @SerializedName("feelslike_f")
        public float feelsLikeF;

        @SerializedName("windchill_c")
        public float windChillC;

        @SerializedName("windchill_f")
        public float windChillF;

        @SerializedName("heatindex_c")
        public float heatIndexC;

        @SerializedName("heatindex_f")
        public float heatIndexF;

        @SerializedName("dewpoint_c")
        public float dewPointC;

        @SerializedName("dewpoint_f")
        public float dewPointF;

        @SerializedName("vis_km")
        public float visibilityKm;

        @SerializedName("vis_miles")
        public float visibilityMiles;

        @SerializedName("uv")
        public float uv;

        @SerializedName("gust_mph")
        public float gustMph;

        @SerializedName("gust_kph")
        public float gustKph;

        public long getLastUpdatedEpoch() {
            return lastUpdatedEpoch;
        }

        public void setLastUpdatedEpoch(long lastUpdatedEpoch) {
            this.lastUpdatedEpoch = lastUpdatedEpoch;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public float getTempC() {
            return tempC;
        }

        public void setTempC(float tempC) {
            this.tempC = tempC;
        }

        public float getTempF() {
            return tempF;
        }

        public void setTempF(float tempF) {
            this.tempF = tempF;
        }

        public int getIsDay() {
            return isDay;
        }

        public void setIsDay(int isDay) {
            this.isDay = isDay;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public float getWindMph() {
            return windMph;
        }

        public void setWindMph(float windMph) {
            this.windMph = windMph;
        }

        public float getWindKph() {
            return windKph;
        }

        public void setWindKph(float windKph) {
            this.windKph = windKph;
        }

        public int getWindDegree() {
            return windDegree;
        }

        public void setWindDegree(int windDegree) {
            this.windDegree = windDegree;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public float getPressureMb() {
            return pressureMb;
        }

        public void setPressureMb(float pressureMb) {
            this.pressureMb = pressureMb;
        }

        public float getPressureIn() {
            return pressureIn;
        }

        public void setPressureIn(float pressureIn) {
            this.pressureIn = pressureIn;
        }

        public float getPrecipMm() {
            return precipMm;
        }

        public void setPrecipMm(float precipMm) {
            this.precipMm = precipMm;
        }

        public float getPrecipIn() {
            return precipIn;
        }

        public void setPrecipIn(float precipIn) {
            this.precipIn = precipIn;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getCloud() {
            return cloud;
        }

        public void setCloud(int cloud) {
            this.cloud = cloud;
        }

        public float getFeelsLikeC() {
            return feelsLikeC;
        }

        public void setFeelsLikeC(float feelsLikeC) {
            this.feelsLikeC = feelsLikeC;
        }

        public float getFeelsLikeF() {
            return feelsLikeF;
        }

        public void setFeelsLikeF(float feelsLikeF) {
            this.feelsLikeF = feelsLikeF;
        }

        public float getWindChillC() {
            return windChillC;
        }

        public void setWindChillC(float windChillC) {
            this.windChillC = windChillC;
        }

        public float getWindChillF() {
            return windChillF;
        }

        public void setWindChillF(float windChillF) {
            this.windChillF = windChillF;
        }

        public float getHeatIndexC() {
            return heatIndexC;
        }

        public void setHeatIndexC(float heatIndexC) {
            this.heatIndexC = heatIndexC;
        }

        public float getHeatIndexF() {
            return heatIndexF;
        }

        public void setHeatIndexF(float heatIndexF) {
            this.heatIndexF = heatIndexF;
        }

        public float getDewPointC() {
            return dewPointC;
        }

        public void setDewPointC(float dewPointC) {
            this.dewPointC = dewPointC;
        }

        public float getDewPointF() {
            return dewPointF;
        }

        public void setDewPointF(float dewPointF) {
            this.dewPointF = dewPointF;
        }

        public float getVisibilityKm() {
            return visibilityKm;
        }

        public void setVisibilityKm(float visibilityKm) {
            this.visibilityKm = visibilityKm;
        }

        public float getVisibilityMiles() {
            return visibilityMiles;
        }

        public void setVisibilityMiles(float visibilityMiles) {
            this.visibilityMiles = visibilityMiles;
        }

        public float getUv() {
            return uv;
        }

        public void setUv(float uv) {
            this.uv = uv;
        }

        public float getGustMph() {
            return gustMph;
        }

        public void setGustMph(float gustMph) {
            this.gustMph = gustMph;
        }

        public float getGustKph() {
            return gustKph;
        }

        public void setGustKph(float gustKph) {
            this.gustKph = gustKph;
        }
    }

    public static class Condition {
        @SerializedName("text")
        public String text;

        @SerializedName("icon")
        public String icon;

        @SerializedName("code")
        public int code;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
