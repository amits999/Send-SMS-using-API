package com.amitsharma.action.javatpointdemos;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mobileNo,smsText;
    Button sendSmsBtn;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onSmsSend();
                } else {
                    Toast.makeText(getApplicationContext(),"you don't have SMS permission",Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                onSmsSend();
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobileNo=(EditText) findViewById(R.id.mob_no_edit_text);
        smsText=(EditText) findViewById(R.id.message_edit_text);
        sendSmsBtn=(Button) findViewById(R.id.send_msg_btn);


    }

    public void sendSms(View view) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS}, 0);
        }
        else{
            onSmsSend();
        }


    }

    public void onSmsSend(){

        String no=mobileNo.getText().toString();
        String msg=smsText.getText().toString();

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,intent,0);

        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(no,null,msg,pi,null);

        Toast.makeText(getApplicationContext(),"Message sent successfully",Toast.LENGTH_SHORT).show();

    }

    public void sendToTextActivity(View view) {
        Intent intent=new Intent(MainActivity.this,TextActivity.class);
        startActivity(intent);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
}
