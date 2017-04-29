package com.example.t_ste.resumekings;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;


/**
 * Created by Greg Wilkinson on 11/10/16.
 */
public class Fragment_View_Applicant_Resume extends Fragment {
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////
    public Fragment_View_Applicant_Resume() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getName(){
        return "ViewApplicantResume";
    }
    // THE STANDARD BLOCK FOR A FRAGMENT DONT EDIT IN HERE ///////////


    // INITIALIZERS //////////
    Applicant_Profile ap;
    private ImageButton currentPaint;
    private float smallBrush = 10;
    private float mediumBrush = 20;
    private float largeBrush = 30;
    public ImageView resumepic;
    public ImageView overlaypic;
    LinearLayout paintColors;
    DrawingView drawView;
    Bitmap resume;
    // INITIALIZERS //////////


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_applicant_resume, container, false); //Creates the view(Fragment)
        ((MainActivity) getActivity()).setAddToBackStack(false);
        //if WE ARE NOT IN CREATE APPLICANT THEN WE NEED TO DOWNLOAD THE PICTURES OF THE APPLICANT WE ARE VIEWING.
        drawView = (DrawingView) view.findViewById(R.id.drawing);
        drawView.setupDrawing();

        if (!((MainActivity) getActivity()).fm.findFragmentById(R.id.Container_left).toString().contains("Fragment_Create_New_Applicant")) {
            ap = ((MainActivity) getActivity()).getTempProfile();

            resumepic = (ImageView) view.findViewById(R.id.ResPic);
            overlaypic = (ImageView) view.findViewById(R.id.OvePic);
            if (ap.getResumePictureURL() != null) {
                new DownloadImageFromInternet(resumepic).execute(ap.getResumePictureURL());
            }
            if (ap.getResumeOverlayURL() != null) {
                new DownloadImageFromInternet(overlaypic).execute(ap.getResumeOverlayURL());
            }
        }else{
            resumepic = (ImageView) view.findViewById(R.id.ResPic);
            resumepic.setImageBitmap(((MainActivity)getActivity()).tempProfile.getResumePicture());
            drawView.setBackground(resumepic.getDrawable());

        }

        // Create all the buttons to control drawing on the image
        ImageButton drawButton = (ImageButton) view.findViewById(R.id.drawButton);
        ImageButton eraseButton = (ImageButton) view.findViewById(R.id.eraseButton);
        ImageButton newButton = (ImageButton) view.findViewById(R.id.newDrawing);
        ImageButton saveButton = (ImageButton) view.findViewById(R.id.saveButton);

        // Check if the tablet mode is active and then also check if the view applicant resume fragment is
        // visible and if it is then remove the save button. Should not be shown in create new applicant.
        if(((MainActivity)getActivity()).tabletMode && ((MainActivity) getActivity()).fm.findFragmentById(R.id.Container_left).toString().contains("Fragment_Create_New_Applicant"))
            saveButton.setVisibility(view.GONE);

        // Grab the paint colors from the xml view
        paintColors = (LinearLayout)view.findViewById(R.id.paintTopColors);

        // Create all the buttons for the paint view.
        ImageButton orange  = (ImageButton) view.findViewById(R.id.orange);
        ImageButton maroon  = (ImageButton) view.findViewById(R.id.maroon);
        ImageButton red     = (ImageButton) view.findViewById(R.id.red);
        ImageButton yellow  = (ImageButton) view.findViewById(R.id.yellow);
        ImageButton green   = (ImageButton) view.findViewById(R.id.green);
        ImageButton cyan    = (ImageButton) view.findViewById(R.id.cyan);
        ImageButton blue    = (ImageButton) view.findViewById(R.id.blue);
        ImageButton purple  = (ImageButton) view.findViewById(R.id.purple);
        ImageButton pink    = (ImageButton) view.findViewById(R.id.pink);
        ImageButton white   = (ImageButton) view.findViewById(R.id.white);
        ImageButton gray    = (ImageButton) view.findViewById(R.id.gray);
        ImageButton black   = (ImageButton) view.findViewById(R.id.black);

        currentPaint = (ImageButton)paintColors.getChildAt(0);
        currentPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        orange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        maroon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        red.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        yellow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        green.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        cyan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        blue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        purple.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        pink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        white.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        gray.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        black.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                paintClicked(V);
            }
        });

        // On click listener for the draw button
        drawButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                final Dialog brushDialog = new Dialog(getContext());
                brushDialog.setTitle("Brush size:");
                brushDialog.setContentView(R.layout.brush_chooser);

                //set small brush
                ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setBrushSize(smallBrush);
                        drawView.setLastBrushSize(smallBrush);
                        drawView.setErase(false);
                        brushDialog.dismiss();
                    }
                });

                //set medium brush
                ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setBrushSize(mediumBrush);
                        drawView.setLastBrushSize(mediumBrush);
                        drawView.setErase(false);
                        brushDialog.dismiss();
                    }
                });

                //set large brush
                ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setBrushSize(largeBrush);
                        drawView.setLastBrushSize(largeBrush);
                        drawView.setErase(false);
                        brushDialog.dismiss();
                    }
                });
                brushDialog.show();
            }
        });

        // On click listener for the erase button
        eraseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                final Dialog brushDialog = new Dialog(getContext());
                brushDialog.setTitle("Eraser size:");
                brushDialog.setContentView(R.layout.brush_chooser);

                //eraser sizes
                ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(true);
                        drawView.setBrushSize(smallBrush);
                        brushDialog.dismiss();
                    }
                });

                ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(true);
                        drawView.setBrushSize(mediumBrush);
                        brushDialog.dismiss();
                    }
                });

                ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        drawView.setErase(true);
                        drawView.setBrushSize(largeBrush);
                        brushDialog.dismiss();
                    }
                });
                brushDialog.show();
            }
        });

        // // On click listener for the new drawing button
        newButton.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v){
             AlertDialog.Builder newDialog = new AlertDialog.Builder(getContext());
             newDialog.setTitle("New drawing");
             newDialog.setMessage("Start new drawing (The current drawing will be lost)?");

             newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     drawView.startNew();
                     dialog.dismiss();
                 }
             });

             newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                 }
             });
             newDialog.show();
         }
        });

        // On click listener for the save drawing button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder newDialog = new AlertDialog.Builder(getContext());
                newDialog.setTitle("Save Drawing");
                newDialog.setMessage("Save the current drawing?");

                newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Applicant_Profile temp = new Applicant_Profile();

                        temp.setResumeOverlay(drawView.getResumeBitmap());
                        temp.setID(ap.getID());
                        temp.setUserName(ap.getUserName());
                        temp.setEmail(ap.getEmail());
                        temp.setPhoneNumber(ap.getPhoneNumber());
                        temp.setNotes(ap.getNotes());
                        temp.setStars(ap.getStars());
                        temp.setProfilePicture(ap.getProfilePicture());
                        temp.setProfilePictureURL(ap.getProfilePictureURL());//will be the same
                        temp.setResumePictureURL(ap.getResumePictureURL()); //will be the same
                        temp.setResumeOverlayURL("http://s3.amazonaws.com/testbucketsource11/"+ap.getID()+"ResumeOverlay.png");//we know this will be the url so we can go ahead and set it

                        // sync the drawing back to the server to save with the applicant
                        if(((MainActivity) getActivity()).API_Mode){
                            Call_Web_API CWA = new Call_Web_API();
                            CWA.doInBackground(temp,"Put");
                        }
                        ((MainActivity)getActivity()).updateCache(ap, temp);
                        ((MainActivity)getActivity()).setAddToBackStack(false);
                        ((MainActivity)getActivity()).viewApplicantResumeSave(temp);
                    }
                });
                newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                newDialog.show();
            }
        });

        return view; //Return the fragment with all the functionality
    }

    public void paintClicked(View view){
        drawView.setErase(false);
        //use chosen color
        if(view != currentPaint){
            //update color
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currentPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currentPaint=(ImageButton)view;
        }
        drawView.setBrushSize(drawView.getLastBrushSize());
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
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
            }
            return bimage;
        }
        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        protected void onPostExecute(Bitmap result) {
            try {
                imageView.setImageBitmap(result);
                drawView.setBackground(resumepic.getDrawable());
                drawView.setForeground(overlaypic.getDrawable());
            } catch (Exception e) {
            }
        }
    }

}