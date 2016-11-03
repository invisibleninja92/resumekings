package com.example.t_ste.resumekings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_View_Applicant} interface
 * to handle interaction events.
 * Use the {@link Fragment_View_Applicant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_View_Applicant extends Fragment {

    FragmentManager fm = getFragmentManager();
    public Fragment_View_Applicant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Fragment_View_Applicant.
     */
    public static Fragment_View_Applicant newInstance(String param1, String param2) {
        Fragment_View_Applicant fragment = new Fragment_View_Applicant();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__view__applicant, container, false);
    }

}
