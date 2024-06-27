package activities;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import AppUtility.Constant;
import gcm.play.android.samples.com.gcmquickstart.MapsActivity;
import gcm.play.android.samples.com.gcmquickstart.R;

public class Form extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BluetoothAdapter myBluetooth = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnFormNext = (Button) findViewById(R.id.btnFormNext);
        btnFormNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constant.isBtConnected){
                    Intent intent = new Intent();
                    intent.setClass(Form.this, Diagnosis.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(Form.this, Bluetooth.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_bluetooth){
            Intent intent = new Intent();
            intent.setClass(Form.this, Bluetooth.class);
            startActivity(intent);
        }else if (id == R.id.nav_reports) {
            Intent intent = new Intent();
            intent.setClass(Form.this, Reports.class);
            startActivity(intent);
        } else if (id == R.id.nav_signout) {


            Intent intent = new Intent(Form.this, Additional.class);
            intent.putExtra("Value","test");
            startActivity(intent);

        }else if(id == R.id.nav_refrence){
            Intent intent = new Intent(Form.this, ReferenceCode.class);
            startActivity(intent);

        }
        else if(id == R.id.nav_ambulance){
            Intent intent = new Intent(Form.this, Ambulance.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
