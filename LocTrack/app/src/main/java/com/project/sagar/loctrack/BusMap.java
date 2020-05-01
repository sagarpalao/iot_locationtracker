package com.project.sagar.loctrack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.google.android.gms.maps.model.*;
import static com.project.sagar.loctrack.CommonUtilities.TAG;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.os.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.sagar.loctrack.Common;
import android.location.*;
import android.content.*;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.google.android.gms.maps.model.*;
import com.project.sagar.loctrack.*;
import static  java.lang.Math.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.*;
import com.google.android.gms.vision.barcode.Barcode.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class BusMap extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, android.location.LocationListener,OnMapReadyCallback {


    android.widget.TextView txtbusno, txttimebus, txttimeu;
    String bus_no;
    String stop;
    com.google.android.gms.maps.model.LatLng melatlng,buslatlng,stoplatlng;
    String bustime,metime;
    public com.google.android.gms.maps.GoogleMap map;
    android.content.BroadcastReceiver rcvr;
    android.content.IntentFilter fp;
    boolean r = false;
    Thread worker;
    Marker busmarker,memarker,stopmarker;
    private com.google.android.gms.common.api.GoogleApiClient client;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 10000;
    private static int FATEST_INTERVAL = 5000;
    private static int DISPLACEMENT = 10;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    protected boolean gps_enabled,network_enabled;
    LatLngBounds.Builder builder;

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        android.util.Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        android.util.Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        android.util.Log.d("Latitude","status");
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mGoogleApiClient.isConnected() && mRequestLocationUpdates) {
            startLocationUpdates();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        if (r) {
            unregisterReceiver(rcvr);
            r = false;
        }
    }

    protected void startLocationUpdates() {
        /*try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        catch(SecurityException e){}*/
    }

    protected void stopLocationUpdates() {
       /* LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);*/
    }

    @Override
    public void onConnected(Bundle bundle) {

        if(mRequestLocationUpdates) {
            startLocationUpdates();
        }
        displayLocation();
    }

    private void displayLocation() {
        /*try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longtitude = mLastLocation.getLongitude();
                melatlng=new LatLng(latitude,longtitude);
                if (memarker != null) memarker.remove();
                memarker = map.addMarker(new com.google.android.gms.maps.model.MarkerOptions()
                        .position(melatlng)
                        .title("Your Loction")
                        .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource(com.project.sagar.loctrack.R.drawable.me)));
                new JSONAsyncTask2().execute();
            } else {
                android.util.Log.d("loc","noloc3");
            }
        }
        catch(SecurityException e){}*/
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //displayLocation();
        //android.widget.Toast.makeText(getApplicationContext(),mLastLocation.getLatitude()+"\n"+mLastLocation.getLongitude(), android.widget.Toast.LENGTH_SHORT).show();
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longtitude = mLastLocation.getLongitude();
            melatlng=new LatLng(latitude,longtitude);
            if (memarker != null) memarker.remove();
            memarker = map.addMarker(new com.google.android.gms.maps.model.MarkerOptions()
                    .position(melatlng)
                    .title("Your Location")
                    .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource(com.project.sagar.loctrack.R.drawable.me)));
            new JSONAsyncTask2().execute();

            CameraPosition position = new CameraPosition.Builder()
                    .target(stoplatlng)
                    .tilt(90)
                    .build();
            builder = new LatLngBounds.Builder();
            builder.include(busmarker.getPosition());
            builder.include(memarker.getPosition());
            builder.include(stopmarker.getPosition());
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory
                    .newLatLngBounds(bounds,50);
            map.animateCamera(cu);

        } else {
            android.util.Log.d("loc","noloc3");
        }


        }

    protected void onCreate(android.os.Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(com.project.sagar.loctrack.R.layout.activity_bus_map);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(com.project.sagar.loctrack.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context=this.getApplicationContext();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(com.google.android.gms.location.LocationServices.API)
                .build();
        createLocationRequest();

        com.google.android.gms.maps.SupportMapFragment mapFragment = (com.google.android.gms.maps.SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.project.sagar.loctrack.R.id.map);
        mapFragment.getMapAsync(this);

        com.sothree.slidinguppanel.SlidingUpPanelLayout slide = (com.sothree.slidinguppanel.SlidingUpPanelLayout) findViewById(com.project.sagar.loctrack.R.id.slideup);
        slide.setAnchorPoint(0.5f);

        bus_no = getIntent().getStringExtra("busno");
        stop = getIntent().getStringExtra("stop")+"+maharashtra+india";
        stoplatlng=getLocationFromAddress(stop);

        txttimebus=(android.widget.TextView)findViewById(com.project.sagar.loctrack.R.id.txtbustime);
        txttimeu=(android.widget.TextView)findViewById(com.project.sagar.loctrack.R.id.txtyoutime);
        txtbusno=(android.widget.TextView)findViewById(R.id.txtbusno);
        txtbusno.setText(bus_no);
        fp = new android.content.IntentFilter();
        fp.addAction("com.androidhive.location.DISPLAY_LOCATION");

        //---when the sms is recieved---
        rcvr = new android.content.BroadcastReceiver() {
            @Override
            public void onReceive(android.content.Context arg0, android.content.Intent arg1) {
                //Processing for recieved broadcast happens here
                String url = "http://" + Common.IP + "/LocTrackWeb/getLoc.php";
                new JSONAsyncTask().execute(url);
            }
        };

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        catch(SecurityException e){}


    }


    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        android.util.Log.e("activity", "done");
        map = googleMap;
        if (!r) {
            registerReceiver(rcvr, fp);
            r = true;
        }

        stopmarker = map.addMarker(new com.google.android.gms.maps.model.MarkerOptions()
                .position(stoplatlng)
                .title("Bus Stop")
                .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource(com.project.sagar.loctrack.R.drawable.stop)));
        String url = "http://" + Common.IP + "/LocTrackWeb/getLoc.php";
        new JSONAsyncTask().execute(url);

        CameraPosition position = new CameraPosition.Builder()
                .target(stoplatlng)
                .tilt(90)
                .build();
        builder = new LatLngBounds.Builder();
