package activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import AppUtility.Constant;
import gcm.play.android.samples.com.gcmquickstart.GcmSender;
import gcm.play.android.samples.com.gcmquickstart.MapsActivity;
import gcm.play.android.samples.com.gcmquickstart.R;

public class Ambulance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        ImageButton btnMap = (ImageButton)findViewById(R.id.btnMap);

        assert btnMap != null;
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Ambulance.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
