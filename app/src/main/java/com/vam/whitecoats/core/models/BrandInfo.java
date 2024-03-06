package com.vam.whitecoats.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandInfo implements Parcelable {
    @SerializedName("type_name")
    @Expose
    private String typeName;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("strength")
    @Expose
    private String strength;
    @SerializedName("packing")
    @Expose
    private String packing;
    @SerializedName("retail_price")
    @Expose
    private Double retailPrice;
    @SerializedName("price_to_retailer")
    @Expose
    private Object priceToRetailer;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;

    protected BrandInfo(Parcel in) {
        typeName = in.readString();
        if (in.readByte() == 0) {
            brandId = null;
        } else {
            brandId = in.readInt();
        }
        strength = in.readString();
        packing = in.readString();
        if (in.readByte() == 0) {
            retailPrice = null;
        } else {
            retailPrice = in.readDouble();
        }
        manufacturer = in.readString();
    }

    public static final Creator<BrandInfo> CREATOR = new Creator<BrandInfo>() {
        @Override
        public BrandInfo createFromParcel(Parcel in) {
            return new BrandInfo(in);
        }

        @Override
        public BrandInfo[] newArray(int size) {
            return new BrandInfo[size];
        }
    };

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Object getPriceToRetailer() {
        return priceToRetailer;
    }

    public void setPriceToRetailer(Object priceToRetailer) {
        this.priceToRetailer = priceToRetailer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(typeName);
        if (brandId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(brandId);
        }
        dest.writeString(strength);
        dest.writeString(packing);
        if (retailPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(retailPrice);
        }
        dest.writeString(manufacturer);
    }
}
