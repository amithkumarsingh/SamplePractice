package com.vam.whitecoats.constants;

import com.vam.whitecoats.utils.UpshotConstants;

/**
 * Created by satyasarathim on 11-10-2017.
 */

public enum ENVIRONMENT {
    QA(RestApiConstants.QA_HOST, "8080"),
    QAKB(RestApiConstants.QA_KB_HOST, "8080"),
    // DEV(RestApiConstants.DEV_HOST,"9181"),
    DEV(RestApiConstants.DEV_HOST, "8080"),
    STAGE(RestApiConstants.STAGE_HOST, ""),
    UAT(RestApiConstants.UAT_STAGE_HOST, ""),
    PROD(RestApiConstants.PROD_HOST, ""),
    DEFAULT();

    private String host;
    private String portNumber;
    private String defaultRestDomain;
    private ENVIRONMENT defaultEnvironment;
    private String defaultGateWayRestDomain;
    private String upshotAppId;

    private ENVIRONMENT(String host, String portNumber) {
        this.host = host;
        this.portNumber = portNumber;
    }

    private ENVIRONMENT() {
    }

    public ENVIRONMENT setDefaultEnvironment(String environment) {
        switch (environment) {
            case "qa":
                this.defaultEnvironment = QA;
                this.defaultRestDomain = "http://" + QA.getHost() + ":" + QA.getPortNumber() + "/ebiz-web/app/m/";
                this.defaultGateWayRestDomain = "http://qa-events.whitecoats.net:5001";
                this.upshotAppId = UpshotConstants.qa_app_id;
                break;
            case "qakb":
                this.defaultEnvironment = QAKB;
                this.defaultRestDomain = "http://" + QAKB.getHost() + ":" + QAKB.getPortNumber() + "/ebiz-web/app/m/";
                this.defaultGateWayRestDomain = "http://qa-events.whitecoats.net:5001";
                this.upshotAppId = UpshotConstants.qa_app_id;
                break;
            case "dev":
                this.defaultEnvironment = DEV;
                this.defaultRestDomain = "http://" + DEV.getHost() + ":" + DEV.getPortNumber() + "/ebiz-web/app/m/";
                this.defaultGateWayRestDomain = "http://dev-events.whitecoats.net:5001";
                this.upshotAppId = UpshotConstants.dev_app_id;
                break;
            case "stage":
                this.defaultEnvironment = STAGE;
                this.defaultRestDomain = "https://" + STAGE.getHost() + "/ebiz-web/app/m/";
                this.defaultGateWayRestDomain = "http://stage-events.whitecoats.com:5001";
                //for UAT
                //this.defaultGateWayRestDomain="http://52.66.29.232:5001";
                this.upshotAppId = UpshotConstants.stage_app_id;
                break;
            case "uat":
                this.defaultEnvironment = UAT;
                this.defaultRestDomain = "https://" + UAT.getHost() + "/ebiz-web/app/m/";
                //this.defaultGateWayRestDomain="http://13.233.203.247:5001";
                //for UAT
                this.defaultGateWayRestDomain = "http://uat-events.whitecoats.com:5001";
                this.upshotAppId = UpshotConstants.uat_app_id;
                break;
            case "prod":
                this.defaultEnvironment = PROD;
                this.defaultRestDomain = "https://" + PROD.getHost() + "/ebiz-web/app/m/";
                this.defaultGateWayRestDomain = "https://events.whitecoats.com";
                this.upshotAppId = UpshotConstants.prod_app_id;
                break;
            default:
                this.defaultEnvironment = DEV;
                this.defaultRestDomain = "http://" + DEV.getHost() + ":" + DEV.getPortNumber() + "/ebiz-web/app/m/";
                this.defaultGateWayRestDomain = "http://dev-events.whitecoats.net:5001";
        }
        return DEFAULT;
    }

    public ENVIRONMENT getDefaultEnvironment() {
        return defaultEnvironment;
    }

    public String getHost() {
        return host;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public String getDefaultRestDomain() {
        return defaultRestDomain;
    }

    public String getDefaultGateWayRestDomain() {
        return defaultGateWayRestDomain;
    }

    public String getUpshotAppId() {
        return upshotAppId;
    }
}
