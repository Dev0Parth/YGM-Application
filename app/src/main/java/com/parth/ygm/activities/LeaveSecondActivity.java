package com.parth.ygm.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
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
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.parth.ygm.R;
import com.parth.ygm.databinding.ActivityLeaveSecondBinding;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.DataSyncWorker;
import com.parth.ygm.utilities.PreferenceManager;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LeaveSecondActivity extends AppCompatActivity {

    private ActivityLeaveSecondBinding binding;
    PreferenceManager preferenceManager;

    private boolean dateSelected = false;
    private boolean reasonAdded = false;
    private String fullName, date, startDate, endDate, department, createdAt;
    private String present = "";
    private String leaveType = "";
    private String scoping = "";
    private String leaveReason = "";
    private String formattedDateTime = "0000-00-00 00:00:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaveSecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


        binding.nameEditText.setText(preferenceManager.getString(Constants.KEY_NAME));
        binding.nameEditText.setEnabled(false);

        binding.halfLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.fullLeaveCheck.setChecked(false);
                    binding.oneDayLeaveCheck.setChecked(false);
                    binding.firstHalfLeaveCheck.setChecked(false);
                    binding.secondHalfLeaveCheck.setChecked(false);
                    binding.multiDayLeaveCheck.setChecked(false);
                    binding.linearLayout6.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout6.setVisibility(View.GONE);
                }
            }
        });

        binding.fullLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.halfLeaveCheck.setChecked(false);
                    binding.firstHalfLeaveCheck.setChecked(false);
                    binding.secondHalfLeaveCheck.setChecked(false);
                    binding.oneDayLeaveCheck.setChecked(false);
                    binding.multiDayLeaveCheck.setChecked(false);
                    binding.linearLayout2.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout2.setVisibility(View.GONE);
                }
            }
        });

        binding.firstHalfLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.secondHalfLeaveCheck.setChecked(false);
                    binding.oneDayLeaveCheck.setChecked(false);
                    binding.multiDayLeaveCheck.setChecked(false);
                    binding.linearLayout2.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout2.setVisibility(View.GONE);
                }
            }
        });

        binding.secondHalfLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.firstHalfLeaveCheck.setChecked(false);
                    binding.oneDayLeaveCheck.setChecked(false);
                    binding.multiDayLeaveCheck.setChecked(false);
                    binding.linearLayout2.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout2.setVisibility(View.GONE);
                }
            }
        });

        binding.oneDayLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.multiDayLeaveCheck.setChecked(false);
                    binding.linearLayout3.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout3.setVisibility(View.GONE);
                }
            }
        });

        binding.multiDayLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.oneDayLeaveCheck.setChecked(false);
                    binding.linearLayout4.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout4.setVisibility(View.GONE);
                }
            }
        });

        binding.oneDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    dateSelected = true;
                } else {
                    dateSelected = false;
                }
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    if (reasonAdded) {
                        binding.submitBtn.setEnabled(true);
                        binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                    }
                    dateSelected = true;
                } else {
                    dateSelected = false;
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.multiDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    dateSelected = true;
                } else {
                    dateSelected = false;
                }
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    if (reasonAdded) {
                        binding.submitBtn.setEnabled(true);
                        binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                    }
                    dateSelected = true;

                } else {
                    dateSelected = false;
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.oneDayLeaveReasonEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    reasonAdded = true;
                } else {
                    reasonAdded = false;
                }
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    if (dateSelected) {
                        binding.submitBtn.setEnabled(true);
                        binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                    }
                    reasonAdded = true;

                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                    reasonAdded = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.multiDayLeaveReasonEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    reasonAdded = true;
                } else {
                    reasonAdded = false;
                }
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    if (dateSelected) {
                        binding.submitBtn.setEnabled(true);
                        binding.submitBtn.setBackground(getDrawable(R.drawable.button_background));
                    }
                    reasonAdded = true;

                } else {
                    reasonAdded = false;
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackground(getDrawable(R.drawable.light_button_background));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.oneCalenderBtn.setOnClickListener(view -> {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("SELECT A DATE");
            final MaterialDatePicker<Long> materialDatePicker = builder.build();
            materialDatePicker.show(getSupportFragmentManager(), "Date_Picker");
            materialDatePicker.addOnPositiveButtonClickListener(selection -> binding.oneDateEditText.setText(simpleDateFormat.format(new Date(materialDatePicker.getHeaderText()))));
        });

        binding.rangeCalenderBtn.setOnClickListener(view -> {
            MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

            materialDatePicker.show(getSupportFragmentManager(), "TAG_PICKER");

            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                String firstDate = simpleDateFormat.format(new Date(selection.first));
                String endDate = simpleDateFormat.format(new Date(selection.second));

                binding.multiDateEditText.setText(firstDate + " - " + endDate);
            });
        });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LeaveFirstActivity.class);
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
        fullName = binding.nameEditText.getText().toString();
        department = preferenceManager.getString(Constants.KEY_DEPARTMENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            formattedDateTime = localDateTime.format(formatter);
        }

        if (binding.halfLeaveCheck.isChecked()) {
            if (binding.firstHalfLeaveCheck.isChecked()) {
                leaveType = "half leave (first half)";
            } else if (binding.secondHalfLeaveCheck.isChecked()) {
                leaveType = "half leave (second half)";
            }

            if (binding.oneDayLeaveCheck.isChecked()) {
                date = binding.oneDateEditText.getText().toString();
                leaveReason = binding.oneDayLeaveReasonEditText.getText().toString();
                scoping = null;

                Data inputData = new Data.Builder()
                        .putString("empId", preferenceManager.getString(Constants.KEY_EMPID))
                        .putString("fullName", fullName)
                        .putString("department", department)
                        .putString("fromDate", date)
                        .putString("toDate", date)
                        .putString("present", "-")
                        .putString("leaveType", leaveType)
                        .putString("scopeOfWork", "-")
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
                View view = LayoutInflater.from(LeaveSecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

                AlertDialog.Builder builder = new AlertDialog.Builder(LeaveSecondActivity.this);
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
            } else {
                date = binding.multiDateEditText.getText().toString();
                String[] dates = date.split(" - ");
                startDate = dates[0];
                endDate = dates[1];

                leaveReason = binding.multiDayLeaveReasonEditText.getText().toString();
                scoping = null;

                Data inputData = new Data.Builder()
                        .putString("empId", preferenceManager.getString(Constants.KEY_EMPID))
                        .putString("fullName", fullName)
                        .putString("department", department)
                        .putString("fromDate", startDate)
                        .putString("toDate", endDate)
                        .putString("present", "-")
                        .putString("leaveType", leaveType)
                        .putString("scopeOfWork", "-")
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
                View view = LayoutInflater.from(LeaveSecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

                AlertDialog.Builder builder = new AlertDialog.Builder(LeaveSecondActivity.this);
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

        } else {

            leaveType = "full leave";

            if (binding.oneDayLeaveCheck.isChecked()) {
                date = binding.oneDateEditText.getText().toString();
                leaveReason = binding.oneDayLeaveReasonEditText.getText().toString();
                scoping = null;

                Data inputData = new Data.Builder()
                        .putString("empId", preferenceManager.getString(Constants.KEY_EMPID))
                        .putString("fullName", fullName)
                        .putString("department", department)
                        .putString("fromDate", date)
                        .putString("toDate", date)
                        .putString("present", "-")
                        .putString("leaveType", leaveType)
                        .putString("scopeOfWork", "-")
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
                View view = LayoutInflater.from(LeaveSecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

                AlertDialog.Builder builder = new AlertDialog.Builder(LeaveSecondActivity.this);
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
            } else {
                date = binding.multiDateEditText.getText().toString();
                String[] dates = date.split(" - ");
                startDate = dates[0];
                endDate = dates[1];

                leaveReason = binding.multiDayLeaveReasonEditText.getText().toString();
                scoping = null;

                Data inputData = new Data.Builder()
                        .putString("empId", preferenceManager.getString(Constants.KEY_EMPID))
                        .putString("fullName", fullName)
                        .putString("department", department)
                        .putString("fromDate", startDate)
                        .putString("toDate", endDate)
                        .putString("present", "-")
                        .putString("leaveType", leaveType)
                        .putString("scopeOfWork", "-")
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
                View view = LayoutInflater.from(LeaveSecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

                AlertDialog.Builder builder = new AlertDialog.Builder(LeaveSecondActivity.this);
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

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LeaveFirstActivity.class));
        finish();
    }
}