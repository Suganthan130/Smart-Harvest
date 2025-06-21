package lk.sugaapps.smartharvest.data.model;

public class CropHandBookModel {
    private String id;
    private  String name;
    private String pdfUrl;
    private String pdfCoverPage;

    public CropHandBookModel() {
    }

    public CropHandBookModel(String id, String name, String pdfUrl, String pdfCoverPage) {
        this.id = id;
        this.name = name;
        this.pdfUrl = pdfUrl;
        this.pdfCoverPage = pdfCoverPage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfCoverPage() {
        return pdfCoverPage;
    }

    public void setPdfCoverPage(String pdfCoverPage) {
        this.pdfCoverPage = pdfCoverPage;
    }
}
