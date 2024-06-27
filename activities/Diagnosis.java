package activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import AppUtility.Constant;

import gcm.play.android.samples.com.gcmquickstart.R;

public class Diagnosis extends AppCompatActivity {

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    //private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        // get Address from Bluetooth Class
        Bundle bundle = getIntent().getExtras();
        address = Constant.btAddress;
        //address = bundle.getString("Bluetooth");


        new ConnectBT().execute();



        Button btnDigoNext = (Button) findViewById(R.id.btnDigoNext);
        btnDigoNext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 diagnos();
             }
         });

    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Diagnosis.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || ! Constant.isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                Constant.isBtConnected = true;
            }
            progress.dismiss();
        }


    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void diagnos()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("b".toString().getBytes());
                Log.d("test", "diagnos: ");
               // String Output = "" + btSocket.getInputStream().read();
                read();
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }


    byte[] bytes = new byte[256];

    private void read() {

        //while(true){
        try {

            String Output = "" + btSocket.getInputStream().read();
            msg("pules " + Output);


            Intent intent = new Intent();
            intent.setClass(Diagnosis.this,  Additional.class);
            intent.putExtra("pules",Output);
            startActivity(intent);
            finish();



        } catch (IOException e) {
            msg("Error" + e.getMessage());
        }
    //}


    }

}
