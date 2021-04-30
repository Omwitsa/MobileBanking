package com.larry.trustfinger.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.larry.trustfinger.Model.TransactionModel;
import com.larry.trustfinger.R;
import com.larry.trustfinger.utils.Transaction;

public class SubmitTransactionActivity extends AppCompatActivity {
    private Transaction transaction;
    TransactionModel transactionModel;
    private Context context;
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    static SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_transaction);
        db = openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS deposits(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS withdrawals(Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS advances (Amount VARCHAR,Pin VARCHAR,Supp VARCHAR,datepp DATETIME, status VARCHAR,transdate  VARCHAR);");
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        String MachineID = android.os.Build.SERIAL;

            String operation = sharedpreferences.getString("operation","");
            Double amount = Double.parseDouble(sharedpreferences.getString("amount",""));
            String status = "0";
            String sNo = sharedpreferences.getString("supplierNo","");
            String accountNo = sharedpreferences.getString("accountNo","");
            String productDescription = sharedpreferences.getString("productDescription","");
            String Nm = sharedpreferences.getString("name", "");
            String DepositID = sharedpreferences.getString("DidNumber", "");
            String auditID = sharedpreferences.getString("AuditId", "");
            //insertDataToSqlite(operation,amount,sNo,status);

            transactionModel = new TransactionModel(operation, amount, Nm, DepositID, "","",sNo,status, MachineID, auditID, productDescription,accountNo);
            transaction = new Transaction(this, transactionModel);

            transaction.transact(transactionModel);

    }

    @Override
    public void onBackPressed() {
        return;
    }



}