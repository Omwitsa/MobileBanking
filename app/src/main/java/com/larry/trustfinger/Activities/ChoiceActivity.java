package com.larry.trustfinger.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.larry.trustfinger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoiceActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.main1) Button First;
    @BindView(R.id.main2) Button Second;
    @BindView(R.id.main3) Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final String idw = sharedpreferences.getString("NewsAgentId", "");

        First.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idw.isEmpty()) {
                    Toast.makeText(ChoiceActivity.this, "Operator ID is missing", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), IdentificationActivity.class);
                    editor.putString("AgentId", idw);
                    editor.commit();
                    startActivity(intent);
                }
            }
        });
        Second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idw.isEmpty()) {
                    Toast.makeText(ChoiceActivity.this, "Operator ID is missing", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Deposit2Account.class);
                    editor.putString("AgentId", idw);
                    editor.commit();
                    startActivity(intent);
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idw.isEmpty()) {
                    Toast.makeText(ChoiceActivity.this, "Operator ID is missing", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), RegisterPhone.class);
                    editor.putString("AgentId", idw);
                    editor.commit();
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}