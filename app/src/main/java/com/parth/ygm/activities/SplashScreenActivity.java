package com.parth.ygm.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.Gson;
import com.parth.ygm.databinding.ActivitySplashScreenBinding;
import com.parth.ygm.models.CheckResponse;
import com.parth.ygm.models.User;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.PreferenceManager;
import com.parth.ygm.utilities.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;

    PreferenceManager preferenceManager;
    private static final int REQUEST_CODE_READ_PHONE_STATE = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        new Handler().postDelayed(() -> {

            // Check if the READ_PHONE_STATE permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, start the AsyncTask to retrieve the GSF ID
                GetGAIdTask task = new GetGAIdTask();
                task.execute();
            } else {
                // Permission is not granted, request it from the user
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_READ_PHONE_STATE);
            }

        }, 3000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_PHONE_STATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, start the AsyncTask to retrieve the GSF ID
                GetGAIdTask task = new GetGAIdTask();
                task.execute();
            } else {
                // Permission is not granted, show a message or request the permission again
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetGAIdTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String ga_ID = null;
            GoogleApiAvailability api = GoogleApiAvailability.getInstance();
            int result = api.isGooglePlayServicesAvailable(SplashScreenActivity.this);
            if (result == ConnectionResult.SUCCESS) {
                try {
                    AdvertisingIdClient.Info info = AdvertisingIdClient.getAdvertisingIdInfo(SplashScreenActivity.this);
                    ga_ID = info.getId();
                } catch (IOException | GooglePlayServicesNotAvailableException |
                         GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
            return ga_ID;
        }

        @Override
        protected void onPostExecute(String ga_ID) {
            if (ga_ID != null) {

                Call<User> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .login(ga_ID);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                        if (response.isSuccessful()) {

                            User user = response.body();

                            String name = user.getName();
                            if (name != null) {
                                String empId = user.getEmp_Code();
                                String department = user.getDepartment();
                                String phone = user.getContact_No();
                                String gaID = user.getGa_Id();
                                String createdAt = user.getCreated_At();

                                preferenceManager.putString(Constants.KEY_NAME, name);
                                preferenceManager.putString(Constants.KEY_EMPID, empId);
                                preferenceManager.putString(Constants.KEY_DEPARTMENT, department);
                                preferenceManager.putString(Constants.KEY_PHONE, phone);
                                preferenceManager.putString(Constants.KEY_GSFID, gaID);
                                preferenceManager.putString(Constants.KEY_CREATEDAT, createdAt);

                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SplashScreenActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(SplashScreenActivity.this, "Error occurred, please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(SplashScreenActivity.this, "Failed to retrieve GSF ID", Toast.LENGTH_SHORT).show();
            }
        }
    }


//    @SuppressLint("HardwareIds")
//    private String getICCID() {
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        return telephonyManager.getSimSerialNumber();
//    }
//
//
//    private void autoLogin(String ICCID) {
//
//        Call<User> call = RetrofitClient
//                .getInstance()
//                .getAPI()
//                .login(ICCID);
//
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
//
//                if (response.isSuccessful()) {
//                    User user = response.body();
//
//                    String name = user.getName();
//                    if (name != null) {
//                        String empId = user.getEmpId();
//                        String department = user.getDepartment();
//
//                        preferenceManager.putString(Constants.KEY_NAME, name);
//                        preferenceManager.putString(Constants.KEY_EMPID, empId);
//                        preferenceManager.putString(Constants.KEY_DEPARTMENT, department);
//
//                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                        finish();
//                    } else {
//                        Toast.makeText(SplashScreenActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(SplashScreenActivity.this, "Error occurred, please try again later!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

}