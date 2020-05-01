package com.project.sagar.loctrack;

/**
 * Created by SAGAR on 1/1/2016.
 */
import static com.project.sagar.loctrack.CommonUtilities.TAG;

import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.common.api.*;
import com.google.android.gms.location.places.ui.*;
import com.google.android.gms.location.places.*;
import android.widget.*;
import com.google.android.gms.common.api.GoogleApiClient.*;
import android.support.v7.widget.*;
import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;


public class CustomFragment extends Fragment implements ConnectionCallbacks,OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    int PLACE_PICKER_REQUEST = 0;
    private Button mButton;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String str[]={};
    String stra[]={};
    java.util.ArrayList<PlaceD> myDataset=new java.util.ArrayList();
    java.util.Map<String, String> params;
    //java.util.ArrayList myDataset;
    android.app.Activity mcontext;
    Thread worker;

    public CustomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mcontext=this.getActivity();
    }

    public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if(resultCode== android.app.Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(),data);
                String toastMsg = String.format("Place: %s %s ", place.getName(),place.getAddress());
                android.widget.Toast.makeText(getActivity(), toastMsg, android.widget.Toast.LENGTH_LONG).show();
                PlaceD p=new PlaceD(place.getName().toString(),place.getAddress().toString());
                myDataset.add(p);
                mAdapter.notifyDataSetChanged();

                params = new java.util.HashMap<String, String>();
                params.put("place",place.getName().toString() );
                params.put("addr", place.getAddress().toString());
                params.put("placeid", place.getId());

                String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/addnoloc.php";
                new ServerPutAsyncTask().execute(url);
            }
            else if (resultCode == PlacePicker.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                android.util.Log.i("hi", status.getStatusMessage());

            } else if (resultCode == android.app.Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == android.app.Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                //android.util.Log.i("hi", "Place: " + place.getName());
                String toastMsg = String.format("Place: %s %s ", place.getName(),place.getAddress());
                android.widget.Toast.makeText(getActivity(), toastMsg, android.widget.Toast.LENGTH_LONG).show();
                PlaceD p=new PlaceD(place.getName().toString(),place.getAddress().toString());
                myDataset.add(p);
                mAdapter.notifyDataSetChanged();

                params = new java.util.HashMap<String, String>();
                params.put("place",place.getName().toString() );
                params.put("addr", place.getAddress().toString());
                params.put("placeid", place.getId());

                String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/addnoloc.php";
                new ServerPutAsyncTask().execute(url);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                android.util.Log.i("hi", status.getStatusMessage());

            } else if (resultCode == android.app.Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    public void handlePlaceT(){
        try {
            android.content.Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (com.google.android.gms.common.GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (com.google.android.gms.common.GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    public void handlePlaceP(){
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (com.google.android.gms.common.GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (com.google.android.gms.common.GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
                                          android.os.Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_custom, container, false);
        int i;
        for(i=0;i<str.length;i++){
            PlaceD p=new PlaceD(str[i],stra[i]);
            myDataset.add(p);
        }
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.no_view);
        mAdapter = new MyAdapterNo(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        String url = "http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/getnoloc.php";
        new ServerGetAsyncTask().execute(url);

        SwipeDismissRecyclerViewTouchListener listener = new SwipeDismissRecyclerViewTouchListener.Builder(
                mRecyclerView,
                new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(View view) {
                        final View v=view;
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                        builder.setTitle("Confirm");
                        builder.setMessage("Are you sure you want to delete the RED place?");
                        builder.setPositiveButton("YES", new android.content.DialogInterface.OnClickListener() {
                            public void onClick(android.content.DialogInterface dialog, int which) {
                                deleteCall(v);
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("NO", new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(android.content.DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        android.support.v7.app.AlertDialog alert = builder.create();
                        alert.show();

                    }
                })
                .setIsVertical(false)
                .create();

        mRecyclerView.setOnTouchListener(listener);

        return rootView;

    }
    public void deleteCall(View view){
        int id = mRecyclerView.getChildPosition(view);

        PlaceD p=myDataset.get(id);
        String place=p.place;
        String addr=p.addr;
        myDataset.remove(id);
        mAdapter.notifyDataSetChanged();

        params = new java.util.HashMap<String, String>();
        params.put("place",place);
        params.put("addr",addr);

        String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/removenoloc.php";
        new ServerPutAsyncTask().execute(url);
        Toast.makeText(getActivity(), String.format("Delete item %s %s",place,addr),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnected(@android.support.annotation.Nullable android.os.Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult connectionResult) {

    }

    private void showDialog(String msg){
        android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setTitle("alert")
                .setMessage(msg)
                .setCancelable(false)
                .create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
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


                @Override
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
                        android.util.Log.v("Post", String.valueOf(status));
                        out.close();
                        // handle the response

                        if (status != 200) {
                            android.util.Log.d("Post", String.valueOf(status));
                        }
                    } catch (Exception e) {
                        android.util.Log.d("Post", e.toString());
                    } finally {
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }

                }

            });
            worker.start();
        }

    }

    class ServerGetAsyncTask{

        public void execute(String u){
            java.net.URL ur=null;
            try {
                ur= new java.net.URL(u);
            }
            catch(Exception e){}

            final String url=ur.toString();

            worker = new Thread(new Runnable(){

                private void updateUI(org.json.JSONObject j)
                {
                    final org.json.JSONObject jsono=j;
                    if(worker.isInterrupted()){
                        return;
                    }
                    mcontext.runOnUiThread(new Runnable(){

                        @Override
                        public void run()
                        {
                            try {
                                int i=0;
                                PlaceD p;
                                org.json.JSONArray toparr=jsono.getJSONArray("top");
                                org.json.JSONObject obj;
                                for (i=0;i<toparr.length();i++){
                                    try {
                                        obj = toparr.getJSONObject(i);
                                        p = new PlaceD(obj.getString("place"), obj.getString("addr"));
                                        myDataset.add(p);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                    catch(Exception e){}
                                }
                            }
                            catch(Exception e){

                            }
                        }
                    });
                }

                private org.json.JSONObject download()
                {
                    try {
                        //------------------>>
                        org.json.JSONObject jsono;
                        org.apache.http.client.methods.HttpGet httppost = new org.apache.http.client.methods.HttpGet(url);
                        org.apache.http.client.HttpClient httpclient = new org.apache.http.impl.client.DefaultHttpClient();
                        org.apache.http.HttpResponse response = httpclient.execute(httppost);
                        // StatusLine stat = response.getStatusLine();
                        int status = response.getStatusLine().getStatusCode();
                        if (status == 200) {
                            org.apache.http.HttpEntity entity = response.getEntity();
                            String data = org.apache.http.util.EntityUtils.toString(entity);
                            jsono = new org.json.JSONObject(data);
                            return jsono;
                        }
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
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

