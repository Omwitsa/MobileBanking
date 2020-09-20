package com.aratek.trustfinger.Activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiInterface;
import com.aratek.trustfinger.utils.DateUtil;
import com.aratek.trustfinger.utils.P25Connector;

import java.util.ArrayList;

public class Reports extends AppCompatActivity {
    SQLiteDatabase db;
    private Button mConnectBtn, mEnableBtn, mPrintReceiptBtn,PrintReceiptBtn,PrintAdvance, btn_synch;
    private Spinner mDeviceSp;
    //public static String Dayshift;
    public static EditText Transsdate;
    public String tomorrow = "";
    public String yesterday = "";
    StringBuffer buffer;
    ApiInterface apiService;
    ProgressDialog dialog = null;
    TextView tv;
    private ProgressDialog mProgressDlg, mConnectingDlg, progressDoalog;
    private BluetoothAdapter mBluetoothAdapter;
    private P25Connector mConnector;
    String qty1 = "";// =getIntent().getStringExtra("qty").toString();
    String sno1 = "";
    String pin1 = "";
    long milis1 = System.currentTimeMillis();
    String date1 = DateUtil.timeMilisToString(milis1, "yyyy-MM-dd");
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    }
}