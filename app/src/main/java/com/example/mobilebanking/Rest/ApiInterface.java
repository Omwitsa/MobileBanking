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
    @POST("webservice/k-pillar/users.php")
    Call<Response> getMember(@Body MemberModel memberModel);

    @POST("webservice/k-pillar/users.php")
    Call<Response> createClient(@Body ClientModel clientModel);

    @POST("webservice/k-pillar/users.php")
    Call<Response> createUser(@Body MemberModel memberModel);

    @POST("webservice/k-pillar/transactions.php")
    Call<Response> deposit(@Body DepositModel depositModel);

    @POST("webservice/k-pillar/transactions.php")
    Call<Response> withdraw(@Body DepositModel depositModel);

//    @GET("webservice/k-pillar/users.php")
//    Call<Response> getMembers(@Path("id") int id, @Query("api_key") String apiKey);
}
