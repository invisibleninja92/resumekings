package com.example.t_ste.resumekings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by t_ste on 11/1/2016.
 * The ApplicantAdapter class the creates a new personitem object and places the converView into
 * The listview in the Fragment_view_applicants The adapter is important.
 */

public class Applicant_Adapter extends ArrayAdapter<Applicant_Profile> {

    public Applicant_Adapter(Context context, ArrayList<Applicant_Profile> users){
        super(context,0,users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Applicant_Profile up = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.personitem, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.UserName);
        RatingBar RatingBar = (RatingBar) convertView.findViewById((R.id.RatingBar));

        name.setText(up.getUserName()+"\n"+up.getEmail());
        RatingBar.setRating(up.getStars());
        return convertView;
    }

}