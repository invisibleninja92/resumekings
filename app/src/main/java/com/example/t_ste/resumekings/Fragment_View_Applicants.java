package com.example.t_ste.resumekings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.t_ste.resumekings.R.id.Applicant_ListView;
import static com.example.t_ste.resumekings.R.id.SearchText;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Fragment_View_Applicants#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_View_Applicants extends Fragment {
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////
    public Fragment_View_Applicants() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment_View_Applicants newInstance() {
        return new Fragment_View_Applicants();
    }

    public String getName(){
        return "ViewApplicants";
    }
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////


    // INITIALIZERS //////////
    Applicant_Adapter adapt = null;
    ListView ApplicantCacheList;
    ArrayList<Applicant_Profile> cachedApplicantProfiles;
    EditText searchText;
    List<String> Names = null;
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_applicants, container, false);//Creates the view(Fragment)
        // the next lines are finding the elements that are inside the fragment to then set the listeners and things

        ApplicantCacheList = (ListView)view.findViewById(Applicant_ListView);
        cachedApplicantProfiles = ((MainActivity)getActivity()).getCachedApplicantProfiles(); //Local cache from Main Activity
        searchText = (EditText)view.findViewById(SearchText);
        ((MainActivity)getActivity()).setAddToBackStack(true);

        //we need to create an application adapter to create the elements in the list
        if(cachedApplicantProfiles.size() != 0) {
            //
            setAdapt(new Applicant_Adapter(getContext(), cachedApplicantProfiles));
            ApplicantCacheList.setAdapter(adapt); // set the adapter of elements to the list view of applicants
            Applicant_Profile temp = (Applicant_Profile)ApplicantCacheList.getAdapter().getItem(0);
            ((MainActivity) getActivity()).viewApplicant(temp);

            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence search_sequence, int start, int before, int count) {
                    // When user changed the Text
                    getAdapt().getFilter().filter(search_sequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }
        else {Toast.makeText(getContext(), "create an applicant!", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).setAddToBackStack(false);
            ((MainActivity)getActivity()).displayView("CreateNewApplicant");
        }

        //When the ListView Element is clicked we need to change the images
        ApplicantCacheList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).setAddToBackStack(false);
                Applicant_Profile temp = (Applicant_Profile)ApplicantCacheList.getAdapter().getItem(position);
                ((MainActivity)getActivity()).viewApplicant(temp);
            }
        });
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

    public Applicant_Adapter getAdapt() {
        return adapt;
    }

    public void setAdapt(Applicant_Adapter adapt) {
        this.adapt = adapt;
    }
}
