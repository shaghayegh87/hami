
package ir.makapps.hami.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MainModel {

    @SerializedName("Address")
    private String address;
    @SerializedName("Description")
    private String description;
    @SerializedName("Id")
    private int id;
    @SerializedName("Images")
    private List<String> images;
    @SerializedName("IsAdvertise")
    private Boolean isAdvertise;
    @SerializedName("IsBookMarked")
    private Boolean isBookMarked;
    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("Longitude")
    private String longitude;
    @SerializedName("Reason")
    private Object reason;
    @SerializedName("RegisterPDate")
    private String registerPDate;
    @SerializedName("StateId")
    private int stateId;
    @SerializedName("StateName")
    private String stateName;
    @SerializedName("StatusId")
    private int statusId;
    @SerializedName("StatusName")
    private String statusName;
    @SerializedName("Title")
    private String title;
    private Boolean IsCreateNote;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean getIsAdvertise() {
        return isAdvertise;
    }

    public void setIsAdvertise(Boolean isAdvertise) {
        this.isAdvertise = isAdvertise;
    }

    public Boolean getIsBookMarked() {
        return isBookMarked;
    }

    public void setIsBookMarked(Boolean isBookMarked) {
        this.isBookMarked = isBookMarked;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public String getRegisterPDate() {
        return registerPDate;
    }

    public void setRegisterPDate(String registerPDate) {
        this.registerPDate = registerPDate;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCreateNote() {
        return IsCreateNote;
    }

    public void setCreateNote(Boolean createNote) {
        IsCreateNote = createNote;
    }
}
