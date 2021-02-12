package com.aratek.trustfinger.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        String machineId = android.os.Build.SERIAL;

        String id = sharedpreferences.getString("loadsPosition", "");
        String IsAdmin = sharedpreferences.getString("loadrole", "");
//        Toast.makeText(RegisterPhone.this,id,Toast.LENGTH_LONG).show();
//        Toast.makeText(RegisterPhone.this,IsAdmin,Toast.LENGTH_LONG).show();
        if(IsAdmin=="True")
        {
            registration.setEnabled(false);
        }

        Transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);

            }
        });

        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AgentMemberActivity.class);
                startActivity(intent);
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PosUsersActivity.class);
                startActivity(intent);
            }
        });



    }
}