package com.example.mobilebanking.Rest;

import com.example.mobilebanking.Model.AgentMember;
import com.example.mobilebanking.Model.ClientModel;
import com.example.mobilebanking.Model.ProductModel;
import com.example.mobilebanking.Model.Response;
import com.example.mobilebanking.Model.MemberModel;
import com.example.mobilebanking.Model.TransactionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("webservice/users/registerAgentMember")
    Call<Response> registerAgentMember(@Body AgentMember agentMember);

    @POST("webservice/transacions/deposit")
    Call<Response> deposit(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/withdraw")
    Call<Response> withdraw(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/balance")
    Call<Response> getBalance(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/applyAdvance")
    Call<Response> applyAdvance(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/sychTransactions")
    Call<Response> sychTransactions(@Body List<TransactionModel> transactionModel);

    @POST("webservice/transacions/fetchAdvanceProducts")
    Call<List<ProductModel>> getAdvanceProcucts(@Body TransactionModel transactionModel);

//    @GET("webservice/k-pillar/users.php")
//    Call<Response> getMembers(@Path("id") int id, @Query("api_key") String apiKey);
}
