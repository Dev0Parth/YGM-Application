package com.parth.ygm.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.parth.ygm.R;
import com.parth.ygm.databinding.ActivityWorkSecondBinding;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.DataSyncWorker;
import com.parth.ygm.utilities.PreferenceManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkSecondActivity extends AppCompatActivity {

    private ActivityWorkSecondBinding binding;
    PreferenceManager preferenceManager;

    private String fullName, date, department, createdAt;
    private String present = "";
    private String leaveType = "";
    private String formattedDateTime = "0000-00-00 00:00:00";
    private String presentWorkText = "";
    private String scoping = "";
    private String halfWorkText = "";
    private String leaveReason = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkSecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        fullName = intent.getStringExtra("fullName");
        date = intent.getStringExtra("date");


        preferenceManager = new PreferenceManager(getApplicationContext());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setListener();
    }

    private void setListener() {

        binding.presentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    binding.halfLeaveCheck.setChecked(false);
                    binding.firstHalfCheck.setChecked(false);
                    binding.secondHalfCheck.setChecked(false);
                    binding.linearLayout2.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout2.setVisibility(View.GONE);
                }
            }
        });

        binding.halfLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    binding.presentCheck.setChecked(false);
                    binding.firstHalfCheck.setChecked(false);
                    binding.secondHalfCheck.setChecked(false);
                    binding.linearLayout3.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout3.setVisibility(View.GONE);
                }
            }
        });

        binding.firstHalfCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.secondHalfCheck.setChecked(false);
                    binding.linearLayout4.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout4.setVisibility(View.GONE);
                }
            }
        });

        binding.secondHalfCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.firstHalfCheck.setChecked(false);
                    binding.linearLayout4.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout4.setVisibility(View.GONE);
                }
            }
        });

        binding.workEditText.addTextChangedListener(new TextWatcher() {
            @SuppressLint({ "UseCompatLoadingForDrawables"})
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.halfWorkEditText.addTextChangedListener(new TextWatcher() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.leaveReasonEditText.addTextChangedListener(new TextWatcher() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkFirstActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendData();

            }
        });

    }


    private void sendData() {
        department = preferenceManager.getString(Constants.KEY_DEPARTMENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            formattedDateTime = localDateTime.format(formatter);
        }

        if (binding.presentCheck.isChecked()) {
            present = "present";
            presentWorkText = binding.workEditText.getText().toString();
            if (TextUtils.isEmpty(binding.scopingEditText.getText().toString())) {
                scoping = null;
            } else {
                scoping = binding.scopingEditText.getText().toString();
            }

            Data inputData = new Data.Builder()
                    .putString("empId", preferenceManager.getString(Constants.KEY_EMPID))
                    .putString("fullName", fullName)
                    .putString("department", department)
                    .putString("fromDate", date)
                    .putString("toDate", date)
                    .putString("present", present)
                    .putString("leaveType", "-")
                    .putString("scopeOfWork", presentWorkText)
                    .putString("scoping", scoping)
                    .putString("leaveReason", "-")
                    .putString("createdAt", formattedDateTime)
                    .build();

            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(DataSyncWorker.class)
                    .setConstraints(constraints)
                    .setInputData(inputData)
                    .build();

            WorkManager.getInstance(getApplicationContext()).enqueue(myWorkRequest);

            ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
            View view = LayoutInflater.from(WorkSecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

            AlertDialog.Builder builder = new AlertDialog.Builder(WorkSecondActivity.this);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();

            new Handler().postDelayed(() -> {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }, 2000);

        } else if (binding.halfLeaveCheck.isChecked()) {
            if (binding.firstHalfCheck.isChecked()) {
                leaveType = "half leave (first half)";
            } else if (binding.secondHalfCheck.isChecked()){
                leaveType = "half leave (second half)";
            }

            halfWorkText = binding.halfWorkEditText.getText().toString();
            leaveReason = binding.leaveReasonEditText.getText().toString();

            if (TextUtils.isEmpty(binding.halfscopingEditText.getText().toString())) {
                scoping = null;
            } else {
                scoping = binding.halfscopingEditText.getText().toString();
            }


            Data inputData = new Data.Builder()
                    .putString("empId", preferenceManager.getString(Constants.KEY_EMPID))
                    .putString("fullName", fullName)
                    .putString("department", department)
                    .putString("fromDate", date)
                    .putString("toDate", date)
                    .putString("present", "-")
                    .putString("leaveType", leaveType)
                    .putString("scopeOfWork", halfWorkText)
                    .putString("scoping", scoping)
                    .putString("leaveReason", leaveReason)
                    .putString("createdAt", formattedDateTime)
                    .build();

            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(DataSyncWorker.class)
                    .setConstraints(constraints)
                    .setInputData(inputData)
                    .build();

            WorkManager.getInstance(getApplicationContext()).enqueue(myWorkRequest);

            ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
            View view = LayoutInflater.from(WorkSecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

            AlertDialog.Builder builder = new AlertDialog.Builder(WorkSecondActivity.this);
            builder.setView(view);
            final AlertDialog alertDialog = builder.create();

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();

            new Handler().postDelayed(() -> {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }, 2000);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), WorkFirstActivity.class));
        finish();
    }
}