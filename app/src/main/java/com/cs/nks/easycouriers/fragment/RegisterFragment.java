package com.cs.nks.easycouriers.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.model.Center;
import com.cs.nks.easycouriers.model.CourseReg;
import com.cs.nks.easycouriers.model.SubjectReg;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.Myspinner;
import com.cs.nks.easycouriers.util.UTIL;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment
        implements DatePickerDialog.OnDateSetListener {

    Context myContext;

    EditText name, father_name, email, mobile,
            session_from_dt, session_to_dt, dob, qualification_board,
            qualification_class, qualification_passingYear, marks_obtain,
            division_grade, course_fee;

    Spinner spnr_board, spnr_course, spnr_session_year, spnr_session_mnth;

    // CheckBox cbox_subject1, cbox_subject2;

    Button btnimage;
    TextView text_imgPath;

    boolean dob_clicked = false;
    boolean session_from_clicked = false;
    boolean session_to_clicked = false;
    boolean passingYear_clicked = false;
    ArrayList<CourseReg> list_Course = new ArrayList<>();
    ArrayList<SubjectReg> list_Subject = new ArrayList<>();
    ArrayList<CourseReg> list_CourseDuration = new ArrayList<>();
    ArrayList<CourseReg> list_CourseYear = new ArrayList<>();
    LinearLayout ll_subject;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        //  getActivity().setTitle(UTIL.getTitle("Registration"));

        myContext = getActivity();
        //  setViewPager(rootView);
        setView(rootView);
        return rootView;
    }


    private void setView(View rootView) {
        name = rootView.findViewById(R.id.name);
        father_name = rootView.findViewById(R.id.father_name);
        email = rootView.findViewById(R.id.email);
        mobile = rootView.findViewById(R.id.mobile);
        session_from_dt = rootView.findViewById(R.id.session_from_dt);
        session_to_dt = rootView.findViewById(R.id.session_to_dt);
        dob = rootView.findViewById(R.id.dob);
        qualification_board = rootView.findViewById(R.id.qualification_board);

        qualification_class = rootView.findViewById(R.id.qualification_class);
        qualification_passingYear = rootView.findViewById(R.id.qualification_passingYear);
        marks_obtain = rootView.findViewById(R.id.marks_obtain);
        division_grade = rootView.findViewById(R.id.division_grade);
        course_fee = rootView.findViewById(R.id.course_fee);

        spnr_board = rootView.findViewById(R.id.spnr_board);
        spnr_course = rootView.findViewById(R.id.spnr_course);
        spnr_session_year = rootView.findViewById(R.id.spnr_session_year);
        spnr_session_mnth = rootView.findViewById(R.id.spnr_session_mnth);


        /* cbox_subject1=rootView.findViewById(R.id.cbox_subject1);
                cbox_subject2=rootView.findViewById(R.id.cbox_subject2);*/

        btnimage = rootView.findViewById(R.id.btnimage);
        text_imgPath = rootView.findViewById(R.id.text_imgPath);

        ll_subject = rootView.findViewById(R.id.ll_subject);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dob_clicked = true;
                session_from_clicked = false;
                session_to_clicked = false;
                passingYear_clicked = false;

                Click_getDate();
            }
        });
        session_from_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dob_clicked = false;
                session_from_clicked = true;
                session_to_clicked = false;
                passingYear_clicked = false;

                Click_getDate();
            }
        });
        session_to_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dob_clicked = false;
                session_from_clicked = false;
                session_to_clicked = true;
                passingYear_clicked = false;

                Click_getDate();
            }
        });
        qualification_passingYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dob_clicked = false;
                session_from_clicked = false;
                session_to_clicked = false;
                passingYear_clicked = true;

                Click_getDate();
            }
        });



    /*    ArrayList<Myspinner> list_BOARD = new ArrayList<>();
        list_BOARD.add(new Myspinner("--Select--", "-1"));
        list_BOARD.add(new Myspinner("CCE", "0"));
        list_BOARD.add(new Myspinner("NCSM", "1"));

        ArrayAdapter<ArrayList<Myspinner>> aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_BOARD);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_board.setAdapter(aa);

       */
        /*Instead of board spinner use type =2*/

        int year=  Calendar.getInstance().get(Calendar.YEAR);
        list_CourseYear.add(new CourseReg("0","-Year-"));
        list_CourseYear.add(new CourseReg(String.valueOf(year),String.valueOf(year)));
        list_CourseYear.add(new CourseReg(String.valueOf(year+1),String.valueOf(year+1)));
        ArrayAdapter<ArrayList<String>> aa1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_CourseYear);
        aa1.setDropDownViewResource(R.layout.spinner_item);
        spnr_session_year.setAdapter(aa1);



        list_CourseDuration.clear();
        list_CourseDuration.add(new CourseReg("0","-Month-"));


