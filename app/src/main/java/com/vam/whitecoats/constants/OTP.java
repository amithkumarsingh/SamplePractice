package com.vam.whitecoats.constants;

/**
 * Created by pardhasaradhid on 3/21/2017.
 */

public enum OTP {
    SUCCESS(1),FAILURE(-1),CHANGE_MOB_NO(2),RESEND(3);
    private int value;
    private OTP(int value){
        this.value=value;
    }
}
