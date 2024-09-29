package com.example.vmeet;

import static com.example.vmeet.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {

    EditText codebox;
    Button joinbtn,sharebtn,outbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_dashboard);

        codebox=findViewById(R.id.codebox);
        joinbtn=findViewById(R.id.joinbtn);
        sharebtn=findViewById(R.id.sharebtn);
        outbtn=findViewById(R.id.outbtn);

        URL serverurl;

        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setFeatureFlag("welcome.enabled",false)
                    .setFeatureFlag("invite.enabled",false)
                    .setFeatureFlag("help.enabled",false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        /*
        try{
            serverurl=new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultoptions=new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverurl).setFeatureFlag("welcomepage.enabled",false).build();
            JitsiMeet.setDefaultConferenceOptions(defaultoptions);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }*/



        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = codebox.getText().toString();
                if (text.length() > 0) {
                    JitsiMeetConferenceOptions options
                            = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(text)
                            .setFeatureFlag("invite.enabled", false)
                            .setFeatureFlag("help.enabled", false)
                            .build();
                    JitsiMeetActivity.launch(DashboardActivity.this, options);
                }

            }
        });

        sharebtn.setOnClickListener(view -> {
            String string = codebox.getText().toString();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,string);
            intent.setType("text/plain");
            startActivity(intent);
        });


        outbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            }
        });


    }



}
/*


public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(4000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };thread.start();
    }
}
*/

