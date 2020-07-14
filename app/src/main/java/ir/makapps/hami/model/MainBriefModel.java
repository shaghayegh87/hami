
package ir.makapps.hami.model;
import com.google.gson.annotations.SerializedName;
public class MainBriefModel {

    @SerializedName("Description")
    private String description;
    @SerializedName("Id")
    private int id;
    @SerializedName("Image")
    private String image;
    @SerializedName("IsAdvertise")
    private Boolean isAdvertise;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIsAdvertise() {
        return isAdvertise;
    }

    public void setIsAdvertise(Boolean isAdvertise) {
        this.isAdvertise = isAdvertise;
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

}
