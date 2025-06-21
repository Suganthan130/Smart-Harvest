package lk.sugaapps.smartharvest.data.model;

public class WeatherItemModel {
    private String title;
    private String description;
    private String iconUrl;
    private String Value;

    public WeatherItemModel(String title, String description, String iconUrl, String value) {
        this.title = title;
        this.description = description;
        this.iconUrl = iconUrl;
        Value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
