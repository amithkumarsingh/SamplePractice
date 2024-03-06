package com.vam.whitecoats.utils;

import com.vam.whitecoats.core.models.BrandInfo;
import com.vam.whitecoats.core.models.DrugBrand;
import com.vam.whitecoats.core.models.DrugDetail;
import com.vam.whitecoats.core.models.DrugDetailsInteractions;
import com.vam.whitecoats.core.models.DrugSubClass;

import java.util.ArrayList;
import java.util.List;

public class DrugDetailsApiResponse {
    private ArrayList<BrandInfo> drugBrandInfoList;
    private ArrayList<DrugBrand> drugBrandList;
    public List<DrugDetail> drugClasses;
    public List<DrugDetailsInteractions> drugDetailsInteractions;
    private String error;

    public DrugDetailsApiResponse(List<DrugDetail> posts, List<DrugDetailsInteractions> drugDetailsInteractions, ArrayList<DrugBrand> _drugBrandsList, ArrayList<BrandInfo> _drugBrandInfo) {
        this.drugClasses = posts;
        this.drugDetailsInteractions = drugDetailsInteractions;
        this.error = null;
        this.drugBrandList=_drugBrandsList;
        this.drugBrandInfoList=_drugBrandInfo;
    }

    public DrugDetailsApiResponse(String error) {
        this.error = error;
        this.drugClasses = null;
    }

    public List<DrugDetailsInteractions> getDrugDetailsInteractions() {
        return drugDetailsInteractions;
    }

    public void setDrugDetailsInteractions(List<DrugDetailsInteractions> drugDetailsInteractions) {
        this.drugDetailsInteractions = drugDetailsInteractions;
    }

    public List<DrugDetail> getDrugDetails() {
        return this.drugClasses;
    }

    public void setDrugDetails(List<DrugDetail> drugClassList) {
        this.drugClasses = drugClassList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<DrugBrand> getDrugBrandList() {
        return drugBrandList;
    }

    public void setDrugBrandList(ArrayList<DrugBrand> drugBrandList) {
        this.drugBrandList = drugBrandList;
    }

    public ArrayList<BrandInfo> getDrugBrandInfoList() {
        return drugBrandInfoList;
    }
}
