package com.parth.ygm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import com.parth.ygm.databinding.ActivityLeaveSecondBinding;
import com.parth.ygm.utilities.PreferenceManager;

public class LeaveSecondActivity extends AppCompatActivity {

    private ActivityLeaveSecondBinding binding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaveSecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setListener();
    }

    private void setListener() {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LeaveFirstActivity.class));
        finish();
    }
}