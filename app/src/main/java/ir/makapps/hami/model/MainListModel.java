
package ir.makapps.hami.model;

import com.google.gson.annotations.SerializedName;


public class MainListModel {

    @SerializedName("Support")
    private SupportModel supportModel;

    @SerializedName("Advertise")
    private AdvertiseModel advertiseModel;

    private boolean IsAdvertise;

    public SupportModel getSupportModel() {
        return supportModel;
    }

    public void setSupportModel(SupportModel supportModel) {
        this.supportModel = supportModel;
    }

    public AdvertiseModel getAdvertiseModel() {
        return advertiseModel;
    }

    public void setAdvertiseModel(AdvertiseModel advertiseModel) {
        this.advertiseModel = advertiseModel;
    }

    public boolean isAdvertise() {
        return IsAdvertise;
    }

    public void setAdvertise(boolean advertise) {
        IsAdvertise = advertise;
    }

    public MainListModel(SupportModel supportModel, AdvertiseModel advertiseModel, boolean isAdvertise) {

        this.supportModel = supportModel;
        this.advertiseModel = advertiseModel;
        IsAdvertise = isAdvertise;
    }
}
