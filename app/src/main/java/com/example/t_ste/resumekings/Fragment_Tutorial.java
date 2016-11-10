package com.example.t_ste.resumekings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Fragment_Tutorial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Tutorial extends Fragment{

    public Fragment_Tutorial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters
     * @return A new instance of Fragment_Tutorial.
     */
    public static Fragment_Tutorial newInstance() {
        return new Fragment_Tutorial();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    // The onCreateView is where you will create the fragment and all the listeners in the fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false); //create the view of the fragment
        // the next lines are finding the elements that are inside the fragment to then set the listeners and things

        return view; // This returns the view(Fragment) with all the initializers
    }

    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

    public String getName(){
        return "Tutorial";
    }
}
