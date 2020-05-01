package com.project.sagar.loctrack;

import static com.project.sagar.loctrack.CommonUtilities.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.google.android.gms.location.places.ui.*;
import com.google.android.gms.location.places.*;

public class AddGeoMsg extends AppCompatActivity {

    String mplace;
    String maddr;
    String mplaceid;
    String mmsg;
    java.util.HashMap<String, String> params;
    android.widget.EditText txtmsg;
    Thread worker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_geo_msg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabadd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(android.text.TextUtils.isEmpty(mplaceid) || android.text.TextUtils.isEmpty(txtmsg.getText().toString())){
                    android.widget.Toast.makeText(getApplicationContext(),"Insufficient data", android.widget.Toast.LENGTH_LONG).show();
                }
                else{
                    params = new java.util.HashMap<String, String>();
                    params.put("placeid",mplaceid);
                    params.put("addr",maddr);
                    params.put("place",mplace);
                    params.put("message",txtmsg.getText().toString());
                    android.util.Log.i("a","a");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/savegeomessage.php";
                    new ServerPutAsyncTask().execute(url);
                    android.widget.Toast.makeText(getApplicationContext(),"Message Added", android.widget.Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(2000);
                    }
                    catch(Exception e){}
                    onBackPressed();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        txtmsg=(android.widget.EditText)findViewById(R.id.editText);


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                mplace=place.getName().toString();
                maddr=place.getAddress().toString();
                mplaceid=place.getId();
                android.util.Log.i("hi", "Place: " + place.getName());
            }

            @Override
            public void onError(com.google.android.gms.common.api.Status status) {
                // TODO: Handle the error.
                android.util.Log.i("hi", "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, GeoMessageActivity.class));
    }

    class ServerPutAsyncTask{

        public void execute(String u){
            java.net.URL ur=null;
            try {
                ur= new java.net.URL(u);
            }
            catch(Exception e){}

            final java.net.URL url=ur;
            worker = new Thread(new Runnable(){

                public void run()
                {
                    StringBuilder bodyBuilder = new StringBuilder();
                    java.util.Iterator<java.util.Map.Entry<String, String>> iterator = params.entrySet().iterator();
                    // constructs the POST body using the parameters
                    while (iterator.hasNext()) {
                        java.util.Map.Entry<String, String> param = iterator.next();
                        bodyBuilder.append(param.getKey()).append('=')
                                .append(param.getValue());
                        if (iterator.hasNext()) {
                            bodyBuilder.append('&');
                        }
                    }
                    String body = bodyBuilder.toString();
                    android.util.Log.v(TAG, "Posting '" + body + "' to " + url);
                    byte[] bytes = body.getBytes();
                    java.net.HttpURLConnection conn = null;
                    try {
                        android.util.Log.e("URL", "> " + url);
                        conn = (java.net.HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setUseCaches(false);
                        conn.setFixedLengthStreamingMode(bytes.length);
                        conn.setRequestMethod("POST");
                        //conn.setRequestProperty("Content-Type",
                        //        "application/x-www-form-urlencoded;charset=UTF-8");
                        // post the request
                        java.io.OutputStream out = conn.getOutputStream();
                        out.write(bytes);
                        java.io.InputStream in = conn.getInputStream();

                        int status = conn.getResponseCode();
                        android.util.Log.v("Post" , String.valueOf(status));
                        out.close();
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
}
