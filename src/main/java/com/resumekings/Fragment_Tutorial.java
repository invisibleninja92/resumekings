package com.resumekings;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Fragment_Tutorial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Tutorial extends Fragment {
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////
    public Fragment_Tutorial() {}

    public String getName(){
        return "Tutorial";
    }

    public static Fragment_Tutorial newInstance() {
        return new Fragment_Tutorial();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////


    // INITIALIZERS //////////


    // INITIALIZERS //////////

    @Override
    // The onCreateView is where you will create the fragment and all the listeners in the fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false); //create the view of the fragment
        // the next lines are finding the elements that are inside the fragment to then set the listeners and things

        return view; // This returns the view(Fragment) with all the initializers
    }
}
