package com.cs.nks.easycouriers.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.cs.nks.easycouriers.activity.FullscreenWebView;
import com.cs.nks.easycouriers.model.Center;
import com.cs.nks.easycouriers.model.Material;
import com.cs.nks.easycouriers.model.Subject;
import com.cs.nks.easycouriers.model.SubjectReg;
import com.cs.nks.easycouriers.util.AppController;
import com.cs.nks.easycouriers.util.ConnectionDetector;
import com.cs.nks.easycouriers.util.UTIL;
import com.payu.magicretry.Helpers.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseMaterial_List_Fragment extends Fragment {
    //ArrayList<Center> list_ProjectPhotos = new ArrayList<>();
    Context myContext;
    UTIL utill;
    TextView tv_msg;
    Context context;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    ArrayList<Subject> list_Subject = new ArrayList<>();
    ArrayList<Material> list_Material = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_course_material_list, container, false);
        getActivity().setTitle(UTIL.getTitle("Course Material List"));
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        tv_msg = rootView.findViewById(R.id.tv_msg);
        context = getActivity();
        myContext= getActivity();
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
            CourseMaterialApiCall();
        } else {
            Toast.makeText(myContext,
                    UTIL.NoInternet, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void CourseMaterialApiCall() {

        list_Subject.clear();
        list_Material.clear();
        String tag_string_req = "req_login";
        showProgressDialog();

        String URL_LOGIN = null;
        String uid = UTIL.getPref(getActivity(), UTIL.Key_UserId);
         uid = "10001";
        try {
            URL_LOGIN = UTIL.Domain_Arduino + UTIL.CourseMaterial_API + uid;
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
                    JSONArray jsonArray = jObj.getJSONArray("data");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);


                    JSONArray jsonArray1 = jsonObject.getJSONArray("subject_list");
                    JSONArray jsonArray2 = jsonObject.getJSONArray("meterial_list");

                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject3 = jsonArray2.getJSONObject(i);
                        String sub_id = jsonObject3.getString("sub_id");
                        String met_name = jsonObject3.getString("met_name");

                        list_Material.add(new Material(sub_id, met_name));

                    }

                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject3 = jsonArray1.getJSONObject(i);
                        String p_id = jsonObject3.getString("p_id");
                        String paper_name = jsonObject3.getString("paper_name");
                        String paper_code = jsonObject3.getString("paper_code");

                        for (int j = 0; j < list_Material.size(); j++) {
                            String materialId = list_Material.get(j).getp_id();
                            if (p_id.equals(materialId)) {
                                list_Subject.add(new Subject(p_id, paper_name, paper_code,list_Material.get(j).getpaper_name()));
                            break;
                            }
                            else if(j==list_Material.size()-1){
                                list_Subject.add(new Subject(p_id, paper_name, paper_code,""));

                            }

                        }


                    }


                    if (list_Subject.size() < 1) {
                        tv_msg.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tv_msg.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        mAdapter = new MoviesAdapter(getActivity(), list_Subject);
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

        private ArrayList<Subject> moviesList;


        public MoviesAdapter(Activity context, ArrayList<Subject> moviesList) {
            this.moviesList = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_course_material, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder,final int position) {

            Subject projectPhoto = moviesList.get(position);

            holder.index_no.setText(String.valueOf(position + 1));


            final String p_id = projectPhoto.getp_id();
            final String paper_name = projectPhoto.getpaper_name();
            final String paper_code = projectPhoto.getpaper_code();
             String material = projectPhoto.getmaterial();


            if (paper_name == null || paper_name.trim().equals("")) {
                holder.tv_file_name.setText("Not available");
            } else {
                holder.tv_file_name.setText(paper_name);
            }
            if (paper_code == null || paper_code.trim().equals("")) {
                holder.tv_job_name.setText("Not available");
            } else {
                holder.tv_job_name.setText(paper_code);
            }
            if (material == null || material.trim().equals("")) {
                holder.tv_dated.setText("Not available");
            } else {
                material=material.substring(material.lastIndexOf("/")+1);
                SpannableString content = new SpannableString(material);
                content.setSpan(new UnderlineSpan(), 0, material.length(), 0);
                holder.tv_dated.setText(content);
                holder.tv_dated.setTextColor(getResources().getColor(R.color.green));

            }

            final String url = UTIL.Domain_Arduino +"/"+  moviesList.get(position).getmaterial();

            holder.tv_dated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!moviesList.get(position).getmaterial().equals("")){

                        Intent i = new Intent(getActivity(), FullscreenWebView.class);
                        i.putExtra("url", url);
                        startActivity(i);
                    }
                }
            });






        }

        @Override
        public int getItemCount() {
            return moviesList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv_file_name, tv_job_name, tv_dated, tv_status, tv_status_heading, tv_mob;
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
