package com.vam.whitecoats.utils;



/**
 * Created by tejaswini on 31-05-2016.
 */
public class QbAuthUtils {

    public static boolean isSessionActive() {
        /*try {
            String token = QBAuth.getBaseService().getToken();
            Date expirationDate = QBAuth.getBaseService().getTokenExpirationDate();

            if (TextUtils.isEmpty(token)) {
                return false;
            }

            if (System.currentTimeMillis() >= expirationDate.getTime()) {
                return false;
            }

            return true;
        } catch (BaseServiceException ignored) {
        }*/

        return false;
    }

}

