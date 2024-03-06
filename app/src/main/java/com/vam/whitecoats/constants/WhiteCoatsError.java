package com.vam.whitecoats.constants;

import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.ErrorUtils;

public enum WhiteCoatsError {
    SERVER_UPGRADE(605,ErrorUtils.ERR_SERVER_UPGRADE_MSG, ErrorUtils.ERR_SERVER_UPGRADE_DESC, R.drawable.ic_serverupgrade),
    INCORRECT_APP_VERSION(109,ErrorUtils.ERR_INCORRECT_VERSION_MSG_, ErrorUtils.ERR_INCORRECT_VERSION_DESC, R.drawable.ic_upgrade),
//    PAGE_NOT_FOUND(404,ErrorUtils.ERR_SERVER_MSG_, ErrorUtils.ERR_SERVER_DESC, R.drawable.ic_servererror),
    SERVER_ERROR(404,ErrorUtils.ERR_SERVER_MSG_, ErrorUtils.ERR_SERVER_DESC, R.drawable.ic_servererror),
    CONNECTION_TIMEOUT(408,ErrorUtils.ERR_CONNECTION_MSG_, ErrorUtils.ERR_CONNECTION_DESC, R.drawable.ic_connectionerror),
    BAD_GATEWAY(502,ErrorUtils.ERR_SERVER_MSG_, ErrorUtils.ERR_SERVER_DESC, R.drawable.ic_servererror),
    SERVICE_UNAVAILABLE(503,ErrorUtils.ERR_SERVER_MSG_, ErrorUtils.ERR_SERVER_DESC, R.drawable.ic_servererror);

    private int errorCode;
    private int errorDrawable;
    private String errorMsg;
    private String errorDesc;

    private WhiteCoatsError(int errorCode, String errorMsg, String errorDesc, int errorDrawable){
        this.errorCode=errorCode;
        this.errorDrawable=errorDrawable;
        this.errorMsg=errorMsg;
        this.errorDesc=errorDesc;
    }
    public int getErrorCode(){
        return errorCode;
    }
    public int getErrorResourceID(){
        return errorDrawable;
    }
    public String getErrorMsg(){
        return errorMsg;
    }
    public String getErrorDesc(){
        return errorDesc;
    }

}
