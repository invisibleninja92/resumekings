package com.example.t_ste.resumekings;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<applicantProfile> taskList= new ArrayList<applicantProfile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        applicantProfile ap = new applicantProfile();
        ap.setUserName("Test");
        ap.setPhoneNumber("1234456789");
        ap.setEmail("Test@email.com");
        ap.setNotes("Android Sucks");
        ap.setStars(2);
        taskList.add(ap);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = view_applicants.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        final int commit = fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.Create_New_User) {
            displayView(id);
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

        Fragment fragment = null;
        Class fragmentClass = null;
        String title = getString(R.string.app_name);

        switch(viewId) {
            case R.id.Create_New_User:
                fragmentClass = create_new_applicant.class;
                break;
            case R.id.Tutorial:
                //fragment = new Tutorial_Fragment();
                //title = getString(R.string.Tutorial);
                Toast.makeText(this, "Here we will start the tutorial!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.View_Recent_Applicants:
                fragmentClass = view_applicants.class;
                //title = getString(R.string.View_Recent_Applicants);
                Toast.makeText(this, "Here we will show the recent applicants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Favorite_Applicants:
                //fragment = new Favorite_Applicants_Fragment();
                //title = getString(R.id.Favorite_Applicants);
                Toast.makeText(this, "Show the favorite applicants", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Settings:
                //fragment = new Settings_Fragment();
                //title = getString(R.id.Settings);
                Toast.makeText(this, "Show the settings...if we add any", Toast.LENGTH_SHORT).show();
                break;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        final int commit = fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }
    public void setTaskList(applicantProfile ap){
        taskList.add(ap);

    }
    public ArrayList<applicantProfile> getTaskList(){
        return taskList;
    }
}
