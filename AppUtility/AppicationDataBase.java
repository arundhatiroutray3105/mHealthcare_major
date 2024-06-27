package AppUtility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by riteshnikose on 02/12/16.
 */
public class AppicationDataBase extends SQLiteOpenHelper {
    static final int READ_BLOCK_SIZE = 100;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rns.db";

    public AppicationDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static boolean dataBaseWriter(Context context,String strName)
    {

        try {
            String FILENAME = "Notification.txt";
           // String string = "Notification! check";
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);;
            fos.write(strName.getBytes());
            fos.close();

            //display file saved message
          /*  Toast.makeText(context, "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }



    public static String dataBaseReader(Context context)
    {
        String s="";
        try {
                FileInputStream fileIn =context.openFileInput("Notification.txt");
                InputStreamReader InputRead= new InputStreamReader(fileIn);

                char[] inputBuffer= new char[READ_BLOCK_SIZE];

                int charRead;

                while ((charRead=InputRead.read(inputBuffer))>0) {
                    // char to string conversion
                    String readstring=String.copyValueOf(inputBuffer,0,charRead);
                    s +=readstring;
                }
                InputRead.close();

            //display file saved message
            Toast.makeText(context, s,
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);


      /*  ContentValues groupNames = new ContentValues();
        // Create a new map of values, where column names are the keys for group
        groupNames.put(AppicationDataBase.FeedEntry.COLUM_GROUP, "Genral");
        groupNames.put(AppicationDataBase.FeedEntry.COLUM_FLAG, 1);
        // Insert the new row, returning the primary key value of the new row for group
        long newGroup = db.insert(AppicationDataBase.FeedEntry.TABLE_GROUP, null, groupNames);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "MHealthCareMessage";
        public static final String COLUM_DATE= "Date";
        public static final String COLUMN_MESSAGE = "Message";

    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUM_DATE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_MESSAGE + TEXT_TYPE + " )";

}

