package ir.makapps.hami.model;


import com.google.gson.annotations.SerializedName;

public class HamiResponse<T> {

    public HamiResponse() {
    }

    public HamiResponse(int resultCode, String message, T results) {
        ResultCode = resultCode;
        Message = message;
        Results = results;
    }

    @SerializedName("ResultCode")
    int ResultCode;

    @SerializedName("Message")
    String Message;

    @SerializedName("Body")
    T Results;


    public T getResults() {
        return Results;
    }

    public void setResults(T results) {
        Results = results;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }
}