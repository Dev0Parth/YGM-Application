package com.parth.ygm.utilities;

import com.parth.ygm.models.CheckResponse;
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
            @Field("Emp_Code") String empId,
            @Field("Name") String fullName,
            @Field("Department") String department,
            @Field("Date") String date,
            @Field("Status") String status,
            @Field("First_Half_Work") String firstHalfWork,
            @Field("Second_Half_Work") String secondHalfWork,
            @Field("Scoping") String scoping,
            @Field("Created_At") String createdAt
    );

    @FormUrlEncoded
    @POST("addLeave.php")
    Call<ResponseBody> addLeave(
            @Field("Emp_Code") String empId,
            @Field("Name") String fullName,
            @Field("Department") String department,
            @Field("From_Date") String fromDate,
            @Field("To_Date") String toDate,
            @Field("Status") String status,
            @Field("Leave_Type") String leaveType,
            @Field("Leave_Reason") String leaveReason,
            @Field("Created_At") String createdAt
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(
            @Field("Ga_Id") String ga_ID
    );


    @FormUrlEncoded
    @POST("checkLeave.php")
    Call<List<LeavesData>> fetchLeaves(
            @Field("Emp_Code") String empId,
            @Field("Date") String date
    );

    @FormUrlEncoded
    @POST("countLeaves.php")
    Call<List<LeaveCount>> countLeaves(
            @Field("Emp_Code") String empId
    );
}
