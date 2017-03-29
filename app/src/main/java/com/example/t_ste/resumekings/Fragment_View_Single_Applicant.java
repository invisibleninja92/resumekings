package com.example.t_ste.resumekings;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Paint;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.text.InputType;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.Toast;
        import java.io.InputStream;

        import static android.graphics.Color.BLUE;


/**
 * A simple {@link Fragment_View_Single_Applicant} subclass.
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
    View view;
    Button deleteApplicant;
    Button updateApplicant;
    Button viewResume;
    Applicant_Profile ap;
    EditText applicantName;
    EditText applicantEmail;
    EditText applicantNotes;
    EditText applicantPhone;
    RatingBar ratingBar;
    Boolean Update = false;
    ImageView ResumeImage;
    ImageView ProfileImage;
    Bitmap bitmap;
    Bitmap ProfilePicBitmap;
    Bitmap ResumePicBitmap;
    ImageView ResumeOverlay;
    final Call_Web_API CWA = new Call_Web_API();
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_single_applicant, container, false);
        ap = ((MainActivity)getActivity()).getTempProfile();

        if(ap == null) return view;

        // Attach the buttons and image views of the different parts of the front end to the back end
        deleteApplicant = (Button)    view.findViewById(R.id.delete_applicant);
        updateApplicant = (Button)    view.findViewById(R.id.update_applicant);
        viewResume      = (Button)    view.findViewById(R.id.show_resume);
        ratingBar       = (RatingBar) view.findViewById(R.id.ratingBar);
        ResumeImage     = (ImageView) view.findViewById(R.id.ResumePicture);
        ProfileImage    = (ImageView) view.findViewById(R.id.ProfilePicture);
        // TODO: add the resume overlay to the xml and then uncomment the few lines here to implement it
        // ResumeOverlay = (ImageView) view.findViewById(R.id.ResumeOverlay);

        applicantName   = (EditText) view.findViewById(R.id.applicantName);
        applicantPhone  = (EditText) view.findViewById(R.id.applicantPhone);
        applicantEmail  = (EditText) view.findViewById(R.id.applicantEmail);
        applicantNotes  = (EditText) view.findViewById(R.id.applicantNotes);

        applicantEmail.setFocusable(false);
        applicantName.setFocusable(false);
        applicantNotes.setFocusable(false);
        applicantPhone.setFocusable(false);

        applicantEmail.setTextColor(BLUE);
        applicantPhone.setTextColor(BLUE);
        applicantEmail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        applicantPhone.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        ResumeImage = (ImageView) view.findViewById(R.id.ResumePicture);
        ProfileImage = (ImageView) view.findViewById(R.id.ProfilePicture);
        ResumeOverlay = (ImageView) view.findViewById(R.id.ResumeOverlay);

//        System.out.println(ap.getProfilePictureURL());
//        System.out.println(ap.getResumePictureURL());

        if(((MainActivity)getActivity()).API_Mode) {
            final Call_Web_API CWA = new Call_Web_API();
        }

        // Set the background of the
        applicantName.setBackgroundColor(0);
        applicantEmail.setBackgroundColor(0);
        applicantNotes.setBackgroundColor(0);
        applicantPhone.setBackgroundColor(0);

        if(ap.getProfilePictureURL() != null) {
            new DownloadImageFromInternet(ProfileImage).execute(ap.getProfilePictureURL());
        }

        if(ap.getResumePictureURL() != null) {
            new DownloadImageFromInternet(ResumeImage).execute(ap.getResumePictureURL());
        }

        if(ap.getResumeOverlayURL() != null) {
            new DownloadImageFromInternet(ResumeOverlay).execute(ap.getResumeOverlayURL());
        }

        ratingBar.setRating(ap.getStars());
        applicantName.setText(ap.getUserName());
        applicantPhone.setText(ap.getPhoneNumber());
        applicantEmail.setText(ap.getEmail());
        applicantNotes.setText(ap.getNotes());

        deleteApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).removeFromCache(ap);

                if (((MainActivity)getActivity()).API_Mode) {
                    CWA.doInBackground(ap, "Delete"); //Passes the SQL ID and calls the "Delete function
                }

                if(((MainActivity)getActivity()).cachedApplicantProfiles.size() != 0) {
                    ((MainActivity) getActivity()).viewApplicant(((MainActivity) getActivity()).cachedApplicantProfiles.get(0));
                }

                else {
                    Toast.makeText(getContext(), "create an applicant!", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).setAddToBackStack(false);
                    ((MainActivity) getActivity()).displayView("CreateNewApplicant");
                }
            }
        });

        updateApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!Update){
                    // Set the background of the Name field as editable and focusable
                    applicantName.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantName.setFocusable(true);
                    applicantName.setFocusableInTouchMode(true);
                    applicantName.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.editbox_background));

                    // Set the background of the Phone field as editable and focusable
                    applicantPhone.setFocusable(true);
                    applicantPhone.setFocusableInTouchMode(true);
                    applicantPhone.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantPhone.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.editbox_background));

                    // Set the background of the Email field as editable and focusable
                    applicantEmail.setFocusable(true);
                    applicantEmail.setFocusableInTouchMode(true);
                    applicantEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantEmail.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.editbox_background));

                    // Set the background of the Notes field as editable and focusable
                    applicantNotes.setInputType(InputType.TYPE_CLASS_TEXT);
                    applicantNotes.setFocusableInTouchMode(true);
                    applicantNotes.setFocusable(true);
                    applicantNotes.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.editbox_background));

                    // Set the rating bar to be changeable
                    ratingBar.setIsIndicator(false);

                    Update = true;
                    updateApplicant.setText("Update Applicant");
                }
                else {
                    // Create a temporary profile to then send back to the main activity to be updated in the local cache and
                    // then have the CWA doinbackground to send the data back to the server for updating
                    Applicant_Profile temp = new Applicant_Profile();
                    temp.setID(ap.getID());
                    temp.setUserName(applicantName.getText().toString());
                    temp.setEmail(applicantEmail.getText().toString());
                    temp.setPhoneNumber(applicantPhone.getText().toString());
                    temp.setNotes(applicantNotes.getText().toString());
                    temp.setStars((int)ratingBar.getRating());
                    temp.setProfilePictureURL(ap.getProfilePictureURL());
                    temp.setResumePictureURL(ap.getResumePictureURL());
                    temp.setResumeOverlayURL(ap.getResumeOverlayURL());

                    ((MainActivity)getActivity()).updateCache(ap, temp);
                    ((MainActivity)getActivity()).setAddToBackStack(false);
                    ((MainActivity)getActivity()).viewApplicant(temp);
                    CWA.doInBackground(temp,"Put"); //Updates the applicant in the web api
                }
            }
        });

        viewResume.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                ((MainActivity)getActivity()).setAddToBackStack(false);
                ((MainActivity)getActivity()).displayView("ViewApplicantResume");
            }
        });

        applicantPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!Update) {
                    String phone_number = ap.getPhoneNumber().replaceAll("-", "");
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone_number));
                    getContext().startActivity(callIntent);
                }
            }
        });

        applicantEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Update) {
                    System.out.println("THis: "+ap.getEmail());

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, ap.getEmail());
                    email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                    email.putExtra(Intent.EXTRA_TEXT, "message");
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                }
            }
        });
//        ProfileImage.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePictureIntent,0); // 0 specifies the requestCode so the on activity result know what to do
//            }
//        });
//
//        ResumeImage.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePictureIntent, 1);
//            }
//        });
        // Inflate the layout for this fragment
        return view;
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

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        // Imageview to hold the picture when fully downloaded and set during the onPostExecute
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap downloadedBitmapImage = null;

            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                downloadedBitmapImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
            }
            return downloadedBitmapImage;
        }
        protected void onPostExecute(Bitmap result) {
            try {
                imageView.setImageBitmap(result);
            } catch (Exception e) {
            }
        }
    }

    // Since we start a camera activity we need to get the results of that this function
    // handles the camera process
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            bitmap = (Bitmap) data.getExtras().get("data");
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        switch(requestCode){
            case 0: //if the requestCode was 0 the user took a profile picture
                ProfileImage.setImageBitmap(bitmap);
                ProfilePicBitmap = bitmap;
                break;

            case 1: //if the requestCode was 1 the user took a Resume picture
                ResumeImage.setImageBitmap(bitmap);
                ResumePicBitmap = bitmap;
                break;
        }
    }
}