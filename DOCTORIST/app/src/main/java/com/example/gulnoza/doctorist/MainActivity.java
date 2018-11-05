package com.example.gulnoza.doctorist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> nameArray = new ArrayList<>();

    ArrayList<String> infoArray = new ArrayList<>();

    ArrayList<Integer> imageArray = new ArrayList<>();

    ListView listView;
    String docName ="";
    Boolean filter = false;
    String fullNameP="";
    String phone ="";
    Integer countTime=0;
    Boolean gotWaitTime = false;
    String smsText="Estimate wait time: ";
    Integer waitTimeMin=0;
    String phoneNumber ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String fullName = intent.getStringExtra("fullName");
         phoneNumber= intent.getStringExtra("phoneNumber");


        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(waitTimeMin>2) {
                    waitTimeMin=waitTimeMin-2;
                }
                if(waitTimeMin!=0) {
                    SendSMS sendSMS = new SendSMS();
                    sendSMS.execute("a", "b", "c");
                }
            }
        }, 0, 2, TimeUnit.MINUTES);
        fullNameP = fullName;
        phone = phoneNumber;
        TextView name = (TextView)findViewById(R.id.userName);
        name.setText(fullName);
        LoadList iTask = new LoadList();
        iTask.execute("a","b","c");
         while (nameArray.isEmpty()){
    try
        {
        Thread.sleep(1000);
    }
    catch(InterruptedException ex)
    {
        Thread.currentThread().interrupt();
    }
}
        CustomListAdapter whatever = new CustomListAdapter(this, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(whatever);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent i = new Intent(MainActivity.this, ClinicActivity.class);
                i.putExtra("fullName",fullNameP);
                i.putExtra("phoneNumber",phone);
                String message = nameArray.get(position);
                i.putExtra("ClinicName", message);
                startActivity(i);
            }
        });

    }
    public void clickPhysician (View v){
docName = "Physician";
        FilterList iTask = new FilterList();
        iTask.execute("a","b","c");
        while (filter == false){
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        CustomListAdapter filterAdapter = new CustomListAdapter(this, nameArray, infoArray, imageArray);
        ListView l;
        l = (ListView) findViewById(R.id.listView);
        l.setAdapter(filterAdapter);
        filter = false;
    }

    public void clickDentist (View v){
        docName = "Dentist";
        FilterList iTask = new FilterList();
        iTask.execute("a","b","c");
        while (filter == false){
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        CustomListAdapter filterAdapter = new CustomListAdapter(this, nameArray, infoArray, imageArray);
        ListView l;
        l = (ListView) findViewById(R.id.listView);
        l.setAdapter(filterAdapter);
        filter = false;
    }

    public void clickPediatrician (View v){
        docName = "Pediatrician";
        FilterList iTask = new FilterList();
        iTask.execute("a","b","c");
        while (filter == false){
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        CustomListAdapter filterAdapter = new CustomListAdapter(this, nameArray, infoArray, imageArray);
        ListView l;
        l = (ListView) findViewById(R.id.listView);
        l.setAdapter(filterAdapter);
        filter = false;
    }
    public void getStatus (View v){
GetStatus getStatus = new GetStatus();
getStatus.execute("a","b","c");



while(gotWaitTime == false){
    try
    {
        Thread.sleep(1000);
    }
    catch(InterruptedException ex)
    {
        Thread.currentThread().interrupt();
    }
}
Integer minutes = countTime*15;
waitTimeMin = minutes;
TextView status = (TextView)findViewById(R.id.status);
status.setText("Estimate wait time: "+minutes.toString()+" minutes");

    }

    private class GetStatus extends AsyncTask<String, Integer, String> {
        String mTAG = "GetStatus";

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
                    String doctorsname="";
                    String[] info = tempString.split(",");
                    String tempPhone = phone.replace("+","");
                    if(tempString.contains(tempPhone)){
                        //for(String val:info){
                        //    String[] parts = val.split(":", 2);
                        ///    holder.put(parts[0],parts[1]);
                       // }
                       // doctorsname = holder.get("\"Doctorname\"");
                        countTime++;
                    }
                  //  if(tempString)

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


    private class SendSMS extends AsyncTask<String, Integer, String> {
        String mTAG = "SendSMS";

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg) {
            try{
                String SmsBody = smsText+waitTimeMin.toString()+" minutes";

                URL url = new URL("https://hackaroo2018.herokuapp.com/sendSMS?phone=+"+phoneNumber+"&text="+SmsBody);
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("POST"); // PUT is another valid option
                http.setDoOutput(true);

                int status_code = http.getResponseCode();
                if (status_code == 200) {
                    System.out.println(status_code);
                }
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
            return "OK";
        }
    }


    private class LoadList extends AsyncTask<String, Integer, String> {
        String mTAG = "LoadList";
        @Override
        protected void onPreExecute() {
        }





        @Override
        protected String doInBackground(String...arg) {
            String result = "";
            BufferedReader reader = null;
            StringBuilder stringBuilder;

            try {

                // Setup the HTTPS connection to api.openalpr.com
                URL url = new URL("https://hackaroo2018.herokuapp.com/getAll");
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

                for(int i =0; i<keyVals.length;i++){
                    String tempString = keyVals[i].toString();
                    String[] key = tempString.split(",");
                    for(String keyVal:key){
                        String[] parts = keyVal.split(":",2);
                        holder.put(parts[0],parts[1]);
                    }
                   nameArray.add(holder.get("\"ClinicName\"").replace("\"",""));
                    infoArray.add(holder.get("\"Telephone\"").replace("\"",""));
                    imageArray.add(R.drawable.doclist);
                }

                System.out.println(stringBuilder.toString());
                return stringBuilder.toString();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            return result;
        }

    }

    private class FilterList extends AsyncTask<String, Integer, String> {
        String mTAG = "FilterList";
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String...arg) {
            String result = "";
            BufferedReader reader = null;
            StringBuilder stringBuilder;

            try {

                // Setup the HTTPS connection to api.openalpr.com
                URL url = new URL("https://hackaroo2018.herokuapp.com/searchDoctor?doctor="+docName);
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
                nameArray.clear();
                infoArray.clear();
                imageArray.clear();
                for(int i =0; i<keyVals.length;i++){
                    String tempString = keyVals[i].toString();
                    String[] key = tempString.split(",");
                    for(String keyVal:key){
                        String[] parts = keyVal.split(":",2);
                        holder.put(parts[0],parts[1]);
                    }

                    nameArray.add( holder.get("\"ClinicName\"").replace("\"",""));
                    infoArray.add(holder.get("\"Telephone\"").replace("\"",""));
                    imageArray.add(R.drawable.doclist);

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
