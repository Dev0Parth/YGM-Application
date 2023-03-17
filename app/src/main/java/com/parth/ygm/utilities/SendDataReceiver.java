package com.parth.ygm.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendDataReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOnline(context)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyEmployeeData", context.MODE_PRIVATE);
            if (!sharedPreferences.getAll().isEmpty()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String empId = sharedPreferences.getString("empId", "");
                String fullName = sharedPreferences.getString("fullName", "");
                String department = sharedPreferences.getString("department", "");
                String date = sharedPreferences.getString("date", "");
                String present = sharedPreferences.getString("present", "");
                String leaveType = sharedPreferences.getString("leaveType", "");
                String scopeOfWork = sharedPreferences.getString("scopeOfWork", "");
                String scoping = sharedPreferences.getString("scoping", "");
                String leaveReason = sharedPreferences.getString("leaveReason", "");
                String createdAt = sharedPreferences.getString("createdAt", "");


                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .submitData(empId, fullName, department, date, present, leaveType, scopeOfWork, scoping, leaveReason, createdAt);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
                editor.clear();
                editor.apply();
            }

        }
    }

    public boolean isOnline(Context context) {
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}