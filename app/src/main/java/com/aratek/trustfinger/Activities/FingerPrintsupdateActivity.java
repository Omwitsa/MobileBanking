package com.aratek.trustfinger.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aratek.trustfinger.R;

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
        final String IdNumber = sharedpreferences.getString("loadsAgentId", "");
        String IsTeller = sharedpreferences.getString("loadTeller", "");
//        final String FirstName = sharedpreferences.getString("agentFirstName", "");
//        final String SecondName = sharedpreferences.getString("agentSecondName", "");
          String Tellers="True";
        String notTellers="False";
//         String Changes="SuperAdmin";
//
//        final String NewMember="New";
//        final String OperatorMember="Operator";
        if(IsTeller.equals(notTellers))
        {
            Exist.setEnabled(false);
            news.setEnabled(false);
        }
        if (IsTeller.equals(Tellers))
        {
            admin.setEnabled(false);
        }
        
        Exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnquiryActivity.class);
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AgentMemberEnquiryActivity.class);
//                editor.putString("NewFinger", NewMember);
//                editor.putString("NewId", IdNumber);
//                editor.putString("NewFirstName", FirstName);
//                editor.putString("NewSecondName", SecondName);
//                editor.commit();
                startActivity(intent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminEnquiryActivity.class);
//                editor.putString("AdminFinger", OperatorMember);
//                editor.putString("NewId", IdNumber);
//                editor.putString("NewFirstName", FirstName);
//                editor.putString("NewSecondName", SecondName);
//                editor.commit();
                startActivity(intent);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterPhone.class);
//                editor.putString("AgentId", idw);
//                editor.commit();
                startActivity(intent);
            }
        });
    }
}