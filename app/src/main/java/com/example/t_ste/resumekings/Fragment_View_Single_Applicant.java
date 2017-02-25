package com.example.t_ste.resumekings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import java.io.InputStream;

import static com.example.t_ste.resumekings.R.attr.editTextBackground;

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
    Button viewResume;
    Applicant_Profile ap;
    EditText applicantName;
    EditText applicantEmail;
    EditText applicantNotes;
    EditText applicantPhone;
    RatingBar ratingBar;
    Boolean Update = false;
    ImageView ResumeImage;
    ImageView ProfileImage;
    Bitmap bitmap;
    Bitmap ProfilePicBitmap;
    Bitmap ResumePicBitmap;
    ImageView ResumeOverlay;
    final Call_Web_API CWA = new Call_Web_API();
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_single_applicant, container, false);
        ap = ((MainActivity)getActivity()).getTempProfile();

        if(ap == null) return view;

        // Attach the buttons and image views of the different parts of the front end to the back end
        deleteApplicant = (Button)    view.findViewById(R.id.delete_applicant);
        updateApplicant = (Button)    view.findViewById(R.id.update_applicant);
        viewResume      = (Button)    view.findViewById(R.id.show_resume);
        ratingBar       = (RatingBar) view.findViewById(R.id.ratingBar);
        ResumeImage     = (ImageView) view.findViewById(R.id.ResumePicture);
        ProfileImage    = (ImageView) view.findViewById(R.id.ProfilePicture);

        applicantName   = (EditText) view.findViewById(R.id.applicantName);
        applicantPhone  = (EditText) view.findViewById(R.id.applicantPhone);
        applicantEmail  = (EditText) view.findViewById(R.id.applicantEmail);
        applicantNotes  = (EditText) view.findViewById(R.id.applicantNotes);

        applicantEmail.setEnabled(false);
        applicantName.setEnabled(false);
        applicantNotes.setEnabled(false);
        applicantPhone.setEnabled(false);

        ResumeImage = (ImageView) view.findViewById(R.id.ResumePicture);
        ProfileImage = (ImageView) view.findViewById(R.id.ProfilePicture);
        ResumeOverlay = (ImageView) view.findViewById(R.id.ResumeOverlay);


        System.out.println(ap.getProfilePictureURL());
        System.out.println(ap.getResumePictureURL());

        final Call_Web_API CWA = new Call_Web_API();
        applicantName.setBackgroundColor(0);
        applicantEmail.setBackgroundColor(0);
        applicantNotes.setBackgroundColor(0);
        applicantPhone.setBackgroundColor(0);

        if(ap.getProfilePictureURL() != null) {
            new DownloadImageFromInternet(ProfileImage).execute(ap.getProfilePictureURL());
        }
        if(ap.getResumePictureURL() != null) {
            new DownloadImageFromInternet(ResumeImage).execute(ap.getResumePictureURL());
        }
        if(ap.getResumeOverlayURL() != null) {
            new DownloadImageFromInternet(ResumeOverlay).execute(ap.getResumeOverlayURL());
        }

        ratingBar.setRating(ap.getStars());
        applicantName.setText(ap.getUserName());
        applicantPhone.setText(ap.getPhoneNumber());
        applicantEmail.setText(ap.getEmail());
        applicantNotes.setText(ap.getNotes());

        deleteApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).removeFromCache(ap);

                if (((MainActivity)getActivity()).API_Mode) {
                    CWA.doInBackground(ap, "Delete"); //Passes the SQL ID and calls the "Delete function
                }

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
                    applicantName.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.editbox_background));
                    applicantPhone.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantPhone.setEnabled(true);
                    applicantPhone.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.editbox_background));
                    applicantEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantEmail.setEnabled(true);
                    applicantEmail.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.editbox_background));
                    applicantNotes.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantNotes.setEnabled(true);
                    applicantNotes.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.editbox_background));
                    ratingBar.setIsIndicator(false);
                    Update = true;
                    System.out.println(ap.getResumePictureURL());
                    updateApplicant.setText("Update Applicant");
                }
                else {
                    Applicant_Profile temp = new Applicant_Profile();
                    temp.setID(ap.getID());
                    temp.setUserName(applicantName.getText().toString());
                    temp.setEmail(applicantEmail.getText().toString());
                    temp.setPhoneNumber(applicantPhone.getText().toString());
                    temp.setNotes(applicantNotes.getText().toString());
                    temp.setStars((int)ratingBar.getRating());
                    temp.setProfilePictureURL(ap.getProfilePictureURL());
                    temp.setResumePictureURL(ap.getResumePictureURL());
                    temp.setResumeOverlayURL(ap.getResumeOverlayURL());

                    ((MainActivity)getActivity()).updateCache(ap, temp);
                    ((MainActivity)getActivity()).setAddToBackStack(false);
                    ((MainActivity)getActivity()).viewApplicant(temp);
                    CWA.doInBackground(temp,"Put"); //Updates the applicant in the web api
                }
            }
        });

        viewResume.setOnClickListener(new View.OnClickListener(){
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

    // Since we start a camera activity we need to get the results of that this function
    // handles the camera process
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            bitmap = (Bitmap) data.getExtras().get("data");
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        switch(requestCode){
            case 0: //if the requestCode was 0 the user took a profile picture
                ProfileImage.setImageBitmap(bitmap);
                ProfilePicBitmap = bitmap;
                break;

            case 1: //if the requestCode was 1 the user took a Resume picture
                ResumeImage.setImageBitmap(bitmap);
                ResumePicBitmap = bitmap;
                break;
        }
    }
}