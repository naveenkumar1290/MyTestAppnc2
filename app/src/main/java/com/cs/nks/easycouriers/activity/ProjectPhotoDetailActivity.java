package com.cs.nks.easycouriers.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.fragment.Course_List_Fragment;
import com.cs.nks.easycouriers.model.Center;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class ProjectPhotoDetailActivity extends AppCompatActivity {
    String status = "", Comment = "";

    String Client_id_Pk, comp_ID, jobID, FileId, dealerId;
    String commentFileShare = "", MailId = "";

    String fileExt;

    String FileName;
    private ProgressDialog mProgressDialog;

    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_photo_detail);


        setTitle(UTIL.getTitle("Details"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //  ProjectPhoto mPhoto = (ProjectPhoto) getIntent().getSerializableExtra("obj");
        FileId = getIntent().getStringExtra("FileId");

        CallApiProjectPhotoDetail();
    }

    private void CallApiProjectPhotoDetail() {
        if (new ConnectionDetector(ProjectPhotoDetailActivity.this).isConnectingToInternet()) {
             AboutUsApiCall();
        } else {
            Toast.makeText(ProjectPhotoDetailActivity.this,
                    UTIL.NoInternet, Toast.LENGTH_SHORT)
                    .show();   }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    void AboutUsApiCall() {


        String tag_string_req = "req_login";
        showProgressDialog();

        String URL_LOGIN = null;

        try {
            URL_LOGIN = UTIL.Domain_Arduino + UTIL.CourseList_API;
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
                    JSONArray jsonArray = jObj.getJSONArray("result");



                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String centre_name = jsonObject.getString("c_id");
                        String centre_address = jsonObject.getString("course");
                        String head_name = jsonObject.getString("course_code");
                        String admin_email = jsonObject.getString("course_duration");
                        String mobile = "";


                          setData();



                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(ProjectPhotoDetailActivity.this, "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProjectPhotoDetailActivity.this,
                        "Volley Error!", Toast.LENGTH_SHORT).show();
                hideProgressDialog();

            }
        }) {


        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void setData() {

//        TextView tv_file_name, tv_job_name,
//                tv_upload_dated, tv_last_action_dated, tv_status, tv_file_desc;
//        tv_file_name = findViewById(R.id.tv_file_name);
//        tv_job_name = findViewById(R.id.tv_job_name);
//        tv_upload_dated = findViewById(R.id.tv_upload_dated);
//        tv_last_action_dated = findViewById(R.id.tv_last_action_dated);
//        tv_status = findViewById(R.id.tv_status);
//        tv_file_desc = findViewById(R.id.tv_file_desc);
//        try {
//            jobID = mPhoto.getJobId() + "";
//
//
//            final String job = mPhoto.getJob();
//            String createdate = mPhoto.getCreatedate();
//            String lastActiondate = mPhoto.getDt();
//            final String Action_status = mPhoto.getActionStatus();
//            final String ImgName = mPhoto.getImgName();
//            final String Descr = mPhoto.getDescr();
//
//            if (ImgName == null || ImgName.trim().equals("")) {
//                tv_file_name.setText("Not available");
//            } else {
//                tv_file_name.setText(ImgName);
//            }
//
//            if (Descr == null || Descr.trim().equals("")) {
//                tv_file_desc.setText("Not available");
//            } else {
//                tv_file_desc.setText(Descr);
//            }
//
//            if (job == null || job.trim().equals("")) {
//                tv_job_name.setText("Not available");
//            } else {
//                tv_job_name.setText(job);
//            }
//
//            if (createdate == null || createdate.trim().equals("")) {
//                tv_upload_dated.setText("Not available");
//            } else {
//                if (createdate.contains("-")) {
//                    createdate = createdate.replaceAll("-", "/");
//                }
//                tv_upload_dated.setText(createdate);
//            }
//
//            if (lastActiondate == null || lastActiondate.trim().equals("")) {
//                tv_last_action_dated.setText("Not available");
//            } else {
//                if (lastActiondate.contains("-")) {
//                    lastActiondate = lastActiondate.replaceAll("-", "/");
//                }
//                if (lastActiondate.contains("T")) {
//                    lastActiondate = lastActiondate.substring(0, lastActiondate.indexOf("T"));
//                }
//                tv_last_action_dated.setText(lastActiondate);
//            }
//
//            if (Action_status == null || Action_status.trim().equals("")) {
//                tv_status.setText("Not available");
//            } else {
//                tv_status.setText(Action_status);
//
//                if (Action_status.equalsIgnoreCase("Rejected")) {
//                    tv_status.setTextColor(getResources().getColor(R.color.red));
//                } else if (Action_status.equalsIgnoreCase("Approved")) {
//                    tv_status.setTextColor(getResources().getColor(R.color.main_green_color));
//                } else if (Action_status.equalsIgnoreCase("Snoozed")) {
//                    tv_status.setTextColor(getResources().getColor(R.color.snoozed));
//                } else {
//                    tv_status.setTextColor(getResources().getColor(R.color.main_orange_light_color));
//                }
//
//            }
//        } catch (Exception e) {
//            e.getMessage();
//        }

    }



    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ProjectPhotoDetailActivity.this);
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
