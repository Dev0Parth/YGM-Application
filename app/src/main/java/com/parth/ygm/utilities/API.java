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
            @Field("empId") String empId,
            @Field("fullName") String fullName,
            @Field("department") String department,
            @Field("date") String date,
            @Field("present") String present,
            @Field("leaveType") String leaveType,
            @Field("scopeOfWork") String scopeOfWork,
            @Field("scoping") String scoping,
            @Field("leaveReason") String leaveReason
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(
            @Field("username") String username,
            @Field("password") String password
    );
}
