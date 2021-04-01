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

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.deposit) CardView deposit;
    @BindView(R.id.withdraw) CardView withdraw;
    @BindView(R.id.balance_enquiry) CardView balanceEnquiry;
    @BindView(R.id.advance) CardView advance;
    @BindView(R.id.bacck) CardView BacCk;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //Bundle extras = getIntent().getExtras();
        //final String Account = extras.getString("supplierNo");

        final String Account = sharedpreferences.getString("supplierNo", "");
        final String id_number=sharedpreferences.getString("number", "");
        final String idSs=sharedpreferences.getString("agtId", "");


        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DepositActivity.class);
                editor.putString("supplierNo", Account);
                editor.putString("number",id_number);
                editor.putString("agtId",idSs);
                editor.commit();
                //intent.putExtra("supplierNo", Account);
                startActivity(intent);
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);
                editor.putString("supplierNo", Account);
                editor.putString("number",id_number);
                editor.putString("agtId",idSs);
                editor.commit();
                //intent.putExtra("supplierNo", Account);
                startActivity(intent);
            }
        });



        balanceEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BalanceActivity.class);
                editor.putString("supplierNo", Account);
                editor.putString("number",id_number);
                editor.putString("agtId",idSs);
                editor.commit();
                //intent.putExtra("supplierNo", Account);
                startActivity(intent);
            }
        });



        advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                editor.putString("supplierNo", Account);
                editor.putString("number",id_number);
                editor.putString("agtId",idSs);
                editor.commit();
                //intent.putExtra("supplierNo", Account);
                startActivity(intent);
            }
        });
        BacCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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