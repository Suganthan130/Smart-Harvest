package lk.sugaapps.smartharvest.data.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lk.sugaapps.smartharvest.data.model.Resource;
import lk.sugaapps.smartharvest.data.remote.api.PriceDetailsApiService;
import lk.sugaapps.smartharvest.data.remote.model.VegetablePriceDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VegetableDetailsRepository {
    private final PriceDetailsApiService api;

    @Inject
    public VegetableDetailsRepository(PriceDetailsApiService api) {
        this.api = api;
    }

    public LiveData<Resource<List<VegetablePriceDetails>>> getSummaryData(String vegetable_id) {
        MutableLiveData<Resource<List<VegetablePriceDetails>>> liveData = new MutableLiveData<>();
        liveData.postValue(Resource.loading(null));

        api.getSummaryHtml(vegetable_id).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VegetablePriceDetails> list = parseHtml(response.body());
                    liveData.postValue(Resource.success(list, response.code()));
                } else {
                    liveData.postValue(Resource.error("Response not successful", null, response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                liveData.postValue(Resource.error(t.getMessage(), null, 0));
            }
        });

        return liveData;
    }

    private List<VegetablePriceDetails> parseHtml(String html) {
        List<VegetablePriceDetails> summaryList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements summaryDivs = document.select("div#summary-container div.summary");

        for (Element summary : summaryDivs) {
            String title = summary.selectFirst("h3").text(); // e.g., "Overall Summary"
            List<String> details = summary.select("p").eachText(); // All <p> under that block
            summaryList.add(new VegetablePriceDetails(title, details));
        }

        return summaryList;
    }
}
