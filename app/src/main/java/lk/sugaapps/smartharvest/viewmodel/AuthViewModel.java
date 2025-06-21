package lk.sugaapps.smartharvest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.GeoPoint;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lk.sugaapps.smartharvest.data.model.LocationModel;
import lk.sugaapps.smartharvest.data.model.Resource;
import lk.sugaapps.smartharvest.data.model.UserModel;
import lk.sugaapps.smartharvest.data.repo.AuthRepository;
@HiltViewModel
public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<Resource<FirebaseUser>> loginResult = new MutableLiveData<>();
    private final MutableLiveData<Resource<UserModel>> registerResult = new MutableLiveData<>();
    private final MutableLiveData<LocationModel> locationResult = new MutableLiveData<>();

    @Inject
    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public MutableLiveData<LocationModel> getLocationResult() {
        return locationResult;
    }
    public void setLocationResult(LocationModel location){
        locationResult.setValue(location);
    }

    public void login(String email, String password) {
        loginResult.setValue(Resource.loading(null));
        authRepository.login(email, password)
                .observeForever(loginResult::postValue);
    }

    public LiveData<Resource<FirebaseUser>> getLoginResult() {
        return loginResult;
    }
    public void registerUser(String name, String email, String password,
                             String mobile, GeoPoint location) {
        registerResult.setValue(Resource.loading(null));
        authRepository.registerUser(name, email, password, mobile, location)
                .observeForever(registerResult::postValue);
    }

    public LiveData<Resource<UserModel>> getRegisterResult() {
        return registerResult;
    }
}