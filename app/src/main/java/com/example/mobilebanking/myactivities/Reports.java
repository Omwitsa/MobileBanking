package com.example.mobilebanking.myactivities;

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

import com.example.mobilebanking.Pockdata.PocketPos;
import com.example.mobilebanking.R;
import com.example.mobilebanking.Util.DateUtil;
import com.example.mobilebanking.Util.FontDefine;
import com.example.mobilebanking.Util.Printer;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static java.util.Calendar.DATE;

public class Reports extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase db;
    private Button mConnectBtn, mEnableBtn, mPrintReceiptBtn,PrintReceiptBtn;
    private Spinner mDeviceSp;
    //public static String Dayshift;
    public static EditText Transsdate;
    public String tomorrow = "";
    public String yesterday = "";
    StringBuffer buffer;

    ProgressDialog dialog = null;
    TextView tv;

    private ProgressDialog mProgressDlg, mConnectingDlg;

    private BluetoothAdapter mBluetoothAdapter;

    private P25Connector mConnector;
    String qty1 = "";// =getIntent().getStringExtra("qty").toString();
    String sno1 = "";
    String pin1 = "";

    long milis1 = System.currentTimeMillis();
    String date1 = DateUtil.timeMilisToString(milis1, "yyyy-MM-dd");

    /*Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date1 = sdf.format(c.getTime());
*/
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports2);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //myToolbar.setTitle("AllClientsActivity Daily Collection");
        //setSupportActionBar(myToolbar);

        //myToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            //@Override
            //public void onClick(View view){
                //finish();
            //}


    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //getSupportActionBar().setDisplayShowHomeEnabled(false);

    db=openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
    mConnectBtn			= (Button) findViewById(R.id.btn_connectd);
    mEnableBtn			= (Button) findViewById(R.id.btn_enabled);
    mPrintReceiptBtn 	= (Button) findViewById(R.id.btn_print_receiptd);
    PrintReceiptBtn 	= (Button) findViewById(R.id.btn_print_receiptd1);
    mDeviceSp 			= (Spinner) findViewById(R.id.sp_deviced);
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

                        String monthString = String.valueOf(monthOfYear);
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
                printStruk();
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
            PrintReceiptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    printStruk1();
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

    private void printStruk1() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            String myDate =(Transsdate.getText().toString());
            myDate =  myDate.trim();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            final Calendar calendar = Calendar.getInstance();
            calendar.add(  DATE,1);
            tomorrow = df.format(calendar.getTime());
            calendar.add(DATE,-2);
            yesterday = df.format(calendar.getTime());

            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH)+1;
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            //Dayshift = spinner.getSelectedItem().toString();
            //Dayshift="";
            //myDate.setText(sdf.format(new Date()));

            Cursor c = db.rawQuery("SELECT * FROM withdrawals WHERE   transdate ='"+myDate+"'  ", null);
            //
             //Cursor c = db.rawQuery("SELECT * FROM deposits WHERE   datepp ='2020-08-12 13:54:10'", null);
            if (c.getCount() == 0) {
                showMessage("Transactions", "No Record found");
                mPrintReceiptBtn.setEnabled(true);
                return ;
            }
            StringBuffer buffer = new StringBuffer();
            String amm = null;


            while (c.moveToNext()) {

                buffer.append(c.getString(0) + "\t" + c.getString(2) + " \n" );

            }
            Cursor c1 = db.rawQuery("SELECT sum(Amount) FROM withdrawals WHERE  transdate ='"+myDate+"'", null);
            while (c1.moveToNext()) {

                amm = c1.getString(0);
                ;

            }
            showMessage("Transaction Total +"+ amm +"", buffer.toString());

            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss");
            long milis1 = System.currentTimeMillis();

            String date1 = DateUtil.timeMilisToString(milis1, "MMM dd, yyyy hh:mm:ss");
            String time1 = DateUtil.timeMilisToString(milis1, "hh:mm a");
            Calendar b = Calendar.getInstance();
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(b.getTime());
            int count=c.getCount();

            MainActivity ma=new MainActivity();

            String titleStr =  "\n" + "K-PILLAR SACCO" +"\n" + "WITHDRAWALS  TRANSACTIONS " + "\n";

            StringBuilder content2Sb = new StringBuilder();

            //content2Sb.append("Suppliers Served : "+numberOfRows+ "\n");
            content2Sb.append("##############################" + "\n");
            content2Sb.append("Total Amount  : "+amm+ "\n");
            content2Sb.append("Amount   AccountNumber      " + "\n");
            content2Sb.append("" +buffer.toString() + "" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("Total amount Withdrawn : "+amm+ "\n");
            content2Sb.append("Customers Served : "+count+ "\n");
            content2Sb.append("                         " + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("##############################" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("Date :" +date1+"\n");
            content2Sb.append("Agent  No : 5  "+"\n");
            content2Sb.append("Signature____________________  "+"\n");
            content2Sb.append("                         " + "\n");
            content2Sb.append("                         " + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("MILK FOR HEALTH AND WEALTH" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("DESIGNED & DEVELOPED BY" + "\n");
            content2Sb.append("AMTECH TECHNOLOGIES LTD" + "\n");
            content2Sb.append("www.amtechafrica.com" + "\n");

            long milis = System.currentTimeMillis();
            byte[] titleByte = Printer.printfont(titleStr, FontDefine.FONT_32PX, FontDefine.Align_CENTER,
                    (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);
            byte[] content2Byte = Printer.printfont(content2Sb.toString(), FontDefine.FONT_32PX, FontDefine.Align_LEFT,
                    (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);
            byte[] totalByte = new byte[titleByte.length + content2Byte.length];
            int offset = 0;
            System.arraycopy(titleByte, 0, totalByte, offset, titleByte.length);
            offset += titleByte.length;
            System.arraycopy(content2Byte, 0, totalByte, offset, content2Byte.length);
            offset += content2Byte.length;
            byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, totalByte, 0, totalByte.length);
            sendData(senddata);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "No New Records Found", Toast.LENGTH_LONG).show();
            finish();

        }


        return;

    }
    private void printStruk() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            String myDate =(Transsdate.getText().toString());
            myDate =  myDate.trim();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            final Calendar calendar = Calendar.getInstance();
            calendar.add(  DATE,1);
            tomorrow = df.format(calendar.getTime());
            calendar.add(DATE,-2);
            yesterday = df.format(calendar.getTime());

            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH)+1;
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            //Dayshift = spinner.getSelectedItem().toString();
            //Dayshift="";
            //myDate.setText(sdf.format(new Date()));

            Cursor c = db.rawQuery("SELECT * FROM deposits WHERE   transdate ='"+myDate+"'  ", null);
            //
            //Cursor c = db.rawQuery("SELECT * FROM deposits WHERE   datepp ='2020-08-12 13:54:10'", null);
            if (c.getCount() == 0) {
                showMessage("Transactions", "No Record found");
                mPrintReceiptBtn.setEnabled(true);
                return ;
            }
            StringBuffer buffer = new StringBuffer();
            String amm = null;


            while (c.moveToNext()) {

                buffer.append(c.getString(0) + "\t" + c.getString(2) + " \n" );

            }
            Cursor c1 = db.rawQuery("SELECT sum(Amount) FROM deposits WHERE  transdate ='"+myDate+"'", null);
            while (c1.moveToNext()) {

                amm = c1.getString(0);
                ;

            }
            showMessage("Transaction Total +"+ amm +"", buffer.toString());

            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss");
            long milis1 = System.currentTimeMillis();

            String date1 = DateUtil.timeMilisToString(milis1, "MMM dd, yyyy hh:mm:ss");
            String time1 = DateUtil.timeMilisToString(milis1, "hh:mm a");
            Calendar b = Calendar.getInstance();
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(b.getTime());
            int count=c.getCount();

            MainActivity ma=new MainActivity();

            String titleStr =  "\n" + "K-PILLAR SACCO" +"\n" + "DEPOSITS  TRANSACTIONS " + "\n";

            StringBuilder content2Sb = new StringBuilder();

            //content2Sb.append("Suppliers Served : "+numberOfRows+ "\n");
            content2Sb.append("##############################" + "\n");
            content2Sb.append("Total Amount  : "+amm+ "\n");
            content2Sb.append("Amount   AccountNumber      " + "\n");
            content2Sb.append("" +buffer.toString() + "" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("Total amount deposited : "+amm+ "\n");
            content2Sb.append("Customers Served : "+count+ "\n");
            content2Sb.append("                         " + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("##############################" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("Date :" +date1+"\n");
            content2Sb.append("Agent  No : 5  "+"\n");
            content2Sb.append("Signature____________________  "+"\n");
            content2Sb.append("                         " + "\n");
            content2Sb.append("                         " + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("MILK FOR HEALTH AND WEALTH" + "\n");
            content2Sb.append("--------------------------" + "\n");
            content2Sb.append("DESIGNED & DEVELOPED BY" + "\n");
            content2Sb.append("AMTECH TECHNOLOGIES LTD" + "\n");
            content2Sb.append("www.amtechafrica.com" + "\n");

            long milis = System.currentTimeMillis();
            byte[] titleByte = Printer.printfont(titleStr, FontDefine.FONT_32PX, FontDefine.Align_CENTER,
                    (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);
            byte[] content2Byte = Printer.printfont(content2Sb.toString(), FontDefine.FONT_32PX, FontDefine.Align_LEFT,
                    (byte) 0x1A, PocketPos.LANGUAGE_ENGLISH);
            byte[] totalByte = new byte[titleByte.length + content2Byte.length];
            int offset = 0;
            System.arraycopy(titleByte, 0, totalByte, offset, titleByte.length);
            offset += titleByte.length;
            System.arraycopy(content2Byte, 0, totalByte, offset, content2Byte.length);
            offset += content2Byte.length;
            byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, totalByte, 0, totalByte.length);
            sendData(senddata);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "No New Records Found", Toast.LENGTH_LONG).show();
            finish();

        }


        return;

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