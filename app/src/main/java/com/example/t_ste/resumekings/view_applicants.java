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
 * {@link view_applicants.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link view_applicants#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_applicants extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public view_applicants() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment view_applicants.
     */
    // TODO: Rename and change types and number of parameters
    public static view_applicants newInstance(String param1, String param2) {
        view_applicants fragment = new view_applicants();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ArrayList<applicantProfile> lTaskList = ((MainActivity)getActivity()).getTaskList();
        View view= inflater.inflate(R.layout.fragment_view_applicants, container, false);
        ListView LL = (ListView)view.findViewById(R.id.listView);
        final ApplicantAdapter adapt = new ApplicantAdapter(view.getContext(),lTaskList);
        LL.setAdapter(adapt);

        LL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView ResumePic= (ImageView)view.findViewById(R.id.imageView2);
                ResumePic.setImageBitmap(lTaskList.get(position).getResumePicture());
                ImageView ProfilePic= (ImageView)view.findViewById(R.id.imageView);
                ResumePic.setImageBitmap(lTaskList.get(position).getProfilePicture());
            }
        });
        return view;
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
        public void onNavFragmentInteraction(String string);
        void onFragmentInteraction(Uri uri);
    }
}
