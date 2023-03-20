package com.parth.ygm.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.parth.ygm.databinding.ActivityHomeBinding;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.DataSyncWorker;
import com.parth.ygm.utilities.PreferenceManager;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    PreferenceManager preferenceManager;
    private static final int INTERVAL = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setListener();
    }

    private void setListener() {

        binding.nameTitleView.setText(preferenceManager.getString(Constants.KEY_NAME));

        binding.addWorkCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WorkFirstActivity.class));
                finish();
            }
        });

        binding.applyLeaveCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LeaveFirstActivity.class));
            }
        });

        binding.logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(preferenceManager.getString(Constants.KEY_IS_SIGNED_IN), "yes")) {
                    preferenceManager.putString(Constants.KEY_IS_SIGNED_IN, "no");
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
