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
        final String idw = sharedpreferences.getString("NewsAgentId", "");
        final String memberExist="Exists";
        final String NewMember="New";
        final String OperatorMember="Operator";
        
        Exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VerifyActivity.class);
                editor.putString("MemberFinger", memberExist);
                editor.commit();
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VerifyActivity.class);
                editor.putString("NewFinger", NewMember);
                editor.commit();
                startActivity(intent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VerifyActivity.class);
                editor.putString("AdminFinger", OperatorMember);
                editor.commit();
                startActivity(intent);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FingeprintActivity.class);
//                editor.putString("AgentId", idw);
//                editor.commit();
                startActivity(intent);
            }
        });
    }
}