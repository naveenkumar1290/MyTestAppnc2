package com.cs.nks.easycouriers.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.activity.ActivityWithNavigationMenu;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;
import com.payu.magicretry.Helpers.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
/* get release sh1 for gSign
 * https://stackoverflow.com/questions/34933380/sha1-key-for-debug-release-android-studio-mac
 * */
/*
 * SHA1: 51:96:13:9B:0F:FB:95:1D:C9:8B:B7:02:E1:B4:DD:D5:83:AE:FD:C9
 *
 * */

public class LoginFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    //,
    //GoogleApiClient.OnConnectionFailedListener{


    // private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    Context myContext;
    UTIL utill;
    TextView tv_timing, tv_compName, tv_title_timing, tv_title_compName;
    Button btnLogin, btn_register;
    ImageView btn_g_sign_in, btn_fb_sign_in;
    EditText et_mobile, et_password;
    // private GoogleApiClient mGoogleApiClient;
    String _year, _month, _day;
    // private Button btnSignOut, btnRevokeAccess;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        myContext = getActivity();
        // utill = new UTIL(myContext);

        //  getActivity().setTitle(UTIL.getTitle("Login"));

        et_mobile = (EditText) rootView.findViewById(R.id.email);
        et_password = (EditText) rootView.findViewById(R.id.password);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        et_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click_getDate();
            }
        });

        //int s = 1 / 0;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String user_mobile = et_mobile.getText().toString().trim();
                String user_pwd = et_password.getText().toString().trim();


                if (user_mobile.isEmpty() ) {
                    Toast.makeText(myContext,
                            "Please enter enrollment no.!", Toast.LENGTH_SHORT)
                            .show();
                } else if (user_pwd.isEmpty()) {
                    Toast.makeText(myContext,
                            "Please enter date of birth!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    if (new ConnectionDetector(myContext).isConnectingToInternet()) {
                        LoginApiCall(user_mobile, _day,_month,_year);

                    } else {
                        Toast.makeText(myContext,
                                UTIL.NoInternet, Toast.LENGTH_SHORT)
                                .show();
                    }

                }


            }

        });


        return rootView;

    }

    void LoginApiCall(final String enrolNo, String date, String month, String year) {
        String tag_string_req = "req_login";
        showProgressDialog();

        String URL_LOGIN = null;


        try {
            URL_LOGIN = UTIL.Domain_Arduino + UTIL.Login_API + "enrollment_number=" + enrolNo + "&user_date=" + date + "&user_month=" + month + "&user_year=" + year + "&type=" + UTIL.API_Type;
            URL_LOGIN = URL_LOGIN.replaceAll(" ", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }


        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                hideProgressDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject jsonObject = jObj.getJSONObject("result");

                    String status = jsonObject.getString("status");
                    if (status.equalsIgnoreCase("Valid")) {


                        Toast.makeText(myContext,
                                "Logged-In successfully!", Toast.LENGTH_SHORT).show();

                        String UserId = jsonObject.getString("id");
                        String Name = jsonObject.getString("name");
                        String course = jsonObject.getString("course");

                        UTIL.setPref(myContext, UTIL.Key_UserId, UserId);
                        UTIL.setPref(myContext, UTIL.Key_USERNAME, Name);
                        UTIL.setPref(myContext, UTIL.Key_COURSE, course);

                        UTIL.setLogin(getActivity(), true);

                        startActivity(new Intent(myContext, ActivityWithNavigationMenu.class));
                        getActivity().finish();
                    } else if (status.equalsIgnoreCase("Not Valid")) {
                        Toast.makeText(myContext,
                                "Incorrect credentials!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(myContext,
                                jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(myContext, "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myContext,
                        "Volley Error!", Toast.LENGTH_SHORT).show();
                hideProgressDialog();

            }
        }) {


        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        String dayOfMonthString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String monthOfYearString = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;

        //2018-01-19%20

        // String  date_1 = year + "-" + monthOfYearString + "-" + dayOfMonthString;
        String date_1 = dayOfMonthString + "-" + monthOfYearString + "-" + year;

        // et_picking_up_date.setText(date_1);


        _year = year + "";
        _month = monthOfYearString;
        _day = dayOfMonthString;

        et_password.setText(date_1);

    }
    private void Click_getDate() {
        final FragmentActivity activity = (FragmentActivity) getActivity();
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                LoginFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);
        dpd.vibrate(false);
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);

        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.setAccentColor(getResources().getColor(R.color.colorAccent));


        dpd.setTitle("Select Date");
        dpd.setYearRange(1985, 2028);
        // dpd.setMinDate(calendar);
        dpd.show(getActivity().getFragmentManager(), "dialog");


    }
}
