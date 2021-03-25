package com.aratek.trustfinger.Rest;
import com.aratek.trustfinger.Model.AgencyModel;
import com.aratek.trustfinger.Model.AgentMember;
import com.aratek.trustfinger.Model.FingurePrintModel;
import com.aratek.trustfinger.Model.FirstNameModel;
import com.aratek.trustfinger.Model.LoadData;
import com.aratek.trustfinger.Model.LoginModel;
import com.aratek.trustfinger.Model.MemberModel;
import com.aratek.trustfinger.Model.ProductModel;
import com.aratek.trustfinger.Model.RegisterFingerprints;
import com.aratek.trustfinger.Model.Response;
import com.aratek.trustfinger.Model.SecondNameModel;
import com.aratek.trustfinger.Model.TellerDate;
import com.aratek.trustfinger.Model.TransactionModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("webservice/users/login")
    Call<Response> login(@Body MemberModel memberModel);

    @POST("webservice/users/existingMembersFingerPrints")
    Call<Response> existingMembersFingerPrints(@Body FingurePrintModel fingurePrint);


    @POST("webservice/users/existingFirstName")
    Call<Response> existingFirstName(@Body FirstNameModel FirstName);


    @POST("webservice/users/existingSecondName")
    Call<Response> existingSecondName(@Body SecondNameModel SecondName);

    @POST("webservice/users/newMembersFingerPrints")
    Call<Response> newMembersFingerPrints(@Body FingurePrintModel fingurePrint);

    @POST("webservice/users/OperatorsFingerPrints")
    Call<Response> OperatorsFingerPrints(@Body FingurePrintModel fingurePrint);

    @POST("webservice/users/adminLogin")
    Call<Response> adminLogin(@Body LoginModel loginModel);

    @POST("webservice/users/TellerStatus")
    Call<Response> TellerStatus(@Body TellerDate tellerDate);

    @POST("webservice/users/passwordLogin")
    Call<Response> passwordLogin(@Body LoadData loadData);

    @POST("webservice/users/registerAgentMember")
    Call<Response> registerAgentMember(@Body AgentMember agentMember);
    @POST("webservice/users/registerAgencyMember")
    Call<Response> registerAgencyMember(@Body AgencyModel agencyModel);

    @POST("webservice/transacions/deposit")
    Call<Response> deposit(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/withdraw")
    Call<Response> withdraw(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/balance")
    Call<Response> getBalance(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/calculateAdvance")
    Call<Response> applyAdvance(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/sychTransactions")
    Call<Response> sychTransactions(@Body List<TransactionModel> transactionModel);

    @POST("webservice/transacions/fetchAdvanceProducts")
    Call<List<ProductModel>> getAdvanceProcucts(@Body TransactionModel transactionModel);
    @POST("webservice/transacions/fetchMemberAccounts")
    Call<List<String>> getUserAccounts(@Body TransactionModel transactionModel);

    @POST("webservice/transacions/fetchAgencyAccounts")
    Call<List<String>> getAgency(@Body TransactionModel transactionModel);

//    @GET("webservice/k-pillar/users.php")
//    Call<Response> getMembers(@Path("id") int id, @Query("api_key") String apiKey);
}
