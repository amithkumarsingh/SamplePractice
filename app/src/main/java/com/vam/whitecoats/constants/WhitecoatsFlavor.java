package com.vam.whitecoats.constants;

import android.util.Log;

import com.vam.whitecoats.core.models.AirshipInfo;
import com.vam.whitecoats.core.models.QuickBloxInfo;

/**
 * Created by satyasarathim on 12-10-2017.
 */

public enum WhitecoatsFlavor {

    QA("qa"),
    QAKB("qakb"),
    DEV("dev"),
    STAGE("stage"),
    UAT("uat"),
    PROD("prod"),
    DEFAULT();
    String flavor;
    //QuickBloxInfo quickBloxInfo;
    //AirshipInfo airshipInfo;
     WhitecoatsFlavor(String flavor){
        this.flavor=flavor;
        //setQBCredentials(flavor);
        //setAirshipCredentials(flavor);

    }
     WhitecoatsFlavor(){}
    public String getName(){
        return flavor;
    }
    /*public QuickBloxInfo getQBCredentials(){
        return quickBloxInfo;
    }*/
    /*private void setQBCredentials(String flavor){
        quickBloxInfo=new QuickBloxInfo();
        switch (flavor){
            case "qa":
                quickBloxInfo.setQbAccountKey(AppConstants.QA_ACCOUNT_KEY);
                quickBloxInfo.setQbAppId(AppConstants.QA_APP_ID);
                quickBloxInfo.setQbAuthKey(AppConstants.QA_AUTH_KEY);
                quickBloxInfo.setQbAuthSecret(AppConstants.QA_AUTH_SECRET);
                break;
            case "dev":
                quickBloxInfo.setQbAccountKey(AppConstants.DEV_ACCOUNT_KEY);
                quickBloxInfo.setQbAppId(AppConstants.DEV_APP_ID);
                quickBloxInfo.setQbAuthKey(AppConstants.DEV_AUTH_KEY);
                quickBloxInfo.setQbAuthSecret(AppConstants.DEV_AUTH_SECRET);
                break;
            case "stage":
                quickBloxInfo.setQbAccountKey(AppConstants.PROD_ACCOUNT_KEY);
                quickBloxInfo.setQbAppId(AppConstants.STAGE_APP_ID);
                quickBloxInfo.setQbAuthKey(AppConstants.STAGE_AUTH_KEY);
                quickBloxInfo.setQbAuthSecret(AppConstants.STAGE_AUTH_SECRET);
                break;
            case "uat":
                quickBloxInfo.setQbAccountKey(AppConstants.UAT_ACCOUNT_KEY);
                quickBloxInfo.setQbAppId(AppConstants.UAT_APP_ID);
                quickBloxInfo.setQbAuthKey(AppConstants.UAT_AUTH_KEY);
                quickBloxInfo.setQbAuthSecret(AppConstants.UAT_AUTH_SECRET);
                break;
            case "prod":
                quickBloxInfo.setQbAccountKey(AppConstants.PROD_ACCOUNT_KEY);
                quickBloxInfo.setQbAppId(AppConstants.PROD_APP_ID);
                quickBloxInfo.setQbAuthKey(AppConstants.PROD_AUTH_KEY);
                quickBloxInfo.setQbAuthSecret(AppConstants.PROD_AUTH_SECRET);
                break;
            default:
                quickBloxInfo=null;
                Log.i("WhitecoatsFlavor","No other Flavors");
        }
    }*/

    /*public AirshipInfo getAirshipInfo() {
        return airshipInfo;
    }*/

    /*public void setAirshipCredentials(String flavor){
        airshipInfo = new AirshipInfo();
        switch (flavor){
            case "qa":
                airshipInfo.setAirshipDevelopmentAppKey(AppConstants.AIRSHIP_QAAPPKEY);
                airshipInfo.setAirshipDevelopmentAppSecret(AppConstants.AIRSHIP_QAAPPSECRET);
                airshipInfo.setAirshipisProduction(false);
                break;
            case "dev":
                airshipInfo.setAirshipDevelopmentAppKey(AppConstants.AIRSHIP_DEVELOPMENTAPPKEY);
                airshipInfo.setAirshipDevelopmentAppSecret(AppConstants.AIRSHIP_DEVELOPMENTAPPSECRET);
                airshipInfo.setAirshipisProduction(false);
                break;
            case "stage":
                airshipInfo.setAirshipDevelopmentAppKey(AppConstants.AIRSHIP_STAGEAPPKEY);
                airshipInfo.setAirshipDevelopmentAppSecret(AppConstants.AIRSHIP_STAGEAPPSECRET);
                airshipInfo.setAirshipisProduction(false);
                break;
            case "uat":
                airshipInfo.setAirshipDevelopmentAppKey(AppConstants.AIRSHIP_UATAPPKEY);
                airshipInfo.setAirshipDevelopmentAppSecret(AppConstants.AIRSHIP_UATAPPSECRET);
                airshipInfo.setAirshipisProduction(false);
                break;
            case "prod":
                airshipInfo.setAirshipProductionAppKey(AppConstants.AIRSHIP_PRODUCTIONAPPKEY);
                airshipInfo.setAirshipProductionAppSecret(AppConstants.AIRSHIP_PRODUCTIONAPPSECRET);
                airshipInfo.setAirshipisProduction(true);
                break;
            default:
                airshipInfo=null;
                Log.i("WhitecoatsFlavor","No other Flavors");

        }
    }*/
    public void setDefaultFlavor(String flavorName){
        //setQBCredentials(flavorName);
        //setAirshipCredentials(flavorName);
    }

}
