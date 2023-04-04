package com.parth.ygm.utilities;

import com.parth.ygm.models.EmployeeData;
import com.parth.ygm.models.LeaveCount;
import com.parth.ygm.models.LeavesData;
import com.parth.ygm.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("addWork.php")
    Call<ResponseBody> addWork(
            @Field("empId") String empId,
            @Field("fullName") String fullName,
            @Field("department") String department,
            @Field("date") String date,
            @Field("firstHalfWork") String firstHalfWork,
            @Field("secondHalfWork") String secondHalfWork,
            @Field("scoping") String scoping,
            @Field("createdAt") String createdAt
    );

    @FormUrlEncoded
    @POST("addLeave.php")
    Call<ResponseBody> addLeave(
            @Field("empId") String empId,
            @Field("fullName") String fullName,
            @Field("department") String department,
            @Field("fromDate") String fromDate,
            @Field("toDate") String toDate,
            @Field("leaveType") String leaveType,
            @Field("leaveReason") String leaveReason,
            @Field("createdAt") String createdAt
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(
            @Field("username") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("checkLeave.php")
    Call<List<LeavesData>> fetchLeaves(
            @Field("empId") String empId,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("countLeaves.php")
    Call<List<LeaveCount>> countLeaves(
            @Field("empId") String empId
    );
}
