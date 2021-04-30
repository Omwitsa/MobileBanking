package com.larry.trustfinger.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.larry.trustfinger.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionActivity extends AppCompatActivity {
    private TextView textView;

    private static String ip = "41.139.225.118/DESKTOP-J90586O\\SQL2020";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "TEST";
    private static String username = "K-PILLAR";
    private static String password = "kpillar";
    //private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private static String url ="jdbc:jtds:sqlserver://41.139.225.118:1433;instanceName=KON-DL-SERVER;DatabaseName="+database+";integratedSecurity=false;user="+username+";password="+password;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_connection);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        textView = findViewById(R.id.textView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            //connection = DriverManager.getConnection(url, username,password);
            connection = DriverManager.getConnection(url);
           // textView.setText("SUCCESS");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            textView.setText("ERROR");
        } catch (SQLException e) {
            e.printStackTrace();
            textView.setText("FAILURE");
        }
    }

    public void sqlButton(View view){
        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from Members where IDNo='22375739';");
                while (resultSet.next()){
                    textView.setText(resultSet.getString(5));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            textView.setText("Connection is null");
        }
    }
    }
