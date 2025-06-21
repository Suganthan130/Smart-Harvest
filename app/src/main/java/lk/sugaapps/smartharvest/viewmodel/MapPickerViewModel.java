package lk.sugaapps.smartharvest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lk.sugaapps.smartharvest.data.repo.MapRepository;
@HiltViewModel
public class MapPickerViewModel extends ViewModel {

    private final MapRepository repository;

    private final MutableLiveData<String> areaNameLiveData = new MutableLiveData<>();

    @Inject
    public MapPickerViewModel(MapRepository repository) {
        this.repository = repository;
    }

    public void fetchAreaName(double lat, double lng) {
        String name = repository.getAreaName(lat, lng);
        areaNameLiveData.postValue(name);
    }

    public LiveData<String> getAreaNameLiveData() {
        return areaNameLiveData;
    }
}