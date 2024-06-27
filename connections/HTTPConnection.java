package connections;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;

import AppUtility.AppicationDataBase;
import AppUtility.Constant;

/**
 * Created by riteshnikose on 02/11/17.
 */
public class HTTPConnection extends AsyncTask<String, String, String> {

    private int ActivityNo;
    public Handler handler;
    private String logingReply;

    public HTTPConnection(int activity){
        ActivityNo = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {
        String Port ="";
        String IP ="";
        try {
            JSONObject jGcmData = new JSONObject();
            if(ActivityNo == Constant.registrationActivity){
                jGcmData.put("userName", params[0]);
                jGcmData.put("emailId", params[1]);
                jGcmData.put("password",params[2]);
                jGcmData.put("phoneNo",params[3]);
                jGcmData.put("requestType",0);
                IP ="http://192.168.0.110:";
                Port = "8083/";
            }
            else if(ActivityNo ==  Constant.AdditionalPageActivity) {
                String data = params[0]+"|"+params[1];
                data= params[0];
                String data1 = params[1];
                //String data = "97";
                IP ="http://192.168.0.110:";
                //IP ="http://"+data1+":";
                Port = "8084/";
                //Port = "8082/";
                jGcmData.put("pulse",data);
            }else if(ActivityNo ==  Constant.LoginPageActivity){
                StringBuffer passwordSb = new StringBuffer();
                jGcmData.put("userName", params[0]);
                //jGcmData.put("password",params[1]);
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(params[1].getBytes());
                    byte byteData[] = md.digest();
                    for (int i = 0; i < byteData.length; i++) {
                        passwordSb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                    }
                    System.out.println("Hex format : " + passwordSb.toString());
                }
                catch (Exception e){
                    Log.d("SHA-256", "login "+e.getMessage());
                }
                jGcmData.put("password",passwordSb);
                jGcmData.put("requestType",1);
                IP ="http://192.168.0.110:";
                Port = "8083/";
            } else if(ActivityNo ==  Constant.MapActivity){
                IP ="http://192.168.0.110:";
                Port = "8082/";
                jGcmData.put("lattitude", params[0]);
                jGcmData.put("longititude", params[1]);
            }
            // Defined URL  where to send data
            URL url = new URL(IP + Port);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + "AIzaSyBeq1gtd_Y_ycUXqdlpDad1zgmSOjmD-3s");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.

            //conn.connect();
            //int response = conn.getResponseCode();

            // OutputStream outputStream = new BufferedOutputStream(conn.getOutputStream());
            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            String st = jGcmData.toString();
            outputStream.writeBytes(st); //working

            //outputStream.writeBytes("message:"+data);
            outputStream.flush();


            // Read GCM response.
            InputStream inputStream = conn.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            logingReply = total.toString();

            Log.d("tag", "doInBackground: " + total.toString());



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        Constant.currentActivity = ActivityNo;
        Message mgs = Message.obtain();
        mgs.arg1 = ActivityNo;
        if(logingReply.equals("true\n")){
            mgs.arg2 =1;
        }
        else{
            mgs.arg2 =0;
        }
        handler.sendMessage(mgs);

    }
}
