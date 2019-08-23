package com.cs.nks.easycouriers.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.nks.easycouriers.R;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by Admin on 7/25/2017.
 */

public class UTIL {
    public static final String HEADER = "http://";
    public static final String URL_DEV = HEADER + "https://www.ncsmgroup.co.in";


    public static final String KEY_USERID = "USERID";
    public static final String KEY_Email = "Email";
    public static final String KEY_Mobile = "Mobile";
    public static final String KEY_UseType = "UseType";
    public static final String KEY_CCId = "CCId";

    public static final String KEY_FirstName = "FirstName";
    public static final String KEY_LastName = "LastName";
    public static final String KEY_Company_ID = "Company_ID";

    public static final String KEY_PendingFromOperationContracting = "PendingFromOperationContracting_CC";
    public static final String KEY_PendingFromCallCenter_CC = "PendingFromCallCenter_CC";
    public static final String KEY_LeadExpireBooking_CC = "LeadExpireBooking_CC";
    public static final String KEY_LeadCloseBooking_CC = "LeadCloseBooking_CC";

    public static final String KEY_Update_BookingRequestRM = "BookingRequestRM";
    public static final String NetworkError = "Please check your internet connection!";


    public static final String KEY_PendingFromCallCenter_CS = "PendingFromCallCenter_CS";
    public static final String KEY_LeadExpireBooking_CS = "LeadExpireBooking_CS";
    public static final String KEY_LeadCloseBooking_CS = "LeadCloseBooking_CS";

    /*new*/
    public static final String KEY_isLogin = "isLogin";
    public static String Key_GENDER = "gender";//string UserId
    public static String Key_RoleId_Admin = "1";
    public static String Key_RoleId_Sub_admin = "2";
    public static String Key_RoleId_Manager = "3";
    public static String Key_RoleId_General = "4";
    public static String Key_Mobile = "Mobile";
    public static String Key_DeviceId = "DeviceId";
    public static String Key_Scheduler_Num = "Scheduler_Num";
    public static String Key_Pin_Num = "Pin_Num";
    public static String Key_Scheduler_Time = "Scheduler_Time";
    public static String Key_Scheduler_SavedAt = "SavedAt";
    public static String NoInternet = "Kindly check your internet connection!";
    public static String Key_UserId = "Key_UserId";
    public static String Key_USERNAME = "USERNAME";
    public static String Key_Schedule = "Schedule";
    public static String Key_COURSE = "USERNAME";
    public static String Key_IMG_URL = "IMG_URL";
    public static String Register_User_API = "/api/Arduino/RegisterUser?";//string Username, string Password, string Mobile, string EmailId
    public static String Register_Device_API = "/api/Arduino/RegisterDevice?";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String Register_DeviceA_API = "/api/Arduino/RegisterDeviceA";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String UpdateTimeZone_API = "/api/Arduino/UpdateTimeZone";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    /***************************************************************/

    public static String Login_API = "/web_api/login_api.php?";//enrollment_number="XXX333&
    public static String API_Type = "1";
    public static String AboutUs_API = "/web_api/about_us_api.php?type=" + API_Type;//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String CenterList_API = "/web_api/center_list_api.php?type=" + API_Type;//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String CourseList_API = "/web_api/course_list_api.php?type=" + API_Type;//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String CourseListRegisteration_API = "/web_api/course_list_user_registration_api.php?type=" + API_Type;//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    // user_date=17&user_month=5&user_year=1987&type=1;

    //string mobileno, string pwd
    public static String SubjectListbyCourse_API = "/web_api/subject_list_user_registration_api.php?paper=";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    public static String CourseMaterial_API = "/web_api/student_meterials.php?sid=";//string UserId, string DeviceId, string DeviceName, string Pin_D4, string Pin_D5, string Pin_D6, string Pin_D7, string Pin_D8
    static String Domain_Arduino_Live = "https://www.ncsmgroup.co.in";
    public static String Domain_Arduino = Domain_Arduino_Live;
    static String Domain_Arduino_Dev = "https://www.ncsmgroup.co.in";
    private Context myContext;
    private ProgressDialog progressDialog;

    /***************************************************/


    public UTIL(Context context) {
        myContext = context;
    }

    public static void setPref(Context context, String key, String value) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPref(Context context, String key) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isLogin(Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getBoolean(KEY_isLogin, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setLogin(Context context, boolean isLogin) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(KEY_isLogin, isLogin);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean clearPref(Context context) {
        boolean result = false;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            result = preferences.edit().clear().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    public static void showToast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


    }

    public static SpannableString getTitle(String title) {
        SpannableString ss1 = new SpannableString(title);
        ss1.setSpan(new AbsoluteSizeSpan(50), 0, title.length(), SPAN_INCLUSIVE_INCLUSIVE);
        return ss1;
    }

    public void showProgressDialog(String message) {
        if (progressDialog == null || (!progressDialog.isShowing())) {
            progressDialog = ProgressDialog.show(myContext, null, null, true);
            progressDialog.setContentView(R.layout.elemento_progress);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            final TextView tv = progressDialog.getWindow().findViewById(R.id.textView6);
            tv.setText(message);
            progressDialog.setCancelable(false);
            if (!progressDialog.isShowing())
                progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

    }

    public static String monthName(String mnthNum) {

        if (mnthNum.equalsIgnoreCase("1"))
            return "January";
        else if (mnthNum.equalsIgnoreCase("2"))
            return "February";
        else if (mnthNum.equalsIgnoreCase("3"))
            return "March";
        else if (mnthNum.equalsIgnoreCase("4"))
            return "April";
        else if (mnthNum.equalsIgnoreCase("5"))
            return "May";
        else if (mnthNum.equalsIgnoreCase("6"))
            return "June";
        else if (mnthNum.equalsIgnoreCase("7"))
            return "July";
        else if (mnthNum.equalsIgnoreCase("8"))
            return "August";
        else if (mnthNum.equalsIgnoreCase("9"))
            return "September";
        else if (mnthNum.equalsIgnoreCase("10"))
            return "October";
        else if (mnthNum.equalsIgnoreCase("11"))
            return "November";
        else if (mnthNum.equalsIgnoreCase("12"))
            return "December";


        else return "Wrong";
    }


}
