package com.larry.trustfinger.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.larry.trustfinger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FingerPrintsupdateActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.main11) Button Exist;
    @BindView(R.id.main12) Button news;
    @BindView(R.id.main13) Button admin;
    @BindView(R.id.main14) Button Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_printsupdate);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final String Change = sharedpreferences.getString("LoadPermission", "");
        String IsTeller = sharedpreferences.getString("loadTeller", "");
        final String Agtid = sharedpreferences.getString("loadsAgentId", "");
        //Toast.makeText(FingerPrintsupdateActivity.this,Agtid,Toast.LENGTH_LONG).show();


        String Tellers="True";
        String notTellers="False";
        String Changes="SuperAdmin";
        if(IsTeller.equals(notTellers))
        {
            Exist.setEnabled(false);
            Exist.setBackgroundColor(Color.parseColor("#808080"));
            news.setEnabled(false);
            news.setBackgroundColor(Color.parseColor("#808080"));
        }
        if (IsTeller.equals(Tellers))
        {
            admin.setEnabled(false);
            admin.setBackgroundColor(Color.parseColor("#808080"));

        }
        if (Change.equals(Changes))
        {
            Exist.setEnabled(false);
            Exist.setBackgroundColor(Color.parseColor("#808080"));
            news.setEnabled(false);
            news.setBackgroundColor(Color.parseColor("#808080"));
        }
        
        Exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnquiryActivity.class);
                editor.putString("loadsCurrentAgentId",Agtid);
                editor.apply();
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AgentMemberEnquiryActivity.class);
                editor.putString("loadsCurrentAgentId",Agtid);
                editor.apply();
                startActivity(intent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminEnquiryActivity.class);
                editor.putString("loadsCurrentAgentId",Agtid);
                editor.apply();
                startActivity(intent);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterPhone.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        return;
    }
}