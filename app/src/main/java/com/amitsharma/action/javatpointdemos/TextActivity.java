package com.amitsharma.action.javatpointdemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class TextActivity extends AppCompatActivity {
    EditText mobile,message;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        message=(EditText) findViewById(R.id.message_field);
        mobile=(EditText) findViewById(R.id.mobile_field);
        sendButton=(Button) findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=message.getText().toString().trim();
                String mob=mobile.getText().toString().trim();

                String apikey="1LK2IKFDIR4S5BPPIJN8YRTRTNKHJKRF";
                String secretkey="CM5ZS8TWSWDR2YNV";
                String useType="testing app";
                String senderId=createSenderId("AWESOM",apikey,secretkey,useType);

                String content=sendCampaign(apikey,secretkey,useType,mob,msg,senderId);
            }
        });

    }

    public static String sendCampaign(String apiKey,String secretKey,String useType, String phone, String message, String senderId){

        StringBuilder content = new StringBuilder();
        try{
            // construct data
            JSONObject urlParameters = new JSONObject();
            urlParameters.put("apikey",apiKey);
            urlParameters.put("secret",secretKey);
            urlParameters.put("usetype",useType);
            urlParameters.put("phone", phone);
            urlParameters.put("message", URLEncoder.encode(message,"UTF-8"));
            urlParameters.put("senderid", senderId);
            URL obj = new URL("http://www.way2sms.com" + "/api/v1/sendCampaign");
            // send data
            HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.write(urlParameters.toString().getBytes());
            // get the response
            BufferedReader bufferedReader = null;
            if (httpConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            // StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        }catch(Exception ex){
            System.out.println("Exception at::"+ex);
        }
        return content.toString();
    }


    public static String createSenderId(String senderId, String apiKey,String secretKey,String usetype){

        StringBuilder content = new StringBuilder();
        try{
            // construct data
            JSONObject urlParameters = new JSONObject();
            urlParameters.put("apikey", apiKey);
            urlParameters.put("secret",secretKey);
            urlParameters.put("usetype",usetype);
            urlParameters.put("senderid", senderId);
            URL obj = new URL("http://www.way2sms.com" + "/api/v1/createSenderId");
            // send data
            HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.write(urlParameters.toString().getBytes());
            // get the response
            BufferedReader bufferedReader = null;
            if (httpConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            //StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        }catch(Exception ex){
            System.out.println("Exception at createSenderId():"+ex);
        }
        return content.toString();
    }
}
