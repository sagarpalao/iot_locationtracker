package com.project.sagar.loctrack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.project.sagar.loctrack.Common.*;

public class ParkMe extends AppCompatActivity implements com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener {

    int PLACE_PICKER_REQUEST = 0;
    String location;
    android.webkit.WebView parkview;

    @Override
    public void onConnected(@android.support.annotation.Nullable android.os.Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult connectionResult) {

    }

    private com.google.android.gms.common.api.GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parkview=(android.webkit.WebView)findViewById(R.id.webview);
        android.webkit.WebSettings webSettings = parkview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder builder = new com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(ParkMe.this), PLACE_PICKER_REQUEST);
                } catch (com.google.android.gms.common.GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (com.google.android.gms.common.GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGoogleApiClient = new com.google.android.gms.common.api.GoogleApiClient
                .Builder(this)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .addApi(com.google.android.gms.location.places.Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if(resultCode== android.app.Activity.RESULT_OK) {
                com.google.android.gms.location.places.Place place = com.google.android.gms.location.places.ui.PlacePicker.getPlace(ParkMe.this,data);
                String toastMsg = String.format("Place: %s %s ", place.getName(),place.getAddress());
                android.widget.Toast.makeText(ParkMe.this, toastMsg, android.widget.Toast.LENGTH_LONG).show();
                location=place.getAddress().toString();
                location="http://en.parkopedia.in/parking/"+location;
                int width=parkview.getWidth();
                int height=parkview.getHeight();
                String url="http://"+Common.IP+"/LocTrackWeb/parkme.php"+"?loc="+location+"&width="+width+"&height="+height;
                parkview.loadUrl(url);

            }
            else if (resultCode == com.google.android.gms.location.places.ui.PlacePicker.RESULT_ERROR) {
                com.google.android.gms.common.api.Status status = com.google.android.gms.location.places.ui.PlaceAutocomplete.getStatus(ParkMe.this, data);
                // TODO: Handle the error.
                android.util.Log.i("hi", status.getStatusMessage());

            } else if (resultCode == android.app.Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

}
