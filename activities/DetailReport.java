package activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import gcm.play.android.samples.com.gcmquickstart.R;

public class DetailReport extends AppCompatActivity {
    String report = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        Bundle bundle = getIntent().getExtras();
        report = bundle.getString("message");
        TextView reciveMessage = (TextView) findViewById(R.id.reciveMessage);
        reciveMessage.setText(report);
    }
}
