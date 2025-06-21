package lk.sugaapps.smartharvest.data.model;

public class Resource<T> {
    public enum Status { SUCCESS, ERROR, LOADING }

    public final Status status;
    public final T data;
    public final String message;
    private final int statusCode;

    public Resource(Status status, T data, String message, int statusCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static <T> Resource<T> success(T data,int statusCode) {
        return new Resource<>(Status.SUCCESS, data, null,statusCode);
    }

    public static <T> Resource<T> error(String msg, T data,int statusCode) {
        return new Resource<>(Status.ERROR, data, msg,statusCode);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource<>(Status.LOADING, data, null,0);
    }
}