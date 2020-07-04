
package ir.makapps.hami.model;

import com.google.gson.annotations.SerializedName;

public class CityModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("Name")
    private String Name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
