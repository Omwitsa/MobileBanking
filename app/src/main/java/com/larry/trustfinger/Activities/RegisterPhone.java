package com.larry.trustfinger.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.larry.trustfinger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterPhone extends AppCompatActivity {
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.transs) CardView Transactions;
    @BindView(R.id.registrations) CardView registration;
    @BindView(R.id.verifys) CardView verification;
    @BindView(R.id.updates) CardView Fingerprints;
    @BindView(R.id.image001) ImageView mage1;
    @BindView(R.id.image002) ImageView mage2;
    @BindView(R.id.image003) ImageView mage3;
    @BindView(R.id.image004) ImageView mage4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        String machineId = android.os.Build.SERIAL;

        final String id = sharedpreferences.getString("loadsAgentId", "");
        String IsAdmin = sharedpreferences.getString("loadrole", "");
        String IsTeller = sharedpreferences.getString("loadTeller", "");
        String confirm="False";
        String lock="You are not an Administrator";
        String OperatorAdmin="True";
        if(IsAdmin.equals(confirm) && IsTeller.equals(OperatorAdmin))
        {
            registration.setEnabled(false);
            registration.setCardBackgroundColor(Color.parseColor("#808080"));
            mage3.setBackgroundColor(Color.parseColor("#808080"));

        }
        if(IsAdmin.equals(OperatorAdmin) && IsTeller.equals(confirm))
        {
            Transactions.setEnabled(false);
            Transactions.setBackgroundColor(Color.parseColor("#808080"));
            mage1.setBackgroundColor(Color.parseColor("#808080"));
            verification.setEnabled(false);
            verification.setBackgroundColor(Color.parseColor("#808080"));
            mage2.setBackgroundColor(Color.parseColor("#808080"));
        }
        if(IsAdmin.equals("") && IsTeller.equals(""))
        {
            registration.setEnabled(false);
            registration.setBackgroundColor(Color.parseColor("#808080"));
            mage3.setBackgroundColor(Color.parseColor("#808080"));
            Transactions.setEnabled(false);
            Transactions.setBackgroundColor(Color.parseColor("#808080"));
            mage1.setBackgroundColor(Color.parseColor("#808080"));
            verification.setEnabled(false);
            verification.setBackgroundColor(Color.parseColor("#808080"));
            mage2.setBackgroundColor(Color.parseColor("#808080"));
            Fingerprints.setEnabled(false);
            Fingerprints.setBackgroundColor(Color.parseColor("#808080"));
            mage4.setBackgroundColor(Color.parseColor("#808080"));
        }

        Transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChoiceActivity.class);
                editor.putString("NewsAgentId", id);
                editor.commit();
                startActivity(intent);

            }
        });

        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AgentMemberActivity.class);
                editor.putString("loadsAgentId", id);
                editor.commit();
                startActivity(intent);
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PosUsersActivity.class);
                editor.putString("loadsAgentId", id);
                editor.commit();
                startActivity(intent);
            }
        });
        Fingerprints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FingerPrintsupdateActivity.class);
                editor.putString("loadsAgentId", id);
                editor.commit();
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        return;
    }
}