
package ir.makapps.hami.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BodyModel {

    @SerializedName("role")
    private Long mRole;
    @SerializedName("token")
    private String mToken;

    public Long getRole() {
        return mRole;
    }

    public void setRole(Long role) {
        mRole = role;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

}
