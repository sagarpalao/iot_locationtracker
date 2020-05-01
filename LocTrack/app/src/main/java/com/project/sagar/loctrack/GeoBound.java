package com.project.sagar.loctrack;

import static com.project.sagar.loctrack.CommonUtilities.TAG;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class GeoBound extends AppCompatActivity implements OnMapReadyCallback {


    GoogleMap map;
    Marker marker[];
    int mrkcnt=0;
    Polygon poly;
    PolygonOptions polyopt;
    boolean markerClicked=false;
    java.util.Map<String, String> params;
    Thread worker;
    android.support.v7.widget.ListViewCompat mListView;
    MyAdapterSel mAdapter;
    java.util.ArrayList<PlaceD> mDataset;
    String ll[][];

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        marker = new Marker[100];
        polyopt = new PolygonOptions();

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(com.google.android.gms.maps.model.LatLng latLng) {
                clicked(latLng);
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(com.google.android.gms.maps.model.LatLng latLng) {
                click(latLng);
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {

                return markerclicked(marker);
            }
        });


    }

    private void click(com.google.android.gms.maps.model.LatLng latLng) {
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        markerClicked = false;
    }

    private boolean markerclicked(Marker marker) {
        if (markerClicked) {

            if (poly != null) {
                poly.remove();
                poly = null;
            }

            polyopt.add(marker.getPosition());
            polyopt.fillColor(R.color.fill);
            poly = map.addPolygon(polyopt);
        } else {
            if (poly != null) {
                poly.remove();
                poly = null;
            }

            polyopt = new PolygonOptions().add(marker.getPosition());
            markerClicked = true;
        }

        return true;
    }



    private void clicked(LatLng latLng) {
        ll[mrkcnt][0]=String.valueOf(latLng.latitude);
        ll[mrkcnt][1]=String.valueOf(latLng.longitude);
        marker[mrkcnt]=map.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
        mrkcnt++;
        //android.widget.Toast.makeText(this,String.valueOf(mrkcnt), android.widget.Toast.LENGTH_LONG).show();
        new com.project.sagar.loctrack.GeoBound.GetAddressTask().execute(latLng);
        markerClicked = false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_bound);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ll=new String[100][2];

        FloatingActionButton fabdone = (FloatingActionButton) findViewById(R.id.fabdone);
        fabdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i;
                String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/removepoly.php";
                new ServerPutAsyncTask().execute(url);

                for(i=0;i<mrkcnt;i++){
                    android.util.Log.d("thisone",ll[i][0]+" "+ll[i][1]);
                }

                for(i=0;i<mrkcnt;i++){
                    String url2="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/addpoly.php?lat="+ll[i][0]+"&lng="+ll[i][1];
                    new ServerPutAsyncTask().execute(url2);
                    //android.widget.Toast.makeText(getApplicationContext(),String.valueOf(i), android.widget.Toast.LENGTH_LONG).show();
                }

                android.widget.Toast.makeText(getApplicationContext(),"Boundary Updated", android.widget.Toast.LENGTH_LONG).show();
                try{Thread.sleep(5000);}catch(Exception e){}
                onBackPressed();

            }
        });

        FloatingActionButton fabclose = (FloatingActionButton) findViewById(R.id.fabclose);
        fabclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.widget.Toast.makeText(getApplicationContext(),"Resetting...", android.widget.Toast.LENGTH_SHORT).show();
                restart();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapsel);
        mapFragment.getMapAsync(this);

        com.sothree.slidinguppanel.SlidingUpPanelLayout slide = (com.sothree.slidinguppanel.SlidingUpPanelLayout) findViewById(com.project.sagar.loctrack.R.id.slideup);
        slide.setAnchorPoint(0.5f);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView=(android.support.v7.widget.ListViewCompat)findViewById(R.id.loc_view);

        String[] values = {  };

        mDataset= new java.util.ArrayList<PlaceD>();
        for (int i = 0; i < values.length; ++i) {
            mDataset.add(new PlaceD(values[i],values[i]));
        }

        mAdapter = new MyAdapterSel(this,mDataset);
        mListView.setAdapter(mAdapter);

    }

    private void restart() {
        this.recreate();
    }

    class ServerPutAsyncTask{

        public void execute(String urls){
            java.net.URL ur=null;
            try {
                ur= new java.net.URL(urls);
            }
            catch(Exception e){}

            final java.net.URL url=ur;

            worker = new Thread(new Runnable(){

                @Override
                public void run()
                {
                    java.net.HttpURLConnection conn = null;
                    try {
                        android.util.Log.e("URL", "> " + url);
                        conn = (java.net.HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        int status = conn.getResponseCode();
                        android.util.Log.v("Post" , String.valueOf(status));
                        // handle the response
                        if (status != 200) {
                            android.util.Log.d("Post" , String.valueOf(status));
                        }
                    }
                    catch(Exception e){
                        android.util.Log.d("Post" , e.toString());
                    }
                    finally
                    {
                        if (conn != null) {
                            conn.disconnect();}
                    }
                }

            });
            worker.start();
        }

    }

    public class GetAddressTask{

        Context mContext;
        String mplace;


        public void execute(LatLng p){

            final LatLng param=p;

            worker = new Thread(new Runnable(){

                private void updateUI(String a)
                {
                    final String addr=a;
                    if(worker.isInterrupted()){
                        return;
                    }
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run()
                        {
                            mDataset.add(new PlaceD(mplace,addr));
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }

                private String download()
                {

                    Geocoder geocoder =
                            new Geocoder(getApplicationContext(), java.util.Locale.getDefault());
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

                        if(!android.text.TextUtils.isEmpty(address.getThoroughfare()))
                            mplace=address.getThoroughfare();
                        else if(!android.text.TextUtils.isEmpty(address.getLocality()))
                            mplace=address.getLocality();
                        else if(!android.text.TextUtils.isEmpty(address.getCountryName()))
                            mplace=address.getCountryName();



                        if (address.getMaxAddressLineIndex() > 0) {
                            int i,j;
                            for(i=0;i<=address.getMaxAddressLineIndex();i++){
                                s=address.getAddressLine(i);
                                explode= android.text.TextUtils.split(s, ", ");
                                for(j=0;j<explode.length;j++){
                                    str.append(explode[j]+"\n");
                                }
                                if(explode.length==0)
                                    str.append(s+"\n");
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

}
