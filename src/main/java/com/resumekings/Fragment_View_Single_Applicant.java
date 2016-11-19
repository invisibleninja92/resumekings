package com.resumekings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
    Button SaveApplicant;
    Button DeleteApplicant;
    Applicant_Profile ap;
    EditText applicantName;
    EditText applicantEmail;
    EditText applicantNotes;
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_single_applicant, container, false);
        ap = ((MainActivity)getActivity()).getTempProfile();
        ((MainActivity)getActivity()).setAddToBackStack(true);

        SaveApplicant = (Button) view.findViewById(R.id.save_applicant);
        DeleteApplicant = (Button) view.findViewById(R.id.delete_applicant);
        applicantName = (EditText) view.findViewById(R.id.applicantName);
        applicantEmail = (EditText) view.findViewById(R.id.applicantEmail);
        applicantNotes = (EditText) view.findViewById(R.id.applicantNotes);

        applicantName.setText(ap.getUserName());
        applicantEmail.setText(ap.getEmail());
        applicantNotes.setText(ap.getNotes());

        DeleteApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).removeFromCache(ap);
                ((MainActivity)getActivity()).setAddToBackStack(false);
                ((MainActivity)getActivity()).displayView("ViewApplicants");
            }
        });

        SaveApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).setAddToBackStack(false);
                ((MainActivity)getActivity()).displayView("ViewApplicants");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
