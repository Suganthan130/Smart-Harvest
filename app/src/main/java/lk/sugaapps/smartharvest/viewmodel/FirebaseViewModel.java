package lk.sugaapps.smartharvest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lk.sugaapps.smartharvest.data.model.CropHandBookModel;
import lk.sugaapps.smartharvest.data.model.Resource;
import lk.sugaapps.smartharvest.data.model.UserModel;
import lk.sugaapps.smartharvest.data.remote.model.VegetableResponse;
import lk.sugaapps.smartharvest.data.repo.FirebaseRepository;
import lk.sugaapps.smartharvest.utils.DialogUtils;

@HiltViewModel
public class FirebaseViewModel extends ViewModel {

    private final FirebaseRepository firebaseRepository;
    private final MutableLiveData<Resource<UserModel>> userInfoResult = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<CropHandBookModel>>> cropHandBookResult = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<VegetableResponse>>> vegetableData = new MutableLiveData<>();
    @Inject
    public FirebaseViewModel(FirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }
    public void callForGetUserDetails() {
        userInfoResult.setValue(Resource.loading(null));
        firebaseRepository.getUserDetailsFormFireStore()
                .observeForever(userInfoResult::postValue);
    }
    public void callForGetCropHandBook() {
        cropHandBookResult.setValue(Resource.loading(null));
        firebaseRepository.getCropHandBook()
                .observeForever(cropHandBookResult::postValue);
    }
    public MutableLiveData<Resource<List<CropHandBookModel>>> getCropHandBookResult() {
        return cropHandBookResult;
    }
    public MutableLiveData<Resource<UserModel>> getUserInfoResult() {
        return userInfoResult;
    }
    public MutableLiveData<Resource<List<VegetableResponse>>> getVegetableData() {
        return vegetableData;
    }

    public void callForGetVegetablesData() {
        vegetableData.setValue(Resource.loading(null));
        firebaseRepository.getVegetableDetails()
                .observeForever(vegetableData::postValue);
    }


}
