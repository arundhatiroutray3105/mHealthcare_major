package gcm.play.android.samples.com.gcmquickstart;

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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import AppUtility.Constant;
import activities.Reports;
import connections.HTTPConnection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private static GoogleMap mMap;
    private static double lattitude;
    private static double longititude;
    private LocationManager locationManager;
    private static MarkerOptions newMarkerOption = new MarkerOptions();
    static Marker mposition,ownPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Constant.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                MapsActivity.setLocation();

            }
        };

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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(lattitude, longititude);
        mposition = mMap.addMarker(newMarkerOption.position(location).title("My location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,18.0f));

        ownPosition = mMap.addMarker(newMarkerOption.position(location).title("My location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,18.0f));

    }


    @Override
    public void onLocationChanged(Location location) {
        lattitude = location.getLatitude();
        longititude = location.getLongitude();
        LatLng CurrentLocation = new LatLng(lattitude, longititude);
        if(Constant.flag){

            mposition.setPosition(CurrentLocation);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, 18.0f));
            Thread thread = new Thread(new GcmSender(lattitude+"|"+longititude));
            thread.start();
        }
        LatLng mylocation = new LatLng(lattitude, longititude);
        ownPosition.setPosition(CurrentLocation);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation,18.0f));
    }
    public static void setLocation() {

        String[] arryLocation = Constant.location.split("\\|");
        LatLng CurrentLocation = new LatLng(Double.parseDouble(arryLocation[0]), Double.parseDouble(arryLocation[1]));
        mposition.setPosition(CurrentLocation);
        mposition.setTitle("Ambulance");
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation,18.0f));
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
