//package com.aratek.trustfinger.utils;
//
//import com.aratek.trustfinger.R;
//import com.vanstone.appsdk.client.ISdkStatue;
//import com.vanstone.trans.api.PrinterApi;
//import com.vanstone.trans.api.SystemApi;
//import com.vanstone.utils.CommonConvert;
//
//import android.app.Activity;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Toast;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class MainActivity extends Activity implements OnClickListener {
//    public static final String TAG = "MainActivity";
//    Button print;
//    int pflag = 0;
//    private InputStream inStream;
//    private ByteArrayOutputStream outStream;
//    private byte[] data;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.print);
//        print = (Button) findViewById(R.id.Print);
//        print.setOnClickListener(this);
//
//        new Thread() {
//            public void run() {
//                String CurAppDir = getApplicationContext().getFilesDir().getAbsolutePath();
//                SystemApi.SystemInit_Api(0, CommonConvert.StringToBytes(CurAppDir + "/" + "\0"), MainActivity.this, new ISdkStatue() {
//                    @Override
//                    public void sdkInitSuccessed() {
//                        pflag = 1;
//                    }
//
//                    @Override
//                    public void sdkInitFailed() {
//                        pflag = 0;
//                    }
//                });
//            }
//
//            ;
//        }.start();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "onPause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d(TAG, "onRestart");
//    }
//
//    @Override
//    public void onClick(View v) {
//        //TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.Print:
//                if (pflag == 1)
//                    PrtCardInfo();
//                else
//                    Toast.makeText(MainActivity.this, "Not prepare well!", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
//
//    public void PrtCardInfo() {
//
//
//
//
//        PrinterApi.PrnClrBuff_Api();
//        PrinterApi.printSetAlign_Api(0);
//        PrinterApi.PrnFontSet_Api(32, 32, 0);
//        PrinterApi.PrnSetGray_Api(15);
//        PrinterApi.PrnLineSpaceSet_Api((short) 5, 0);
//        PrinterApi.PrnStr_Api("     K-PILLAR SACCO SOCIETY LIMITED");
//        PrinterApi.PrnStr_Api("     POS Receipt");
//        PrinterApi.PrnFontSet_Api(24, 24, 0);
//        PrinterApi.printSetAlign_Api(1);
//        PrinterApi.PrnStr_Api("CARDHOLDER COPY");
//        PrinterApi.printSetAlign_Api(0);
//        PrinterApi.PrnStr_Api("--------------------------------");
//
//        PrinterApi.PrnStr_Api("MERCHANT NAME:");
//        PrinterApi.PrnStr_Api("CARREFOUR");
//        PrinterApi.PrnStr_Api("MERCHANT NO.: ");
//        PrinterApi.PrnStr_Api("120401124594");
//        PrinterApi.PrnStr_Api("TERMINAL NO.: ");
//        PrinterApi.PrnStr_Api("TRANS TYPE.: ");
//        PrinterApi.PrnFontSet_Api(32, 32, 0);
//        PrinterApi.PrnStr_Api("Sale");
//        PrinterApi.PrnFontSet_Api(24, 24, 0);
//        PrinterApi.PrnStr_Api("PAYMENT TYPE.: ");
//        PrinterApi.PrnStr_Api("CARDHOLDER SIGNATURE:\n\n\n\n");
//
//        // when printing images, the max size of images is 384 * 600
//        PrinterApi.printSetAlign_Api(1);
//        //PrinterApi.PrnLogo_Api( BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.feifenglogo2));
//
//        PrinterApi.PrnStr_Api("\n\n");
////        String apkPath = "";
////        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
////            apkPath = MainActivity.this.getExternalFilesDir("").getAbsolutePath();
////        } else {
////            apkPath = MainActivity.this.getFilesDir().getAbsolutePath();
////        }
////        apkPath += "/logo_receipt.jpg";
////        PrinterApi.PrnLogo_Api(apkPath.getBytes(), 1);
//
//        PrinterApi.PrnStr_Api("\n\n");
////        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bb);
////        int width = bitmap.getWidth();
////        PrinterApi.PrnLeftIndSet_Api((short) ((384 - width) / 2));// if you want to set align when calling PrinterApi.PrnLogo_Api(bitmap), you need to use this api to set it
////        PrinterApi.PrnLogo_Api(bitmap);
//
//
//        PrinterApi.PrnStr_Api("\n\n");
//        PrinterApi.PrnLeftIndSet_Api((short) 0);
//        PrinterApi.printSetAlign_Api(1);
//        PrinterApi.printAddBarCode_Api(1, 400, 80, true, "www.szfptech.com");
//
//        PrinterApi.PrnStr_Api("\n\n");
//        PrinterApi.printAddQrCode_Api(1, 200, "www.szfptech");
//
//        PrinterApi.PrnStr_Api("\n\n\n\n");
//
//        PrinterApi.PrnStr_Api("--------------------------------");
//        PrinterApi.PrnStr_Api("I accept this trade and allow it on my account");
//        PrinterApi.PrnStr_Api("----------x------------x-------");
//        PrinterApi.PrnStr_Api("\n\n");
//        PrintData();
//    }
//
//    public static int PrintData() {
//        int ret;
//        String Buf = null;
//        while (true) {
//            ret = PrinterApi.PrnStart_Api();
//            Log.d("aabb", "PrnStart_Api:" + ret);
//            if (ret == 2) {
//                Buf = "Return:" + ret + "	paper is not enough";
//            } else if (ret == 3) {
//                Buf = "Return:" + ret + "	too hot";
//            } else if (ret == 4) {
//                Buf = "Return:" + ret + "	PLS put it back\nPress any key to reprint";
//            } else if (ret == 0) {
//                return 0;
//            }
//            return -1;
//        }
//    }
//
//    public byte[] readStream(int id) {
//        try {
//            byte[] buffer = new byte[1024];
//            int len = -1;
//
//            inStream = getResources().openRawResource(id);
//            outStream = new ByteArrayOutputStream();
//
//            while ((len = inStream.read(buffer)) != -1) {
//                outStream.write(buffer, 0, len);
//            }
//
//            data = outStream.toByteArray();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outStream.close();
//                inStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return data;
//    }
//
//
//    private long exitTime = 0;
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(this, "Press again to exit the app", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                PrinterApi.PrnClose_Api();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//
//}
package com.aratek.trustfinger.utils;

import com.aratek.trustfinger.Activities.HomeActivity;
import com.aratek.trustfinger.R;
import com.vanstone.appsdk.client.ISdkStatue;
import com.vanstone.trans.api.PrinterApi;
import com.vanstone.trans.api.SystemApi;
import com.vanstone.utils.CommonConvert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends Activity implements OnClickListener {
    public static final String TAG = "MainActivity";
    Button print;
    int pflag = 0;
    private InputStream inStream;
    private ByteArrayOutputStream outStream;
    private byte[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print);
        print = (Button) findViewById(R.id.Print);
        print.setOnClickListener(this);

        new Thread() {
            public void run() {
                String CurAppDir = getApplicationContext().getFilesDir().getAbsolutePath();
                SystemApi.SystemInit_Api(0, CommonConvert.StringToBytes(CurAppDir + "/" + "\0"), MainActivity.this, new ISdkStatue() {
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

            ;
        }.start();
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
    //my code



    int min = 1;
    int max = 10000;
    Random r = new Random();
    int i1 = r.nextInt(max - min + 1) + min;
    String version = "MG001POS" + i1;

    private StringBuffer getAgencyPrintData(Bundle extras){
        StringBuffer buffer = new StringBuffer();
        if (extras != null) {
            String amount = extras.getString("amount");
            String transaction = extras.getString("transaction");
            String fingurePrint = extras.getString("fingurePrint");
            String supplierNo = extras.getString("supplierNo");
            String pin = extras.getString("pin");

            buffer.append("Agent Copy \n");
            buffer.append("----------------------------- \n");
           // buffer.append( operat.toUpperCase()+" RECEIPT" + "\n");
            buffer.append("ReceiptNumber : "+version+ "\n");
            buffer.append("Account No:" + supplierNo + "\n");
            buffer.append("Amount    :" + amount + "\n");
        }

        return buffer;
    }
    private StringBuffer getCustomerPrintData(Bundle extras){

        StringBuffer buffer = new StringBuffer();
        if (extras != null) {
            String amount = extras.getString("amount");
            String transaction = extras.getString("transaction");
            String fingurePrint = extras.getString("fingurePrint");
            String supplierNo = extras.getString("supplierNo");
            String pin = extras.getString("pin");

            buffer.append("Customer Copy \n");
            buffer.append("----------------------------- \n");
            buffer.append( transaction.toUpperCase()+" RECEIPT" + "\n");
            buffer.append("ReceiptNumber : "+version+ "\n");
            buffer.append("Account No:" + supplierNo + "\n");
            buffer.append("Amount    :" + amount + "\n");
        }

        return buffer;
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    //end of code

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.Print:
                if (pflag == 1)
                    PrtCardInfo();
                else
                    Toast.makeText(MainActivity.this, "Not prepare well!", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void PrtCardInfo() {
        Bundle extras = getIntent().getExtras();
        String amount = extras.getString("amount");
        String transaction = extras.getString("transaction");
        String sNo = extras.getString("supplierNo");
        int min = 1;
        int max = 10000;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        String version = "MG001POS" + i1;

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
        PrinterApi.PrnStr_Api("\n" + "K-PILLAR SACCO SOCIETY LIMITED" + "\n");
        PrinterApi.PrnStr_Api("Agent Copy \n");
        PrinterApi.PrnStr_Api( transaction.toUpperCase()+" RECEIPT" + "\n");
        PrinterApi.PrnStr_Api("ReceiptNumber : "+version+ "\n");
        PrinterApi.PrnStr_Api("Amount   :"   + amount+ "\n");
        PrinterApi.PrnStr_Api("Account NO   :" + sNo+ "\n");
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
        PrinterApi.PrnStr_Api("                           " + "\n");
        PrinterApi.PrnStr_Api("\n" + "K-PILLAR SACCO SOCIETY LIMITED" + "\n");
        PrinterApi.PrnStr_Api("Customer Copy \n");
        PrinterApi.PrnStr_Api( transaction.toUpperCase()+" RECEIPT" + "\n");
        PrinterApi.PrnStr_Api("ReceiptNumber : "+version+ "\n");
        PrinterApi.PrnStr_Api("Amount   :"   + amount+ "\n");
        PrinterApi.PrnStr_Api("Account NO   :" + sNo+ "\n");
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
                Toast.makeText(this, "Press again to exit the app", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                PrinterApi.PrnClose_Api();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}