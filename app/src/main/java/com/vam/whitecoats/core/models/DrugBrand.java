package com.vam.whitecoats.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugBrand implements Parcelable {
    @SerializedName("drug_id")
    @Expose
    private Integer drugId;
    @SerializedName("drug_name")
    @Expose
    private String drugName;
    @SerializedName("drug_type")
    @Expose
    private String drugType;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("deleted")
    @Expose
    private Integer deleted;
    @SerializedName("info")
    @Expose
    private List<BrandInfo> info = null;

    protected DrugBrand(Parcel in) {
        if (in.readByte() == 0) {
            drugId = null;
        } else {
            drugId = in.readInt();
        }
        drugName = in.readString();
        drugType = in.readString();
        manufacturer = in.readString();
        if (in.readByte() == 0) {
            deleted = null;
        } else {
            deleted = in.readInt();
        }
        info = in.createTypedArrayList(BrandInfo.CREATOR);
    }

    public static final Creator<DrugBrand> CREATOR = new Creator<DrugBrand>() {
        @Override
        public DrugBrand createFromParcel(Parcel in) {
            return new DrugBrand(in);
        }

        @Override
        public DrugBrand[] newArray(int size) {
            return new DrugBrand[size];
        }
    };

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public List<BrandInfo> getInfo() {
        return info;
    }

    public void setInfo(List<BrandInfo> info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (drugId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(drugId);
        }
        dest.writeString(drugName);
        dest.writeString(drugType);
        dest.writeString(manufacturer);
        if (deleted == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(deleted);
        }
        dest.writeTypedList(info);
    }

}
