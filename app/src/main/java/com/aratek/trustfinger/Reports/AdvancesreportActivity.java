package com.aratek.trustfinger.Reports;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.aratek.trustfinger.Activities.AuthenticationActivity;
import com.aratek.trustfinger.Activities.Reports;
import com.aratek.trustfinger.R;
import com.aratek.trustfinger.utils.DateUtil;
import com.vanstone.appsdk.client.ISdkStatue;
import com.vanstone.trans.api.PrinterApi;
import com.vanstone.trans.api.SystemApi;
import com.vanstone.utils.CommonConvert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvancesreportActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "Reports";
    private Button mPrintReceiptBtn;
    int pflag = 0;
    private InputStream inStream;
    private ByteArrayOutputStream outStream;
    private byte[] data;
    public static EditText Transsdate;
    SQLiteDatabase db;
    @BindView(R.id.back)
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancesreport);
        mPrintReceiptBtn = (Button) findViewById(R.id.btn_print_advance);
        db = openOrCreateDatabase("MobileDB", Context.MODE_PRIVATE, null);
        Transsdate = (EditText) findViewById(R.id.Transsdate);
        mPrintReceiptBtn.setOnClickListener(this);
        ButterKnife.bind(this);
        new Thread() {
            public void run() {
                String CurAppDir = getApplicationContext().getFilesDir().getAbsolutePath();
                SystemApi.SystemInit_Api(0, CommonConvert.StringToBytes(CurAppDir + "/" + "\0"), AdvancesreportActivity.this, new ISdkStatue() {
                    @Override
                    public void sdkInitSuccessed() {
                        pflag = 1;
                    }

                    @Override
                    public void sdkInitFailed() {
                        pflag = 0;
                    }
                });
            }
        }.start();

        Transsdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH) + 1;
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(AdvancesreportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String monthString = String.valueOf((monthOfYear) + 1);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void PrtCardInfo(StringBuffer buffer, int count, String amm) {

        long milis1 = System.currentTimeMillis();
        String date1 = DateUtil.timeMilisToString(milis1, "dd-MM-yyyy");
        String time1 = DateUtil.timeMilisToString(milis1, "  HH:mm a");
        PrinterApi.PrnClrBuff_Api();
        PrinterApi.printSetAlign_Api(0);
        PrinterApi.PrnFontSet_Api(32, 32, 0);
        PrinterApi.PrnSetGray_Api(15);
        PrinterApi.PrnLineSpaceSet_Api((short) 5, 0);
        PrinterApi.PrnFontSet_Api(24, 24, 0);
        PrinterApi.PrnStr_Api("                           " + "\n");
        PrinterApi.PrnStr_Api("                           " + "\n");
        PrinterApi.PrnStr_Api("\n" + "K-PILLAR SACCO SOCIETY LIMITED" + "\n");
        PrinterApi.PrnStr_Api("Deposit Report \n");
        PrinterApi.PrnStr_Api("Amount    AccountNumber \n");
        PrinterApi.PrnStr_Api("" + buffer.toString() + "" + "\n");
        PrinterApi.PrnStr_Api("Customers Served : " + count + "\n");
        PrinterApi.PrnStr_Api("Total amount Deposited : " + amm + "\n");
        PrinterApi.PrnStr_Api("--------------------------" + "\n");
        PrinterApi.PrnStr_Api("Date:" + date1 + "" + "," + "Time:" + time1 + "" + "\n");
        PrinterApi.PrnStr_Api("--------------------------" + "\n");
        PrinterApi.PrnStr_Api("Thankyou for your Patronage" + "\n");
        PrinterApi.PrnStr_Api("--------------------------" + "\n");
        PrinterApi.PrnStr_Api("DESIGNED & DEVELOPED BY" + "\n");
        PrinterApi.PrnStr_Api("AMTECH TECHNOLOGIES LTD" + "\n");
        PrinterApi.PrnStr_Api("www.amtechafrica.com" + "\n");
        PrinterApi.PrnStr_Api("--------------------------" + "\n");
        PrinterApi.PrnStr_Api("                           " + "\n");
        PrinterApi.PrnStr_Api("                           " + "\n");
        PrinterApi.PrnStr_Api("\n\n");

        // when printing images, the max size of images is 384 * 600
        PrinterApi.printSetAlign_Api(1);
        PrintData();

        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
        startActivity(intent);
    }

    public static int PrintData() {
        int ret;
        String Buf = null;
        while (true) {
            ret = PrinterApi.PrnStart_Api();
            Log.d("aabb", "PrnStart_Api:" + ret);
            if (ret == 2) {
                Buf = "Return:" + ret + "	paper is not enough";
            } else if (ret == 3) {
                Buf = "Return:" + ret + "	too hot";
            } else if (ret == 4) {
                Buf = "Return:" + ret + "	PLS put it back\nPress any key to reprint";
            } else if (ret == 0) {
                return 0;
            }
            return -1;
        }
    }

    public byte[] readStream(int id) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;

            inStream = getResources().openRawResource(id);
            outStream = new ByteArrayOutputStream();

            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }

            data = outStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outStream.close();
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "Press again to refresh the app", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                PrinterApi.PrnClose_Api();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_print_advance:
                if (pflag == 1) {
                    String myDate = (Transsdate.getText().toString());
                    myDate = myDate.trim();
                    Cursor c = db.rawQuery("SELECT * FROM advances WHERE   transdate ='" + myDate + "'  ", null);
                    if (c.getCount() == 0) {
                        showMessage("Message", "No Transactions found");
                        mPrintReceiptBtn.setEnabled(true);
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    String amm = null;
                    while (c.moveToNext()) {
                        buffer.append(c.getString(0) + "\t" + c.getString(2) + " \n");
                    }
                    Cursor c1 = db.rawQuery("SELECT sum(Amount) FROM advances WHERE  transdate ='" + myDate + "'", null);
                    while (c1.moveToNext()) {
                        amm = String.format("%.2f", Double.parseDouble(c1.getString(0)));

                    }
                    showMessage("Transactions Details +" + amm + "", buffer.toString());
                    int count = c.getCount();


                    PrtCardInfo(buffer, count, amm);
                    break;
                }
                else
                {
                    Toast.makeText(AdvancesreportActivity.this, "Printer Not Ready,Try again ", Toast.LENGTH_SHORT).show();

                }
        }
    }
}