//        ArrayAdapter<ArrayList<CourseReg>> aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_CourseDuration);
//        aa.setDropDownViewResource(R.layout.spinner_item);
//        spnr_session_mnth.setAdapter(aa);

        ArrayAdapter<ArrayList<CourseReg>> aa = new ArrayAdapter(getActivity(), R.layout.spinner_item, list_CourseDuration);
        aa.setDropDownViewResource(R.layout.spinner_item);
        spnr_session_mnth.setAdapter(aa);



        spnr_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourseReg couresReg = (CourseReg) spnr_course.getSelectedItem();
                String cid = couresReg.getCId();

                if (cid.equals("0")) {
                    ll_subject.removeAllViews();

                    list_CourseDuration.clear();
                    list_CourseDuration.add(new CourseReg("0","-Month-"));
                    ArrayAdapter<ArrayList<CourseReg>> aa = new ArrayAdapter(getActivity(), R.layout.spinner_item, list_CourseDuration);
                    aa.setDropDownViewResource(R.layout.spinner_item);
                    spnr_session_mnth.setAdapter(aa);

                    spnr_session_mnth.setSelection(0);
                    spnr_session_year.setSelection(0);



                } else {
                    ll_subject.removeAllViews();
                    if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                        SubjectListbyCourseApiCall(cid);
                    } else {
                        Toast.makeText(myContext,
                                UTIL.NoInternet, Toast.LENGTH_SHORT)
                                .show();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            callApiCourseListRegisteration();
        } else {
        }
    }

    private void Click_getDate() {
        final FragmentActivity activity = (FragmentActivity) getActivity();
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                RegisterFragment.this,
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth + "/" + (++monthOfYear) + "/" + year;

        String dayOfMonthString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        String monthOfYearString = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;

        //2018-01-19%20

        // String  date_1 = year + "-" + monthOfYearString + "-" + dayOfMonthString;
        String date_1 = dayOfMonthString + "-" + monthOfYearString + "-" + year;
        String date_2 = monthOfYearString + "-" + year;

        // et_picking_up_date.setText(date_1);


        if (dob_clicked) {
            dob.setText(date_1);
        } else if (session_from_clicked) {
            session_from_dt.setText(date_2);
        } else if (session_to_clicked) {
            session_to_dt.setText(date_2);
        } else if (passingYear_clicked) {
            qualification_passingYear.setText(String.valueOf(year));
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // mSwipeRefreshLayout.setRefreshing(true);
    }

    private void callApiCourseListRegisteration() {
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
            CourseListApiCall();
        } else {
            Toast.makeText(myContext,
                    UTIL.NoInternet, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void CourseListApiCall() {

        list_Course.clear();

        list_Course.add(new CourseReg("0", "--Select Course--"));

        String tag_string_req = "req_login";
        showProgressDialog();

        String URL_LOGIN = null;

        try {
            URL_LOGIN = UTIL.Domain_Arduino + UTIL.CourseListRegisteration_API;
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
                    JSONArray jsonArray = jObj.getJSONArray("course");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String c_id = jsonObject.getString("c_id");
                        String course = jsonObject.getString("course");

                        list_Course.add(new CourseReg(c_id, course));

                    }

                    // ArrayAdapter<ArrayList<CourseReg>> aa = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_Course);

                    ArrayAdapter<ArrayList<CourseReg>> aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list_Course);
                    aa.setDropDownViewResource(R.layout.spinner_item);
                    spnr_course.setAdapter(aa);


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

    void SubjectListbyCourseApiCall(String cid) {

        list_Subject.clear();
        list_CourseDuration.clear();
        list_CourseDuration.add(new CourseReg("0","-Month-"));


        String tag_string_req = "req_login";
        showProgressDialog();

        String URL_LOGIN = null;

        try {
            URL_LOGIN = UTIL.Domain_Arduino + UTIL.SubjectListbyCourse_API + cid;
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
                    JSONArray jArray = jObj.getJSONArray("data");

                    JSONObject jsonObject = jArray.getJSONObject(0);
                    JSONArray jsonArray = jsonObject.getJSONArray("course");
                    JSONArray jsonArray_Dur = jsonObject.getJSONArray("course_duration");

                    if(jsonArray_Dur.length()>0) {
                        for (int i = 0; i < jsonArray_Dur.length(); i++) {
                            JSONObject jObject = jsonArray_Dur.getJSONObject(i);
                            String course_emonthn = jObject.getString("course_emonthn");
                            String mnthname = UTIL.monthName(course_emonthn);
                            list_CourseDuration.add(new CourseReg(course_emonthn, mnthname));
                        }

                    }
                    if(jsonArray.length()>0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            String p_id = jObject.getString("p_id");
                            String paper_name = jObject.getString("paper_name");
                            list_Subject.add(new SubjectReg(p_id, paper_name));
                        }
                    }
                    /*adding checkbox*/
                    ll_subject.removeAllViews();
                    for (int i = 0; i < list_Subject.size(); i++) {
                        CheckBox cb = new CheckBox(getActivity().getApplicationContext());
                        cb.setText(list_Subject.get(i).getpaper_name());
                        cb.setTag(list_Subject.get(i).getp_id());
                        cb.setChecked(true);
                        cb.setTextColor(getResources().getColor(R.color.primaryTextColor));
                        cb.setClickable(false);
                        ll_subject.addView(cb);
                    }


                    ArrayAdapter<ArrayList<CourseReg>> aa = new ArrayAdapter(getActivity(), R.layout.spinner_item, list_CourseDuration);
                    aa.setDropDownViewResource(R.layout.spinner_item);
                    spnr_session_mnth.setAdapter(aa);

                    spnr_session_mnth.setSelection(0);


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

}
