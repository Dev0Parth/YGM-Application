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

public class WorkSyncWorker extends Worker {

    private final MutableLiveData<Boolean> syncInProgressLiveData = new MutableLiveData<>();

    public WorkSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {

        String empId, fullName, department, date, status, firstHalfWork, secondHalfWork, scoping, createdAt;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyEmployeeData", Context.MODE_PRIVATE);

        if (sharedPreferences.getAll().isEmpty()) {
            empId = getInputData().getString("empId");
            fullName = getInputData().getString("fullName");
            department = getInputData().getString("department");
            date = getInputData().getString("date");
            status = getInputData().getString("status");
            firstHalfWork = getInputData().getString("firstHalfWork");
            secondHalfWork = getInputData().getString("secondHalfWork");
            scoping = getInputData().getString("scoping");
            createdAt = getInputData().getString("createdAt");
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            empId = sharedPreferences.getString("empId", "");
            fullName = sharedPreferences.getString("fullName", "");
            department = sharedPreferences.getString("department", "");
            date = sharedPreferences.getString("date", "");
            status = sharedPreferences.getString("status", "");
            firstHalfWork = sharedPreferences.getString("firstHalfWork", "");
            secondHalfWork = sharedPreferences.getString("secondHalfWork", "");
            scoping = sharedPreferences.getString("scoping", "");
            createdAt = sharedPreferences.getString("createdAt", "");

            editor.clear();
            editor.apply();
        }


        if (isInternetAvailable()) {
            sendDataToServer(empId, fullName, department, date, status, firstHalfWork, secondHalfWork, scoping, createdAt);
        } else {
            saveDataToSharedPreferences(empId, fullName, department, date, status, firstHalfWork, secondHalfWork, scoping, createdAt);
        }

        return ListenableWorker.Result.success();
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void sendDataToServer(String empId, String fullName, String department, String date, String status, String firstHalfWork, String secondHalfWork, String scoping, String createdAt) {


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .addWork(empId, fullName, department, date, status, firstHalfWork, secondHalfWork, scoping, createdAt);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void saveDataToSharedPreferences(String empId, String fullName, String department, String date, String status, String firstHalfWork, String secondHalfWork, String scoping, String createdAt) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyEmployeeData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("empId", empId);
        editor.putString("fullName", fullName);
        editor.putString("department", department);
        editor.putString("date", date);
        editor.putString("status", status);
        editor.putString("firstHalfWork", firstHalfWork);
        editor.putString("secondHalfWork", secondHalfWork);
        editor.putString("scoping", scoping);
        editor.putString("createdAt", createdAt);
        editor.apply();

        Toast.makeText(getApplicationContext(), "data saved", Toast.LENGTH_SHORT).show();
    }
}
