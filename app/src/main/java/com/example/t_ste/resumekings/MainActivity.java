package com.example.t_ste.resumekings;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    // Temporary (possibly permanent) list of applicants to keep locally
    ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();
    FragmentManager fm = getSupportFragmentManager();
    Applicant_Profile tempProfile = new Applicant_Profile();
    Call_Web_API CWA;

    public boolean addToBackStack = true; // Set up TAGs to be allowed or not allowed to add to the backstack


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // The standard on create items and initializing the toolbars
        super.onCreate(savedInstanceState);
         CWA = new Call_Web_API();
         getCache();
        setContentView(R.layout.main_activity_drawer);

        // Set the initial fragment in that container
        FragmentTransaction ft = fm.beginTransaction();

        Fragment_View_Applicants newFragment = new Fragment_View_Applicants();
        ft.add(R.id.Container, newFragment).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Testing Derpage
        /*String[] Names = new String[] {"Bob", "Jill", "Paul", "Brother morgan", "Spidey", "Ronald Cross", "Derpina", "humm", "Trevor Stephens", "Greg Wilkinson"};
        String[] Email = new String[] {"Bob@yahoo.whynot", "jill@weirdo.net", "PaulBiggers@gmail.com", "psychward@where.fired",
                "Spidey@web.net", "kissme.com", "derpina@yuno.net", "yayitworked!", "Trevor.Stevens@HI", "Greg.Wilkinson@IBREAKEVERYTHING"};

        for (int i = 0; i < Names.length; i++) {
            Applicant_Profile ap = new Applicant_Profile();
            ap.setUserName(Names[i]);
            ap.setPhoneNumber("8765309");
            ap.setEmail(Email[i]);
            ap.setNotes("We're all bad!");
            ap.setStars(3);
            addToCache(ap);
        }*/

        // Floating action bar that we may turn into a hotswap to something else if we think we need it...
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // The drawer on the left side of the home screen
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_activity_drawer);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (fm.getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            fm.popBackStack();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.Create_New_Applicant) {
            displayView("CreateNewApplicant");
        } else if (id == R.id.Tutorial) {
            displayView("Tutorial");
        } else if (id == R.id.View_Applicants) {
            displayView("ViewApplicants");
        } else if (id == R.id.Favorite_Applicants) {
            displayView("FavoriteApplicants");
        } else if (id == R.id.Something) {
            displayView("Something");
        } else if (id == R.id.Settings) {
            displayView("Settings");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displayView(String TAG) {
        // This is a fragment by service  publisher/subscriber framework for the app. Any string passed into
        // here will initialize the swap to one of the other accepted fragments. Some temporary variables in
        // MainActivity will allow for passage of needed items to and from other fragments.

        Fragment newFragment = null;

        switch(TAG) {
            case "CreateNewApplicant":
                // Initialize the create new applicant fragment
                newFragment = new Fragment_Create_New_Applicant();
                break;

            case "ViewApplicants":
                // Initialize the view applicants fragment
                newFragment = new Fragment_View_Applicants();
                break;

            case "ViewSingleApplicant":
                // Initialize the view single applicant fragment
                newFragment = new Fragment_View_Single_Applicant();
                break;

            case "ViewApplicantResume":
                // Initialize the view applicant resume fragment
                newFragment = new Fragment_View_Applicant_Resume();
                break;

            case "FavoriteApplicants":
                // Initialize the favorite applicants fragment
                newFragment = new Fragment_Favorite_Applicants();
                break;

            case "Tutorial":
                // fragment to show the tutorial
                newFragment = new Fragment_Tutorial();
                break;

            case "Settings":
                // Initialize the Settings fragment
                newFragment = new Fragment_Settings();
                break;
        }

        // TODO: fix this to check if current fragment is the same as the one to start
        // If the new fragment is not null then have the fragment manager commit the swap.
        if (newFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.Container, newFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if(addToBackStack) transaction.addToBackStack(TAG);
            transaction.commit();
        }
    }

    // Takes in a profile from create new applicant or view applicants to then save the profile in the mainactivity
    // The getTempProfile call then allows the fragment to apply it and display to the user
    public void viewApplicant(Applicant_Profile ap) {
        tempProfile = ap;
        displayView("ViewSingleApplicant");
    }

    public Applicant_Profile getTempProfile(){
        return tempProfile;
    }

    public void addToCache(Applicant_Profile ap){
        cachedApplicantProfiles.add(ap);
    }

    public ArrayList <Applicant_Profile> getCachedApplicantProfiles(){
        return cachedApplicantProfiles;
    }

    public void removeFromCache(Applicant_Profile ap) {
        cachedApplicantProfiles.remove(ap);
    }

    public void setAddToBackStack(boolean input) {
        addToBackStack = input;
    }

    // TODO: FINISH THIS THING
    public void updateCache(Applicant_Profile old, Applicant_Profile updated) {
        int i = cachedApplicantProfiles.indexOf(old);
        Applicant_Profile temp = cachedApplicantProfiles.get(i);

        if(!(temp.getUserName()).equals(updated.getUserName()) && updated.getUserName() != null)
            temp.setUserName(updated.getUserName());

        if(!(temp.getEmail()).equals(updated.getEmail()) && updated.getEmail() != null)
            temp.setEmail(updated.getEmail());

        if(!(temp.getPhoneNumber()).equals(updated.getPhoneNumber()) && updated.getPhoneNumber() != null)
            temp.setPhoneNumber(updated.getPhoneNumber());

        if(!(temp.getNotes()).equals(updated.getNotes()) && updated.getNotes() != null)
            temp.setNotes(updated.getNotes());
    }

    public void getCache() {
        cachedApplicantProfiles = CWA.doInBackground("","Get");
    }
}