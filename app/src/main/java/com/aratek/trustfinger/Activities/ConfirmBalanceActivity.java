package com.aratek.trustfinger.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aratek.trustfinger.R;

public class ConfirmBalanceActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_balance);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        //String Machineid = android.os.Build.MODEL+""+ Build.SERIAL;

        final String operation = sharedpreferences.getString("operation","");
        final Double amount = Double.parseDouble(sharedpreferences.getString("amount",""));
        final String ammmount=String.format("%.2f", amount);
        String status = "0";
        final String sNo = sharedpreferences.getString("supplierNo","");
        final String accountNo = sharedpreferences.getString("accountNo","");
        final String productDescription = sharedpreferences.getString("productDescription","");
        final String machineId = sharedpreferences.getString("machine_id", "");
        final String auditId = sharedpreferences.getString("loggedInUser", "");



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//Setting message manually and performing action on button click

        builder.setMessage("Do you want to complete  this "+operation+ "Transaction  of Account Number? "+sNo+ "")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), TransactionActivity.class);
                        editor.putString("operation", operation);
                        editor.putString("amount", ammmount);
                        editor.putString("machineId", machineId);
                        editor.putString("supplierNo", sNo);
                        editor.putString("fingurePrint", "");
                        editor.putString("auditId", auditId);
                        editor.putString("accountNo",accountNo);
                        editor.putString("productDescription", productDescription);
                        editor.commit();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), RegisterPhone.class);
                        startActivity(intent);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Confirmation of the Transaction");
        alert.show();
    }
    @Override
    public void onBackPressed() {
        return;
    }


}