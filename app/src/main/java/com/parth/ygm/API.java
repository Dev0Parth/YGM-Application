package com.parth.ygm;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("submitData")
    Call<ResponseBody> submitData(
            @Field("fullName") String fullName,
            @Field("presentOrLeave") String presentOrLeave,
            @Field("halfLeave") String halfLeave,
            @Field("fullLeave") String fullLeave,
            @Field("workDone") String workDone,
            @Field("leaveReason") String leaveReason,
            @Field("createdAt") String createdAt
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
