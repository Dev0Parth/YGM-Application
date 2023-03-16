package com.parth.ygm.utilities;

import com.parth.ygm.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("getEmployeeData.php")
    Call<ResponseBody> submitData(
            @Field("fullName") String fullName,
            @Field("date") String date,
            @Field("presentOrLeave") String presentOrLeave,
            @Field("halfLeave") String halfLeave,
            @Field("fullLeave") String fullLeave,
            @Field("scopeOfWork") String scopeOfWork,
            @Field("leaveReason") String leaveReason
//            @Field("createdAt") String createdAt
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
