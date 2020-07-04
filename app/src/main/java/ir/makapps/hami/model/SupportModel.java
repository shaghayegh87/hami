
package ir.makapps.hami.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class SupportModel {

    @SerializedName("Address")
    private String address;
    @SerializedName("Deleted")
    private Boolean deleted;
    @SerializedName("Description")
    private String description;
    @SerializedName("Finished")
    private Boolean finished;
    @SerializedName("Id")
    private Long id;
    @SerializedName("Images")
    private List<ImageModel> images;
    @SerializedName("Latitude")
    private String latitude;
    @SerializedName("Longitude")
    private String longitude;
    @SerializedName("RegisterPDate")
    private Long registerPDate;
    @SerializedName("StateId")
    private Long stateId;
    @SerializedName("StateName")
    private String stateName;
    @SerializedName("StatusId")
    private Long statusId;
    @SerializedName("StatusName")
    private String statusName;
    @SerializedName("Title")
    private String title;
    @SerializedName("UserId")
    private Long userId;
    @SerializedName("UserName")
    private String userName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ImageModel> getImages() {
        return images;
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
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

    public Long getRegisterPDate() {
        return registerPDate;
    }

    public void setRegisterPDate(Long registerPDate) {
        this.registerPDate = registerPDate;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
