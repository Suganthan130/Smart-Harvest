package lk.sugaapps.smartharvest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lk.sugaapps.smartharvest.data.repo.CropAdvisorRepository;

@HiltViewModel
public class CropViewModel extends ViewModel {

    private final CropAdvisorRepository repository;

    @Inject
    public CropViewModel(CropAdvisorRepository repository) {
        this.repository = repository;
    }

    public LiveData<Integer> getLux() {
        return repository.getLuxData();
    }

    public LiveData<String> getCropAdvice() {
        return repository.getCropAdvice();
    }

    public LiveData<Boolean> isSensorAvailable() {
        return repository.isSensorAvailable();
    }
}
