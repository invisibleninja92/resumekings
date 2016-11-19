package com.example.t_ste.resumekings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
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
    // INITIALIZERS //////////





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_applicants, container, false);//Creates the view(Fragment)
        // the next lines are finding the elements that are inside the fragment to then set the listeners and things

        ApplicantCacheList = (ListView)view.findViewById(Applicant_ListView);
        cachedApplicantProfiles = ((MainActivity)getActivity()).getCachedApplicantProfiles(); //Local cache from Main Activity

        //we need to create an application adapter to create the elements in the list
        final Applicant_Adapter adapt = new Applicant_Adapter(view.getContext(),cachedApplicantProfiles);
        ApplicantCacheList.setAdapter(adapt);//set the adapter of elements to listview

        //When the ListView Element is clicked we need to change the images
        ApplicantCacheList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView ResumePic= (ImageView)getActivity().findViewById(R.id.imageView2);
                ResumePic.setImageBitmap(cachedApplicantProfiles.get(position).getProfilePicture());
                ImageView ProfilePic= (ImageView)getActivity().findViewById(R.id.Resume_Applicant_ImageView);
                ProfilePic.setImageBitmap(cachedApplicantProfiles.get(position).getProfilePicture());

                ((MainActivity)getActivity()).setAddToBackStack(true);
                ((MainActivity)getActivity()).viewApplicant(cachedApplicantProfiles.get(position));
            }
        });
        return view; //Return the fragment with all the functionality
    }
}
