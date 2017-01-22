package com.example.t_ste.resumekings;

import android.Manifest;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();   // Local cache of applicants to pass to the other fragments
    FragmentManager fm = getSupportFragmentManager();                           // Fragment manager that transitions all fragments in the app
    Applicant_Profile tempProfile = new Applicant_Profile();                    // Temporary profile that allows fragments to talk to each other or pass data
    Call_Web_API CWA;

    public boolean tablet_mode = false;         // Determined at startup. Don't mess with this
    public boolean horizontal = false;          // standard user will open the app from a vertical position so open vertically first.
    public boolean addToBackStack = false;      // Set up TAGs to be allowed or not allowed to add to the backstack
    public boolean deleteApplicant = false;
    boolean API_Mode = true;                   // Toggle this to true if you want to use the cloud


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // The standard on create items and initializing the toolbars
        super.onCreate(savedInstanceState);
        if(!API_Mode) {
            // Testing Derpage
            String[] Names = new String[] {"Bob", "Jill", "Paul", "Brother morgan", "Spidey", "Ronald Cross", "Derpina", "humm", "Trevor Stephens", "Greg Wilkinson"};
            String[] Email = new String[] {"Bob@yahoo.whynot", "jill@weirdo.net", "PaulBiggers@gmail.com", "psychward@where.fired",
                    "Spidey@web.net", "RIP.com", "derpina@yuno.net", "yayitworked!", "Trevor.Stevens@HI", "Greg.Wilkinson@IBREAKEVERYTHING"};

            for (int i = 0; i < Names.length; i++) {
                Applicant_Profile ap = new Applicant_Profile();
                ap.setUserName(Names[i]);
                ap.setPhoneNumber("8765309");
                ap.setEmail(Email[i]);
                ap.setNotes("We're all OK!");
                ap.setProfilePictureURL("http://www.freshdesignpedia.com/wp-content/uploads/what-is-cat-s-education/cat-educate-tips-small-katzenbaby.jpg");
                ap.setResumePictureURL("https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/Resume.pdf/page1-220px-Resume.pdf.jpg");
                ap.setStars(3);
                addToCache(ap);
            }
        }
        if(API_Mode) {
            CWA = new Call_Web_API();
            getCache();
        }

        // Determine whether or not the device is large enough to support multiple fragments if yes this will be true.
        get_device_size();

        // Set the drawer and all of its contents.
        setContentView(R.layout.main_activity_drawer);

        // Set the initial fragment in the Container_left in the section_user xml layout
        FragmentTransaction ft = fm.beginTransaction();
        Fragment_View_Applicants newFragment = new Fragment_View_Applicants();
        ft.add(R.id.Container_left, newFragment).commit();

        // Start up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // If tablet mode is activated then the Container_right needs to be populated by the first cached applicant profile
        if(tablet_mode && cachedApplicantProfiles != null){
//            viewApplicant(cachedApplicantProfiles.get(0));
        }

        // Floating action bar that we may turn into a hotswap to something else if we think we need it...
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

        // Standard phone sized view and shows the left container only so all fragments rotate in/out to show the flow to the user
        Fragment newFragmentLeft = null;
        FragmentTransaction transaction1 = fm.beginTransaction();

        //specifically for tablet mode. Left container and right container hold the fragments to support the tablet view
        Fragment newFragmentRight = null;
        FragmentTransaction transaction2 = fm.beginTransaction();

        // Phone mode. This will keep only one fragment viewable at a time and d
        if(!tablet_mode) {
            switch (TAG) {
                case "CreateNewApplicant":
                    // Initialize the create new applicant fragment
                    newFragmentLeft = new Fragment_Create_New_Applicant();
                    break;

                case "ViewApplicants":
                    // Initialize the view applicants fragment
                    newFragmentLeft = new Fragment_View_Applicants();
                    break;

                case "ViewSingleApplicant":
                    // Initialize the view single applicant fragment
                    newFragmentLeft = new Fragment_View_Single_Applicant();
                    break;

                case "ViewApplicantResume":
                    // Initialize the view applicant resume fragment
                    newFragmentLeft = new Fragment_View_Applicant_Resume();
                    break;

                case "FavoriteApplicants":
                    // Initialize the favorite applicants fragment
                    newFragmentLeft = new Fragment_Favorite_Applicants();
                    break;

                case "Tutorial":
                    // fragment to show the tutorial
                    newFragmentLeft = new Fragment_Tutorial();
                    break;

                case "Settings":
                    // Initialize the Settings fragment
                    newFragmentLeft = new Fragment_Settings();
                    break;
            }
        }

        // Tablet only mode. This will allow the second fragment to be set and utilized depending on the fragment
        // combos that we decide to use.
        if(tablet_mode) {
            switch (TAG) {
                case "CreateNewApplicant":
                    // Initialize the create new applicant fragment
                    newFragmentLeft = new Fragment_Create_New_Applicant();
                    transaction2.remove(fm.findFragmentById(R.id.Container_right));
                    break;

                case "ViewApplicants":
                    // Initialize the view applicants fragment
                    newFragmentLeft = new Fragment_View_Applicants();
                    newFragmentRight = new Fragment_View_Single_Applicant();
                    break;

                case "ViewSingleApplicant":
                    // Initialize the view single applicant fragment
                    newFragmentLeft = new Fragment_View_Applicants();
                    newFragmentRight = new Fragment_View_Single_Applicant();
                    break;

                case "ViewApplicantResume":
                    // Initialize the view applicant resume fragment
                    newFragmentLeft = new Fragment_View_Single_Applicant();
                    newFragmentRight = new Fragment_View_Applicant_Resume();
                    break;

                case "FavoriteApplicants":
                    // Initialize the favorite applicants fragment
                    newFragmentLeft = new Fragment_Favorite_Applicants();
                    break;

                case "Tutorial":
                    // fragment to show the tutorial
                    newFragmentLeft = new Fragment_Tutorial();
                    break;

                case "Settings":
                    // Initialize the Settings fragment
                    newFragmentLeft = new Fragment_Settings();
                    break;
            }
        }

        // If the new fragment is not null then have the fragment manager commit the swap.
        if (newFragmentLeft != null) {
            transaction1.replace(R.id.Container_left, newFragmentLeft, TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        if (newFragmentRight != null) {
            transaction2.replace(R.id.Container_right, newFragmentRight, TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        if(addToBackStack) transaction1.addToBackStack(TAG);

        transaction1.commit();
        if (transaction2 != null)
            transaction2.commit();
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

    public void updateCache(Applicant_Profile old, Applicant_Profile updated) {
        int i = cachedApplicantProfiles.indexOf(old);

        if(!(cachedApplicantProfiles.get(i).getUserName()).equals(updated.getUserName()) && updated.getUserName() != null)
            cachedApplicantProfiles.get(i).setUserName(updated.getUserName());
        else cachedApplicantProfiles.get(i).setUserName(old.getUserName());

        if(!(cachedApplicantProfiles.get(i).getEmail()).equals(updated.getEmail()) && updated.getEmail() != null)
            cachedApplicantProfiles.get(i).setEmail(updated.getEmail());
        else cachedApplicantProfiles.get(i).setEmail(old.getEmail());

        if(!(cachedApplicantProfiles.get(i).getPhoneNumber()).equals(updated.getPhoneNumber()) && updated.getPhoneNumber() != null)
            cachedApplicantProfiles.get(i).setPhoneNumber(updated.getPhoneNumber());
        else cachedApplicantProfiles.get(i).setPhoneNumber(old.getPhoneNumber());

        if(!(cachedApplicantProfiles.get(i).getNotes()).equals(updated.getNotes()) && updated.getNotes() != null)
            cachedApplicantProfiles.get(i).setNotes(updated.getNotes());
        else cachedApplicantProfiles.get(i).setNotes(old.getNotes());

        if(!(cachedApplicantProfiles.get(i).getStars() == updated.getStars()))
            cachedApplicantProfiles.get(i).setStars(updated.getStars());
        else cachedApplicantProfiles.get(i).setStars(old.getStars());
    }

    public void getCache() {
        Applicant_Profile dummyProfile=new Applicant_Profile(); //need this to call the switch function its dumb I know
        cachedApplicantProfiles = CWA.doInBackground(dummyProfile,"Get");
    }

    public void get_device_size() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        tablet_mode = diagonalInches >= 6.5;
    }
}