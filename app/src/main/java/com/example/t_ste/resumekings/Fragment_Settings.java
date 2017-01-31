package com.example.t_ste.resumekings;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by gregorywilkinson on 11/10/16.
 */
public class Fragment_Settings extends Fragment {
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////
    public Fragment_Settings() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment_Settings newInstance() {
        return new Fragment_Settings();
    }

    public String getName(){
        return "Settings";
    }
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////


    // INITIALIZERS //////////
    EditText userID = null;
    EditText password = null;
    String hashedPass = null;

    // INITIALIZERS //////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);//Creates the view(Fragment)
        // the next lines are finding the elements that are inside the fragment to then set the listeners and things

        ((MainActivity)getActivity()).setAddToBackStack(true);
        // TODO: add in two EditTexts to take in both a username and password
        // this can be used for mysql access and user authentication either in IAM or in mysql

        return view; //Return the fragment with all the functionality
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    getActivity().onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

    private String passHash(String userToPass){
        // change this to something else
        String hashedPass = userToPass;
        // TODO: create hash processor to pass to main

        ((MainActivity)getActivity()).setUserandPass(userToPass, hashedPass);
        return hashedPass;
    }
}
