package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;
import com.aratek.trustfinger.utils.Transaction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceActivity extends AppCompatActivity {
    ApiInterface apiService;
    ProgressDialog progressDoalog;
    @BindView(R.id.sNo) EditText sNo;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.back) Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        progressDoalog = new ProgressDialog(BalanceActivity.this);
//        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        Bundle extras = getActivity().getIntent().getExtras();
//        String operation = extras.getString("operation");
//        Double amount = Double.parseDouble(extras.getString("amount"));
//        String status = "0";
//        String sNo = extras.getString("supplierNo");
//        String machineId = sharedpreferences.getString("machine_id", "");
//        String auditId = sharedpreferences.getString("loggedInUser", "");
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        final String Account = extras.getString("supplierNo");
        sNo.setText(Account);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String SupNo = sNo.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SubmitTransactionActivity.class);
                intent.putExtra("operation", "balance");
                intent.putExtra("amount", "0");
                intent.putExtra("fingurePrint", "");
                intent.putExtra("supplierNo", Account);
                intent.putExtra("pin", "");
                intent.putExtra("accountNo", "");
                intent.putExtra("productDescription", "");

                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}