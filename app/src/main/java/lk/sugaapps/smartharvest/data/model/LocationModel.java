package lk.sugaapps.smartharvest.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class LocationModel implements Parcelable {
    private final String areaName;
    private final double latitude;
    private final double longitude;

    public LocationModel(String areaName, double latitude, double longitude) {
        this.areaName = areaName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected LocationModel(Parcel in) {
        areaName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    public String getAreaName() {
        return areaName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(areaName);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
