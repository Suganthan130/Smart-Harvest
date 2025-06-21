package lk.sugaapps.smartharvest.data.repo;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lk.sugaapps.smartharvest.data.local.sqlite.UserDao;
import lk.sugaapps.smartharvest.data.model.UserModel;
import lk.sugaapps.smartharvest.data.remote.model.LocationResultResponse;

@Singleton
public class UserRepository {

    private final UserDao userDao;

    @Inject
    public UserRepository(Context context) {
        this.userDao = new UserDao(context);
    }

    public void insertUser(UserModel user) {
        new Thread(() -> userDao.insertOrUpdateUser(user)).start();
    }

    public UserModel getUserById(String uid) {
        return userDao.getUserById(uid);
    }
    public List<LocationResultResponse> getLocationList() {
        return userDao.getAllLocations();
    }
    public void insertLocation(LocationResultResponse locationResultResponse) {
        new Thread(() -> userDao.insertLocation(locationResultResponse)).start();
    }
}
