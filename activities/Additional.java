package activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import AppUtility.Constant;
import connections.HTTPConnection;
import gcm.play.android.samples.com.gcmquickstart.R;

public class Additional extends AppCompatActivity  implements LocationListener{
    String pules = null;
    LocationManager locationManager;
     int count=0;
    String lattitude;
    String longititude;

    ArrayList<View>  al = new ArrayList<View>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional);
        Bundle bundle = getIntent().getExtras();
        pules = bundle.getString("pules");

        Button  btnadd = (Button)findViewById(R.id.btnadd);
        Button btnAddNext = (Button)findViewById(R.id.btnAddnext);
        final LinearLayout container = (LinearLayout)findViewById(R.id.container);

        /***************** GPS location******************/

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }



        /***********************************/

        assert btnadd != null;

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.add_addtional, null);
                container.addView(addView);
                al.add(addView);
                textView.append(count);

            }
        });
        assert btnAddNext != null;

        btnAddNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer sendData = new StringBuffer("");
                for(int i =0; i<al.size();i++){
                    EditText add = (EditText)al.get(i).findViewById(R.id.add_key);
                    String st = add.getText().toString();
                    System.out.print(st);
                    sendData.append(st);
                    //sendData.append(',');
                }



                pules = "8|183|64|0|0|23.3|0.672|32|";
                HTTPConnection objectCallAPI = new HTTPConnection(Constant.AdditionalPageActivity);
                AsyncTask<String, String, String> val = objectCallAPI.execute(pules,sendData.toString());

                objectCallAPI.handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        if( Constant.AdditionalPageActivity == msg.arg1){
                            Intent intent = new Intent();
                            intent.setClass(Additional.this, Reports.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                };



            }
        });




    }


    @Override
    public void onLocationChanged(Location location) {

        lattitude = ""+location.getLatitude();
        longititude =""+ location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
