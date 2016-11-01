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
 * {@link create_new_applicant.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link create_new_applicant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class create_new_applicant extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public create_new_applicant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment create_new_applicant.
     */
    // TODO: Rename and change types and number of parameters
    public static create_new_applicant newInstance(String param1, String param2) {
        create_new_applicant fragment = new create_new_applicant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_create_new_applicant, container, false);
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


        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                applicantProfile ap = new applicantProfile();
                ap.setUserName(Name.getText().toString());
                ap.setPhoneNumber(Phone.getText().toString());
                ap.setEmail(Email.getText().toString());
                ap.setNotes(Notes.getText().toString());
                ap.setStars((int) rb.getRating());
                ap.setProfilePicture(ProfilePicBit);
                ap.setResumePicture(ResumePicBit);
                ((MainActivity)getActivity()).setTaskList(ap);
                ((MainActivity)getActivity()).displayView(R.id.View_Recent_Applicants);
            }
        });
        return view;

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        bp = (Bitmap) data.getExtras().get("data");
        switch(requestCode){
            case 0:
                ProfilePic.setImageBitmap(bp);
                ProfilePicBit = bp;
                break;
            case 1:
                ResumePic.setImageBitmap(bp);
                ResumePicBit=bp;
                break;
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
