package com.vam.whitecoats.parser;

/**
 * Created by lokeshl on 2/18/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HttpStatusCodes;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class HttpClient {

    private Context context;
    private String url;
    private HttpURLConnection con;
    private OutputStream os;
    private String delimiter = "--";
    private String boundary = "SwA" + Long.toString(System.currentTimeMillis()) + "SwA";
    public static String cookie;
    private static String spring_securityToken;
    private File myDirectory, file;
    private FileOutputStream fos;
    private SharedPreferences httpshp;
    private MySharedPref mySharedPref;
    private String reg_id = "";
    private int status = 0;


    public HttpClient(Context mContext, String url) {
        this.url = url;
        this.context = mContext;
    }

    public String downloadImage(String fileName) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            int size = 1024;
            byte[] buf;

            URL download_url = new URL(url);
            if (fileName.startsWith("group_pic")) {
                myDirectory = AppUtil.getExternalStoragePathFile(context, ".Whitecoats/Group_Pic");
            } else {
                myDirectory = AppUtil.getExternalStoragePathFile(context, ".Whitecoats/Doc_Profiles");
            }
            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }

            file = new File(myDirectory + "/" + fileName);


            URLConnection ucon = download_url.openConnection();
            InputStream is;
            int status = con.getResponseCode();
            if (status >= HttpStatusCodes.SC_BAD_REQUEST) {
                is = con.getErrorStream();
            } else {
                is = con.getInputStream();
            }
            BufferedInputStream bis = new BufferedInputStream(is);
            /* * Read bytes to the Buffer until there is nothing more to read(-1). **/
            buf = new byte[size];

            while ((len = bis.read(buf, 0, size)) != -1) {
                baos.write(buf, 0, len);
            }
            buf = baos.toByteArray();

            fos = new FileOutputStream(file);
            fos.write(buf);
            fos.close();
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public void connectForMultipart(Context context) throws Exception {
        con = (HttpURLConnection) (new URL(url)).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        if (cookie != null) {
            con.setRequestProperty("Cookie", cookie);
        }

        if (getDeviceId(context) != null)
            con.setRequestProperty("X-DEVICE-ID", getDeviceId(context));
        con.setRequestProperty("X-TIME-ZONE", "+05:30");


        if (getVerionCode(context) != null)
            con.setRequestProperty("X-APP-VERSION", getVerionCode(context));
        con.setRequestProperty("X-APP-CHANNEL-NAME", "ANDROID");

        httpshp = context.getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
        String spring_securityToken = httpshp.getString("SSTOKEN", "");
        if (spring_securityToken != null && !spring_securityToken.equals(""))
            con.setRequestProperty("X-Auth-Token", spring_securityToken);
        con.setRequestProperty("X-STAY-LOGGEDIN", "yes");
        con.setChunkedStreamingMode(0);
        con.setConnectTimeout(2 * 10000);
        con.connect();
        os = con.getOutputStream();
    }

    public void connectForSinglepart(Context context) throws Exception {
        mySharedPref = new MySharedPref(context);
        reg_id = mySharedPref.getPrefsHelper().getPref(MyFcmListenerService.PROPERTY_REG_ID, "");
        con = (HttpURLConnection) (new URL(url)).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Connection", "close");
        con.setConnectTimeout(2 * 10000);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if (cookie != null) {
            con.setRequestProperty("Cookie", cookie);
            Log.v("Cookie@addanother", "" + cookie);
        }
        con.setRequestProperty("X-DEVICE-ID", getDeviceId(context));
        con.setRequestProperty("X-TIME-ZONE", "+05:30");
        con.setRequestProperty("X-STAY-LOGGEDIN", "yes");
        httpshp = context.getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
        String spring_securityToken = httpshp.getString("SSTOKEN", "");
        if (spring_securityToken != null && !spring_securityToken.equals(""))
            con.setRequestProperty("X-Auth-Token", spring_securityToken);
        Log.v("ssyToken@addanother", "" + spring_securityToken);


        if (getVerionCode(context) != null)
            con.setRequestProperty("X-APP-VERSION", getVerionCode(context));
        con.setRequestProperty("X-APP-CHANNEL-NAME", "ANDROID");

        if (RestApiConstants.Community_Local_Dayfirstfeed.equalsIgnoreCase(url) || RestApiConstants.REGISTRATION_PROCESS.equalsIgnoreCase(url)) {
            String AndroidVersion = android.os.Build.VERSION.RELEASE;
            String devicemodel = android.os.Build.MODEL;
            String deviceMaker = Build.MANUFACTURER;
            if (!AndroidVersion.equals("") && !devicemodel.equals("")) {
                con.setRequestProperty("X-DEVICE-OS-VERSION", AndroidVersion);
                con.setRequestProperty("X-DEVICE-MODEL", devicemodel);
            }
            con.setRequestProperty("X-DEVICE-MAKER", deviceMaker);
            con.setRequestProperty("X_APP_NOTIFICATIONS_ID", reg_id);
        }
        con.connect();
        os = con.getOutputStream();
    }

    public void connectForNotifySinglePart(Context context) throws Exception {
        con = (HttpURLConnection) (new URL(url)).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Accept-Encoding", "gzip");
        System.setProperty("http.keepAlive", "false");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setConnectTimeout(126000);
        if (cookie != null) {
            con.setRequestProperty("Cookie", cookie);
        }
        con.setRequestProperty("X-DEVICE-ID", getDeviceId(context));
        con.setRequestProperty("X-TIME-ZONE", "+05:30");

        if (getVerionCode(context) != null)
            con.setRequestProperty("X-APP-VERSION", getVerionCode(context));
        con.setRequestProperty("X-APP-CHANNEL-NAME", "ANDROID");

        con.connect();
        os = con.getOutputStream();
    }

    public void connectForMultipartCookie(Context context) throws Exception {
        con = (HttpURLConnection) (new URL(url)).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setConnectTimeout(2 * 10000);
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        if (cookie != null) {
            con.setRequestProperty("Cookie", cookie);
        }
        con.setRequestProperty("X-DEVICE-ID", getDeviceId(context));
        con.setRequestProperty("X-TIME-ZONE", "+05:30");

        httpshp = context.getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
        String spring_securityToken = httpshp.getString("SSTOKEN", "");
        if (spring_securityToken != null && !spring_securityToken.equals(""))
            con.setRequestProperty("X-Auth-Token", spring_securityToken);

        if (getVerionCode(context) != null)
            con.setRequestProperty("X-APP-VERSION", getVerionCode(context));
        con.setRequestProperty("X-APP-CHANNEL-NAME", "ANDROID");
        con.setRequestProperty("X-STAY-LOGGEDIN", "yes");

        con.connect();
        os = con.getOutputStream();
    }

    public void connectForLoginMultipartCookie(Context context) throws Exception {

        con = (HttpURLConnection) (new URL(url)).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setConnectTimeout(2 * 10000);
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        con.setRequestProperty("X-DEVICE-ID", getDeviceId(context));
        con.setRequestProperty("X-TIME-ZONE", "+05:30");

        if (getVerionCode(context) != null)
            con.setRequestProperty("X-APP-VERSION", getVerionCode(context));
        con.setRequestProperty("X-APP-CHANNEL-NAME", "ANDROID");


        String AndroidVersion = android.os.Build.VERSION.RELEASE;
        String devicemodel = android.os.Build.MODEL;

        if (!AndroidVersion.equals("") && !devicemodel.equals("")) {
            con.setRequestProperty("X-DEVICE-OS-VERSION", AndroidVersion);
            con.setRequestProperty("X-DEVICE-MODEL", devicemodel);
        }
        con.setRequestProperty("X-STAY-LOGGEDIN", "yes");
        con.connect();
        os = con.getOutputStream();
    }


    public void clearCookie() {
        cookie = null;
    }

    public void connectForSinglepartWithOutCookie(Context context) throws Exception {
        Log.v("search_server", "connectForSinglepartWithOutCookie");
        con = (HttpURLConnection) (new URL(url)).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if (cookie != null) {
            con.setRequestProperty("Cookie", cookie);
        }
        if (getDeviceId(context) != null)
            con.setRequestProperty("X-DEVICE-ID", getDeviceId(context));
        con.setRequestProperty("X-TIME-ZONE", "+05:30");

        if (getVerionCode(context) != null)
            con.setRequestProperty("X-APP-VERSION", getVerionCode(context));
        con.setRequestProperty("X-APP-CHANNEL-NAME", "ANDROID");

        httpshp = context.getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
        String spring_securityToken = httpshp.getString("SSTOKEN", "");
        if (spring_securityToken != null && !spring_securityToken.equals(""))
            con.setRequestProperty("X-Auth-Token", spring_securityToken);
        con.setRequestProperty("X-STAY-LOGGEDIN", "yes");
        con.setConnectTimeout(30 * 1000);
        con.connect();
        os = new DataOutputStream(con.getOutputStream());
        Log.v("search_server", "connectForSinglepartWithOutCookie");
    }

    public void connectForSinglepartWithOutCookieGet(Context context) throws Exception {
        con = (HttpURLConnection) (new URL(url)).openConnection();
        con.setRequestMethod("GET");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if (cookie != null) {
            con.setRequestProperty("Cookie", cookie);
        }

        if (getDeviceId(context) != null)
            con.setRequestProperty("X-DEVICE-ID", getDeviceId(context));
        con.setRequestProperty("X-TIME-ZONE", "+05:30");

        if (getVerionCode(context) != null)
            con.setRequestProperty("X-APP-VERSION", getVerionCode(context));
        con.setRequestProperty("X-APP-CHANNEL-NAME", "ANDROID");
        con.setRequestProperty("X-STAY-LOGGEDIN", "yes");

        httpshp = context.getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
        String spring_securityToken = httpshp.getString("SSTOKEN", "");
        if (spring_securityToken != null && !spring_securityToken.equals(""))
            con.setRequestProperty("X-Auth-Token", spring_securityToken);

        con.setConnectTimeout(30 * 1000);
        con.connect();
        os = new DataOutputStream(con.getOutputStream());
    }


    public void addFormPart(String paramName, String value) throws Exception {
        writeParamData(paramName, value);
    }

    public void addpara(String paramName, String value) throws Exception {
        value = URLEncoder.encode(value, "UTF-8");
        os.write((paramName + "=" + value).getBytes());
        os.flush();
        os.close();
        Log.v("searchreqdata", "addpara");
    }

    public void addFilePart(String paramName, String fileName, byte[] data) throws Exception {
        os.write((delimiter + boundary + "\r\n").getBytes());
        os.write(("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"\r\n").getBytes());
        // os.write( ("Content-Type: application/octet-stream\r\n"  ).getBytes());
        os.write(("Content-Type: image/png\r\n").getBytes());
        os.write(("Content-Transfer-Encoding: binary\r\n").getBytes());
        os.write("\r\n".getBytes());

        os.write(data);

        os.write("\r\n".getBytes());
    }

    private void writeParamData(String paramName, String value) throws Exception {
        os.write((delimiter + boundary + "\r\n").getBytes());
        os.write("Content-Type: text/plain\r\n".getBytes());
        os.write(("Content-Disposition: form-data; name=\"" + paramName + "\"\r\n").getBytes());
        os.write(("\r\n" + value + "\r\n").getBytes());
    }

    public void finishMultipart() throws Exception {
        os.write((delimiter + boundary + delimiter + "\r\n").getBytes());
    }


    public String getResponse() throws Exception {
        int len;
        int size = 1024;
        byte[] buf;
        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        try {
            Log.v("search_Response", "" + con.getResponseCode());
            if (con.getHeaderField("Set-Cookie") != null) {
                cookie = con.getHeaderField("Set-Cookie");
                System.out.println("Cookie Found..." + cookie);
            }
            status = con.getResponseCode();
            if (status >= HttpStatusCodes.SC_BAD_REQUEST) {
                is = con.getErrorStream();
            } else {
                is = con.getInputStream();
            }
            while ((len = is.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, len);
            }
            buf = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            con.disconnect();
            bos.close();
            if (is != null) {
                is.close();
            }
        }
        return new String(buf);
    }

    public String getNotificationResponse() throws Exception {
        int len;
        int size = 1024;
        byte[] buf;

        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        try {
            Log.v("Response", "" + con.getResponseCode());
            int status = con.getResponseCode();
            if (status >= HttpStatusCodes.SC_BAD_REQUEST) {
                is = con.getErrorStream();
            } else {
                is = con.getInputStream();
            }
            while ((len = is.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, len);
            }
            buf = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            con.disconnect();
            bos.close();
            if (is != null) {
                is.close();
            }
        }
        String val = new String(buf);
        return val;
    }

    public String getJSONResponse() throws IOException {
        StringBuilder sb = null;
        InputStream is;
        int status = con.getResponseCode();
        if (status >= HttpStatusCodes.SC_BAD_REQUEST) {
            is = con.getErrorStream();
        } else {
            is = con.getInputStream();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

        } catch (Exception e) {
            Log.e("Buffer Error", "Error: " + e.toString());
        } finally {
            is.close();
            con.disconnect();
        }

        if (sb != null) {
            return sb.toString();
        } else {
            return "";
        }
    }


    public String getLoginJSONResponse() throws IOException {
        StringBuilder sb = null;
        try {
            System.out.println("HeaderFields..." + con.getHeaderFields());

            if (con.getHeaderField("Set-Cookie") != null) {
                List<String> cookiesList = con.getHeaderFields().get("Set-Cookie");
                HttpClient.cookie = "";
                for (String _cookie : cookiesList) {
                    System.out.println("_cookie =" + _cookie);
                    HttpClient.cookie += _cookie + ",";
                }

                System.out.println(HttpClient.cookie);
            }

            InputStream is;
            int status = con.getResponseCode();
            if (status >= HttpStatusCodes.SC_BAD_REQUEST) {
                is = con.getErrorStream();
            } else {
                is = con.getInputStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error: " + e.toString());
        }
        if (sb != null) {
            return sb.toString();
        } else {
            return "";
        }
    }

    private String getDeviceId(Context context) {
        String deviceId = null;
        try {
            deviceId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            /*TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();*/
            Log.v("UUID", "RESPONSE" + deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    public static String getVerionCode(Context context) {
        String versionNumber = null;
        try {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionNumber = "" + pinfo.versionCode;
            String versionName = pinfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionNumber;
    }

    public int getStatusCode() {
        return status;
    }

}