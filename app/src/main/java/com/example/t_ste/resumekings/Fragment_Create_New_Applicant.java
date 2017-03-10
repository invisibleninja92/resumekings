package com.example.t_ste.resumekings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
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
 * Use the {@link Fragment_Create_New_Applicant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Create_New_Applicant extends Fragment {
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////
    public Fragment_Create_New_Applicant() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment_Create_New_Applicant newInstance() {
        return new Fragment_Create_New_Applicant();
    }

    public String getName(){
        return "CreateNewApplicant";
    }
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////


    // INITIALIZERS //////////
    Button SaveButton;
    ImageView ProfilePic;
    ImageView ResumePic;
    ImageView ResumeOverlayPic;
    EditText Name;
    EditText Email;
    EditText Phone;
    EditText Notes;
    RatingBar RatingBar;
    Bitmap bitmap;
    Bitmap ProfilePicBitmap;
    Bitmap ResumePicBitmap;
    Bitmap ResumeOverlayPicBitmap;
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_create_new_applicant, container, false); //create the view of the fragment
        // the next lines are finding the elements that are inside the fragment to then set the listeners and things

        ((MainActivity)getActivity()).setAddToBackStack(true);

        ProfilePic = (ImageView)view.findViewById(R.id.EditProfilePic);
        ResumePic = (ImageView)view.findViewById(R.id.EditResumePic);
        //TODO: add this to the xml
        //ResumeOverlayPic = (ImageView)view.findViewById(R.id.EditResumeOverlay);
        SaveButton = (Button) view.findViewById(R.id.saveButton);
        Name = (EditText)view.findViewById(R.id.EditName);
        Email = (EditText)view.findViewById(R.id.EditEmail);
        Phone = (EditText)view.findViewById(R.id.EditPhone);
        Notes = (EditText)view.findViewById(R.id.EditNotes);
        RatingBar = (RatingBar)view.findViewById(R.id.EditRating);

        ProfilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 0); // 0 specifies the requestCode so the on activity result know what to do
            }
        });

        ResumePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
            }
        });

        // When we click save we use all the data in the edit text fields to create a new applicant profile
        SaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){

                if(isEmpty(Name)){
                    Name.setBackgroundResource(R.drawable.backtext);
                    Name.setHintTextColor(Color.RED);
                }
                else if(isEmpty(Phone)){
                    Phone.setBackgroundResource(R.drawable.backtext);
                    Phone.setHintTextColor(Color.RED);
                }
                else if(isEmpty(Email)){
                    Email.setBackgroundResource(R.drawable.backtext);
                    Email.setHintTextColor(Color.RED);
                }
                else if(isEmpty(Notes)){
                    Notes.setBackgroundResource(R.drawable.backtext);
                    Notes.setHintTextColor(Color.RED);
                }
                else{
                Applicant_Profile ap = new Applicant_Profile();
                ap.setUserName(Name.getText().toString());
                ap.setPhoneNumber(Phone.getText().toString());
                ap.setEmail(Email.getText().toString());
                ap.setNotes(Notes.getText().toString());
                ap.setStars((int) RatingBar.getRating());
                ap.setProfilePicture(ProfilePicBitmap);
                ap.setResumePicture(ResumePicBitmap);
                // ap.setResumeOverlay(ResumeOverlayBitmap);

                if(((MainActivity) getActivity()).API_Mode) {
                    Call_Web_API CWA = new Call_Web_API();
                    CWA.doInBackground(ap, "Post");
                }

                // We want to send it back to the mainActivity to do this we get the main activity and
                // call the setTaskListFunction then call the displayView to go back to the main screen.
                ((MainActivity)getActivity()).addToCache(ap);
                ((MainActivity)getActivity()).setAddToBackStack(false);
                ((MainActivity)getActivity()).viewApplicant(ap);}
            }
        });
        return view; // This returns the view(Fragment) with all the initializers

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
                ProfilePic.setImageBitmap(bitmap);
                ProfilePicBitmap = bitmap;
                break;

            case 1: //if the requestCode was 1 the user took a Resume picture
                ResumePic.setImageBitmap(bitmap);
                ResumePicBitmap = bitmap;
                break;
        }
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
