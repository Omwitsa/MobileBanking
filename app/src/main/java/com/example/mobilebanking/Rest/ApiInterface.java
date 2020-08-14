package com.example.mobilebanking.Rest;

import com.example.mobilebanking.Model.ClientModel;
import com.example.mobilebanking.Model.DepositModel;
import com.example.mobilebanking.Model.Response;
import com.example.mobilebanking.Model.MemberModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("webservice/users/login")
    Call<Response> login(@Body MemberModel memberModel);

    @POST("webservice/users/registerFingerPrints")
    Call<Response> registerFingerPrints(@Body ClientModel clientModel);

    @POST("webservice/users/seedAdminUser")
    Call<Response> createUser(@Body MemberModel memberModel);

    @POST("webservice/transacions/deposit")
    Call<Response> deposit(@Body DepositModel depositModel);

    @POST("webservice/transacions/withdraw")
    Call<Response> withdraw(@Body DepositModel depositModel);

//    @GET("webservice/k-pillar/users.php")
//    Call<Response> getMembers(@Path("id") int id, @Query("api_key") String apiKey);
}
