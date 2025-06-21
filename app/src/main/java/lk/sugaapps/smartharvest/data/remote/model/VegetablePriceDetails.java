package lk.sugaapps.smartharvest.data.remote.model;

import java.util.List;

public class VegetablePriceDetails {
    public String title;
    public List<String> details;

    public VegetablePriceDetails(String title, List<String> details) {
        this.title = title;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
