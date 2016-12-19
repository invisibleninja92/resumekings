package com.example.t_ste.resumekings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_View_Single_Applicant} interface
 * to handle interaction events.
 * Use the {@link Fragment_View_Single_Applicant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_View_Single_Applicant extends Fragment {
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////
    public Fragment_View_Single_Applicant() {}

    public static Fragment_View_Single_Applicant newInstance() {
        return new Fragment_View_Single_Applicant();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getName(){
        return "ViewSingleApplicant";
    }
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////


    // INITIALIZERS //////////
    View view;
    Button SaveApplicant;
    Button DeleteApplicant;
    Button UpdateApplicant;
    Button ShowResume;
    Applicant_Profile ap;
    EditText applicantName;
    EditText applicantEmail;
    EditText applicantNotes;
    EditText applicantPhone;
    RatingBar ratingBar;
    Boolean Update = false;
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_single_applicant, container, false);
        ap = ((MainActivity)getActivity()).getTempProfile();

        SaveApplicant = (Button) view.findViewById(R.id.save_applicant);
        DeleteApplicant = (Button) view.findViewById(R.id.delete_applicant);
        UpdateApplicant = (Button) view.findViewById(R.id.update_applicant);
        ShowResume = (Button) view.findViewById(R.id.show_resume);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        applicantName = (EditText) view.findViewById(R.id.applicantName);
        applicantPhone = (EditText) view.findViewById(R.id.applicantPhone);
        applicantEmail = (EditText) view.findViewById(R.id.applicantEmail);
        applicantNotes = (EditText) view.findViewById(R.id.applicantNotes);

        ratingBar.setRating(ap.getStars());
        applicantName.setText(ap.getUserName());
        applicantPhone.setText(ap.getPhoneNumber());
        applicantEmail.setText(ap.getEmail());
        applicantNotes.setText(ap.getNotes());

        DeleteApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).removeFromCache(ap);
                // TODO: remove the applicant from the database with api call
                ((MainActivity)getActivity()).setAddToBackStack(false);
                ((MainActivity)getActivity()).deleteApplicant = true;
                ((MainActivity)getActivity()).viewApplicant(((MainActivity)getActivity()).cachedApplicantProfiles.get(0));
            }
        });

        SaveApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).setAddToBackStack(false);
                // TODO: add the applicant to S3 Database with api call
                ((MainActivity)getActivity()).displayView("ViewApplicants");
            }
        });

        UpdateApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!Update){
                    applicantName.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantName.setFocusable(true);
                    applicantPhone.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantPhone.setFocusable(true);
                    applicantEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantEmail.setFocusable(true);
                    applicantNotes.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantNotes.setFocusable(true);
                    ratingBar.setIsIndicator(false);
                    Update = true;
                    UpdateApplicant.setText("Update Applicant");
                }
                else {
                    Applicant_Profile temp = new Applicant_Profile();
                    temp.setUserName(applicantName.getText().toString());
                    temp.setEmail(applicantEmail.getText().toString());
                    temp.setPhoneNumber(applicantPhone.getText().toString());
                    temp.setNotes(applicantNotes.getText().toString());
                    temp.setStars((int)ratingBar.getRating());

                    ((MainActivity)getActivity()).updateCache(ap, temp);
                    ((MainActivity)getActivity()).setAddToBackStack(false);
                    ((MainActivity)getActivity()).viewApplicant(temp);
                }
            }
        });

        ShowResume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).setAddToBackStack(false);
                ((MainActivity)getActivity()).displayView("ViewApplicantResume");
            }
        });

        // Inflate the layout for this fragment
        return view;
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
}