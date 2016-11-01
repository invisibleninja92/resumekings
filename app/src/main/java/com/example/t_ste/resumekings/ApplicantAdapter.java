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
 */

public class ApplicantAdapter extends ArrayAdapter<applicantProfile> {
    public ApplicantAdapter(Context context, ArrayList<applicantProfile> users){
        super(context,0,users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        applicantProfile up = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.personitem, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.UserName);
        RatingBar rb = (RatingBar) convertView.findViewById((R.id.ratingBar));

        name.setText(up.getUserName()+"\n"+up.getEmail());
        rb.setRating(up.getStars());
        return convertView;
    }

}