
package ir.makapps.hami.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdvertiseModel {
    @SerializedName("Id")
    private Long id;
    @SerializedName("Images")
    private List<ImageModel> images;

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

}
