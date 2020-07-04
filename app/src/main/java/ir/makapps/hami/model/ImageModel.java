package ir.makapps.hami.model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class ImageModel {

    @SerializedName("Id")
    private int Id;
    @SerializedName("ImageTypeId")
    private short ImageTypeId;
    @SerializedName("Path")
    private String Path;
    @SerializedName("RefId")
    private Long RefId;
    private Bitmap bitmap;
    private String name;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Uri Local_Image_Uri;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getImageTypeId() {
        return ImageTypeId;
    }

    public void setImageTypeId(short imageTypeId) {
        ImageTypeId = imageTypeId;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public Long getRefId() {
        return RefId;
    }

    public void setRefId(Long refId) {
        RefId = refId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Uri getLocal_Image_Uri() {
        return Local_Image_Uri;


    }

    public void setLocal_Image_Uri(Uri local_Image_Uri) {
        Local_Image_Uri = local_Image_Uri;
    }
}
