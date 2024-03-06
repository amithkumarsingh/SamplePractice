package com.vam.whitecoats.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vam.whitecoats.R;

import java.util.ArrayList;
import java.util.Calendar;


public class ValidationUtils extends SimpleTextWatcher {

    private final int UNAUTHORIZED_ERROR_CODE = 401;
    private final int FULLNAME_IS_TOO_SHORT_ERROR_CODE = 422;
    private final int EMAIL_SHOULD_LOOK_LIKE_AN_EMAIL_ADDRESS_ERROR_CODE = 422;
    private final int PASSWORD_IS_TOO_SHORT_ERROR_CODE = 422;
    private final int PASSWORD_SHOULD_CONTAIN_ALPHANUMERIC_AND_PUNCTUATION_CHARACTERS_ERROR_CODE = 422;

    private Context context;
    private EditText[] fieldsArray;
    private TextView[] textArray;

    private String[] fieldsErrorArray;

    ArrayList<String> splty_array;

    public ValidationUtils(Context context, EditText[] fieldsArray, TextView[] textArray) {
        this.context = context;
        this.fieldsArray = fieldsArray;
        this.textArray = textArray;
        initListeners();
    }

    public ValidationUtils(Context context, EditText[] fieldsArray, ArrayList<String> splty_array, TextView[] textArray) {
        this.context = context;
        this.fieldsArray = fieldsArray;
        this.splty_array = splty_array;
        this.textArray = textArray;
        initListeners();
    }

    public ValidationUtils(Context context, EditText[] fieldsArray, String[] fieldsErrorArray) {
        this.context = context;
        this.fieldsArray = fieldsArray;
        this.fieldsErrorArray = fieldsErrorArray;
        initListeners();
    }

    public ValidationUtils(Context context, EditText[] fieldsArray) {
        this.context = context;
        this.fieldsArray = fieldsArray;
        initListeners();
    }

    public ValidationUtils(Context context, TextView[] textArray) {
        this.context = context;
        this.textArray = textArray;
    }

    public ValidationUtils(Context context) {
        this.context = context;
    }

