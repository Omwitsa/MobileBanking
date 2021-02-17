package com.aratek.trustfinger.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aratek.trustfinger.Model.TransactionModel;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.utils.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

            String operation = sharedpreferences.getString("operation","");
            Double amount = Double.parseDouble(sharedpreferences.getString("amount",""));
            String status = "0";
            String sNo = sharedpreferences.getString("supplierNo","");
            String accountNo = sharedpreferences.getString("accountNo","");
            String productDescription = sharedpreferences.getString("productDescription","");
            String machineId = sharedpreferences.getString("machine_id", "");
            String auditId = sharedpreferences.getString("agentId", "");
            insertDataToSqlite(operation,amount,sNo,status);

            transactionModel = new TransactionModel(operation, amount, "", "", sNo, status, machineId, auditId, productDescription, "",accountNo);
            transaction = new Transaction(this, transactionModel);

            transaction.transact(transactionModel);

    }

    private void insertDataToSqlite(String operation,Double amount,String sNo,String status) {
        String bal = String.valueOf(amount);
        //final String ammmount=String.format("%.0f", bal);
        Calendar cc = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_pp = sdf.format(cc.getTime());
        SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
        String trans = ff.format(cc.getTime());
        if (sharedpreferences.getString("operation","").equals("deposit")) {
            db.execSQL("INSERT INTO deposits VALUES('" + bal + "','" + status + "','" + sNo + "','" + date_pp + "','0','" + trans + "');");
            Toast.makeText(SubmitTransactionActivity.this, "Deposits saved successfully", Toast.LENGTH_LONG).show();
        }
else if(sharedpreferences.getString("operation","").equals("withdraw"))
        {
            db.execSQL("INSERT INTO withdrawals VALUES('" + bal + "','" + status + "','" + sNo + "','" + date_pp + "','0','" + trans + "');");
            Toast.makeText(SubmitTransactionActivity.this, "Withdrawals saved successfully", Toast.LENGTH_LONG).show();
        }
        else if(sharedpreferences.getString("operation","").equals("advance"))
        {
            db.execSQL("INSERT INTO advances VALUES('" + bal + "','" + status + "','" + sNo + "','" + date_pp + "','0','" + trans + "');");
            Toast.makeText(SubmitTransactionActivity.this, "Advance saved successfully", Toast.LENGTH_LONG).show();
        }

    }



}