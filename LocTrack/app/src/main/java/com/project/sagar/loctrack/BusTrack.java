package com.project.sagar.loctrack;

import static com.project.sagar.loctrack.CommonUtilities.TAG;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BusTrack extends AppCompatActivity {

    android.widget.AutoCompleteTextView txtsrc,txtdest;
    Thread worker;
    String myDataset[]={
            "R.C.CHURCH",
            "INS ASHWINI HOSPITAL",
            "PILOT BUNDER",
            "AFGHAN CHURCH",
            "COLABA POST OFFICE",
            "COLABA BUS STATION",
            "SASOON DOCK",
            "STRAND CINEMA",
            "COLABA DEPOT",
            "DR.S.P.MUKHERJI CHOWK / MUSEUM",
            "HUTATMA CHOWK",
            "MUMBAI C.S.T.",
            "MAHATMA PHULE MARKET",
            "CHAKALA MARKET",
            "ZAKERI MASJID",
            "MINARA MASJID",
            "JOHAR CHOWK",
            "DR.M.IQBAL CHOWK",
            "A.H.ANSARI CHOWK",
            "BYCULLA STATION (E)",
            "JIJAMATA UDYAN",
            "JAIHIND CINEMA",
            "SHANTARAM PUJJARE CHOWK",
            "SANT JAGNADE CHOWK / LALBAUG",
            "BHARATMATA CINEMA",
            "MADKEBUWA CHOWK / PAREL",
            "HINDMATA CINEMA",
            "SHINDE WADI",
            "CHITRA CINEMA",
            "KHODADAD CIRCLE",
            "VEER KOTWAL UDYAN / DADAR STATION / PLAZA",
            "RAM GANESH GADKARI CHOWK (SHIVAJI PARK)",
            "CITYLIGHT CINEMA",
            "SHITALADEVI TEMPLE",
            "MAHIM",
            "MAHIM BUS STATION",
            "MAHIM KOLIWADA",
            "TELEPHONE EXCHANGE BANDRA",
            "SIR ALI YAVAR JUNG INSTITUTE",
            "AKSHAYA (BANDRA)",
            "PARIJAT BANDRA",
            "UNIT TRUST OF INDIA",
            "BANDRA RECLAMATION BUS STATION",
            "COLABA BUS STATION",
            "SASOON DOCK",
            "STRAND CINEMA",
            "COLABA DEPOT",
            "D.R.S.P.MUKHERJI CHOWK / MUSEUM",
            "HUTATMA CHOWK",
            "KHADI BHANDAR",
            "MUMBAI C.S.T",
            "MAHATMA PHULE MARKET",
            "CHAKALA MARKET",
            "ZAKERIA MARKET",
            "JOHAR MARKET",
            "DR.M.IQBAL CHOWK",
            "A.H.ANSARI CHOWK",
            "BYCULLA STATION (E)",
            "JIJAMATA UDYAN",
            "JAIHIND CINEMA",
            "SANT JAGNADE CHOWK / LALBAUG",
            "MADKEBUWA CHOWK / PAREL",
            "HINDMATA CINEMA",
            "KHODADAD CIRCLE",
            "VEER KOTWAL UDYAN / DADAR",
            "RAM GANESH GADKARI CHOWK (SHIVAJI PARK)",
            "CITYLIGHT CINEMA",
            "SHITALADEVI TEMPLE",
            "MAHIM (PARADISE)",
            "BANDRA STATION ROAD (W)",
            "BANDRA TALKIES",
            "KHAR STATION (W)",
            "ST. TERESA CONVENT SCHOOL",
            "SANTACRUZ STATION (W)",
            "KHIRA NAGAR",
            "SANTACRUZ DEPOT",
            "COLABA DEPOT",
            "DR.S.P.MUKHERJI CHOWK / MUSEUM",
            "R.B.I. (FORT)",
            "SWAMI D.SARASWATI CHOWK FORT",
            "MUMBAI C.S.T",
            "MAHATMA PHULE MARKET",
            "CHAKALA MARKET",
            "ZAKERIA MASJID",
            "JOHAR MASJID",
            "DR.M.IQBAL CHOWK",
            "A.H.ANSARI CHOWK",
            "BYCULLA STATION (W)",
            "S BRIDGE / MANDLIK CHOWK",
            "COM.GULABRAO GANACHARYA CHOWK",
            "N.M.JOSHI MARG POLICE STATION",
            "BAWLA MASJID",
            "LOWER PAREL  STATION",
            "DEEPAK CINEMA",
            "DHANMILL NAKA",
            "PAREL S.T.DEPOT / SANT ROHIDAS CHOWK",
            "JAKADEVI / DADAR POLICE STATION",
            "OUR LADY OF STARVATION SCHOOL",
            "P.THAKRE CHOWK DADAR",
            "RANADE ROAD DADAR",
            "RAM GANESH GADAKARI CHOWK (SHIVAJI CHOWK)",
            "CITYLIGHT CINEMA",
            "SHITALADEVI TEMPLE",
            "MAHIM",
            "MAHIM (PARADISE)",
            "KALA NAGAR",
            "KHER WADI JUNCTION",
            "CARDINAL GRACIOUS SCHOOL / TEACHERS COLONY",
            "MARATHA COLONY",
            "VAKOLA POLICE STATION",
            "NEW AGRIPADA",
            "MILAN SUB WAY",
            "VILE PARLE SUB-WAY",
            "DOMESTIC AIRPORT JUNCTION",
            "GUJRAT SOCIETY / DAYALDAS ROAD",
            "SATHE COLLEGE",
            "VILE PARLE STATIOM (E)",
            "PARLESHWAR MANDIR",
            "PARLE TILAK SCHOOL",
            "MATRUCHAYA",
            "HUMAN SOCIETY BLDG NO. 5",
            "SUBHASH MARG PARLE",
            "GARWARE CHOWK",
            "SHIVAJI CHOWK ANDHERI",
            "VIJAY NAGAR SOCIETY ANDHERI",
            "AGARKAR CHOWK",
            "MAHUL VILLAGE",
            "MAHUL MARKET",
            "WADALA ROAD MAHUL",
            "MAZGAON DOCK (MAHUL)",
            "ACHARYA VIDYANIKETAN MAHUL",
            "H.P. NAGAR MAHUL",
            "BPCL SPORTS CLUB MAHUL",
            "SHANKAR MANDIR",
            "VASI NAKA CHEMBUR(E)",
            "AZIZ BAUG CHEMBUR (E)",
            "MARAWALI CHURCH CHEMBUR(E)",
            "CHEMBUR COLONY (E)",
            "NAVJEEVAN SOCIETY CHEMBUR (E)",
            "BASANT PARK CHEMBUR (E)",
            "CHEMBUR NAKA (E)",
            "KUMBHAR WADA CHEMBUR(E)",
            "UMARSHI BAPPA CHOWK CHEMBUR (E)",
            "THAKKAR BAPPA COLONY KURLA (E)",
            "KAMGAR NAGAR KURLA (E)",
            "NEHRU NAGAR KURLA (E)",
            "KURLA BUS STATION (E)",
            "DR. AMBEDKAR GARDEN CHEMBUR STATION (E)",
            "10TH ROAD CHEMBUR CHURCH(W)",
            "ACHARYA GARDEN CHEMBUR(E)",
            "R.B.I. COLONY (CHEMBUR E)",
            "MAITRI PARK DEONAR",
            "R.K. STUDIO DEONAR",
            "PANJARA POL DEONAR",
            "BASANT TALKIES CHEMBUR (W)",
            "WADAVALI VILLAGE CHEMBUR (E)",
            "R.C.F. COLONY CHEMBUR (E)",
            "GANDHI MARKET CHEMBUR (W)",
            "CHEMBUR COLONY (E)",
            "NAVJEEVAN SOCIETY CHEMBUR (E)",
            "BASANT PARK CHEMBUR (E)",
            "BHAKTI BAVAN CHEMBUR (E)",
            "UMARSHI BAPPA CHOWK CHEMBUR(E)",
            "THAKKAR BAPPA CHOWK KURLA(E)",
            "KAMGAR NAGAR KURLA (E)",


    };
    android.widget.ArrayAdapter<String> myAdapter;
    java.util.List<org.apache.http.NameValuePair> params;
    com.daimajia.slider.library.SliderLayout sliderShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(android.text.TextUtils.isEmpty(txtsrc.getText().toString()) || android.text.TextUtils.isEmpty(txtdest.getText().toString()) ){
                    android.widget.Toast.makeText(getApplicationContext(),"Insufficient data", android.widget.Toast.LENGTH_SHORT).show();
                }
                else {
                    String url = "http://" + com.project.sagar.loctrack.Common.IP + "/LocTrackWeb/srcdest.php";
                    params = new java.util.ArrayList<org.apache.http.NameValuePair>();
                    params.add(new org.apache.http.message.BasicNameValuePair("src", txtsrc.getText().toString()));
                    params.add(new org.apache.http.message.BasicNameValuePair("dest", txtdest.getText().toString()));
                    new SrcDestTask().execute(url);
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtsrc=(android.widget.AutoCompleteTextView)findViewById(com.project.sagar.loctrack.R.id.txtsrc);
        txtdest=(android.widget.AutoCompleteTextView)findViewById(com.project.sagar.loctrack.R.id.txtdest);

        myAdapter=new android.widget.ArrayAdapter<String>(getApplicationContext(), com.project.sagar.loctrack.R.layout.ddlist,myDataset);
        txtdest.setAdapter(myAdapter);
        txtdest.setThreshold(1);

        txtsrc.setAdapter(myAdapter);
        txtsrc.setThreshold(1);

        sliderShow = (com.daimajia.slider.library.SliderLayout) findViewById(R.id.slider);
        com.daimajia.slider.library.SliderTypes.TextSliderView textSliderView = new com.daimajia.slider.library.SliderTypes.TextSliderView(this);
        textSliderView
                .image(com.project.sagar.loctrack.R.drawable.bestbus1);
        com.daimajia.slider.library.SliderTypes.TextSliderView textSliderView2 = new com.daimajia.slider.library.SliderTypes.TextSliderView(this);
        textSliderView2
                .image(com.project.sagar.loctrack.R.drawable.bestbus2);
        sliderShow.addSlider(textSliderView);
        sliderShow.addSlider(textSliderView2);


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
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run()
                        {
                            try {
                                int i=0;
                                PlaceD p;
                                int cnt=0;
                                myDataset=new String[100];
                                org.json.JSONArray toparr=jsono.getJSONArray("top");
                                org.json.JSONObject obj;
                                for (i=0;i<toparr.length();i++){
                                    try {
                                        obj = toparr.getJSONObject(i);
                                        myDataset[cnt]=obj.getString("stop");
                                        android.util.Log.d("stop",myDataset[cnt]);
                                        cnt++;
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

    class SrcDestTask{

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
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run()
                        {
                            String bus_no=null;
                            try {
                                bus_no=jsono.getString("top");
                                android.util.Log.d("bus",bus_no);
                                if(bus_no.equals("no")){
                                    android.widget.Toast.makeText(getApplicationContext(),"No Bus Found...", android.widget.Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    startMap(bus_no,txtsrc.getText().toString());
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
                        org.apache.http.client.methods.HttpPost httppost = new org.apache.http.client.methods.HttpPost(url);
                        httppost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(params));
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

    private void startMap(String bus_no,String stop) {
        android.content.Intent i=new android.content.Intent(this, BusMap.class);
        i.putExtra("busno",bus_no);
        i.putExtra("stop",stop);
        startActivity(i);
    }
}
