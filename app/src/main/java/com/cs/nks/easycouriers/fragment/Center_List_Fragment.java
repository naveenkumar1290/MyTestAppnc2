package com.cs.nks.easycouriers.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.cs.nks.easycouriers.activity.ActivityWithNavigationMenu;
import com.cs.nks.easycouriers.model.Center;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Center_List_Fragment extends Fragment {
    ArrayList<Center> list_ProjectPhotos = new ArrayList<>();
    Context myContext;
    UTIL utill;
    TextView tv_msg;
    Context context;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_center_list, container, false);
getActivity().setTitle(UTIL.getTitle("Center List"));
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        tv_msg = rootView.findViewById(R.id.tv_msg);
        context = getActivity();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        callApiProjectPhotos();
        // mSwipeRefreshLayout.setRefreshing(true);
    }

    private void callApiProjectPhotos() {
        if (new ConnectionDetector(context).isConnectingToInternet()) {
            AboutUsApiCall();
        } else {
            Toast.makeText(myContext,
                    UTIL.NoInternet, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void AboutUsApiCall() {

        list_ProjectPhotos.clear();
        String tag_string_req = "req_login";
        showProgressDialog();

        String URL_LOGIN = null;

        try {
            URL_LOGIN = UTIL.Domain_Arduino + UTIL.CenterList_API;
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

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String centre_name = jsonObject.getString("centre_name");
                        String centre_address = jsonObject.getString("centre_address");
                        String head_name = jsonObject.getString("head_name");
                        String admin_email = jsonObject.getString("admin_email");
                        String mobile = jsonObject.getString("mobile");

                        list_ProjectPhotos.add(new Center(centre_name, centre_address, head_name, admin_email, mobile));

                    }
                    if (list_ProjectPhotos.size() < 1) {
                        tv_msg.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tv_msg.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        mAdapter = new MoviesAdapter(getActivity(), list_ProjectPhotos);
                        recyclerView.setAdapter(mAdapter);


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


    public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

        private ArrayList<Center> moviesList;


        public MoviesAdapter(Activity context, ArrayList<Center> moviesList) {
            this.moviesList = moviesList;
                }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_client_clientfiles, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            Center projectPhoto = moviesList.get(position);

            holder.index_no.setText(String.valueOf(position + 1));


            final String CentreName = projectPhoto.getCentreName();
            final String CentreAddress = projectPhoto.getCentreAddress();
            final String HeadName = projectPhoto.getHeadName();
            String mail = projectPhoto.getAdminEmail();
            String Mobile = projectPhoto.getMobile();


            if (CentreName == null || CentreName.trim().equals("")) {
                holder.tv_file_name.setText("Not available");
            } else {
                holder.tv_file_name.setText(CentreName);
            }
            if (CentreAddress == null || CentreAddress.trim().equals("")) {
                holder.tv_job_name.setText("Not available");
            } else {
                holder.tv_job_name.setText(CentreAddress);
            }

            if (HeadName == null || HeadName.trim().equals("")) {
                holder.tv_dated.setText("Not available");
            } else {
                holder.tv_dated.setText(HeadName);

            }


            if (mail == null || mail.trim().equals("")) {
                holder.tv_status.setText("Not available");
            } else {

                holder.tv_status.setText(mail);

            }

            if (Mobile == null || Mobile.trim().equals("")) {
                holder.tv_mob.setText("Not available");
            } else {

                holder.tv_mob.setText(Mobile);

            }



        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_file_name, tv_job_name, tv_dated, tv_status, tv_status_heading,tv_mob;
            ImageView thumbnail;
            Button index_no;
            LinearLayout parentView;

            ProgressBar spinner;

            public MyViewHolder(View view) {
                super(view);

                index_no = (Button) view.findViewById(R.id.serial_no);
                tv_file_name = (TextView) view.findViewById(R.id.tv_file_name);
                tv_job_name = (TextView) view.findViewById(R.id.tv_job_name);
                tv_dated = (TextView) view.findViewById(R.id.tv_dated);
                tv_status = (TextView) view.findViewById(R.id.tv_status);
                parentView = (LinearLayout) view.findViewById(R.id.parentView);
                tv_status_heading = (TextView) view.findViewById(R.id.tv_status_heading);
                tv_mob = (TextView) view.findViewById(R.id.tv_mob);


            }
        }
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
