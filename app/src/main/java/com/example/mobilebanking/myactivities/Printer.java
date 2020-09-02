package com.example.mobilebanking.myactivities;

import android.database.sqlite.SQLiteDatabase;

import com.example.mobilebanking.Pockdata.PocketPos;

public class Printer {
    StringBuffer _buffer;
    P25Connector _mConnector;
    SQLiteDatabase _db;
    String _transaction;
    String _supplierNo;
    public Printer(StringBuffer buffer, P25Connector mConnector, SQLiteDatabase db, String transaction, String supplierNo){
        _buffer = buffer;
        _mConnector = mConnector;
        _transaction = transaction;
        _supplierNo = supplierNo;
        _db = db;
    }


    public  void print(){
        long milis1 = System.currentTimeMillis();
        String date1 = com.example.mobilebanking.Util.DateUtil.timeMilisToString(milis1, "dd-MM-yyyy");
        String time1 = com.example.mobilebanking.Util.DateUtil.timeMilisToString(milis1, "  HH:mm a");
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

        byte[] content2Byte = com.example.mobilebanking.Util.Printer.printfont(content2Sb.toString(), com.example.mobilebanking.Util.FontDefine.FONT_32PX, com.example.mobilebanking.Util.FontDefine.Align_LEFT, (byte) 0x1A,
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