    private void initListeners() {
        for (int i = 0; i < fieldsArray.length; i++) {
            if (fieldsArray[i] != null) {
                fieldsArray[i].addTextChangedListener(this);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        //setError(null);
        clearError();
    }


    public boolean isValidUserData(String emailText, String passwordText) {
        boolean isEmailEntered = !TextUtils.isEmpty(emailText);
        boolean isPasswordEntered = !TextUtils.isEmpty(passwordText);

        if (isEmailEntered && isPasswordEntered) {
            if (!AppUtil.validateEmail(emailText)) {
                setError(0, context.getString(R.string.valid_email));
                return false;
            }
            return true;
        } else {
            if (isEmailEntered) {
                if (!AppUtil.validateEmail(emailText)) {
                    isEmailEntered = false;
                    setError(0, context.getString(R.string.valid_email));
                }
            }
            setErrors(new boolean[]{isEmailEntered, isPasswordEntered});
        }
        return false;
    }

    public boolean isValidFourField(String fName, String lName, String speciality, String contactno) {
        boolean isFNameEntered = !TextUtils.isEmpty(fName);
        boolean isLNameEntered = !TextUtils.isEmpty(lName);
        boolean isSpecialtyEntered = !TextUtils.isEmpty(speciality);
        boolean isContactNo = !TextUtils.isEmpty(contactno);

        if (isFNameEntered && isLNameEntered && isSpecialtyEntered) {
            if (!isContactNo) {
                setError(3, "Enter mobile number");
                return false;
            } else if (isContactNo && contactno.length() < 10) {
                setErrors(new boolean[]{isFNameEntered, isLNameEntered, isSpecialtyEntered, false});
                return false;
            }
            return true;
        } else {
            if (!isContactNo) {
                setErrors(new boolean[]{isFNameEntered, isLNameEntered, isSpecialtyEntered, false});
            } else if (isContactNo && contactno.length() < 10) {
                setErrors(new boolean[]{isFNameEntered, isLNameEntered, isSpecialtyEntered, false});

            } else {
                setErrors(new boolean[]{isFNameEntered, isLNameEntered, isSpecialtyEntered, true});
            }
        }

        return false;
    }

    public boolean isValidThreeField(String fName, String lName, String speciality) {
        boolean isFNameEntered = !TextUtils.isEmpty(fName);
        boolean isLNameEntered = !TextUtils.isEmpty(lName);
        boolean isSpecialtyEntered = !TextUtils.isEmpty(speciality);

        if (isFNameEntered && isLNameEntered && isSpecialtyEntered) {
            return true;
        } else {
            setErrors(new boolean[]{isFNameEntered, isLNameEntered, isSpecialtyEntered});
        }

        return false;
    }

    public boolean isValidAnyField(String name, String workPlace, String location, String speciality) {

        boolean isNameEntered = !TextUtils.isEmpty(name);
        boolean isWorkPlaceEntered = !TextUtils.isEmpty(workPlace);
        boolean isLocationEntered = !TextUtils.isEmpty(location);
        boolean isSpecialityEntered = !TextUtils.isEmpty(speciality);

        if (isNameEntered || isWorkPlaceEntered || isLocationEntered || isSpecialityEntered) {
            return true;
        } else {
            setErrors(new boolean[]{isNameEntered, isWorkPlaceEntered, isSpecialityEntered, isLocationEntered});
        }
        return false;
    }

    public boolean isValidSetPassword(String mPassword) {
        boolean isPswdEntered = !TextUtils.isEmpty(mPassword);
        if (isPswdEntered) {
            if (mPassword.length() < 8) {
                setError(0, context.getString(R.string.required_minimum));
                return false;
            }
            return true;
        } else {
            setErrors(new boolean[]{isPswdEntered});
        }

        return false;
    }

    public boolean isValidPasswordChange(String oldpassword, String userid, String newpasswordText, String loginPswd) {
        boolean isOldPasswordEntered = !TextUtils.isEmpty(oldpassword);
        boolean isNewPasswordEntered = !TextUtils.isEmpty(newpasswordText);

        if (isNewPasswordEntered && isOldPasswordEntered) {
            if (8 <= newpasswordText.length() && 8 <= oldpassword.length()) {
                if (userid.equals(newpasswordText)) {
                    setError(1, context.getString(R.string.not_be_same_as_userid));//"Please enter valid email id");
                    return false;
                } else if (oldpassword.equals(newpasswordText)) {
                    setError(1, context.getString(R.string.notsame));
                    return false;
                } else if (!oldpassword.equalsIgnoreCase(loginPswd)) {
                    setError(0, "Old password not matched with login password");
                    return false;
                }
                return true;
            } else {
                setError(0, context.getString(R.string.required_minimum));
                setError(1, context.getString(R.string.required_minimum));
                if (8 <= newpasswordText.length()) {
                    if (userid.equals(newpasswordText)) {
                        isNewPasswordEntered = false;
                        setError(1, context.getString(R.string.not_be_same_as_userid));//"Please enter valid email id");

                    } else if (oldpassword.equals(newpasswordText)) {
                        isNewPasswordEntered = false;
                        setError(1, context.getString(R.string.notsame));
                    }
                } else {
                    isNewPasswordEntered = false;
                }

                if (8 <= oldpassword.length()) {
                    if (oldpassword.equals(newpasswordText)) {
                        isOldPasswordEntered = false;
                        setError(0, context.getString(R.string.notsame));
                    } else if (!oldpassword.equals(loginPswd)) {
                        isOldPasswordEntered = false;
                        setError(0, "Old password not matched with login password");
                    }
                } else {
                    isOldPasswordEntered = false;
                }

                setErrors(new boolean[]{isOldPasswordEntered, isNewPasswordEntered});
            }
        } else {
            if (isNewPasswordEntered) {
                if (8 <= newpasswordText.length()) {
                    if (userid.equals(newpasswordText)) {
                        setError(1, context.getString(R.string.not_be_same_as_userid));//"Please enter valid email id");
                        isNewPasswordEntered = false;
                    } else if (oldpassword.equals(newpasswordText)) {
                        setError(1, context.getString(R.string.notsame));
                        isNewPasswordEntered = false;
                    }
                } else {
                    setError(1, context.getString(R.string.required_minimum));
                    isNewPasswordEntered = false;
                }
            }

            if (isOldPasswordEntered) {
                if (8 <= oldpassword.length()) {
                    if (!oldpassword.equals(loginPswd)) {
                        setError(0, "Old password not matched with login password");//"Please enter valid email id");
                        isOldPasswordEntered = false;
                    } else if (oldpassword.equals(newpasswordText)) {
                        setError(0, context.getString(R.string.notsame));
                        isOldPasswordEntered = false;
                    }
                } else {
                    setError(0, context.getString(R.string.required_minimum));
                    isOldPasswordEntered = false;
                }
            }
            setErrors(new boolean[]{isOldPasswordEntered, isNewPasswordEntered});
        }

        return false;
    }

    public boolean isValidUserReg(String selectedImagePath, String fName, String lName, String email, boolean check, boolean validemail) {
        boolean isFNameEntered = !TextUtils.isEmpty(fName);
        boolean isLNameEntered = !TextUtils.isEmpty(lName);
        boolean isEmailEntered = !TextUtils.isEmpty(email);
        boolean isSelectedImagePath = !TextUtils.isEmpty(selectedImagePath);

        if (isSelectedImagePath && isFNameEntered && isLNameEntered && isEmailEntered && check && validemail) {
            return true;
        } else {
            if (isEmailEntered) {
                if (!validemail) {
                    isEmailEntered = false;
                    setError(3, context.getString(R.string.valid_email));
                }
            }
            setErrors(new boolean[]{isSelectedImagePath, isFNameEntered, isLNameEntered, isEmailEntered, check});
        }

        return false;
    }

    public boolean isValidForgotPasswordData(String passwordText) {
        boolean isPasswordEntered = !TextUtils.isEmpty(passwordText);

        if (isPasswordEntered) {
            return true;
        } else {
            setErrors(new boolean[]{isPasswordEntered});
        }

        return false;
    }

    public boolean isAcademicDetEnteder(String degree, String year, boolean currently_check) {
        boolean isEntered = !TextUtils.isEmpty(year);
        boolean isDegreeEnterd = !TextUtils.isEmpty(degree);
        int cyear = Calendar.getInstance().get(Calendar.YEAR);

        if (isDegreeEnterd && isEntered) {
            if (year.length() != 4 && !currently_check) {
                setError(1, context.getString(R.string.dlg_year_yyyy_format_error));
                return false;
            } else {
                if (!year.equals("")) {
                    if (Integer.parseInt(year) > cyear) {
                        setError(1, context.getString(R.string.dlg_current_year_error));
                        return false;
                    } else if (Integer.parseInt(year) <= 1900) {
                        setError(1, "Please provide valid year above 1900 to continue");
                        return false;
                    }
                }
            }
            return true;
        } else if (isDegreeEnterd && currently_check) {
            return true;
        } else if (currently_check || isEntered) {
            if (!isDegreeEnterd) {
                setErrors(new boolean[]{isDegreeEnterd, true});
            }
            if (year.length() != 4 && !currently_check) {
                setError(1, context.getString(R.string.dlg_year_yyyy_format_error));
                return false;
            } else {
                if (!year.equals("")) {
                    if (Integer.parseInt(year) > cyear) {
                        setError(1, context.getString(R.string.dlg_current_year_error));
                        return false;
                    } else if (Integer.parseInt(year) <= 1900) {
                        setError(1, "Please provide valid year above 1900 to continue");
                        return false;
                    }
                }
            }
            return false;
        } else {
            setErrors(new boolean[]{isDegreeEnterd, isEntered});
        }
        return false;
    }

    public boolean isTwoEnteder(String workplace, String location) {
        boolean isworkplaceEntered = !TextUtils.isEmpty(workplace);
        boolean islocationEnterd = !TextUtils.isEmpty(location);
        if (isworkplaceEntered && islocationEnterd) {
            return true;
        } else {
            setErrors(new boolean[]{isworkplaceEntered, islocationEnterd});
        }
        return false;
    }

    public boolean isMandatoryEntered(String experience, String location, String speciality, boolean isMandatory) {
        boolean isExperienceEntered = !TextUtils.isEmpty(experience);
        boolean isLocationEntered = !TextUtils.isEmpty(location);
        boolean isSpecialityEntered = !TextUtils.isEmpty(speciality);
        if (isMandatory) {
            if (isExperienceEntered && isLocationEntered && isSpecialityEntered) {
                return true;
            } else {
                setErrors(new boolean[]{isExperienceEntered, isLocationEntered, isSpecialityEntered});
            }
        } else {
            if (isExperienceEntered && isLocationEntered) {
                return true;
            } else {
                setErrors(new boolean[]{isExperienceEntered, isLocationEntered, isSpecialityEntered});
            }
        }
        return false;
    }

    public boolean isAreaEnteder(String areaText) {
        boolean isAreaTextEntered = !TextUtils.isEmpty(areaText);
        if (isAreaTextEntered) {
            return true;
        } else {
            setErrors(new boolean[]{isAreaTextEntered});
        }
        return false;
    }

    public boolean isThreeEntered(String title, String startdate, String enddate) {
        boolean isworkplaceEntered = !TextUtils.isEmpty(title);
        boolean isstartdateEntered = !TextUtils.isEmpty(startdate);
        boolean isenddateEntered = !TextUtils.isEmpty(enddate);
        if (isworkplaceEntered && isstartdateEntered && isenddateEntered) {
            return true;
        } else {
            setErrors(new boolean[]{isworkplaceEntered, isstartdateEntered, isenddateEntered});
        }
        return false;
    }

    public boolean isphoneEmailEntered(String phoneNumber, String emailText) {
        boolean isEmailEntered = !TextUtils.isEmpty(emailText);
        boolean isPhoneEntered = !TextUtils.isEmpty(phoneNumber);
        boolean isVaildEmail = AppUtil.validateEmail(emailText);
        if (isEmailEntered && isPhoneEntered) {
            if (phoneNumber.length() < 10) {
                setError(0, context.getString(R.string.vaild_phone_number));
                return false;
            }
            if (!isVaildEmail) {
                setError(1, context.getString(R.string.valid_email));
                return false;
            }
            return true;
        } else if (isEmailEntered || isPhoneEntered) {
            if (phoneNumber.length() < 10) {
                setError(0, context.getString(R.string.vaild_phone_number));
                return false;
            }
            if (!AppUtil.validateEmail(emailText)) {
                setError(1, context.getString(R.string.valid_email));
                return false;
            }
            return true;
        } else {
            setErrors(new boolean[]{isEmailEntered, isPhoneEntered});
        }
        return false;
    }

//    public boolean isregistrationScreenone(String firstName, String lastName, String emailId, String phoneNumber, String createPassword, boolean isChecked) {
//        boolean isfirstNameEnterd = !TextUtils.isEmpty(firstName);
//        boolean islastNameEnterd = !TextUtils.isEmpty(lastName);
//        boolean isemailIdEnterd = !TextUtils.isEmpty(emailId);
//        boolean isphoneNumberEnterd = !TextUtils.isEmpty(phoneNumber);
//        boolean iscreatePasswordEnterd = !TextUtils.isEmpty(createPassword);
//        boolean isValidEmail = AppUtil.validateEmail(emailId);
//        boolean isValidPhNum = false;
//        if (phoneNumber.length() >= 10) {
//            isValidPhNum = true;
//        }
//        if (isfirstNameEnterd && islastNameEnterd && isValidEmail && isValidPhNum && iscreatePasswordEnterd && isChecked) {
//            if (createPassword.length() < 8) {
//                setError(4, context.getString(R.string.required_minimum));
//                return false;
//            }
//            return true;
//        } else {
//            setErrors(new boolean[]{isfirstNameEnterd, islastNameEnterd, isValidEmail, isValidPhNum, iscreatePasswordEnterd, isChecked});
//            if (createPassword.length() > 0 && createPassword.length() < 8) {
//                setError(4, context.getString(R.string.required_minimum));
//            }
//        }
//        return false;
//    }

    public boolean isregistrationScreenone(String firstName, String lastName, String emailId, String phoneNumber, String specialityName, boolean isChecked) {
        boolean isfirstNameEnterd = !TextUtils.isEmpty(firstName);
        boolean islastNameEnterd = !TextUtils.isEmpty(lastName);
        boolean isemailIdEnterd = !TextUtils.isEmpty(emailId);
        boolean isphoneNumberEnterd = !TextUtils.isEmpty(phoneNumber);
        boolean isSpecialityName = !TextUtils.isEmpty(specialityName);
        boolean isValidEmail = AppUtil.validateEmail(emailId);
        boolean isValidPhNum = false;
        if (phoneNumber.length() >= 10) {
            isValidPhNum = true;
        }
        if (isfirstNameEnterd && islastNameEnterd && isValidEmail && isValidPhNum && isSpecialityName && isChecked) {
//            if (createPassword.length() < 8) {
//                setError(4, context.getString(R.string.required_minimum));
//                return false;
//            }
            return true;
        } else {
            setErrors(new boolean[]{isfirstNameEnterd, islastNameEnterd, isValidEmail, isValidPhNum, isSpecialityName, isChecked});
//            if (createPassword.length() > 0 && createPassword.length() < 8) {
//                setError(4, context.getString(R.string.required_minimum));
//            }
        }
        return false;
    }

    public boolean isLoginScreen(String phoneNumber) {

        boolean isphoneNumberEnterd = !TextUtils.isEmpty(phoneNumber);
        boolean isValidPhNum = false;
        if (phoneNumber.length() >= 10) {
            isValidPhNum = true;
        }
        if (isValidPhNum) {
//            if (createPassword.length() < 8) {
//                setError(4, context.getString(R.string.required_minimum));
//                return false;
//            }
            return true;
        } else {
            setErrors(new boolean[]{isValidPhNum});
//            if (createPassword.length() > 0 && createPassword.length() < 8) {
//                setError(4, context.getString(R.string.required_minimum));
//            }
        }
        return false;
    }


    public boolean isEmailForgotPassword(String email_id) {
        boolean isEmailEntered = !TextUtils.isEmpty(email_id);
        boolean isVaildEmail = AppUtil.validateEmail(email_id);
        if (isEmailEntered) {
            if (!isVaildEmail) {
                setError(0, context.getString(R.string.valid_email));
                return false;
            }
            return true;
        } else {
            setErrors(new boolean[]{isEmailEntered});
        }
        return false;
    }

    public boolean isPhoneForgotPassword(String phoneNumber) {
        boolean isPhoneEntered = !TextUtils.isEmpty(phoneNumber);
        if (isPhoneEntered) {
            if (phoneNumber.length() < 10) {
                setError(0, context.getString(R.string.vaild_phone_number));
                return false;
            }
            return true;
        } else {
            setErrors(new boolean[]{isPhoneEntered});
        }
        return false;
    }

    public boolean isOneEntered(String title) {
        boolean istitle = !TextUtils.isEmpty(title);
        if (istitle) {
            return true;
        } else {
            setErrors(new boolean[]{istitle});
        }
        return false;
    }

    public boolean isContactSupport(String fullName, String phoneNumber) {
        boolean isfullNameEntered = !TextUtils.isEmpty(fullName);
        boolean isPhoneNumberEntered = !TextUtils.isEmpty(phoneNumber);
        boolean isValidPhNum = false;
        if (phoneNumber.length() >= 10) {
            isValidPhNum = true;
        }
        if (isfullNameEntered && isValidPhNum) {
            return true;
        } else {
            setErrors(new boolean[]{isfullNameEntered, isValidPhNum});
        }
        return false;
    }


    public boolean isSymEnteder(String duration, String symptoms, String details) {
        boolean isdurationEntered = !TextUtils.isEmpty(duration);
        boolean issymptomsEnterd = !TextUtils.isEmpty(symptoms);
        boolean isdetailsEnterd = !TextUtils.isEmpty(details);

        if (isdurationEntered && issymptomsEnterd) {
            return true;
        } else if (!isdurationEntered && !issymptomsEnterd && !isdetailsEnterd) {
            return true;
        } else if (!isdurationEntered && !issymptomsEnterd && isdetailsEnterd) {
            setErrors(new boolean[]{isdurationEntered, issymptomsEnterd});
        } else {
            setErrors(new boolean[]{isdurationEntered, issymptomsEnterd});
        }
        return false;
    }

    public boolean isCaseroomDetailsEntered(String caseroom_title, String caseroom_splty, boolean patient_details, String age) {
        boolean iscaseroom_title = !TextUtils.isEmpty(caseroom_title);
        boolean iscaseroom_splty = !TextUtils.isEmpty(caseroom_splty);
        boolean isage = !TextUtils.isEmpty(age);
        if (iscaseroom_title && iscaseroom_splty) {
            if (patient_details && isage)
                return true;
            else if (patient_details && !isage)
                setErrors(new boolean[]{iscaseroom_title, iscaseroom_splty, isage});
            else
                return true;

        } else {
            if (patient_details && isage)
                setErrors(new boolean[]{iscaseroom_title, iscaseroom_splty, isage});
            else if (patient_details && !isage)
                setErrors(new boolean[]{iscaseroom_title, iscaseroom_splty, isage});
            else
                setErrors(new boolean[]{iscaseroom_title, iscaseroom_splty, true});
        }
        return false;
    }


    public boolean isVitalsDetEntered(String hrprs, String resprates, String bp1s, String bp2s, String temps, String spos, String weights, String heights) {
        ArrayList<Boolean> isInRange = new ArrayList<>();
        int hrpr = 0;
        int resprate = 0;
        int bp1 = 0;
        int bp2 = 0;
        int temp = 0;
        int spo = 0;
        float weight = 0;
        int height = 0;
        isInRange.add(true);
        isInRange.add(true);
        isInRange.add(true);
        isInRange.add(true);
        isInRange.add(true);
        isInRange.add(true);
        isInRange.add(true);
        //isInRange.add(true);

        if (!hrprs.equals("")) {
            hrpr = Integer.parseInt(hrprs);
            if (hrpr < 20 || hrpr > 300) {
                isInRange.add(0, false);
            }
        }
        if (!resprates.equals("")) {
            resprate = Integer.parseInt(resprates);
            if (resprate < 10 || resprate > 100)
                isInRange.add(1, false);
        }
        if (!bp1s.equals("")) {
            bp1 = Integer.parseInt(bp1s);
            if (bp1 < 40 || bp1 > 300)
                isInRange.add(2, false);
        }
        if (!bp2s.equals("")) {
            bp2 = Integer.parseInt(bp2s);
            if (bp2 < 30 || bp2 > 120)
                isInRange.add(2, false);
        }
        if (!temps.equals("")) {
            temp = Integer.parseInt(temps);
            if (temp < 90 || temp > 107)
                isInRange.add(3, false);
        }
        if (!spos.equals("")) {
            spo = Integer.parseInt(spos);
            if (spo < 20 || spo > 100)
                isInRange.add(4, false);
        }
        if (!weights.equals("")) {
            weight = Float.parseFloat(weights);
            if (weight < 0.5 || weight > 200)
                isInRange.add(5, false);
        }
        if (!heights.equals("")) {
            height = Integer.parseInt(heights);
            if (height < 30 || height > 200)
                isInRange.add(6, false);
        }


        setErrors(isInRange.toArray(new Boolean[isInRange.size()]));

        for (Boolean b : isInRange) {
            if (!b)
                return false;
        }
        return true;
    }

    public boolean isSystemicExaminationDetEntered(boolean isheentchecked, String heent,
                                                   boolean isrespiratorychecked, String respiratory,
                                                   boolean iscvschecked, String cvs,
                                                   boolean isabdominalchecked, String abdominal,
                                                   boolean iscnschecked, String cns,
                                                   boolean ismusculochecked, String musculo,
                                                   boolean isotherschecked, String others) {
        boolean isheent = !TextUtils.isEmpty(heent);
        boolean isrespiratory = !TextUtils.isEmpty(respiratory);
        boolean iscvs = !TextUtils.isEmpty(cvs);
        boolean isabdominal = !TextUtils.isEmpty(abdominal);
        boolean iscns = !TextUtils.isEmpty(cns);
        boolean ismusculo = !TextUtils.isEmpty(musculo);
        boolean isothers = !TextUtils.isEmpty(others);


        ArrayList<Boolean> ischeckednotempty = new ArrayList<>();


        if (isheentchecked && !isheent)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isrespiratorychecked && !isrespiratory)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (iscvschecked && !iscvs)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isabdominalchecked && !isabdominal)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (iscnschecked && !iscns)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (ismusculochecked && !ismusculo)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);


