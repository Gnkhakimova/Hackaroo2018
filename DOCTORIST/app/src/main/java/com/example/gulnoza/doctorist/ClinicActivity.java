package com.example.gulnoza.doctorist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.WithHint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


public class ClinicActivity extends AppCompatActivity  {

ArrayList<String> nameArray = new ArrayList<>();

ArrayList<String> infoArray = new ArrayList<>();
ArrayList<Integer> imageArray = new ArrayList<>();
String Address="";
    ListView listView;
    String clinicName = "";
    Boolean filter = false;
    int arrayLength = 0;
    final Context context = this;
    String docName = "";
    String phone ="";
    String FullName ="";
    Boolean gotIntoQueue=false;
    Boolean gotWaitTime = false;
    Integer countTime =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);
        String savedExtra = getIntent().getStringExtra("ClinicName");
        clinicName = savedExtra;
        TextView myText = (TextView) findViewById(R.id.userName);
        myText.setText(savedExtra);
        phone = getIntent().getStringExtra("phoneNumber");
        FullName = getIntent().getStringExtra("fullName");
        LoadList iTask = new LoadList();
        iTask.execute("a", "b", "c");
        while (nameArray.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        final CustomListAdapter whatever = new CustomListAdapter(this, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.doctorList);
        listView.setAdapter(whatever);
        while (Address==""){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        TextView address = (TextView) findViewById(R.id.address);
        address.setText("Location: "+Address.replace("\"",""));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set title
                alertDialogBuilder.setTitle("Get into " + infoArray.get(position) + " queue");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Would you like to get into queue for " + nameArray.get(position))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                // ClinicActivity.this.finish();
                                docName = nameArray.get(position);
                                phone = getIntent().getStringExtra("phoneNumber");
                                FullName = getIntent().getStringExtra("fullName");
                                GetIntoQueue getIntoQueue = new GetIntoQueue();
                                getIntoQueue.execute("a", "b", "c");
                                while (gotIntoQueue == false) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                gotIntoQueue = false;
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                }
                                GetWaitTime getWaitTime = new GetWaitTime();
                                getWaitTime.execute("a", "b", "c");
                                while (gotWaitTime == false) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                Integer minutes = countTime * 15 - 15;
                                TextView t = (TextView) findViewById(R.id.waitTime);
                                t.setText("Estimate wait time: " + minutes.toString() + " minutes");
                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

    }

    private class GetIntoQueue extends AsyncTask<String, Integer, String> {
        String mTAG = "GetIntoQueue";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg) {
            try{

                URL url = new URL("https://hackaroo2018.herokuapp.com/add?DoctorName="+docName+"&PhoneNumber="+phone+"&FullName="+FullName);
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("POST"); // PUT is another valid option
                http.setDoOutput(true);

                int status_code = http.getResponseCode();
                if (status_code == 200) {
                    gotIntoQueue = true;
                    System.out.println(status_code);
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
            return "OK";
        }
    }

    private class GetWaitTime extends AsyncTask<String, Integer, String> {
        String mTAG = "GetWaitTime";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg) {
            String result = "";
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            try{

                URL url = new URL("https://hackaroo2018.herokuapp.com/getQueue");
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("GET"); // PUT is another valid option
                con.setReadTimeout(15000);
                con.connect();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line + "\n");
                }

                String temp = stringBuilder.toString();
                temp = temp.substring(2);
                HashMap<String, String> holder = new HashMap();
                String payload = temp;
                String[] keyVals = payload.split("\\},\\{");

                for(int i =0; i<keyVals.length;i++) {

                    String tempString = keyVals[i].toString();
                    if(tempString.contains(docName)){
                        countTime++;
                    }
                    gotWaitTime = true;
//                    String[] key = tempString.split(",");
//                    for (String keyVal : key) {
//                        String[] parts = keyVal.split(":", 2);
//                        holder.put(parts[0], parts[1]);
//                    }
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
            return "OK";
        }
    }
    public void GoBack(View v){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("fullName",FullName);
        intent.putExtra("phoneNumber",phone);
        startActivity(intent);
    }

    private class LoadList extends AsyncTask<String, Integer, String> {
        String mTAG = "LoadList";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg) {
            String result = "";
            BufferedReader reader = null;
            StringBuilder stringBuilder;

            try {

                // Setup the HTTPS connection to api.openalpr.com
                URL url = new URL("https://hackaroo2018.herokuapp.com/searchClinic?clinicname=" + clinicName);
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("GET"); // PUT is another valid option
                con.setReadTimeout(15000);
                con.connect();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                String temp = stringBuilder.toString();
                temp = temp.substring(2);
                HashMap<String, String> holder = new HashMap();
                String payload = temp;
                String[] keyVals = payload.split(",");

                for (int i = 0; i < keyVals.length; i++) {

                    for (String keyVal : keyVals) {
                        String[] parts = keyVal.split(":", 2);
                        holder.put(parts[0], parts[1]);
                    }


                }
                String Physician = holder.get("\"Physician\"");
                String Dentist = holder.get("\"Dentist\"");
                String Pediatrician = holder.get("\"Pediatrician\"");
                Address = holder.get("\"Adress\"");
                int counter =0;
                if(Physician != null) {
                    nameArray.add(Physician.replace("\"",""));
                    infoArray.add("Physician");
                    imageArray.add(R.drawable.doclist);
                    counter ++;
                }
                if(Dentist != null){
                    nameArray.add(Dentist.replace("\"",""));
                    infoArray.add("Dentist");
                    imageArray.add(R.drawable.doclist);
                    counter++;
                }
                if(Pediatrician != null){
                    nameArray.add(Pediatrician.replace("\"",""));
                    infoArray.add("Pediatrician");
                    imageArray.add(R.drawable.doclist);
                    counter++;
                }

                System.out.println(stringBuilder.toString());
                filter = true;
                return stringBuilder.toString();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            return result;
        }
    }
}

