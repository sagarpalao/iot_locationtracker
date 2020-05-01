package com.project.sagar.loctrack;

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
import com.google.android.gcm.*;
import static com.project.sagar.loctrack.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.project.sagar.loctrack.CommonUtilities.EXTRA_MESSAGE;
import static com.project.sagar.loctrack.CommonUtilities.SENDER_ID;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AsyncTask<Void, Void, Void> mRegisterTask;
    boolean r=false;
    android.content.SharedPreferences pref;

    //android.widget.TextView lblMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String value = pref.getString("ip_text", "");
        Common.IP=value;
        Log.d("IP", Common.IP);

        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.Intent i=new android.content.Intent(HomeActivity.this, com.project.sagar.loctrack.MapActivity.class);
                startActivity(i);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));
        r=true;

        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(this);

        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
                //Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
                Log.i("REGID",regId);
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        ServerUtilities.register(context, "sagar","palo", regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }

    }

    /**
     * Receiving push messages
     * */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Showing received message
           // lblMessage.append(newMessage + "\n");
            //Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };

    @Override
    protected void onResume() {
        if(!r) {
            registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
            r=true;
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(r) {
            unregisterReceiver(mHandleMessageReceiver);
            r=false;
        }
        super.onPause();
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
        getMenuInflater().inflate(R.menu.home, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void locCarCall(View v){
        android.content.Intent i=new android.content.Intent(HomeActivity.this, com.project.sagar.loctrack.MapActivity.class);
        startActivity(i);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == com.project.sagar.loctrack.R.id.nav_loccar) {
            // Handle the camera action
            android.content.Intent i=new android.content.Intent(HomeActivity.this, com.project.sagar.loctrack.MapActivity.class);
            startActivity(i);
        } else if (id == com.project.sagar.loctrack.R.id.nav_geofence) {
            android.content.Intent i=new android.content.Intent(HomeActivity.this, com.project.sagar.loctrack.GeoFenceActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_bus) {
            android.content.Intent i=new android.content.Intent(this, BusTrack.class);
            startActivity(i);
        } else if (id == R.id.nav_geobound){
            android.content.Intent i=new android.content.Intent(this, com.project.sagar.loctrack.GeoBound.class);
            startActivity(i);
        }else if(id==R.id.nav_geoalarm){
            android.content.Intent i=new android.content.Intent(this, com.project.sagar.loctrack.GeoMessageActivity.class);
            startActivity(i);
        }else if(id==R.id.nav_bus){
            android.content.Intent i=new android.content.Intent(this, BusTrack.class);
            startActivity(i);
        }else if(id== com.project.sagar.loctrack.R.id.nav_park){
            android.content.Intent i=new android.content.Intent(this, ParkMe.class);
            startActivity(i);
        }else if(id== com.project.sagar.loctrack.R.id.nav_settings){
            android.content.Intent i=new android.content.Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void geofenceCall(android.view.View view) {
        android.content.Intent i=new android.content.Intent(this, com.project.sagar.loctrack.GeoFenceActivity.class);
        startActivity(i);
    }

    public void geoboundCall(android.view.View view) {
        android.content.Intent i=new android.content.Intent(this, com.project.sagar.loctrack.GeoBound.class);
        startActivity(i);
    }

    public void geoAlarmCall(android.view.View view) {
        android.content.Intent i=new android.content.Intent(this, GeoMessageActivity.class);
        startActivity(i);
    }

    public void buscall(android.view.View view) {
        android.content.Intent i=new android.content.Intent(this, BusTrack.class);
        startActivity(i);
    }

    public void parkcall(android.view.View view) {
        android.content.Intent i=new android.content.Intent(this, ParkMe.class);
        startActivity(i);
    }
}
