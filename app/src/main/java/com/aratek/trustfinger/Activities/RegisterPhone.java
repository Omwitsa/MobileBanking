package com.aratek.trustfinger.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.aratek.trustfinger.R;

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
//    @BindView(R.id.logout) CardView Logout;
//    @BindView(R.id.switchUser) CardView SwitchUser;

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
//            SwitchUser.setEnabled(false);
//            Logout.setEnabled(false);
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
//        Logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                editor.clear();
////                startActivity(intent);
//                //finishAffinity(); // Close all activites
//                //System.exit(0);  // Releasing resources
//
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("EXIT", true);
//                startActivity(intent);
//
//            }
//        });
//        SwitchUser.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("EXIT", true);
//                startActivity(intent);
//            }
//        });


    }
    @Override
    public void onBackPressed() {
        return;
    }
}