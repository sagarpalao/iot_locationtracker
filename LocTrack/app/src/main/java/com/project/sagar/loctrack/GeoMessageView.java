package com.project.sagar.loctrack;

import static com.project.sagar.loctrack.CommonUtilities.TAG;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class GeoMessageView extends AppCompatActivity {

    Thread worker;
    String placeid;
    android.widget.TextView txtplace,txtmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_message_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        placeid=getIntent().getStringExtra("placeid");
        //placeid="ChIJj7q7Oj635zsRFuC_tjGQDL8";
        txtplace=(android.widget.TextView)findViewById(R.id.txtplace);
        txtmsg=(android.widget.TextView)findViewById(R.id.txtmsg);

        android.util.Log.i("placeid",placeid);

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
                            MsgD p=null;
                            org.json.JSONArray toparr=jsono.getJSONArray("top");
                            org.json.JSONObject obj;
                            android.util.Log.i("adding1",String.valueOf(toparr.length()));
                            for (i=0;i<toparr.length();i++){
                                try {
                                    obj = toparr.getJSONObject(i);
                                    p = new MsgD(obj.getString("message"),obj.getString("place"),obj.getString("placeid"));
                                    //.widget.Toast.makeText(getApplicationContext(),"add", android.widget.Toast.LENGTH_LONG).show();
                                    android.util.Log.i("adding",p.txtloc+" "+p.txtmsg);
                                }
                                catch(Exception e){}
                            }
                            txtplace.setText(p.txtloc);
                            txtmsg.setText(p.txtmsg);
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
                    org.apache.http.client.methods.HttpGet httppost = new org.apache.http.client.methods.HttpGet("http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/getalert.php?pid="+placeid);

                    // Depends on your web service
                    httppost.setHeader("Content-type", "application/json");

                    java.io.InputStream inputStream = null;
                    String result = null;
                    org.apache.http.HttpResponse response = httpclient.execute(httppost);
                    // StatusLine stat = response.getStatusLine();
                    int status = response.getStatusLine().getStatusCode();
                    if (status == 200) {
                        android.util.Log.i("status",String.valueOf(status));
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

    }

}
