package activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.games.appcontent.AppContentUtils;

import java.util.ArrayList;
import java.util.List;

import AppUtility.AppicationDataBase;
import gcm.play.android.samples.com.gcmquickstart.R;

public class Reports extends AppCompatActivity {

    List<String> li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        /// sql data part start///
        AppicationDataBase mDbHelper = new AppicationDataBase(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // you will actually use after this query.
        String[] projection = {
                AppicationDataBase.FeedEntry._ID,
                AppicationDataBase.FeedEntry.COLUM_DATE,
                AppicationDataBase.FeedEntry.COLUMN_MESSAGE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = AppicationDataBase.FeedEntry.COLUMN_MESSAGE + " DESC";

        final Cursor cursor = db.query(
                AppicationDataBase.FeedEntry.TABLE_NAME,                     // The table to query
                projection,
                null,
                null,                             // The columns to return
                //selection,                                // The columns for the WHERE clause
                //selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null//sortOrder                                 // The sort order
        );

            /*
        /// sql data part over///
*/


        final RelativeLayout rl=(RelativeLayout) findViewById(R.id.rl);
        final RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams
                ((int) RelativeLayout.LayoutParams.WRAP_CONTENT,(int) RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin=10;
        params.topMargin=0;

        final ListView list=new ListView(this);

        li=new ArrayList<String>();

        // Add message in the list from database
        cursor.moveToFirst();
        // int i = cursor.getCount();
        for (int i = 0; i < cursor.getCount(); i++) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(AppicationDataBase.FeedEntry.COLUM_DATE)
            );

            cursor.moveToNext();
            li.add(itemId);
        }

        ArrayAdapter<String> adp=new ArrayAdapter<String> (getBaseContext(),
                android.R.layout.simple_dropdown_item_1line,li);
        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        list.setAdapter(adp);
        list.setLayoutParams(params);
        rl.addView(list);

        //click on list item
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToFirst();
                String message ="";
                for (int i = 0; i <= cursor.getCount(); i++) {
                    if(i == position) {
                        message = cursor.getString(cursor.getColumnIndexOrThrow(AppicationDataBase.FeedEntry.COLUMN_MESSAGE));
                        break;
                    }
                    cursor.moveToNext();

                }

                // open message
               // AppContentUtils.setMessage(message);
                Intent ReadMessageViewIntent = new Intent(Reports.this,
                        DetailReport.class);
                ReadMessageViewIntent.putExtra("message",message);
                startActivity(ReadMessageViewIntent);

            }
        });

    }
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Reports.this, Form.class));
        finish();

    }
}
