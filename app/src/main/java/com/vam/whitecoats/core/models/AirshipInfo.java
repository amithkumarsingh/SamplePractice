package com.vam.whitecoats.core.models;

public class AirshipInfo {

    String AirshipDevelopmentAppKey;
    String AirshipDevelopmentAppSecret;
    String AirshipProductionAppKey;
    String AirshipProductionAppSecret;
    boolean AirshipisProduction;


    public void setAirshipDevelopmentAppKey(String airshipDevelopmentAppKey) {
        AirshipDevelopmentAppKey = airshipDevelopmentAppKey;
    }

    public void setAirshipDevelopmentAppSecret(String airshipDevelopmentAppSecret) {
        AirshipDevelopmentAppSecret = airshipDevelopmentAppSecret;
    }


    public String getAirshipDevelopmentAppKey() {
        return AirshipDevelopmentAppKey;
    }

    public String getAirshipDevelopmentAppSecret() {
        return AirshipDevelopmentAppSecret;
    }


    public void setAirshipProductionAppKey(String airshipProductionAppKey) {
        AirshipProductionAppKey = airshipProductionAppKey;
    }

    public void setAirshipProductionAppSecret(String airshipProductionAppSecret) {
        AirshipProductionAppSecret = airshipProductionAppSecret;
    }


    public String getAirshipProductionAppKey() {
        return AirshipProductionAppKey;
    }

    public String getAirshipProductionAppSecret() {
        return AirshipProductionAppSecret;
    }

    public boolean getAirshipisProduction() {
        return AirshipisProduction;
    }

    public void setAirshipisProduction(Boolean airshipisProduction) {
        AirshipisProduction = airshipisProduction;
    }




}
