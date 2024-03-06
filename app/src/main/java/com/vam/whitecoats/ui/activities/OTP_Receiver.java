package com.vam.whitecoats.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

public class OTP_Receiver extends BroadcastReceiver {
    private static EditText editText1;
    private static EditText editText2;
    private static EditText editText3;
    private static EditText editText4;


    public void setEditText(EditText editText1, EditText editText2, EditText editText3, EditText editText4) {
        OTP_Receiver.editText1 = editText1;
        OTP_Receiver.editText2 = editText2;
        OTP_Receiver.editText3 = editText3;
        OTP_Receiver.editText4 = editText4;


    }

    // OnReceive will keep trace when sms is been received in mobile
    @Override
    public void onReceive(Context context, Intent intent) {
        //message will be holding complete sms that is received
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        /*Added the null check condition to avoid the crash*/
        if (messages != null) {
            for (SmsMessage sms : messages) {
                String msg = sms.getMessageBody();
                /*Added the null check condition to avoid the crash*/
                if (msg != null) {
                    if (msg.contains("WhiteCoats")) {
                        String otp1 = msg.substring(0);
                        String otp2 = msg.substring(1);
                        String otp3 = msg.substring(2);
                        String otp4 = msg.substring(3);
                        editText1.setText(otp1);
                        editText2.setText(otp2);
                        editText3.setText(otp3);
                        editText4.setText(otp4);
                    }
                }

            }
        }

    }
}

