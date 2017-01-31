package com.example.t_ste.resumekings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
    Button deleteApplicant;
    Button updateApplicant;
    Button showResume;
    Applicant_Profile ap;
    EditText applicantName;
    EditText applicantEmail;
    EditText applicantNotes;
    EditText applicantPhone;
    RatingBar ratingBar;
    Boolean Update = false;
    ImageView ResumeImage;
    ImageView ProfileImage;
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_single_applicant, container, false);
        ap = ((MainActivity)getActivity()).getTempProfile();

        if(ap == null){
            return view;
        }

        deleteApplicant = (Button) view.findViewById(R.id.delete_applicant);
        updateApplicant = (Button) view.findViewById(R.id.update_applicant);
        showResume      = (Button) view.findViewById(R.id.show_resume);
        ratingBar       = (RatingBar) view.findViewById(R.id.ratingBar);

        applicantName  = (EditText) view.findViewById(R.id.applicantName);
        applicantPhone = (EditText) view.findViewById(R.id.applicantPhone);
        applicantEmail = (EditText) view.findViewById(R.id.applicantEmail);
        applicantNotes = (EditText) view.findViewById(R.id.applicantNotes);

        applicantEmail.setEnabled(false);
        applicantName.setEnabled(false);
        applicantNotes.setEnabled(false);
        applicantPhone.setEnabled(false);

        ResumeImage = (ImageView) view.findViewById(R.id.ResumePicture);
        ProfileImage = (ImageView) view.findViewById(R.id.ProfilePicture);

        // final Call_Web_API CWA = new Call_Web_API();


        new DownloadImageFromInternet(ProfileImage).execute(ap.getProfilePictureURL());
        new DownloadImageFromInternet(ResumeImage).execute(ap.getResumePictureURL());

        ratingBar.setRating(ap.getStars());
        applicantName.setText(ap.getUserName());
        applicantPhone.setText(ap.getPhoneNumber());
        applicantEmail.setText(ap.getEmail());
        applicantNotes.setText(ap.getNotes());

        deleteApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).removeFromCache(ap);
                 //CWA.doInBackground(ap,"Delete"); //Passes the SQL ID and calls the "Delete function
                ((MainActivity)getActivity()).setAddToBackStack(false);
                ((MainActivity)getActivity()).deleteApplicant = true;
                if(((MainActivity)getActivity()).cachedApplicantProfiles.size() != 0) {
                    ((MainActivity) getActivity()).viewApplicant(((MainActivity) getActivity()).cachedApplicantProfiles.get(0));
                }
                else {
                    Toast.makeText(getContext(), "create an applicant!", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).setAddToBackStack(false);
                    ((MainActivity) getActivity()).displayView("CreateNewApplicant");
                }
            }
        });

        updateApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!Update){
                    applicantName.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantName.setEnabled(true);
                    applicantPhone.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantPhone.setEnabled(true);
                    applicantEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantEmail.setEnabled(true);
                    applicantNotes.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantNotes.setEnabled(true);
                    ratingBar.setIsIndicator(false);
                    Update = true;
                    updateApplicant.setText("Update Applicant");
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
                    //CWA.doInBackground(ap,"Put"); //Updates the applicant in the web api
                }
            }
        });

        showResume.setOnClickListener(new View.OnClickListener(){
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


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        //TODO: clean this up UPDATE: NEVER
        //UPDATE: What needs to be cleaned up here? do you want the class to be placed in a different class file?

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}