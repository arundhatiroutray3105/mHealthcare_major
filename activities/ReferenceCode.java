package activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import gcm.play.android.samples.com.gcmquickstart.R;

public class ReferenceCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_code);

        SharedPreferences sharedPrefs = getSharedPreferences("MedicalHealthCare", 0);
        String userName = sharedPrefs.getString("userName", "");
        String token = sharedPrefs.getString("token","");
                //getBoolean("userName", false);
        VCard Doctor=new VCard("Doctor")
                .setName(userName)
                .setAddress(token);
        Bitmap myBitmap=QRCode.from(Doctor).bitmap();


        ImageView myImage=(ImageView) findViewById(R.id.imageView);
        myImage.setImageBitmap(myBitmap);
    }
}
