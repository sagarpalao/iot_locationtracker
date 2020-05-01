package com.project.sagar.loctrack;

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


public class MapActivity extends android.support.v7.app.AppCompatActivity implements com.google.android.gms.maps.OnMapReadyCallback {

    GoogleMap map;
    Polyline line;
    PolylineOptions lineopt = new PolylineOptions();
    String url = "http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/getLoc.php";
    Marker marker;
    LatLng prev;
    double ptime;
    android.widget.TextView txtspeed;
    android.widget.TextView txtaddr;
    android.content.BroadcastReceiver rcvr;
    android.content.IntentFilter fp;
    boolean r=false;
    Thread worker;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    static final double M_PI=3.14;


    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.project.sagar.loctrack.R.layout.activity_map);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(com.project.sagar.loctrack.R.id.toolbar);
        setSupportActionBar(toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        com.google.android.gms.maps.SupportMapFragment mapFragment = (com.google.android.gms.maps.SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.project.sagar.loctrack.R.id.map);
        mapFragment.getMapAsync(this);
        com.sothree.slidinguppanel.SlidingUpPanelLayout slide = (com.sothree.slidinguppanel.SlidingUpPanelLayout) findViewById(com.project.sagar.loctrack.R.id.slideup);
        slide.setAnchorPoint(0.5f);
        txtspeed=(android.widget.TextView)findViewById(com.project.sagar.loctrack.R.id.txtspeed);
        txtaddr=(android.widget.TextView)findViewById(com.project.sagar.loctrack.R.id.txtloc);


        fp = new android.content.IntentFilter();
        fp.addAction("com.androidhive.location.DISPLAY_LOCATION");

        //---when the sms is recieved---
        rcvr=new android.content.BroadcastReceiver() {
            @Override
            public void onReceive(android.content.Context arg0, android.content.Intent arg1) { //Processing for recieved broadcast happens here
                new com.project.sagar.loctrack.MapActivity.JSONAsyncTask().execute(url);
            }
        };

    }

    @Override
    protected void onPause() {
        if(r) {
            unregisterReceiver(rcvr);
            r=false;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
        android.util.Log.e("activity", "done");
        map = googleMap;
        if(!r) {
            registerReceiver(rcvr, fp);
            r = true;
        }
        new JSONAsyncTask().execute(url);

    }

    class JSONAsyncTask{
        double lat, longi,time;

        public void execute(String u){

            java.net.URL ur=null;
            try {
                ur= new java.net.URL(u);
            }
            catch(Exception e){}

            final String url=ur.toString();

            worker = new Thread(new Runnable(){

                private void updateUI()
                {
                    if(worker.isInterrupted()){
                        return;
                    }
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run()
                        {
                            LatLng carLoc = new LatLng(lat, longi);
                            double s=0;
                            Double ss=new Double(s);
                            if(prev!=null) {
                                s = speed(prev, carLoc,ptime,time);

                                ss=new java.math.BigDecimal(s).setScale(3, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
                            }
                            txtspeed.setText(ss.toString()+" kmph");
                            new com.project.sagar.loctrack.MapActivity.GetAddressTask().execute(carLoc);
                            lineopt.add(carLoc)
                                    .width(25)
                                    .color(android.graphics.Color.BLUE)
                                    .geodesic(true);
                            map.addPolyline(lineopt);
                            if (marker != null)
                                marker.remove();
                            com.google.android.gms.maps.model.CameraPosition position = new com.google.android.gms.maps.model.CameraPosition.Builder()
                                    .target(carLoc)
                                    .zoom(18)
                                    .tilt(90)
                                    .build();
                            map.animateCamera(com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition(position));
                            marker = map.addMarker(new com.google.android.gms.maps.model.MarkerOptions()
                                    .position(carLoc)
                                    .title("Current Car Loction")
                                    .icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource(com.project.sagar.loctrack.R.drawable.car)));
                            prev=new LatLng(lat,longi);
                            ptime=time;
                        }
                    });
                }

                private void download()
                {
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
                            time=jsono.getDouble("time");

                        }
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void run()
                {
                    android.util.Log.d(TAG, "Thread run()");
                    download();
                    updateUI();
                }

            });
            worker.start();
        }

    }

    public class GetAddressTask{

        android.app.Activity mContext;
        String mStreet=null,
        mHouseNumber=null,
        mLatitude=null,
        mLongtitude=null,
        mPostalCode=null,
        mCity=null,
        mCountry=null,
        maddr=null;

        public GetAddressTask () {
            super();
            mContext = (android.app.Activity)MapActivity.this;
        }

        public void execute(LatLng p){

            final LatLng param=p;

            worker = new Thread(new Runnable(){

                private void updateUI(String s)
                {
                    final String str=s;
                    if(worker.isInterrupted()){
                        return;
                    }
                    mContext.runOnUiThread(new Runnable(){

                        @Override
                        public void run()
                        {
                            txtaddr.setText(str);
                            //android.widget.Toast.makeText(MapActivity.this,str.toString(), android.widget.Toast.LENGTH_LONG).show();
                        }
                    });
                }

                private String download()
                {

                    Geocoder geocoder =
                            new Geocoder(mContext, java.util.Locale.getDefault());
                    LatLng location = param;
                    double lat=location.latitude;
                    double longi=location.longitude;
                    java.util.List<android.location.Address> addresses = null;
                    try {
                        if (location != null) {
                            addresses = geocoder.getFromLocation( lat ,longi, 1);
                        }
                    } catch (IOException exception) {
                        android.util.Log.e("ComplaintLocation",
                                "IO Exception in getFromLocation()", exception);

                        return ("IO Exception trying to get address");

                    } catch (IllegalArgumentException exception) {
                        String errorString = "Illegal arguments " +
                                Double.toString(location.latitude) + " , " +
                                Double.toString(location.longitude) + " passed to address service";
                        android.util.Log.e("LocationSampleActivity", errorString, exception);

                        return errorString;
                    }
                    StringBuilder str=new StringBuilder(" ");
                    String s=null;
                    String explode[];
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);

                        if (address.getMaxAddressLineIndex() > 0) {
                            int i,j;
                            for(i=0;i<=address.getMaxAddressLineIndex();i++) {
                                s = address.getAddressLine(i);
                                explode = android.text.TextUtils.split(s, ", ");
                                for (j = 0; j < explode.length; j++) {
                                    str.append(explode[j] + "\n");
                                }
                                if (explode.length == 0)
                                    str.append(s + "\n");
                            }
                            return str.toString();
                        }
                    }
                    return "No address found";

                }

                @Override
                public void run()
                {
                    android.util.Log.d(TAG, "Thread run()");
                    updateUI(download());
                }

            });
            worker.start();
        }

    }

    double distance_on_geoid(double lat1, double lon1, double lat2, double lon2) {

        // Convert degrees to radians
        lat1 = lat1 * M_PI / 180.0;
        lon1 = lon1 * M_PI / 180.0;

        lat2 = lat2 * M_PI / 180.0;
        lon2 = lon2 * M_PI / 180.0;

        // radius of earth in metres
        double r = 6378100;

        // P
        double rho1 = r * cos(lat1);
        double z1 = r * sin(lat1);
        double x1 = rho1 * cos(lon1);
        double y1 = rho1 * sin(lon1);

        // Q
        double rho2 = r * cos(lat2);
        double z2 = r * sin(lat2);
        double x2 = rho2 * cos(lon2);
        double y2 = rho2 * sin(lon2);

        // Dot product
        double dot = (x1 * x2 + y1 * y2 + z1 * z2);
        double cos_theta = dot / (r * r);

        double theta = acos(cos_theta);

        // Distance in Metres
        return r * theta;
    }

    public double speed(LatLng p1,LatLng p2,double t1,double t2){
        double dist = distance_on_geoid(p1.latitude, p1.longitude, p2.latitude, p2.longitude);
        double time_s = (t2 - t1) / 1000.0;
        double speed_mps = dist / time_s;
        double speed_kph = (speed_mps * 3600.0) / 1000.0;
        return speed_kph;
    }
}


