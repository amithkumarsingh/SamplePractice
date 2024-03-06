package com.vam.whitecoats.ui.interfaces;

import com.vam.whitecoats.constants.OTP;

/**
 * Created by pardhasaradhid on 3/22/2017.
 */

public interface OnOtpActionListener {
    /**
     *
     * @param otp
     * @param response_OTP
     */
    public void onOtpAction(OTP otp, String response_OTP);
}
