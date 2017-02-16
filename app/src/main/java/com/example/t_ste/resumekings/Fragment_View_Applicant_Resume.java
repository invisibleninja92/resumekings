package com.example.t_ste.resumekings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    LinearLayout paintColors;
    DrawingView drawView;
    // INITIALIZERS //////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_applicant_resume, container, false); //Creates the view(Fragment)
        ((MainActivity)getActivity()).setAddToBackStack(false);
        ap = ((MainActivity)getActivity()).getTempProfile();

        drawView = (DrawingView)view.findViewById(R.id.drawing);
        drawView.setBrushSize(smallBrush);
        drawView.setupDrawing();

        ImageButton drawButton  = (ImageButton) view.findViewById(R.id.drawButton);
        ImageButton eraseButton = (ImageButton) view.findViewById(R.id.eraseButton);
        ImageButton newButton   = (ImageButton) view.findViewById(R.id.newDrawing);
        ImageButton saveButton  = (ImageButton) view.findViewById(R.id.saveButton);

        paintColors = (LinearLayout)view.findViewById(R.id.paintTopColors);

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
                        //temp.setResumeOverlayURL("http://s3.amazonaws.com/testbucketsource11/"+ap.getUserName()+ap.getPhoneNumber()+"ResumeOverlay.png");
                        if(((MainActivity) getActivity()).API_Mode==true){
                        Call_Web_API CWA = new Call_Web_API();
                        CWA.doInBackground(temp,"Put");}

                        ((MainActivity)getActivity()).updateCache(ap, temp);
                        ((MainActivity)getActivity()).setAddToBackStack(false);
                        ((MainActivity)getActivity()).viewApplicant(temp);
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
}