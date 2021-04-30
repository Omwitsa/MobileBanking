//package com.aratek.trustfinger.utils;
//import com.vanstone.trans.api.PrinterApi;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//import com.aratek.trustfinger.Pockdata.PocketPos;
//import com.aratek.trustfinger.R;
//import com.vanstone.appsdk.client.ISdkStatue;
//import com.vanstone.trans.api.PrinterApi;
//import com.vanstone.trans.api.SystemApi;
//import com.vanstone.utils.CommonConvert;
//
//
//
//
//
//
//public class Printer {
//    StringBuffer _buffer;
//    P25Connector _mConnector;
//    SQLiteDatabase _db;
//    String _transaction;
//    String _supplierNo;
//
//    public Printer(){}
//    public Printer(StringBuffer buffer, P25Connector mConnector, SQLiteDatabase db, String transaction, String supplierNo){
//        _buffer = buffer;
//        _mConnector = mConnector;
//        _transaction = transaction;
//        _supplierNo = supplierNo;
//        _db = db;
//    }
//
//    public static byte[] printfont (String content, byte fonttype, byte fontalign, byte linespace, byte language){
//
//        if (content != null && content.length() > 0) {
//
//            content = content + "\n";
//            byte[] temp = null;
//            temp = PocketPos.convertPrintData(content, 0,content.length(), language, fonttype,fontalign,linespace);
//
//            return temp;
//        }else{
//            return null;
//        }
//    }
//
//
//    public  void print(){
//
//        PrinterApi.PrnStr_Api("--------------------------------");
//        PrinterApi.PrnStr_Api("I accept this trade and allow it on my account");
//        PrinterApi.PrnStr_Api("----------x------------x-------");
//        PrinterApi.PrnStr_Api("\n\n");
//        PrintData();
//        long milis1 = System.currentTimeMillis();
//        String date1 = DateUtil.timeMilisToString(milis1, "dd-MM-yyyy");
//        String time1 = DateUtil.timeMilisToString(milis1, "  HH:mm a");
//        StringBuilder content2Sb = new StringBuilder();
//        content2Sb.append("                           " + "\n");
//        content2Sb.append("\n" + "K-PILLAR SACCO SOCIETY LIMITED"+ "\n");
//        content2Sb.append("" + _buffer.toString() + "" + "\n");
//        content2Sb.append("--------------------------" + "\n");
//        content2Sb.append("Date:" + date1 + "" + "," + "Time:" + time1 + "" + "\n");
//        content2Sb.append("--------------------------" + "\n");
//        content2Sb.append("Thankyou for your Patronage" + "\n");
//        content2Sb.append("--------------------------" + "\n");
//        content2Sb.append("DESIGNED & DEVELOPED BY" + "\n");
//        content2Sb.append("AMTECH TECHNOLOGIES LTD" + "\n");
//        content2Sb.append("www.amtechafrica.com" + "\n");
//        content2Sb.append("--------------------------" + "\n");
//        content2Sb.append("                           " + "\n");
//        content2Sb.append("                           " + "\n");
//        content2Sb.append("                           " + "\n");
//        content2Sb.append("                           " + "\n");
//        content2Sb.append("                           " + "\n");
//
//
//        byte[] content2Byte = printfont(content2Sb.toString(), com.aratek.trustfinger.utils.FontDefine.FONT_32PX, com.aratek.trustfinger.utils.FontDefine.Align_LEFT, (byte) 0x1A,
//                PocketPos.LANGUAGE_ENGLISH);
//        byte[] totalByte = new byte[content2Byte.length];
//        int offset = 0;
//        System.arraycopy(content2Byte, 0, totalByte, offset, content2Byte.length);
//        offset += content2Byte.length;
//        byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, totalByte, 0, totalByte.length);
//        sendData(senddata, _transaction, _supplierNo);
//    }
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
//    private void sendData(byte[] bytes, String transaction, String supplierNo) {
//        try {
//            _mConnector.sendData(bytes);
//        } catch (P25ConnectionException e) {
//            e.printStackTrace();
//        }
//    }
//}

package com.larry.trustfinger.utils;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.larry.trustfinger.Pockdata.PocketPos;
import com.larry.trustfinger.R;
import com.vanstone.appsdk.client.ISdkStatue;
import com.vanstone.trans.api.PrinterApi;
import com.vanstone.trans.api.SystemApi;
import com.vanstone.utils.CommonConvert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Printer extends Activity implements View.OnClickListener {
    public static final String TAG = "Printer";
    Button print;
    StringBuffer _buffer;
    P25Connector _mConnector;
    SQLiteDatabase _db;
    String _transaction;
    String _supplierNo;
    int pflag = 0;
    private InputStream inStream;
    private ByteArrayOutputStream outStream;
    private byte[] data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print);
        //print = (Button) findViewById(R.id.Print);
        //print.setOnClickListener(this);


        new Thread() {
            public void run() {
                String CurAppDir = getApplicationContext().getFilesDir().getAbsolutePath();
                SystemApi.SystemInit_Api(0, CommonConvert.StringToBytes(CurAppDir + "/" + "\0"), Printer.this, new ISdkStatue() {
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

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.Print:
//                if (pflag == 1)
//                    print();
//                else
//                    Toast.makeText(Printer.this, "Not prepare well!", Toast.LENGTH_SHORT).show();
//                break;
//        }


    }

    public Printer() {
    }


    public Printer(StringBuffer buffer, P25Connector mConnector, SQLiteDatabase db, String transaction, String supplierNo) {
        _buffer = buffer;
        _mConnector = mConnector;
        _transaction = transaction;
        _supplierNo = supplierNo;
        _db = db;
    }

    public static byte[] printfont(String content, byte fonttype, byte fontalign, byte linespace, byte language) {

        if (content != null && content.length() > 0) {

            content = content + "\n";
            byte[] temp = null;
            temp = PocketPos.convertPrintData(content, 0, content.length(), language, fonttype, fontalign, linespace);

            return temp;
        } else {
            return null;
        }
    }





    public void print() {
        long milis1 = System.currentTimeMillis();
        String date1 = DateUtil.timeMilisToString(milis1, "dd-MM-yyyy");
        String time1 = DateUtil.timeMilisToString(milis1, "  HH:mm a");
        PrinterApi.PrnClrBuff_Api();
        PrinterApi.printSetAlign_Api(0);
        PrinterApi.PrnFontSet_Api(32, 32, 0);
        PrinterApi.PrnSetGray_Api(15);
        PrinterApi.PrnLineSpaceSet_Api((short) 5, 0);
        PrinterApi.PrnStr_Api("     K-PILLAR SACCO SOCIETY LIMITED");
        PrinterApi.PrnStr_Api("     POS Receipt");
        PrinterApi.PrnFontSet_Api(24, 24, 0);
        PrinterApi.PrnStr_Api("                           " + "\n");
        PrinterApi.PrnStr_Api("\n" + "K-PILLAR SACCO SOCIETY LIMITED" + "\n");
        PrinterApi.PrnStr_Api("" + _buffer.toString() + "" + "\n");
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

