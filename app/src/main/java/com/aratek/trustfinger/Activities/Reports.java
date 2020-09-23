package com.aratek.trustfinger.Activities;
import android.Manifest;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aratek.trustfinger.R;
import com.aratek.trustfinger.Rest.ApiClient;
import com.aratek.trustfinger.Rest.ApiInterface;
import com.aratek.trustfinger.utils.DateUtil;
import com.aratek.trustfinger.utils.P25ConnectionException;
import com.aratek.trustfinger.utils.P25Connector;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;



import static java.util.Calendar.DATE;

public class Reports extends AppCompatActivity implements View.OnClickListener {
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        db=openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
        mConnectBtn			= (Button) findViewById(R.id.btn_connectd);
        mEnableBtn			= (Button) findViewById(R.id.btn_enabled);
        mPrintReceiptBtn 	= (Button) findViewById(R.id.btn_print_receiptd);
        mDeviceSp 			= (Spinner) findViewById(R.id.sp_deviced);
        btn_synch			= (Button) findViewById(R.id.btn_synch);
        //final Spinner spinner = (Spinner) findViewById(R.id.Dayshift);


        Transsdate 			= (EditText) findViewById(R.id.Transsdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Transsdate.setText(sdf.format(new Date()));
        tv = (TextView)findViewById(R.id.tvd);
        qty1 ="2342";//getIntent().getStringExtra("qty").toString();
        sno1 ="234234";//getIntent().getStringExtra("sno").toString();
        pin1 ="A345455345G";// getIntent().getStringExtra("pin").toString()

        mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            showUnsupported();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                showDisabled();
            } else {
                showEnabled();

                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                if (pairedDevices != null) {
                    mDeviceList.addAll(pairedDevices);
                    updateDeviceList();
                }
            }

            mProgressDlg 	= new ProgressDialog(this);
            mProgressDlg.setMessage("Scanning...");
            mProgressDlg.setCancelable(false);
            mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mBluetoothAdapter.cancelDiscovery();
                }
            });

            mConnectingDlg 	= new ProgressDialog(this);
            progressDoalog = new ProgressDialog(this);;
            mConnectingDlg.setMessage("Connecting...");
            mConnectingDlg.setCancelable(false);

            mConnector 		= new P25Connector(new P25Connector.P25ConnectionListener() {
                @Override
                public void onStartConnecting() {
                    mConnectingDlg.show();
                }

                @Override
                public void onConnectionSuccess() {
                    mConnectingDlg.dismiss();
                    showConnected();
                }

                @Override
                public void onConnectionFailed(String error) {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onConnectionCancelled() {
                    mConnectingDlg.dismiss();
                }

                @Override
                public void onDisconnected() {
                    showDisonnected();
                }
            });

            //enable bluetooth
            mEnableBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 1000);
                }
            });

            //connect/disconnect
            mConnectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    connect();
                }
            });

            btn_synch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                List<TransactionModel> transsctions = new List<TransactionModel>();
