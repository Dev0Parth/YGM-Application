package com.parth.ygm.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.parth.ygm.R;
import com.parth.ygm.activities.HomeActivity;
import com.parth.ygm.activities.WorkSecondActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSyncWorker extends Worker {

    private final MutableLiveData<Boolean> syncInProgressLiveData = new MutableLiveData<>();



    public DataSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String empId, fullName, department, fromDate, toDate, present, leaveType, scopeOfWork, scoping, leaveReason, createdAt;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyEmployeeData", Context.MODE_PRIVATE);

        if (sharedPreferences.getAll().isEmpty()) {
            empId = getInputData().getString("empId");
            fullName = getInputData().getString("fullName");
            department = getInputData().getString("department");
            fromDate = getInputData().getString("fromDate");
            toDate = getInputData().getString("toDate");
            present = getInputData().getString("present");
            leaveType = getInputData().getString("leaveType");
            scopeOfWork = getInputData().getString("scopeOfWork");
            scoping = getInputData().getString("scoping");
            leaveReason = getInputData().getString("leaveReason");
            createdAt = getInputData().getString("createdAt");
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            empId = sharedPreferences.getString("empId", "");
            fullName = sharedPreferences.getString("fullName", "");
            department = sharedPreferences.getString("department", "");
            fromDate = sharedPreferences.getString("fromDate", "");
            toDate = sharedPreferences.getString("toDate", "");
            present = sharedPreferences.getString("present", "");
            leaveType = sharedPreferences.getString("leaveType", "");
            scopeOfWork = sharedPreferences.getString("scopeOfWork", "");
            scoping = sharedPreferences.getString("scoping", "");
            leaveReason = sharedPreferences.getString("leaveReason", "");
            createdAt = sharedPreferences.getString("createdAt", "");

            editor.clear();
            editor.apply();
        }


        if (isInternetAvailable()) {
            sendDataToServer(empId, fullName, department, fromDate, toDate, present, leaveType, scopeOfWork, scoping, leaveReason, createdAt);
        } else {
            saveDataToSharedPreferences(empId, fullName, department, fromDate, toDate, present, leaveType, scopeOfWork, scoping, leaveReason, createdAt);
        }

        return Result.success();
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void sendDataToServer(String empId, String fullName, String department, String fromDate, String toDate, String present, String leaveType, String scopeOfWork, String scoping, String leaveReason, String createdAt) {


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .submitData(empId, fullName, department, fromDate, toDate, present, leaveType, scopeOfWork, scoping, leaveReason, createdAt);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void saveDataToSharedPreferences(String empId, String fullName, String department, String fromDate, String toDate, String present, String leaveType, String scopeOfWork, String scoping, String leaveReason, String createdAt) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyEmployeeData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("empId", empId);
        editor.putString("fullName", fullName);
        editor.putString("department", department);
        editor.putString("fromDate", fromDate);
        editor.putString("toDate", toDate);
        editor.putString("present", present);
        editor.putString("leaveType", leaveType);
        editor.putString("scopeOfWork", scopeOfWork);
        editor.putString("scoping", scoping);
        editor.putString("leaveReason", leaveReason);
        editor.putString("createdAt", createdAt);
        editor.apply();

        Toast.makeText(getApplicationContext(), "data saved", Toast.LENGTH_SHORT).show();
    }
}
