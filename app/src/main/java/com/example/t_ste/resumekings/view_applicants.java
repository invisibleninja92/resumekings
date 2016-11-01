package com.example.t_ste.resumekings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link view_applicants#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_applicants extends Fragment {
    ListView LL;
    ArrayList<applicantProfile> lTaskList;

    public view_applicants() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment view_applicants.
     */
    public static view_applicants newInstance(String param1, String param2) {
        view_applicants fragment = new view_applicants();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lTaskList = ((MainActivity)getActivity()).getTaskList(); //Local tasklist from Main Activity

        View view= inflater.inflate(R.layout.fragment_view_applicants, container, false);//Creates the view(Fragment)
        LL = (ListView)view.findViewById(R.id.listView);
        //we need to create an application adapter to create the elements in the list
        final ApplicantAdapter adapt = new ApplicantAdapter(view.getContext(),lTaskList);
        LL.setAdapter(adapt);//set the adapter of elements to listview
        //When the ListView Element is clicked we need to change the images
        LL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView ResumePic= (ImageView)((MainActivity)getActivity()).findViewById(R.id.imageView2);
                ResumePic.setImageBitmap(lTaskList.get(position).getProfilePicture());
                ImageView ProfilePic= (ImageView)((MainActivity)getActivity()).findViewById(R.id.imageView);
                ProfilePic.setImageBitmap(lTaskList.get(position).getProfilePicture());
            }
        });
        return view; //Return the fragment with all the functionality
    }


}
