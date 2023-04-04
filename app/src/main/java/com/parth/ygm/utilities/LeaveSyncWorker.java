package com.parth.ygm.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveSyncWorker extends Worker {

    private final MutableLiveData<Boolean> syncInProgressLiveData = new MutableLiveData<>();

    public LeaveSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String empId, fullName, department, fromDate, toDate, status, leaveType, leaveReason, createdAt;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyEmployeeLeaves", Context.MODE_PRIVATE);

        if (sharedPreferences.getAll().isEmpty()) {
            empId = getInputData().getString("empId");
            fullName = getInputData().getString("fullName");
            department = getInputData().getString("department");
            fromDate = getInputData().getString("fromDate");
            toDate = getInputData().getString("toDate");
            status = getInputData().getString("status");
            leaveType = getInputData().getString("leaveType");
            leaveReason = getInputData().getString("leaveReason");
            createdAt = getInputData().getString("createdAt");
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            empId = sharedPreferences.getString("empId", "");
            fullName = sharedPreferences.getString("fullName", "");
            department = sharedPreferences.getString("department", "");
            fromDate = sharedPreferences.getString("fromDate", "");
            toDate = sharedPreferences.getString("toDate", "");
            status = sharedPreferences.getString("status", "");
            leaveType = sharedPreferences.getString("leaveType", "");
            leaveReason = sharedPreferences.getString("leaveReason", "");
            createdAt = sharedPreferences.getString("createdAt", "");

            editor.clear();
            editor.apply();
        }


        if (isInternetAvailable()) {
            sendDataToServer(empId, fullName, department, fromDate, toDate, status, leaveType, leaveReason, createdAt);
        } else {
            saveDataToSharedPreferences(empId, fullName, department, fromDate, toDate, status, leaveType, leaveReason, createdAt);
        }


        return ListenableWorker.Result.success();
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void sendDataToServer(String empId, String fullName, String department, String fromDate, String toDate, String status, String leaveType, String leaveReason, String createdAt) {


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .addLeave(empId, fullName, department, fromDate, toDate, status, leaveType, leaveReason, createdAt);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void saveDataToSharedPreferences(String empId, String fullName, String department, String fromDate, String toDate, String status, String leaveType, String leaveReason, String createdAt) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyEmployeeLeaves", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("empId", empId);
        editor.putString("fullName", fullName);
        editor.putString("department", department);
        editor.putString("fromDate", fromDate);
        editor.putString("toDate", toDate);
        editor.putString("status", status);
        editor.putString("leaveType", leaveType);
        editor.putString("leaveReason", leaveReason);
        editor.putString("createdAt", createdAt);
        editor.apply();

        Toast.makeText(getApplicationContext(), "data saved", Toast.LENGTH_SHORT).show();
    }
}