        if (isotherschecked && !isothers)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        setErrors(ischeckednotempty.toArray(new Boolean[ischeckednotempty.size()]));

        for (Boolean b : ischeckednotempty) {
            if (!b)
                return false;
        }
        return true;
    }

    public boolean isPatientHistoryDetEntered(boolean ispastchecked, String past,
                                              boolean isbirthchecked, String birth,
                                              boolean isdevelopmentalchecked, String developmental,
                                              boolean isdietchecked, String diet,
                                              boolean isimmunizationchecked, String immunization,
                                              boolean isfamilychecked, String family,
                                              boolean ispersonalchecked, String personal,
                                              boolean isobstetricchecked, String obstetric,
                                              boolean isotherschecked, String others) {
        boolean ispast = !TextUtils.isEmpty(past);
        boolean isbirth = !TextUtils.isEmpty(birth);
        boolean isdevelopmental = !TextUtils.isEmpty(developmental);
        boolean isdiet = !TextUtils.isEmpty(diet);
        boolean isimmunization = !TextUtils.isEmpty(immunization);
        boolean isfamily = !TextUtils.isEmpty(family);
        boolean ispersonal = !TextUtils.isEmpty(personal);
        boolean isobstetric = !TextUtils.isEmpty(obstetric);
        boolean isothers = !TextUtils.isEmpty(others);

        ArrayList<Boolean> ischeckednotempty = new ArrayList<>();


        if (ispastchecked && !ispast)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isbirthchecked && !isbirth)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isdevelopmentalchecked && !isdevelopmental)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isdietchecked && !isdiet)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isimmunizationchecked && !isimmunization)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isfamilychecked && !isfamily)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (ispersonalchecked && !ispersonal)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isobstetricchecked && !isobstetric)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        if (isotherschecked && !isothers)
            ischeckednotempty.add(false);
        else
            ischeckednotempty.add(true);

        setErrors(ischeckednotempty.toArray(new Boolean[ischeckednotempty.size()]));

        for (Boolean b : ischeckednotempty) {
            if (!b)
                return false;
        }
        return true;
    }


    public void setError(String error) {
        for (int i = 0; i < fieldsArray.length; i++) {
            fieldsArray[i].setVisibility(View.GONE);
        }
    }

    public void clearError() {
        for (int i = 0; i < textArray.length; i++) {

            if (textArray[i].getText().equals(context.getString(R.string.valid_email))) {
                textArray[i].setText(context.getString(R.string.dlg_not_email_field_entered));
            }
            if (textArray[i].getText().equals(context.getString(R.string.email_exists))) {
                textArray[i].setText(context.getString(R.string.dlg_not_email_field_entered));
            }
            if (textArray[i].getText().equals(context.getString(R.string.required_minimum))) {
                textArray[i].setText(context.getString(R.string.dlg_not_password_field_entered));
            }
            if (textArray[i].getText().equals(context.getString(R.string.same_password))) {
                textArray[i].setText(context.getString(R.string.dlg_not_password_field_entered));
            }
            if (textArray[i].getText().equals(context.getString(R.string.notsame))) {
                textArray[i].setText(context.getString(R.string.dlg_not_password_field_entered));
            }
            if (textArray[i].getText().equals(context.getString(R.string.incorrect_email_pass))) {
                textArray[i].setText(context.getString(R.string.dlg_not_password_field_entered));
            }
            if (textArray[i].getText().equals(context.getString(R.string.not_be_same_as_userid))) {
                textArray[i].setText(context.getString(R.string.dlg_not_password_field_entered));
            }
            if (textArray[i].getText().equals(context.getString(R.string.dlg_year_yyyy_format_error))) {
                textArray[i].setText(context.getString(R.string.dlg_year_error));
            }

            textArray[i].setVisibility(View.GONE);
        }
    }

    public void setError(int index, String error) {
        textArray[index].setVisibility(View.VISIBLE);
        textArray[index].setText(error);
    }

    public void setErrorAtindex(int index) {
        textArray[index].setVisibility(View.VISIBLE);
    }

    public void setErrors(boolean[] isFieldsEnteredArray) {
        boolean edittext = true;
        for (int i = 0; i < textArray.length; i++) {
            try {
                if (edittext) {
                    if (!isFieldsEnteredArray[i]) {
                        if (fieldsArray[i] != null) {
                            fieldsArray[i].requestFocus();
                            edittext = false;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            textArray[i].setVisibility(isFieldsEnteredArray[i] ? View.GONE : View.VISIBLE);
        }
    }

    public void setErrors(Boolean[] isFieldsEnteredArray) {
        boolean edittext = true;
        for (int i = 0; i < textArray.length; i++) {
            try {
                if (edittext) {
                    if (!isFieldsEnteredArray[i]) {
                        if (fieldsArray[i] != null) {
                            fieldsArray[i].requestFocus();
                            edittext = false;
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            textArray[i].setVisibility(isFieldsEnteredArray[i] ? View.GONE : View.VISIBLE);
        }
    }


    // TODO SF temp method
    public String getErrorMessageByCode(int errorCode) {
        String errorMessage = "ERROR";
        switch (errorCode) {
            case 1:
                errorMessage = "error 1";
                break;
        }
        return errorMessage;
    }


    public boolean isValidDates(long fromdate, long todate) {
        if (fromdate < todate) {
            clearError();
            return true;
        } else {
            setErrors(new boolean[]{false});
        }
        return false;
    }

    public boolean isUrlValid(String url) {
        if (url == null) {
            return false;
        }
        return (url.startsWith("http", 0) || url.startsWith("https", 0));
    }

    public void hideViewIfnull() {
        for (int i = 0; i < textArray.length; i++) {
            if (TextUtils.isEmpty(textArray[i].getText()))
                textArray[i].setVisibility(View.GONE);
            else
                textArray[i].setVisibility(View.VISIBLE);

        }
    }

    public boolean isValidMandatoryField(String fName, String lName, String speciality, String experience, boolean isMandatory) {
        boolean isFNameEntered = !TextUtils.isEmpty(fName);
        boolean isLNameEntered = !TextUtils.isEmpty(lName);
        boolean isSpecialtyEntered = !TextUtils.isEmpty(speciality);
        boolean isExperienceEntered = !TextUtils.isEmpty(experience);

        if (isMandatory) {
            if (isFNameEntered && isLNameEntered && isSpecialtyEntered && isExperienceEntered) {
                return true;
            } else {
                setErrors(new boolean[]{isFNameEntered, isLNameEntered, isSpecialtyEntered, isExperienceEntered});
            }
        } else {
            if (isFNameEntered && isLNameEntered && isExperienceEntered) {
                return true;
            } else {
                setErrors(new boolean[]{isFNameEntered, isLNameEntered, isExperienceEntered});
            }
        }

        return false;
    }

    public boolean isValidMandatoryFieldForSpecialtyAndExperience(String speciality, String experience, String location, String preferred_speciality, String preferred_location, boolean locationsAndSpecializationsMandatory) {
        boolean isSpecialtyEntered = !TextUtils.isEmpty(speciality);
        boolean isExperienceEntered = !TextUtils.isEmpty(experience);
        boolean isLocationEntered = !TextUtils.isEmpty(location);
        boolean isPreferred_speciality=!TextUtils.isEmpty(preferred_speciality);
        boolean isPreferred_location=!TextUtils.isEmpty(preferred_location);
        if(locationsAndSpecializationsMandatory){
            if (isSpecialtyEntered && isLocationEntered && isExperienceEntered && isPreferred_speciality && isPreferred_location) {
                return true;
            } else {
                setErrors(new boolean[]{isSpecialtyEntered, isLocationEntered, isExperienceEntered,isPreferred_speciality,isPreferred_location});
            }
        }else{
            if (isSpecialtyEntered && isLocationEntered && isExperienceEntered) {
                return true;
            } else {
                setErrors(new boolean[]{isSpecialtyEntered, isLocationEntered, isExperienceEntered});
            }
        }



        return false;
    }

    public boolean isValidMandatoryFieldForLocation() {
        setErrors(new boolean[]{false});
        return false;
    }
}