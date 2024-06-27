package gcm.play.android.samples.com.gcmquickstart;

import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import AppUtility.Constant;

/**
 * Created by riteshnikose on 15/02/18.
 */
public class GcmSender implements Runnable {



    public static final String API_KEY = "AIzaSyBeq1gtd_Y_ycUXqdlpDad1zgmSOjmD-3s";
    String location;

    public GcmSender(String location) {
        this.location = location;
    }

    @Override
    public void run() {
        try {
            // Prepare JSON containing the GCM message content. What to send and where to send.
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();
           /* jData.put("message", args[0].trim());
            // Where to send GCM message.
            if (args.length > 1 && args[1] != null) {
                jGcmData.put("to", args[1].trim());
            } else {
                jGcmData.put("to", "/topics/global");
            }*/
            jData.put("message", Constant.userName+"|"+Constant.token+"|"+location);
            //jGcmData.put("to", token);
            jGcmData.put("to", "/topics/notifi");
            // What to send in GCM message.
            jGcmData.put("data", jData);

            // Create connection to send GCM Message request.
            URL url = new URL("https://android.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
           InputStream inputStream = conn.getInputStream();
            byte[] data = new byte[inputStream.read()+100];
            inputStream.read(data);
            String st = new String(data);
            Log.d("GCMSender", "send: " + st + " Check your device/emulator for notification or logcat for " +
                   "confirmation of the receipt of the GCM message.");
            Constant.flag = false;
        } catch (Exception e) {
            Log.d("GCMSender", "send: Exception "+e.getMessage());
        }
    }


}
