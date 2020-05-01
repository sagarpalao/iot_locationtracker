package com.project.sagar.loctrack;


import static com.project.sagar.loctrack.CommonUtilities.TAG;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.support.v7.app.*;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity {


    static java.util.HashMap<String, String> params;
    static Thread worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_settings);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        params=new java.util.HashMap<String, String>();
    }

    public static class SettingsFragment extends PreferenceFragment implements android.content.SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(com.project.sagar.loctrack.R.xml.pref_notification);
        }

        public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, String key) {
            if (key.equals("not_geo_msg")){
                if(sharedPreferences.getBoolean("not_geo_msg",false)){
                    params.clear();
                    params.put("pref","msg");
                    params.put("value","true");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/prefchange.php";
                    new ServerPutAsyncTask().execute(url);
                }
                else{
                    params.clear();
                    params.put("pref","msg");
                    params.put("value","false");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/prefchange.php";
                    new ServerPutAsyncTask().execute(url);
                }
            }
            else if(key.equals("not_geo_fence")){
                if(sharedPreferences.getBoolean("not_geo_fence",false)){
                    params.clear();
                    params.put("pref","fence");
                    params.put("value","true");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/prefchange.php";
                    new ServerPutAsyncTask().execute(url);
                }
                else{
                    params.clear();
                    params.put("pref","fence");
                    params.put("value","false");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/prefchange.php";
                    new ServerPutAsyncTask().execute(url);
                }

            }
            else if(key.equals("not_geo_bound")){
                if(sharedPreferences.getBoolean("not_geo_bound",false)){
                    params.clear();
                    params.put("pref","bound");
                    params.put("value","true");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/prefchange.php";
                    new ServerPutAsyncTask().execute(url);
                }
                else{
                    params.clear();
                    params.put("pref","bound");
                    params.put("value","false");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/prefchange.php";
                    new ServerPutAsyncTask().execute(url);
                }

            }
            else if(key.equals("not_geo_freeze")){
                if(sharedPreferences.getBoolean("not_geo_freeze",false)){
                    params.clear();
                    params.put("pref","freeze");
                    params.put("value","true");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/prefchange.php";
                    new ServerPutAsyncTask().execute(url);
                }
                else{
                    params.clear();
                    params.put("pref","freeze");
                    params.put("value","false");
                    String url="http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/prefchange.php";
                    new ServerPutAsyncTask().execute(url);
                }

            }
            else if(key.equals("ip_text")){
                Common.IP=sharedPreferences.getString("ip_text","");
            }
        }

        android.content.SharedPreferences.OnSharedPreferenceChangeListener mListener = new android.content.SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(android.content.SharedPreferences prefs, String key) {
                // listener implementation

            }
        };

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen()
                    .getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
            //prefs.registerOnSharedPreferenceChangeListener(mListener);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen()
                    .getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

    }

    static class ServerPutAsyncTask{

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




