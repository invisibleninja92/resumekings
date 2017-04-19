package com.example.t_ste.resumekings;

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
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.t_ste.resumekings.R.id.Container_right;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();           // Local cache of applicants to pass to the other fragments
    FragmentManager fm                                   = getSupportFragmentManager(); // Fragment manager that transitions all fragments in the app
    Applicant_Profile tempProfile                        = new Applicant_Profile();     // Temporary profile that allows fragments to talk to each other or pass data
    Call_Web_API CWA;

    public boolean tabletMode      = false;  // Determined at startup. Don't mess with this
    public boolean addToBackStack  = false;  // Set up TAGs to be allowed or not allowed to add to the backstack
    public boolean API_Mode        = false;  // Toggle this to true if you want to use the cloud

    //TODO: remove this eventually and make api calls
    public List<String> Names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // The standard on create items and initializing the toolbars
        super.onCreate(savedInstanceState);

        // This runs if API mode is disabled. It will auto load some default testing applicants
        if(!API_Mode) {
            // Testing Derpage
            Names = new ArrayList<>();
            List<String> Email = new ArrayList<>();
            List<String> Phone = new ArrayList<>();
            BufferedReader reader;

            // These three text files are in the assets folder and access 100 applicants so the list is bigger
            try {
                final InputStream fpNames = getAssets().open("names.txt");
                reader = new BufferedReader(new InputStreamReader(fpNames));
                String line = reader.readLine();
                while (line != null) {
                    Names.add(line);
                    line = reader.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                final InputStream fpEmails = getAssets().open("email.txt");
                reader = new BufferedReader(new InputStreamReader(fpEmails));
                String line = reader.readLine();
                while (line != null) {
                    Email.add(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                final InputStream fpPhone = getAssets().open("phone.txt");
                reader = new BufferedReader(new InputStreamReader(fpPhone));
                String line = reader.readLine();
                while (line != null) {
                    Phone.add(line);
                    line = reader.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < Names.size(); i++) {
                Applicant_Profile ap = new Applicant_Profile();
                ap.setUserName(Names.get(i));
                ap.setPhoneNumber(Phone.get(i));
                ap.setEmail(Email.get(i));
                ap.setNotes("We're all OK!");
                ap.setProfilePictureURL("http://www.freshdesignpedia.com/wp-content/uploads/what-is-cat-s-education/cat-educate-tips-small-katzenbaby.jpg");
                ap.setResumePictureURL("https://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg");
                ap.setResumeOverlayURL("https://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg");
                ap.setStars(3);
                addToCache(ap);
            }
        }

        // App Startup
        // Regular API mode which will pull everything from the server(s).
        if(API_Mode) {
            CWA = new Call_Web_API();
            getCache();
        }

        // Determine whether or not the device is large enough to support multiple fragments. Returns true or false.
        get_device_size();

        // Set the drawer and all of its contents.
        setContentView(R.layout.main_activity_drawer);

        // Set the initial fragment in the Container_left in the section_user xml layout
        FragmentTransaction fragTransactionLeft = fm.beginTransaction();
        FragmentTransaction fragTransactionRight = fm.beginTransaction();
        Fragment startupFragmentLeft;
        Fragment startupFragmentRight;

        // First determine whether or not the cache is empty. If empty then show create new applicant
        if (cachedApplicantProfiles.size() != 0) {

            // Get the first profile saved in the cache locally.
            tempProfile = cachedApplicantProfiles.get(0);
            startupFragmentLeft = new Fragment_View_Applicants();
            fragTransactionLeft.add(R.id.Container_left, startupFragmentLeft, "ViewApplicants");
            fragTransactionLeft.addToBackStack("ViewApplicants");

            // Add in support for the tablet view to show the first applicant in the list
            if(tabletMode) {
                startupFragmentRight = new Fragment_View_Single_Applicant();
                fragTransactionRight.add(Container_right, startupFragmentRight, "ViewSingleApplicant");
            }

            // Commit the first transactions to initialize the first view the user sees
            fragTransactionLeft.commit();
            if(tabletMode) fragTransactionRight.commit();
        }
        else {
            // Both Tablet and phone will only view CreateNewApplicant if the cache is empty
            startupFragmentLeft = new Fragment_Create_New_Applicant();
            fragTransactionLeft.add(startupFragmentLeft, "CreateNewApplicant");
            fragTransactionLeft.commit();
        }

        // Start up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // End App Startup


        // The drawer on the left side of the home screen
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_activity_drawer);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Wait for the back button to be pressed and depending on if the drawer is open then close it,
    // otherwise pop the first item off of the backstack and show that view.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Drawer checking if it needs closed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        // If the backstack count is zero then close the app otherwise show the last item on the backstack
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
        // This will handle selections in the menu in the top right of the app
        // When an item is selected in the drop down menu then it will go to create new applicant
        // or sort the displayed list of applicants by A-Z or rating
        int id = item.getItemId();

        // Sort by alphabetic order
        if (id == R.id.action_sort_alpha) {
            Fragment_View_Applicants fragment = (Fragment_View_Applicants) fm.findFragmentById(R.id.Container_left);
            fragment.getAdapt().sort(new Comparator<Applicant_Profile>() {
                @Override
                public int compare(Applicant_Profile applicant_profile_1, Applicant_Profile applicant_profile_2) {
                    return applicant_profile_1.getUserName().compareToIgnoreCase(applicant_profile_2.getUserName());
                }});

        }
        // Sort by rating from highest to lowest
        else if(id == R.id.action_sort_rate) {
            Fragment_View_Applicants fragment = (Fragment_View_Applicants) fm.findFragmentById(R.id.Container_left);
            fragment.getAdapt().sort(new Comparator<Applicant_Profile> () {

                @Override
                public int compare(Applicant_Profile applicant_profile_1, Applicant_Profile applicant_profile_2) {
                    if(applicant_profile_1.getStars() > applicant_profile_2.getStars()){
                        return -1;
                    }
                    return 1;
                }});
        }
        // The other option in that menu is to create a new applicant profile
        else if (id == R.id.Create_New_Applicant) {
            displayView("CreateNewApplicant");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.Create_New_Applicant) {
            displayView("CreateNewApplicant");
        }
        else if (id == R.id.Tutorial) {
            displayView("Tutorial");
        }
        else if (id == R.id.View_Applicants) {
            displayView("ViewApplicants");
        }
        else if (id == R.id.Favorite_Applicants) {
            displayView("FavoriteApplicants");
        }
        else if (id == R.id.Something) {
            displayView("Something");
        }
        else if (id == R.id.Settings) {
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
        if(!tabletMode) {
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
                    Fragment_View_Applicants fragment = (Fragment_View_Applicants) fm.findFragmentById(R.id.Container_left);
                    if(fragment.adapt.mOriginalValues == null) { //We have not searched so its ok to update left without updating cache
                        newFragmentLeft = new Fragment_View_Single_Applicant();
                    }
                    break;

                case "ViewSingleApplicantWithSearch":
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
        } // END Phone view switch

        // Tablet only mode. This will allow the second fragment to be set and utilized depending on the fragment
        // combos that we decide to use.
        if(tabletMode) {
            switch (TAG) {
                case "CreateNewApplicant":
                    // Initialize the create new applicant fragment
                    newFragmentLeft = new Fragment_Create_New_Applicant();
                    newFragmentRight= new Fragment_View_Applicant_Resume();
                    break;

                case "ViewApplicants":
                    // Initialize the view applicants fragment
                    newFragmentLeft = new Fragment_View_Applicants();
                    newFragmentRight = new Fragment_View_Single_Applicant();
                    break;

                case "ViewSingleApplicantWithSearch":
                    // Initialize the view single applicant fragment
                    Fragment_View_Applicants fragment = null;

                    fragment = (Fragment_View_Applicants) fm.findFragmentById(R.id.Container_left);

                    if(fragment.adapt.mOriginalValues == null){ //We have not searched so its ok to update left without updating cache
                        newFragmentLeft = new Fragment_View_Applicants();
                    }
                    newFragmentRight = new Fragment_View_Single_Applicant();
                    break;

                case "ViewSingleApplicant":
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
        } // END Tablet view switch

        // If the new fragment is not null then have the fragment manager commit the swap.
        if (newFragmentLeft != null) {
            transaction1.replace(R.id.Container_left, newFragmentLeft, TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        if (newFragmentRight != null) {
            transaction2.replace(R.id.Container_right, newFragmentRight, TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        // Backstack handles whether or not that fragment will reappear if the back button is pressed
        if(addToBackStack) transaction1.addToBackStack(TAG);

        // This handles whether or not the tablet view mode is displayed. Currently if the device is small enough the
        // tablet view will never be used since the first switch is the only one used.
        transaction1.commit();
        if (transaction2 != null)
            transaction2.commit();
    }

    // Takes in a profile from create new applicant or view applicants to then save the profile in the mainactivity
    // The getTempProfile call then allows the fragment to apply it and display to the user
    public void viewApplicant(Applicant_Profile ap) {
        if (ap == null){
            return;
        }
        tempProfile = ap;
        displayView("ViewSingleApplicantWithSearch");
    }
    public void viewApplicantResumeSave(Applicant_Profile ap){
        if (ap == null){
            return;
        }
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

    public List<String> getNameList() {
        return Names;
    }

    // Remove the passed in applicant profile from the cache
    public void removeFromCache(Applicant_Profile ap) {
        cachedApplicantProfiles.remove(ap);
        // TODO: does this need an api call to remove it from the server?
    }

    // Set whether or not the fragment calling this should be allowed to add the fragment to the backstack
    public void setAddToBackStack(boolean input) {
        addToBackStack = input;
    }


    // This determines whether or not the new applicant profile passed in needs to be updated in the
    // cache of other applicant profiles. It will append the different version on to the bottom of
    // the cache list
    public void updateCache(Applicant_Profile old, Applicant_Profile updated) {
        int i = cachedApplicantProfiles.indexOf(old);

        // Check the applicant's name first and see if it is different
        //TODO BUG #1 in google doc
        if(!(cachedApplicantProfiles.get(i).getUserName()).equals(updated.getUserName()) && updated.getUserName() != null)
            cachedApplicantProfiles.get(i).setUserName(updated.getUserName());
        else cachedApplicantProfiles.get(i).setUserName(old.getUserName());

        // Check the email and see if it is different
        if(!(cachedApplicantProfiles.get(i).getEmail()).equals(updated.getEmail()) && updated.getEmail() != null)
            cachedApplicantProfiles.get(i).setEmail(updated.getEmail());
        else cachedApplicantProfiles.get(i).setEmail(old.getEmail());

        // Check the phone number and see if it is different
        if(!(cachedApplicantProfiles.get(i).getPhoneNumber()).equals(updated.getPhoneNumber()) && updated.getPhoneNumber() != null)
            cachedApplicantProfiles.get(i).setPhoneNumber(updated.getPhoneNumber());
        else cachedApplicantProfiles.get(i).setPhoneNumber(old.getPhoneNumber());

        // Check if the notes have changed
        if(!(cachedApplicantProfiles.get(i).getNotes()).equals(updated.getNotes()) && updated.getNotes() != null)
            cachedApplicantProfiles.get(i).setNotes(updated.getNotes());
        else cachedApplicantProfiles.get(i).setNotes(old.getNotes());

        // Check if the rating is different
        if(!(cachedApplicantProfiles.get(i).getStars() == updated.getStars()))
            cachedApplicantProfiles.get(i).setStars(updated.getStars());
        else cachedApplicantProfiles.get(i).setStars(old.getStars());

        // Swap the resume picture and profile picture links over to the new applicant profile version
        cachedApplicantProfiles.get(i).setResumePicture(old.getResumePicture());
        cachedApplicantProfiles.get(i).setProfilePicture(old.getProfilePicture());

    }

    // Grab the cache from the server and save it locally to display to the user
    public void getCache() {
        Applicant_Profile dummyProfile = new Applicant_Profile(); //need this to call the switch function its dumb I know
        // Do the GET request in the background
        cachedApplicantProfiles = CWA.doInBackground(dummyProfile,"Get");
    }

    // Grab the device size from the OS and determine whether or not the tablet view or phone view
    // should be displayed.
    public void get_device_size() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);

        // Depending on the size of the screen tabletMode will be true or false
        tabletMode = diagonalInches >= 6.5;
    }


    public void setUserandPass(String user, String hashedPass){
        String username = user;
        String password = hashedPass;
    }
}