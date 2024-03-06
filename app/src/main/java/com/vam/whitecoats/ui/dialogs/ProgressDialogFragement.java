package com.vam.whitecoats.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

@SuppressLint("NewApi")
public class ProgressDialogFragement extends DialogFragment {

    private static final String ARGS_MESSAGE_ID = "message_id";
    private android.app.ProgressDialog dialog = null;
    private String currentMessage;
    public static ProgressDialogFragement newInstance(int messageId) {
        ProgressDialogFragement progressDialogFragement = new ProgressDialogFragement();
        Bundle args = new Bundle();
        args.putInt(ARGS_MESSAGE_ID, messageId);
        progressDialogFragement.setArguments(args);
        return progressDialogFragement;
    }
    public static ProgressDialogFragement newInstance() {
        ProgressDialogFragement progressDialogFragement = new ProgressDialogFragement();
        return progressDialogFragement;
    }
    public void setMessage(String message) {
        currentMessage = message;
        dialog.setMessage(message);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       /* int messageId = getArguments().getInt(ARGS_MESSAGE_ID);
        android.app.ProgressDialogFragement dialog = new android.app.ProgressDialogFragement(getActivity());
        dialog.setMessage(getString(messageId));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);*/

        int messageId = getArguments().getInt(ARGS_MESSAGE_ID);
        dialog = new android.app.ProgressDialog(getActivity());
        if (currentMessage == null) {
            dialog.setMessage(getString(messageId));
        }else {
            dialog.setMessage(currentMessage);
        }
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        };
        dialog.setOnKeyListener(keyListener);

        return dialog;
    }
}