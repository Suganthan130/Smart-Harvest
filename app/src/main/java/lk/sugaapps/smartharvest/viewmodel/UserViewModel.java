package lk.sugaapps.smartharvest.viewmodel;


import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lk.sugaapps.smartharvest.data.model.UserModel;
import lk.sugaapps.smartharvest.data.remote.model.LocationResultResponse;
import lk.sugaapps.smartharvest.data.repo.UserRepository;
@HiltViewModel
public class UserViewModel extends ViewModel {
    private final UserRepository repository;


    @Inject
    public UserViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public void saveUser(UserModel user) {
        repository.insertUser(user);
    }

    public UserModel getUser(String uid) {
        return repository.getUserById(uid);
    }
    public List<LocationResultResponse> getLocationList() {
        return repository.getLocationList();
    }
    public void insertLocation(LocationResultResponse locationResultResponse) {
        repository.insertLocation(locationResultResponse);
    }
}
