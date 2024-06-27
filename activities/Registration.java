package activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import AppUtility.Constant;
import connections.HTTPConnection;
import gcm.play.android.samples.com.gcmquickstart.R;

public class Registration extends AppCompatActivity {

    boolean completed;
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sharedPrefs = getSharedPreferences("MedicalHealthCare", 0);
        completed = sharedPrefs.getBoolean("complete", false);

        if (completed == true) {
            Intent intent = new Intent();
            intent.setClass(Registration.this, Login.class);
            startActivity(intent);
            finish();
        }
        Button btnRegister = (Button) findViewById(R.id.register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putBoolean("complete", true);
                editor.commit();

                EditText userName = (EditText) findViewById(R.id.editTextusername);
                EditText emailId = (EditText) findViewById(R.id.editTextmailid);
                EditText password = (EditText) findViewById(R.id.editTextpass);
                EditText conPassword = (EditText) findViewById(R.id.editTextconpass);
                EditText phoneNo = (EditText) findViewById(R.id.editTextphone);

                String userNameSt = userName.getText().toString();
                String emailIdSt = emailId.getText().toString();
                String passwordSt = password.getText().toString();
                String conPasswordSt = conPassword.getText().toString();
                String phoneNoSt = phoneNo.getText().toString();

                String error;

                if(userNameSt.length()<=0){
                    error = "Plsease enter your name";
                    message(error);
                }
                if(phoneNo.length() != 10){
                    error = "Plsease enter valid phone number";
                    message(error);
                }
                if( passwordSt != conPasswordSt ){
                    error = "Passworld not match!";
                    message(error);
                }


                editor.putString("userName",userNameSt);

                editor.commit();

                HTTPConnection objectCallAPI = new HTTPConnection(Constant.registrationActivity);
                AsyncTask<String, String, String> val = objectCallAPI.execute(userNameSt,emailIdSt,passwordSt,conPasswordSt,phoneNoSt);
                objectCallAPI.handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        if( Constant.registrationActivity == msg.arg1){
                            Intent intent = new Intent();
                            intent.setClass(Registration.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                };

           /*     //if(Constant.currentActivity == Constant.registrationActivity ){
                   // Constant.currentActivity=0;
                    Intent intent = new Intent();
                    intent.setClass(Registration.this, Login.class);
                    startActivity(intent);
                    finish();
                //}*/

            }
        });
    }

    public  void message( String message){

    }

}
