package lk.sugaapps.smartharvest.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lk.sugaapps.smartharvest.data.model.PredicatedPriceModel;
import lk.sugaapps.smartharvest.data.model.Resource;
import lk.sugaapps.smartharvest.data.remote.model.VegetablePriceDetails;
import lk.sugaapps.smartharvest.data.repo.VegetableDetailsRepository;
@HiltViewModel
public class VegetableDetailsViewModel extends ViewModel {
    private final VegetableDetailsRepository repository;
    private final MutableLiveData<Resource<List<VegetablePriceDetails>>> summaryLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<PredicatedPriceModel>>> pricePredicatedLiveData = new MutableLiveData<>();

    @Inject
    public VegetableDetailsViewModel(VegetableDetailsRepository repository) {
        this.repository = repository;
    }


    public LiveData<Resource<List<VegetablePriceDetails>>> getVegetableDetailsRepository() {
        return summaryLiveData;
    }

    public void loadVegetableDetailsRepository(String vegetable_id) {
        repository.getSummaryData(vegetable_id).observeForever(summaryLiveData::setValue);
    }
    public LiveData<Resource<List<PredicatedPriceModel>>> getPricePredicatedRepository() {
        return pricePredicatedLiveData;
    }
    public void loadPricePredicatedRepository(String vegetable_id) {
        repository.getPricePredicatedData(vegetable_id).observeForever(pricePredicatedLiveData::setValue);
    }

}
