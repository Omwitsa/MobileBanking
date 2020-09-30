package com.aratek.trustfinger.Rest;
import com.aratek.trustfinger.Model.AgentMember;
import com.aratek.trustfinger.Model.FingurePrintModel;
import com.aratek.trustfinger.Model.MemberModel;
import com.aratek.trustfinger.Model.ProductModel;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.Model.TransactionModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("webservice/users/login")
    Call<Response> login(@Body MemberModel memberModel);

    @POST("webservice/users/registerFingerPrints")
    Call<Response> registerFingerPrints(@Body FingurePrintModel fingurePrint);

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
    @POST("webservice/transacions/fetchMemberAccounts")
    Call<List<String>> getUserAccounts(@Body TransactionModel transactionModel);

//    @GET("webservice/k-pillar/users.php")
//    Call<Response> getMembers(@Path("id") int id, @Query("api_key") String apiKey);
}
