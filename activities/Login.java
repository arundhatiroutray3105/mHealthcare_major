package activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import AppUtility.Constant;
import connections.HTTPConnection;
import gcm.play.android.samples.com.gcmquickstart.MapsActivity;
import gcm.play.android.samples.com.gcmquickstart.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText userName = (EditText) findViewById(R.id.loginUserName);
        final EditText password = (EditText) findViewById(R.id.loginPassword);



        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        assert btnLogin != null;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.userName = userName.getText().toString();
                HTTPConnection objectCallAPI = new HTTPConnection(Constant.LoginPageActivity);
                AsyncTask<String, String, String> val = objectCallAPI.execute(userName.getText().toString(),password.getText().toString());


                objectCallAPI.handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        if( Constant.LoginPageActivity == msg.arg1){
                            if(msg.arg2 == 1){
                            Intent intent = new Intent();
                            intent.setClass(Login.this, Form.class);
                            startActivity(intent);
                            finish();
                            }
                            else{
                                msg("Login fail please check the credentials");
                            }
                        }
                    }
                };

            }
        });
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
