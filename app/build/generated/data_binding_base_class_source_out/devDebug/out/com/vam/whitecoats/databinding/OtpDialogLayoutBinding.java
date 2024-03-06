// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class OtpDialogLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final EditText editText1;

  @NonNull
  public final EditText editText2;

  @NonNull
  public final EditText editText3;

  @NonNull
  public final EditText editText4;

  @NonNull
  public final EditText etOtp;

  @NonNull
  public final LinearLayout mainLay;

  @NonNull
  public final EditText mobileNum;

  @NonNull
  public final Button numberEdit;

  @NonNull
  public final TextView otpError;

  @NonNull
  public final Button resendOtp;

  private OtpDialogLayoutBinding(@NonNull LinearLayout rootView, @NonNull EditText editText1,
      @NonNull EditText editText2, @NonNull EditText editText3, @NonNull EditText editText4,
      @NonNull EditText etOtp, @NonNull LinearLayout mainLay, @NonNull EditText mobileNum,
      @NonNull Button numberEdit, @NonNull TextView otpError, @NonNull Button resendOtp) {
    this.rootView = rootView;
    this.editText1 = editText1;
    this.editText2 = editText2;
    this.editText3 = editText3;
    this.editText4 = editText4;
    this.etOtp = etOtp;
    this.mainLay = mainLay;
    this.mobileNum = mobileNum;
    this.numberEdit = numberEdit;
    this.otpError = otpError;
    this.resendOtp = resendOtp;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static OtpDialogLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static OtpDialogLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.otp_dialog_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static OtpDialogLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.editText1;
      EditText editText1 = ViewBindings.findChildViewById(rootView, id);
      if (editText1 == null) {
        break missingId;
      }

      id = R.id.editText2;
      EditText editText2 = ViewBindings.findChildViewById(rootView, id);
      if (editText2 == null) {
        break missingId;
      }

      id = R.id.editText3;
      EditText editText3 = ViewBindings.findChildViewById(rootView, id);
      if (editText3 == null) {
        break missingId;
      }

      id = R.id.editText4;
      EditText editText4 = ViewBindings.findChildViewById(rootView, id);
      if (editText4 == null) {
        break missingId;
      }

      id = R.id.et_otp;
      EditText etOtp = ViewBindings.findChildViewById(rootView, id);
      if (etOtp == null) {
        break missingId;
      }

      LinearLayout mainLay = (LinearLayout) rootView;

      id = R.id.mobile_num;
      EditText mobileNum = ViewBindings.findChildViewById(rootView, id);
      if (mobileNum == null) {
        break missingId;
      }

      id = R.id.number_edit;
      Button numberEdit = ViewBindings.findChildViewById(rootView, id);
      if (numberEdit == null) {
        break missingId;
      }

      id = R.id.otp_error;
      TextView otpError = ViewBindings.findChildViewById(rootView, id);
      if (otpError == null) {
        break missingId;
      }

      id = R.id.resend_otp;
      Button resendOtp = ViewBindings.findChildViewById(rootView, id);
      if (resendOtp == null) {
        break missingId;
      }

      return new OtpDialogLayoutBinding((LinearLayout) rootView, editText1, editText2, editText3,
          editText4, etOtp, mainLay, mobileNum, numberEdit, otpError, resendOtp);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
