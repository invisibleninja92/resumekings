package com.example.t_ste.resumekings;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.t_ste.resumekings.R.id.Applicant_ListView;

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
    ListView ApplicantCacheList;
    ArrayList<Applicant_Profile> cachedApplicantProfiles;
    SearchView inputSearch;
    List<String> Names = null;
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_applicants, container, false);//Creates the view(Fragment)
        // the next lines are finding the elements that are inside the fragment to then set the listeners and things

        ApplicantCacheList = (ListView)view.findViewById(Applicant_ListView);
        cachedApplicantProfiles = ((MainActivity)getActivity()).getCachedApplicantProfiles(); //Local cache from Main Activity
        ((MainActivity)getActivity()).setAddToBackStack(true);
        Names = ((MainActivity)getActivity()).getNameList();

        inputSearch = (SearchView)view.findViewById(R.id.search);
        CharSequence inputSearchQuery = inputSearch.getQuery();

        // TODO implement the result list of applicant profiles
        ArrayList<Applicant_Profile> searchResult = ((MainActivity)getActivity()).search(inputSearchQuery.toString());

        //we need to create an application adapter to create the elements in the list
        if(cachedApplicantProfiles.size() != 0) {
            final Applicant_Adapter adapt = new Applicant_Adapter(getContext(), cachedApplicantProfiles);
            ApplicantCacheList.setAdapter(adapt); // set the adapter of elements to the list view of applicants
        }
        else {Toast.makeText(getContext(), "create an applicant!", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).setAddToBackStack(false);
            ((MainActivity)getActivity()).displayView("CreateNewApplicant");
        }

        // TODO ?
//        if(!(ApplicantCacheList == null)) {
//            final Applicant_Adapter adapt = new Applicant_Adapter(view.getContext(), cachedApplicantProfiles);
//            ApplicantCacheList.setAdapter(adapt);//set the adapter of elements to listview
//        } else { Toast.makeText(getContext(), "Please create a new applicant profile first!", Toast.LENGTH_SHORT).show(); }

        //When the ListView Element is clicked we need to change the images
        ApplicantCacheList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ((MainActivity)getActivity()).setAddToBackStack(false);
               ((MainActivity)getActivity()).viewApplicant(cachedApplicantProfiles.get(position));
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
}
