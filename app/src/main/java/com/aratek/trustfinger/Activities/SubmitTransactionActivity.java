package com.aratek.trustfinger.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aratek.trustfinger.Model.TransactionModel;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.utils.Transaction;

public class SubmitTransactionActivity extends AppCompatActivity {
    private Transaction transaction;
    TransactionModel transactionModel;
    private Context context;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_transaction);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            Bundle extras = getIntent().getExtras();
            String operation = extras.getString("operation");
            Double amount = Double.parseDouble(extras.getString("amount"));
            String status = "0";
            String sNo = extras.getString("supplierNo");
            String accountNo = extras.getString("accountNo");
            String productDescription = extras.getString("productDescription");
            String machineId = sharedpreferences.getString("machine_id", "");
            String auditId = sharedpreferences.getString("loggedInUser", "");
            transactionModel = new TransactionModel(operation, amount, "", "", sNo, status, machineId, auditId, productDescription, accountNo);
            transaction = new Transaction(this, transactionModel);

            transaction.transact(transactionModel);

    }

}