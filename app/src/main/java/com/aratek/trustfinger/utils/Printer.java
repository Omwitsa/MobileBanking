package com.aratek.trustfinger.utils;

import android.database.sqlite.SQLiteDatabase;
import com.aratek.trustfinger.Pockdata.PocketPos;
import com.aratek.trustfinger.utils.DateUtil;
import com.aratek.trustfinger.utils.P25ConnectionException;
import com.aratek.trustfinger.utils.P25Connector;

public class Printer {
    StringBuffer _buffer;
    P25Connector _mConnector;
    SQLiteDatabase _db;
    String _transaction;
    String _supplierNo;

    public Printer(){}
    public Printer(StringBuffer buffer, P25Connector mConnector, SQLiteDatabase db, String transaction, String supplierNo){
        _buffer = buffer;
        _mConnector = mConnector;
        _transaction = transaction;
        _supplierNo = supplierNo;
        _db = db;
    }

    public static byte[] printfont (String content, byte fonttype, byte fontalign, byte linespace, byte language){

        if (content != null && content.length() > 0) {

            content = content + "\n";
            byte[] temp = null;
            temp = PocketPos.convertPrintData(content, 0,content.length(), language, fonttype,fontalign,linespace);

            return temp;
        }else{
            return null;
        }
    }


    public  void print(){
        long milis1 = System.currentTimeMillis();
        String date1 = DateUtil.timeMilisToString(milis1, "dd-MM-yyyy");
        String time1 = DateUtil.timeMilisToString(milis1, "  HH:mm a");
        StringBuilder content2Sb = new StringBuilder();
        content2Sb.append("                           " + "\n");
        content2Sb.append("\n" + "K-PILLAR SACCO SOCIETY LIMITED"+ "\n");
        content2Sb.append("" + _buffer.toString() + "" + "\n");
        content2Sb.append("--------------------------" + "\n");
        content2Sb.append("Date:" + date1 + "" + "," + "Time:" + time1 + "" + "\n");
        content2Sb.append("--------------------------" + "\n");
        content2Sb.append("Thankyou for your Patronage" + "\n");
        content2Sb.append("--------------------------" + "\n");
        content2Sb.append("DESIGNED & DEVELOPED BY" + "\n");
        content2Sb.append("AMTECH TECHNOLOGIES LTD" + "\n");
        content2Sb.append("www.amtechafrica.com" + "\n");
        content2Sb.append("--------------------------" + "\n");
        content2Sb.append("                           " + "\n");
        content2Sb.append("                           " + "\n");
        content2Sb.append("                           " + "\n");
        content2Sb.append("                           " + "\n");
        content2Sb.append("                           " + "\n");

        byte[] content2Byte = printfont(content2Sb.toString(), com.aratek.trustfinger.utils.FontDefine.FONT_32PX, com.aratek.trustfinger.utils.FontDefine.Align_LEFT, (byte) 0x1A,
                PocketPos.LANGUAGE_ENGLISH);
        byte[] totalByte = new byte[content2Byte.length];
        int offset = 0;
        System.arraycopy(content2Byte, 0, totalByte, offset, content2Byte.length);
        offset += content2Byte.length;
        byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, totalByte, 0, totalByte.length);
        sendData(senddata, _transaction, _supplierNo);
    }

    private void sendData(byte[] bytes, String transaction, String supplierNo) {
        try {
            _mConnector.sendData(bytes);
        } catch (P25ConnectionException e) {
            e.printStackTrace();
        }
    }
}
