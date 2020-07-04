
package ir.makapps.hami.model;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ValidationModel {

    @SerializedName("Body")
    private int body;
    @SerializedName("Message")
    private String message;
    @SerializedName("ResultCode")
    private Long resultCode;

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getResultCode() {
        return resultCode;
    }

    public void setResultCode(Long resultCode) {
        this.resultCode = resultCode;
    }

}
