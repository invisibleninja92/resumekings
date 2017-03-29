package com.example.t_ste.resumekings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Created by t_ste on 11/1/2016.
 * The ApplicantAdapter class the creates a new personitem object and places the converView into
 * The listview in the Fragment_view_applicants The adapter is important.
 */

public class Applicant_Adapter extends ArrayAdapter<Applicant_Profile> implements Filterable {
    List<Applicant_Profile> arrayList;
    List<Applicant_Profile> mOriginalValues; // Original Values
    Context AboveContexts;

    public Applicant_Adapter(Context context, ArrayList<Applicant_Profile> arrayList){
        super(context,0,arrayList);
        this.arrayList=arrayList;
        this.AboveContexts=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Applicant_Profile tempProfile = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.personitem, parent, false);
        }
        TextView UserName = (TextView) convertView.findViewById(R.id.UserName);
        TextView UserEmail = (TextView) convertView.findViewById(R.id.UserEmail);
        RatingBar RatingBar = (RatingBar) convertView.findViewById((R.id.RatingBar));

        UserName.setText(tempProfile.getUserName());
        UserEmail.setText(tempProfile.getEmail());
        RatingBar.setRating(tempProfile.getStars());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {


            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {
                arrayList.clear();
                arrayList.addAll((Collection<? extends Applicant_Profile>) results.values);
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<Applicant_Profile> FilteredArrList = new ArrayList<Applicant_Profile>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Applicant_Profile>(arrayList); // saves the original data in mOriginalValues
                }
                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getUserName();
                        String Number = mOriginalValues.get(i).getPhoneNumber();
                        String Email = mOriginalValues.get(i).getEmail();

                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(mOriginalValues.get(i));
                        }
                        else if(Number.toLowerCase().startsWith(constraint.toString())){
                            FilteredArrList.add(mOriginalValues.get(i));
                        }
                        else if(Email.toLowerCase().startsWith(constraint.toString())){
                            FilteredArrList.add(mOriginalValues.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }


}