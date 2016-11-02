package com.example.t_ste.resumekings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
 * Use the {@link create_new_applicant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class create_new_applicant extends Fragment {

    Button save;
    ImageView ProfilePic;
    ImageView ResumePic;
    EditText Name;
    EditText Email;
    EditText Phone;
    EditText Notes;
    RatingBar rb;
    Bitmap bp;
    Bitmap ProfilePicBit;
    Bitmap ResumePicBit;

    public create_new_applicant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters
     * @return A new instance of fragment create_new_applicant.
     */
    // TODO: Rename and change types and number of parameters
    public static create_new_applicant newInstance() {
        create_new_applicant fragment = new create_new_applicant();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override//The onCreateView is where you will create the fragment and all the listeners in the fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_create_new_applicant, container, false); //create the view of the fragment
    //the next lines are finding the elements that are inside the fragment to then set the listeners and things

        ProfilePic=(ImageView)view.findViewById(R.id.EditProfilePic);
        ResumePic=(ImageView)view.findViewById(R.id.EditResumePic);
        save= (Button) view.findViewById(R.id.saveButton);
        Name= (EditText)view.findViewById(R.id.EditName);
        Email= (EditText)view.findViewById(R.id.EditEmail);
        Phone= (EditText)view.findViewById(R.id.EditPhone);
        Notes= (EditText)view.findViewById(R.id.EditNotes);
        rb =(RatingBar)view.findViewById(R.id.EditRating);

        ProfilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0); //0 specifies the requestCode so the on activity result know what to do
            }
        });

        ResumePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);//1 specifies the requestCode so the on activity result know what to do
            }
        });

        //When we click save we use all the data in the edit text fields to create a new applicant profile
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Applicant_Profile ap = new Applicant_Profile();
                ap.setUserName(Name.getText().toString());
                ap.setPhoneNumber(Phone.getText().toString());
                ap.setEmail(Email.getText().toString());
                ap.setNotes(Notes.getText().toString());
                ap.setStars((int) rb.getRating());
                ap.setProfilePicture(ProfilePicBit);
                ap.setResumePicture(ResumePicBit);
                //We want to send it back to the mainActivity to do this we get the main activity and
                // call the setTaskListFunction then call the displayView to go back to the main screen.
                ((MainActivity)getActivity()).setTaskList(ap);
                ((MainActivity)getActivity()).displayView(R.id.View_Recent_Applicants);
            }
        });
        return view; //This returns the view(Fragment) with all the initializers

    }
    //Since we start a camera activity we need to get the results of that this function
    //handles the camera process
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        bp = (Bitmap) data.getExtras().get("data");
        switch(requestCode){
            case 0: //if the requestCode was 0 the user took a profile picture
                ProfilePic.setImageBitmap(bp);
                ProfilePicBit = bp;
                break;
            case 1: //if the requestCode was 1 the user took a Resume picture
                ResumePic.setImageBitmap(bp);
                ResumePicBit=bp;
                break;
        }
    }



}
