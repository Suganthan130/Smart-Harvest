package lk.sugaapps.smartharvest.data.remote.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PriceDetailsApiService {
    @GET
    Call<String> getSummaryHtml(@Url String dynamicUrl);
}
