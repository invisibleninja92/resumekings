package com.example.t_ste.resumekings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
    ListView ListViewCache;
    ArrayList<Applicant_Profile> lTaskList;

    public Fragment_View_Applicants() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment view_applicants.
     */
    public static Fragment_View_Applicants newInstance(String param1, String param2) {
        Fragment_View_Applicants fragment = new Fragment_View_Applicants();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_applicants, container, false);//Creates the view(Fragment)

//        EditText searchText = (EditText)view.findViewById(R.id.search);
  //      Toast.makeText(getContext(), "eventually this will search " + searchText, Toast.LENGTH_SHORT).show();

        ListViewCache = (ListView)view.findViewById(Applicant_ListView);
        lTaskList = ((MainActivity)getActivity()).getTaskList(); //Local tasklist from Main Activity

        //we need to create an application adapter to create the elements in the list
        final Applicant_Adapter adapt = new Applicant_Adapter(view.getContext(),lTaskList);
        ListViewCache.setAdapter(adapt);//set the adapter of elements to listview

        //When the ListView Element is clicked we need to change the images
        ListViewCache.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).viewApplicant(lTaskList.get(position));

                ImageView ResumePic= (ImageView)getActivity().findViewById(R.id.imageView2);
                ResumePic.setImageBitmap(lTaskList.get(position).getProfilePicture());
                ImageView ProfilePic= (ImageView)getActivity().findViewById(R.id.Resume_Applicant_ImageView);
                ProfilePic.setImageBitmap(lTaskList.get(position).getProfilePicture());
            }
        });

        return view; //Return the fragment with all the functionality
    }

    public String getName(){
        return "ViewApplicants";
    }

}
