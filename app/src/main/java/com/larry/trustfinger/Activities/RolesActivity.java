package com.larry.trustfinger.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.larry.trustfinger.Model.Response;
import com.larry.trustfinger.Model.TellerDate;
import com.larry.trustfinger.R;
import com.larry.trustfinger.Rest.ApiClient;
import com.larry.trustfinger.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class RolesActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "POSDETAILS" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final String ids = sharedpreferences.getString("loadsAgentId", "");
        final String IsAdmins = sharedpreferences.getString("loadrole", "");
        if(ids.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Agent id is Missing",Toast.LENGTH_LONG).show();
        }
        else if(IsAdmins.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Operator Role is missing",Toast.LENGTH_LONG).show();
        }
        else{

        String machineId = android.os.Build.SERIAL;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        TellerDate tellerDate= new TellerDate(ids,machineId);
        Call<Response> call = apiService.TellerStatus(tellerDate);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response responseData = response.body();
                String role=responseData.getMessage();
                if(!role.equals(null))
                {
         //Toast.makeText(getApplicationContext(), role, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), RegisterPhone.class);
                editor.putString("loadsAgentId", ids);
                editor.putString("loadTeller", role);
                editor.putString("loadrole", IsAdmins);
                editor.commit();
                startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "You don't have permission as Administrator ", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
    }
    @Override
    public void onBackPressed() {
        return;
    }
}