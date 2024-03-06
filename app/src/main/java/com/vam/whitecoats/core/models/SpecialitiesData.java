package com.vam.whitecoats.core.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SpecialitiesData {
    @SerializedName("specialities")
    @Expose
    private List<SpecialitiesInfo> specialities = null;

    public List<SpecialitiesInfo> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<SpecialitiesInfo> specialities) {
        this.specialities = specialities;
    }
}
