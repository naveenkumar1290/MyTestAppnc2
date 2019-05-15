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
public class Subject_List_Fragment extends Fragment {


    public Subject_List_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(UTIL.getTitle("Subject List"));
        return inflater.inflate(R.layout.fragment_subject_list, container, false);
    }

}
