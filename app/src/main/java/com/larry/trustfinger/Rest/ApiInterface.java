package com.larry.trustfinger.Rest;
import com.larry.trustfinger.Model.AdvanceLimitModel;
import com.larry.trustfinger.Model.AgencyModel;
import com.larry.trustfinger.Model.AgentMember;
import com.larry.trustfinger.Model.AgentNameModel;
import com.larry.trustfinger.Model.FingurePrintModel;
import com.larry.trustfinger.Model.FirstNameModel;
import com.larry.trustfinger.Model.LoadData;
import com.larry.trustfinger.Model.LoginModel;
import com.larry.trustfinger.Model.MemberModel;
import com.larry.trustfinger.Model.ProductModel;
import com.larry.trustfinger.Model.Response;
import com.larry.trustfinger.Model.SecondNameModel;
import com.larry.trustfinger.Model.SuperAdminModel;
import com.larry.trustfinger.Model.TellerDate;
import com.larry.trustfinger.Model.TransActionEnquiryModel;
import com.larry.trustfinger.Model.TransactionModel;

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
    @POST("webservice/users/lastTransaction")
    Call<Response> lastTransaction(@Body TransActionEnquiryModel TransName);

    @POST("webservice/users/superAdminDetails")
    Call<Response> superAdminDetails(@Body SuperAdminModel FirstName);

    @POST("webservice/users/existingSecondName")
    Call<Response> existingSecondName(@Body SecondNameModel SecondName);

    @POST("webservice/users/agentMembersSecondName")
    Call<Response> agentMembersSecondName(@Body AgentNameModel AgentName);

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

    @POST("webservice/transacions/advanceLimit")
    Call<Response> advanceLimit(@Body AdvanceLimitModel advanceLimitModel);

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
