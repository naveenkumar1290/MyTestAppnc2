package com.cs.nks.easycouriers.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs.nks.easycouriers.R;
import com.cs.nks.easycouriers.util.UTIL;

/**
 * A simple {@link Fragment} subclass.
 */
public class Center_search_Fragment extends Fragment {


    public Center_search_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(UTIL.getTitle("Center Search"));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_center_search, container, false);
    }

}
