package com.example.t_ste.resumekings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gregorywilkinson on 11/10/16.
 */
public class Fragment_View_Applicant_Resume extends android.support.v4.app.Fragment {
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////
    public Fragment_View_Applicant_Resume() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getName(){
        return "ViewApplicantResume";
    }
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////


    // INITIALIZERS //////////
    Applicant_Profile ap;



    // INITIALIZERS //////////



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_applicant_resume, container, false);//Creates the view(Fragment)

        ((MainActivity)getActivity()).setAddToBackStack(false);
        ap = ((MainActivity)getActivity()).getTempProfile();
        //TODO: Trevor halp...idk how to get photos out of the applicant profile. this will show the reusme





        return view; //Return the fragment with all the functionality
    }
}
