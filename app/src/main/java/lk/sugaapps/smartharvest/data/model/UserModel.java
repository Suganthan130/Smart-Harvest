package lk.sugaapps.smartharvest.data.model;

import com.google.firebase.firestore.GeoPoint;

public class UserModel {
    private String uid;
    private String name;
    private String email;
    private String mobile;
    private GeoPoint location;

    public UserModel() {
        // Required for Firebase
    }
    public UserModel(String uid, String name, String email, String mobile, double latitude, double longitude) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.location = new GeoPoint(latitude,longitude);
    }

    public UserModel(String uid, String name, String email, String mobile, GeoPoint location) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}