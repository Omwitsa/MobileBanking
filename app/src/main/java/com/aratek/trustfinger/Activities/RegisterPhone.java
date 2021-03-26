package com.aratek.trustfinger.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.aratek.trustfinger.Model.AgentMember;
import com.aratek.trustfinger.Model.FingurePrintModel;
import com.aratek.trustfinger.Model.RegisterFingerprints;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
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
    @BindView(R.id.logout) CardView Logout;
    @BindView(R.id.switchUser) CardView SwitchUser;

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
//        Toast.makeText(RegisterPhone.this,id,Toast.LENGTH_LONG).show();
//        Toast.makeText(RegisterPhone.this,IsAdmin,Toast.LENGTH_LONG).show();
        if(IsAdmin.equals(confirm) && IsTeller.equals(OperatorAdmin))
        {
            registration.setEnabled(false);
        }
        if(IsAdmin.equals(OperatorAdmin) && IsTeller.equals(confirm))
        {
            Transactions.setEnabled(false);
            verification.setEnabled(false);
            Fingerprints.setEnabled(false);
        }
        if(IsAdmin.equals("") && IsTeller.equals(""))
        {
            registration.setEnabled(false);
            Transactions.setEnabled(false);
            verification.setEnabled(false);
            Fingerprints.setEnabled(false);
            SwitchUser.setEnabled(false);
            Logout.setEnabled(false);
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
        Logout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                editor.clear();
//                startActivity(intent);
                finishAffinity();
                //System.exit(0);
            }
        });
        SwitchUser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FingeprintActivity.class);
                editor.clear();
                startActivity(intent);

            }
        });


    }
}