//                Call<Response> call = apiService.sychTransactions(transsctions);
                }
            });

            Transsdate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    final Calendar calendar = Calendar.getInstance();
                    calendar.add(  DATE,1);
                    tomorrow = df.format(calendar.getTime());
                    calendar.add(DATE,-2);
                    yesterday = df.format(calendar.getTime());


                    int yy = calendar.get(Calendar.YEAR);
                    int mm = calendar.get(Calendar.MONTH)+1;
                    int dd = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(Reports.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            String monthString = String.valueOf((monthOfYear)+1);
                            if (monthString.length() == 1) {
                                monthString = "0" + monthString;
                            }
                            String dayOfMonthString = String.valueOf(dayOfMonth);
                            if (dayOfMonthString.length() == 1) {
                                dayOfMonthString = "0" + dayOfMonthString;
                            }

                            Transsdate.setText(new StringBuilder().append(year).append("-")
                                    .append(monthString).append("-").append(dayOfMonthString).append(" "));

                            Transsdate.setText(new StringBuilder().append(year).append("-")
                                    .append(monthString).append("-").append(dayOfMonthString).append(" "));
                        }
                    }, yy, mm, dd);
                    datePicker.show();
                }
            });


            //print struk
            mPrintReceiptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    String myDate =(Transsdate.getText().toString());
                    myDate =  myDate.trim();
                    Cursor c = db.rawQuery("SELECT * FROM deposits WHERE   transdate ='"+myDate+"'  ", null);
                    if (c.getCount() == 0) {
                        showMessage("Message", "No Transactions found");
                        mPrintReceiptBtn.setEnabled(true);
                        return ;
                    }
                    Cursor c1 = db.rawQuery("SELECT sum(Amount) FROM deposits WHERE  transdate ='"+myDate+"'", null);

                    //com.example.mobilebanking.myactivities.Printer printer = new com.example.mobilebanking.myactivities.Printer(buffer, mConnector, db, "", "");
                    //printer.print();
                    //printStruk();
                    Cursor c2 = db.rawQuery("SELECT * FROM withdrawals WHERE   transdate ='"+myDate+"'  ", null);
                    if (c2.getCount() == 0) {
                        showMessage("Message", "No Transactions found");
                        PrintReceiptBtn.setEnabled(true);
                        return ;
                    }
                    Cursor c3 = db.rawQuery("SELECT sum(Amount) FROM withdrawals WHERE  transdate ='"+myDate+"'", null);
                    Cursor c4 = db.rawQuery("SELECT * FROM advance WHERE   transdate ='"+myDate+"'  ", null);
                    if (c4.getCount() == 0) {
                        showMessage("Message", "No Transactions found");
                        PrintAdvance.setEnabled(true);
                        return ;

                    }
                    Cursor c5 = db.rawQuery("SELECT sum(Amount) FROM advance WHERE  transdate ='"+myDate+"'", null);
                    //StringBuffer buffer1 = getReportData(c2, c3, "Withdrawn");
                    StringBuffer buffer = getReportData(c, c1, c2, c3, c4, c5, "Deposited");
                    com.aratek.trustfinger.utils.Printer printer = new com.aratek.trustfinger.utils.Printer(buffer, mConnector, db, "", "");
                    printer.print();
                    dialog = ProgressDialog.show(Reports.this, "",
                            "Clearing the collection/data from phone memory", true);
                    new Thread(new Runnable() {
                        public void run() {
                            //sendToDB();
                            //DailyReportsActivity.this.finish();
                            dialog.dismiss();
                        }
                    }).start();
                }
            });



        }



        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        registerReceiver(mReceiver, filter);
    }

    private StringBuffer getReportData(Cursor c, Cursor c1, Cursor c2, Cursor c3, Cursor c4, Cursor c5, String operation) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(operation+"  Transactions\n");
        buffer.append(" Amount      Account Number\n");
        while (c.moveToNext()) {
            buffer.append(c.getString(0) + "\t" + c.getString(2) + " \n" );
        }
        String amm = null;

        while (c1.moveToNext()) {
            amm = String.format("%.2f", Double.parseDouble(c1.getString(0)));

        }

        showMessage("Transaction Total +"+ amm +"", buffer.toString());
        int count=c2.getCount();
        buffer.append("-------------------------- \n");
        buffer.append("Customers Served : "+count+ "\n");
        buffer.append("Total amount "+operation+ ": "+amm+ "\n");
        buffer.append("############################### \n");

        //Advance
        buffer.append("Advance  Transactions\n");
        buffer.append(" Amount      Account Number\n");
        while (c4.moveToNext()) {
            buffer.append(c4.getString(0) + "\t" + c4.getString(2) + " \n" );
        }
        String ammnt = null;

        while (c5.moveToNext()) {
            ammnt = String.format("%.2f", Double.parseDouble(c5.getString(0)));

        }

        showMessage("Transaction Total +"+ ammnt +"", buffer.toString());
        int count2=c4.getCount();
        buffer.append("-------------------------- \n");
        buffer.append("Customers Served : "+count2+ "\n");
        buffer.append("Total amount Advanced "+ammnt+ "\n");
        buffer.append("------------------------------ \n");
        //withdrwals
        buffer.append("Withdrawal  Transactions\n");
        buffer.append(" Amount      Account Number\n");
        while (c2.moveToNext()) {
            buffer.append(c2.getString(0) + "\t" + c2.getString(2) + " \n" );
        }
        String ammn = null;

        while (c3.moveToNext()) {
            ammn = String.format("%.2f", Double.parseDouble(c3.getString(0)));

        }

        showMessage("Transaction Total +"+ ammn +"", buffer.toString());
        int count1=c2.getCount();
        buffer.append("-------------------------- \n");
        buffer.append("Customers Served : "+count1+ "\n");
        buffer.append("Total amount withdrwan "+ammn+ "\n");
        buffer.append("------------------------------ \n");
        buffer.append("############################### \n");


        return buffer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_scan2)


        {
            int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            mBluetoothAdapter.startDiscovery();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }

        if (mConnector != null) {
            try {
                mConnector.disconnect();
            } catch (P25ConnectionException e) {
                e.printStackTrace();
            }
        }

        super.onPause();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }

    private String[] getArray(ArrayList<BluetoothDevice> data) {
        String[] list = new String[0];
        if (data == null) return list;
        int size	= data.size();
        list		= new String[size];
        for (int i = 0; i < size; i++) {
            list[i] = data.get(i).getName();
        }
        return list;
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void updateDeviceList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item2, getArray(mDeviceList));
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item1);
        mDeviceSp.setAdapter(adapter);
        mDeviceSp.setSelection(0);
    }

    private void showDisabled() {
        showToast("Bluetooth disabled");
        mEnableBtn.setVisibility(View.VISIBLE);
        mConnectBtn.setVisibility(View.GONE);
        mDeviceSp.setVisibility(View.GONE);
    }

    private void showEnabled() {
        showToast("Bluetooth enabled");
        mEnableBtn.setVisibility(View.GONE);
        mConnectBtn.setVisibility(View.VISIBLE);
        mDeviceSp.setVisibility(View.VISIBLE);
    }

    private void showUnsupported() {
        showToast("Bluetooth is unsupported by this device");
        mConnectBtn.setEnabled(false);
        mPrintReceiptBtn.setEnabled(false);
        mDeviceSp.setEnabled(false);
    }

    private void showConnected() {
        showToast("Connected");
        mConnectBtn.setText("Disconnect");
        mPrintReceiptBtn.setEnabled(true);
        mDeviceSp.setEnabled(false);
    }

    private void showDisonnected() {
        showToast("Disconnected");
        mConnectBtn.setText("Connect");
        mPrintReceiptBtn.setEnabled(false);
        mDeviceSp.setEnabled(true);
    }

    private void connect() {
        if (mDeviceList == null || mDeviceList.size() == 0) {
            return;
        }

        BluetoothDevice device = mDeviceList.get(mDeviceSp.getSelectedItemPosition());
        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
            try {
                createBond(device);
            } catch (Exception e) {
                showToast("Failed to pair device");
                return;
            }
        }

        try {
            if (!mConnector.isConnected()) {
                mConnector.connect(device);
            } else {
                mConnector.disconnect();

                showDisonnected();
            }
        } catch (P25ConnectionException e) {
            e.printStackTrace();
        }
    }

    private void createBond(BluetoothDevice device) throws Exception {

        try {
            Class<?> cl 	= Class.forName("android.bluetooth.BluetoothDevice");
            Class<?>[] par 	= {};
            Method method 	= cl.getMethod("createBond", par);
            method.invoke(device);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void sendData(byte[] bytes) {

        try {

            mConnector.sendData(bytes);
        } catch (P25ConnectionException e) {
            e.printStackTrace();
        }

    }
    public void sendToDB(){

        try{


            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(Reports.this,"Collection deleted Successfully", Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }

    public void showAlert(){
        Reports.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Reports.this);
                builder.setTitle("Submition to DB error.");
                builder.setMessage("Not successful")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state 	= intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    showEnabled();
                } else if (state == BluetoothAdapter.STATE_OFF) {
                    showDisabled();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();

                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();

                updateDeviceList();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                mDeviceList.add(device);

                showToast("Found device " + device.getName());
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED) {
                    showToast("Paired");

                    connect();
                }
            }
        }
    };

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }
}