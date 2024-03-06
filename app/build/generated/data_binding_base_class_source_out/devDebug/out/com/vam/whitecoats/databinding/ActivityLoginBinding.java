// Generated by view binder compiler. Do not edit!
package com.vam.whitecoats.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.vam.whitecoats.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final ImageView appImageView;

  @NonNull
  public final Button button;

  @NonNull
  public final CountryCodePicker ccp;

  @NonNull
  public final TextView countrycode;

  @NonNull
  public final LinearLayout countrycodeLay;

  @NonNull
  public final LinearLayout countrycodeMobilenumLay;

  @NonNull
  public final FrameLayout createPasswordLay;

  @NonNull
  public final TextView forgotPasswordTxt;

  @NonNull
  public final LinearLayout mobilenumLay;

  @NonNull
  public final TextView newUserTv;

  @NonNull
  public final EditText passwordEdit;

  @NonNull
  public final TextView passwordErrorText;

  @NonNull
  public final TextView phoneNumberErrorText;

  @NonNull
  public final TextInputLayout phoneNumberLay;

  @NonNull
  public final EditText phoneNumberLogin;

  @NonNull
  public final Button pswdTransformationBtn;

  @NonNull
  public final Button registerNowBut;

  @NonNull
  public final CheckBox staySingedIn;

  @NonNull
  public final TextView userIdText;

  @NonNull
  public final EditText usernameEdit;

  @NonNull
  public final TextView usernameErrorText;

  private ActivityLoginBinding(@NonNull ScrollView rootView, @NonNull ImageView appImageView,
      @NonNull Button button, @NonNull CountryCodePicker ccp, @NonNull TextView countrycode,
      @NonNull LinearLayout countrycodeLay, @NonNull LinearLayout countrycodeMobilenumLay,
      @NonNull FrameLayout createPasswordLay, @NonNull TextView forgotPasswordTxt,
      @NonNull LinearLayout mobilenumLay, @NonNull TextView newUserTv,
      @NonNull EditText passwordEdit, @NonNull TextView passwordErrorText,
      @NonNull TextView phoneNumberErrorText, @NonNull TextInputLayout phoneNumberLay,
      @NonNull EditText phoneNumberLogin, @NonNull Button pswdTransformationBtn,
      @NonNull Button registerNowBut, @NonNull CheckBox staySingedIn, @NonNull TextView userIdText,
      @NonNull EditText usernameEdit, @NonNull TextView usernameErrorText) {
    this.rootView = rootView;
    this.appImageView = appImageView;
    this.button = button;
    this.ccp = ccp;
    this.countrycode = countrycode;
    this.countrycodeLay = countrycodeLay;
    this.countrycodeMobilenumLay = countrycodeMobilenumLay;
    this.createPasswordLay = createPasswordLay;
    this.forgotPasswordTxt = forgotPasswordTxt;
    this.mobilenumLay = mobilenumLay;
    this.newUserTv = newUserTv;
    this.passwordEdit = passwordEdit;
    this.passwordErrorText = passwordErrorText;
    this.phoneNumberErrorText = phoneNumberErrorText;
    this.phoneNumberLay = phoneNumberLay;
    this.phoneNumberLogin = phoneNumberLogin;
    this.pswdTransformationBtn = pswdTransformationBtn;
    this.registerNowBut = registerNowBut;
    this.staySingedIn = staySingedIn;
    this.userIdText = userIdText;
    this.usernameEdit = usernameEdit;
    this.usernameErrorText = usernameErrorText;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_imageView;
      ImageView appImageView = ViewBindings.findChildViewById(rootView, id);
      if (appImageView == null) {
        break missingId;
      }

      id = R.id.button;
      Button button = ViewBindings.findChildViewById(rootView, id);
      if (button == null) {
        break missingId;
      }

      id = R.id.ccp;
      CountryCodePicker ccp = ViewBindings.findChildViewById(rootView, id);
      if (ccp == null) {
        break missingId;
      }

      id = R.id.countrycode;
      TextView countrycode = ViewBindings.findChildViewById(rootView, id);
      if (countrycode == null) {
        break missingId;
      }

      id = R.id.countrycode_lay;
      LinearLayout countrycodeLay = ViewBindings.findChildViewById(rootView, id);
      if (countrycodeLay == null) {
        break missingId;
      }

      id = R.id.countrycode_mobilenum_lay;
      LinearLayout countrycodeMobilenumLay = ViewBindings.findChildViewById(rootView, id);
      if (countrycodeMobilenumLay == null) {
        break missingId;
      }

      id = R.id.create_password_lay;
      FrameLayout createPasswordLay = ViewBindings.findChildViewById(rootView, id);
      if (createPasswordLay == null) {
        break missingId;
      }

      id = R.id.forgot_password_txt;
      TextView forgotPasswordTxt = ViewBindings.findChildViewById(rootView, id);
      if (forgotPasswordTxt == null) {
        break missingId;
      }

      id = R.id.mobilenum_lay;
      LinearLayout mobilenumLay = ViewBindings.findChildViewById(rootView, id);
      if (mobilenumLay == null) {
        break missingId;
      }

      id = R.id.newUser_tv;
      TextView newUserTv = ViewBindings.findChildViewById(rootView, id);
      if (newUserTv == null) {
        break missingId;
      }

      id = R.id.password_edit;
      EditText passwordEdit = ViewBindings.findChildViewById(rootView, id);
      if (passwordEdit == null) {
        break missingId;
      }

      id = R.id.password_error_text;
      TextView passwordErrorText = ViewBindings.findChildViewById(rootView, id);
      if (passwordErrorText == null) {
        break missingId;
      }

      id = R.id.phoneNumber_error_text;
      TextView phoneNumberErrorText = ViewBindings.findChildViewById(rootView, id);
      if (phoneNumberErrorText == null) {
        break missingId;
      }

      id = R.id.phone_number_lay;
      TextInputLayout phoneNumberLay = ViewBindings.findChildViewById(rootView, id);
      if (phoneNumberLay == null) {
        break missingId;
      }

      id = R.id.phone_number_login;
      EditText phoneNumberLogin = ViewBindings.findChildViewById(rootView, id);
      if (phoneNumberLogin == null) {
        break missingId;
      }

      id = R.id.pswd_transformation_btn;
      Button pswdTransformationBtn = ViewBindings.findChildViewById(rootView, id);
      if (pswdTransformationBtn == null) {
        break missingId;
      }

      id = R.id.register_now_but;
      Button registerNowBut = ViewBindings.findChildViewById(rootView, id);
      if (registerNowBut == null) {
        break missingId;
      }

      id = R.id.stay_singedIn;
      CheckBox staySingedIn = ViewBindings.findChildViewById(rootView, id);
      if (staySingedIn == null) {
        break missingId;
      }

      id = R.id.user_id_text;
      TextView userIdText = ViewBindings.findChildViewById(rootView, id);
      if (userIdText == null) {
        break missingId;
      }

      id = R.id.username_edit;
      EditText usernameEdit = ViewBindings.findChildViewById(rootView, id);
      if (usernameEdit == null) {
        break missingId;
      }

      id = R.id.username_error_text;
      TextView usernameErrorText = ViewBindings.findChildViewById(rootView, id);
      if (usernameErrorText == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ScrollView) rootView, appImageView, button, ccp, countrycode,
          countrycodeLay, countrycodeMobilenumLay, createPasswordLay, forgotPasswordTxt,
          mobilenumLay, newUserTv, passwordEdit, passwordErrorText, phoneNumberErrorText,
          phoneNumberLay, phoneNumberLogin, pswdTransformationBtn, registerNowBut, staySingedIn,
          userIdText, usernameEdit, usernameErrorText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
