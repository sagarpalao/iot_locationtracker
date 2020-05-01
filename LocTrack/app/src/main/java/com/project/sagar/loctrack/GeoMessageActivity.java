package com.project.sagar.loctrack;

import static com.project.sagar.loctrack.CommonUtilities.TAG;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.common.api.*;
import com.google.android.gms.location.places.ui.*;
import com.google.android.gms.location.places.*;
import android.widget.*;
import com.google.android.gms.common.api.GoogleApiClient.*;
import android.support.v7.widget.*;

public class GeoMessageActivity extends AppCompatActivity {

    java.util.ArrayList<MsgD> myDataset=new java.util.ArrayList();
    java.util.Map<String, String> params;
    private com.google.android.gms.common.api.GoogleApiClient mGoogleApiClient;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    android.os.AsyncTask<String, Void, Boolean> atask;
    Thread worker;
    android.content.Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.content.Intent i=new android.content.Intent(getApplicationContext(), AddGeoMsg.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGoogleApiClient = new com.google.android.gms.common.api.GoogleApiClient
                .Builder(this)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .addApi(com.google.android.gms.location.places.Places.PLACE_DETECTION_API)
                .build();

        mRecyclerView = (RecyclerView)findViewById(R.id.msglist);

        mLayoutManager = new android.support.v7.widget.LinearLayoutManager(this);
        myDataset.clear();
        mAdapter = new MyAdapterMsg(myDataset,mGoogleApiClient,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mContext=getApplicationContext();

        worker = new Thread(new Runnable(){

            private void updateUI(org.json.JSONObject j)
            {
                final org.json.JSONObject jsono=j;
                if(worker.isInterrupted()){
                    return;
                }
                runOnUiThread(new Runnable(){

                    @Override
                    public void run()
                    {
                        try {
                            int i=0;
                            MsgD p;
                            org.json.JSONArray toparr=jsono.getJSONArray("top");
                            org.json.JSONObject obj;
                            for (i=0;i<toparr.length();i++){
                                try {
                                    obj = toparr.getJSONObject(i);
                                    p = new MsgD(obj.getString("message"),obj.getString("addr"),obj.getString("placeid"));
                                    //Toast.makeText(getApplicationContext(),"add", android.widget.Toast.LENGTH_LONG).show();
                                    myDataset.add(p);
                                    android.util.Log.i("adding","Adding");
                                }
                                catch(Exception e){}
                            }
                            mAdapter.notifyDataSetChanged();
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
                    org.apache.http.impl.client.DefaultHttpClient httpclient = new org.apache.http.impl.client.DefaultHttpClient(new org.apache.http.params.BasicHttpParams());
                    org.apache.http.client.methods.HttpPost httppost = new org.apache.http.client.methods.HttpPost("http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/getmessages.php");

                    // Depends on your web service
                    httppost.setHeader("Content-type", "application/json");

                    java.io.InputStream inputStream = null;
                    String result = null;
                    org.apache.http.HttpResponse response = httpclient.execute(httppost);
                    // StatusLine stat = response.getStatusLine();
                    int status = response.getStatusLine().getStatusCode();
                    if (status == 200) {
                        org.apache.http.HttpEntity entity = response.getEntity();
                        String data = org.apache.http.util.EntityUtils.toString(entity);
                        org.json.JSONObject jsono = new org.json.JSONObject(data);
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

        io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener listener = new io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener.Builder(
                mRecyclerView,
                new io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(View view) {
                        int id = mRecyclerView.getChildPosition(view);
                        MsgD p = myDataset.get(id);
                        String place = p.placeid;
                        myDataset.remove(id);
                        mAdapter.notifyDataSetChanged();
                        params = new java.util.HashMap<String, String>();
                        params.put("placeid", place);
                        String url = "http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/removegeomessage.php";
                        new ServerPutAsyncTask().execute(url);
                        Toast.makeText(getApplicationContext(),"Message Deleted", Toast.LENGTH_LONG).show();

                        }
                })
                .setIsVertical(false)
                .create();

        mRecyclerView.setOnTouchListener(listener);

    }


    public void deleteCall(View view){

    }
    @Override
    protected void onPause() {
        super.onPause();
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


}
