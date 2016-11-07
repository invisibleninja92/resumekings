package com.example.t_ste.resumekings;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.toggle;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*
    FUUUUUUUUUUUUUUCCCCCCCCCCCCCKKKKKKKKKKKKKKKKKKK YYYYYYYYYYYOOOOOOOOOOOOOUUUUUUUUUUUUUUUUU
    GGGGGGGRRRRRRRRRRREEEEEEEEEEEGGGGGGGGGGGGGGGGGGGGGGG
    FFFFFFFFFFUUUUUUUUUUUUUUCCCCCCCCCCCCCCKKKKKKKKKKKKK YYYYYYYYYYYYOOOOOOOOOOUUUUUUUUUUU
     */

// I SWEAR I FUCKING HATE YOU
    // LOVE YOU TOO!

    // Temporary (possibly permanent) list of applicants to keep locally
    ArrayList<Applicant_Profile> taskList= new ArrayList<>();
    FragmentManager fm = getSupportFragmentManager();
    Boolean BaseView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // The standard on create items and initializing the toolbars
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_drawer);

        // Set the initial fragment in that container
        FragmentTransaction ft = fm.beginTransaction();

        Fragment_View_Applicants newFragment = new Fragment_View_Applicants();
        ft.add(R.id.Container, newFragment).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Applicant_Profile ap = new Applicant_Profile();
        Applicant_Profile ap2 = new Applicant_Profile();
        Applicant_Profile ap3 = new Applicant_Profile();
        Applicant_Profile ap4 = new Applicant_Profile();

        // Testing Derpage
        ap.setUserName("Derpina");
        ap.setPhoneNumber("1234456789");
        ap.setEmail("Derpina@yuno.net");
        ap.setNotes("Android Sucks");
        ap.setStars(2);
        taskList.add(ap);
        ap2.setUserName("Ronald Cross");
        ap2.setPhoneNumber("16662126969");
        ap2.setEmail("RonaldCross@IWorkAtLockheed.com");
        ap2.setNotes("Doesnt know what a conclusion paragraph is. ");
        ap2.setStars(2);
        taskList.add(ap2);
        ap3.setUserName("Brother Morgan");
        ap3.setPhoneNumber("12555847777");
        ap3.setEmail("BrotherMogran@RUOKJesusSaves.com");
        ap3.setNotes("Doesnt stop smileing, Seriously this guy is too happy");
        ap3.setStars(1);
        taskList.add(ap3);

        ap4.setUserName("humm");
        ap4.setEmail("Yayitworked");
        ap4.setStars(5);
        taskList.add(ap4);

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
        return;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.Create_New_Applicant) {
            displayView(id);
            callAPI aps = new callAPI();
            aps.doInBackground();
        } else if (id == R.id.Tutorial) {
            displayView(id);
        } else if (id == R.id.View_Recent_Applicants) {
            displayView(id);
        } else if (id == R.id.Favorite_Applicants) {
            displayView(id);
        } else if (id == R.id.Something) {
            displayView(id);
        } else if (id == R.id.Settings) {
            displayView(id);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void displayView(int viewId) {

        // Create a new fragment here to swap out with the one that was already there.
        Fragment newFragment = null;
        String TAG = null;

        switch(viewId) {
            case R.id.Create_New_Applicant:
                // Initialize the new fragment to swap out
                newFragment = new Fragment_Create_New_Applicant();
                TAG = "CreateNewApplicant";
                break;
            case R.id.View_Recent_Applicants:
                // Initialize the view applicants fragment
                newFragment = new Fragment_View_Applicants();
                TAG = "ViewApplicants";
                break;
            case R.id.Favorite_Applicants:
                //fragment = new Fragment_Favorite_Applicants();
                Toast.makeText(this, "Show the favorite applicants", Toast.LENGTH_SHORT).show();
                TAG = "FavoriteApplicants";
                break;
            case R.id.Tutorial:
                //fragment = new Fragment_Tutorial();
                TAG = "Tutorial";
                Toast.makeText(this, "Show the tutorial", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Settings:
                //fragment = new Fragment_Settings();
                TAG = "Settings";
                Toast.makeText(getBaseContext(), "Show the settings...if we add any", Toast.LENGTH_SHORT).show();
                break;
        }

        // If the new
        if (newFragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.Container, newFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(TAG);
            transaction.commit();
        }
    }

    public void setTaskList(Applicant_Profile ap){
        taskList.add(ap);
    }

    public ArrayList<Applicant_Profile> getTaskList(){
        return taskList;
    }
}