/*
        builder.include(busmarker.getPosition());
        builder.include(stopmarker.getPosition());
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory
                .newLatLngBounds(bounds, 0);
        map.animateCamera(cu);*/
    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        android.util.Log.i(TAG, "Connection failed: " + connectionResult.getErrorCode());
    }

    class JSONAsyncTask {
        double lat, longi, time;

        public void execute(String u) {

            java.net.URL ur = null;
            try {
                ur = new java.net.URL(u);
            } catch (Exception e) {
            }

            final String url = ur.toString();

            worker = new Thread(new Runnable() {

                private void updateUI() {
                    if (worker.isInterrupted()) {
                        return;
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if(busmarker!=null)busmarker.remove();
                            busmarker = map.addMarker(new com.google.android.gms.maps.model.MarkerOptions()
                                    .position(buslatlng)
                                    .title("Current Bus Loction")
                                    .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource(com.project.sagar.loctrack.R.drawable.bus)));
                            txttimebus.setText(bustime);
                            builder.include(busmarker.getPosition());
                            builder.include(stopmarker.getPosition());
                            LatLngBounds bounds = builder.build();
                            CameraUpdate cu = CameraUpdateFactory
                                    .newLatLngBounds(bounds, 50);
                            map.animateCamera(cu);

                        }
                    });
                }

                private void download() {
                    try {
                        //------------------>>
                        org.apache.http.client.methods.HttpGet httppost = new org.apache.http.client.methods.HttpGet(url);
                        org.apache.http.client.HttpClient httpclient = new org.apache.http.impl.client.DefaultHttpClient();
                        org.apache.http.HttpResponse response = httpclient.execute(httppost);
                        // StatusLine stat = response.getStatusLine();
                        int status = response.getStatusLine().getStatusCode();
                        if (status == 200) {
                            org.apache.http.HttpEntity entity = response.getEntity();
                            String data = org.apache.http.util.EntityUtils.toString(entity);
                            org.json.JSONObject jsono = new org.json.JSONObject(data);
                            lat = jsono.getDouble("lat");
                            longi = jsono.getDouble("long");
                            time = jsono.getDouble("time");
                        }
                        buslatlng = new LatLng(lat, longi);
                        String url2="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+buslatlng.latitude+","+buslatlng.longitude+"&destinations="+stoplatlng.latitude+","+stoplatlng.longitude+"&mode=driving&language=en-us&traffic_model=best_guess&departure_time=now&key=AIzaSyDFOus2FkIVRktRCg2gMtscciCe44FKdMQ";
                        org.apache.http.client.methods.HttpGet httppost2 = new org.apache.http.client.methods.HttpGet(url2);
                        org.apache.http.client.HttpClient httpclient2 = new org.apache.http.impl.client.DefaultHttpClient();
                        org.apache.http.HttpResponse response2 = httpclient2.execute(httppost2);
                        // StatusLine stat = response.getStatusLine();
                        int status2 = response2.getStatusLine().getStatusCode();
                        if (status2 == 200) {
                            org.apache.http.HttpEntity entity = response2.getEntity();
                            String data = org.apache.http.util.EntityUtils.toString(entity);
                            org.json.JSONObject jsono = new org.json.JSONObject(data);
                            bustime=jsono.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration_in_traffic").getString("text");
                        }

                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void run() {
                    android.util.Log.d(TAG, "Thread run()");
                    download();
                    updateUI();
                }

            });
            worker.start();
        }

    }

    class JSONAsyncTask2 {
        double lat, longi, time;

        public void execute() {
            worker = new Thread(new Runnable() {

                private void updateUI() {
                    if (worker.isInterrupted()) {
                        return;
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            txttimeu.setText(metime);
                        }
                    });
                }

                private void download() {
                    try {

                        String url2="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+melatlng.latitude+","+melatlng.longitude+"&destinations="+stoplatlng.latitude+","+stoplatlng.longitude+"&mode=walking&language=en-us&key=AIzaSyDFOus2FkIVRktRCg2gMtscciCe44FKdMQ";
                        org.apache.http.client.methods.HttpGet httppost2 = new org.apache.http.client.methods.HttpGet(url2);
                        org.apache.http.client.HttpClient httpclient2 = new org.apache.http.impl.client.DefaultHttpClient();
                        org.apache.http.HttpResponse response2 = httpclient2.execute(httppost2);
                        // StatusLine stat = response.getStatusLine();
                        int status2 = response2.getStatusLine().getStatusCode();
                        if (status2 == 200) {
                            org.apache.http.HttpEntity entity = response2.getEntity();
                            String data = org.apache.http.util.EntityUtils.toString(entity);
                            org.json.JSONObject jsono = new org.json.JSONObject(data);
                            metime=jsono.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getString("text");
                        }

                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void run() {
                    android.util.Log.d(TAG, "Thread run()");
                    download();
                    updateUI();
                }

            });
            worker.start();
        }

    }

    public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        java.util.List<android.location.Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            android.location.Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng((location.getLatitude()),
                    (location.getLongitude() ));

            return p1;
        }
        catch(Exception e){}
        return null;
    }

}
