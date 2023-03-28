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

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.parth.ygm.R;
import com.parth.ygm.databinding.ActivityHomeBinding;
import com.parth.ygm.models.LeavesData;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.LeaveSyncWorker;
import com.parth.ygm.utilities.WorkSyncWorker;
import com.parth.ygm.utilities.PreferenceManager;
import com.parth.ygm.utilities.RetrofitClient;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    private String[] options = {"First Half", "Second Half", "Full Leave"};

    private String empId, department, formattedDateTime, fullName, firstHalfWork, secondHalfWork, scoping, date, fromDate, toDate, selectedItem, leaveReason;

    private String fetchedLeaveType = "";

    PreferenceManager preferenceManager;
    private static final int INTERVAL = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drop_list_item, options);
        binding.autoCompleteTv.setAdapter(adapter);

        setListener();
    }

    private void setListener() {

        preferenceManager.putString(Constants.KEY_DATA_TYPE, "work");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Get today's date
        Date dateObj = new Date();
        String formattedDate = simpleDateFormat.format(dateObj);

        fetchLeaves(formattedDate);

        binding.dateEditText.setText(formattedDate);
        binding.fromDateEditText.setText(formattedDate);
        binding.toDateEditText.setText(formattedDate);

        binding.dateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("SELECT A DATE");
                final MaterialDatePicker<Long> materialDatePicker = builder.build();
                materialDatePicker.show(getSupportFragmentManager(), "Date_Picker");
                materialDatePicker.addOnPositiveButtonClickListener(selection -> binding.dateEditText.setText(simpleDateFormat.format(new Date(materialDatePicker.getHeaderText()))));
            }
        });

        binding.fromDateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("SELECT A DATE");
                final MaterialDatePicker<Long> materialDatePicker = builder.build();
                materialDatePicker.show(getSupportFragmentManager(), "Date_Picker");
                materialDatePicker.addOnPositiveButtonClickListener(selection -> binding.fromDateEditText.setText(simpleDateFormat.format(new Date(materialDatePicker.getHeaderText()))));
            }
        });

        binding.toDateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("SELECT A DATE");
                final MaterialDatePicker<Long> materialDatePicker = builder.build();
                materialDatePicker.show(getSupportFragmentManager(), "Date_Picker");
                materialDatePicker.addOnPositiveButtonClickListener(selection -> binding.toDateEditText.setText(simpleDateFormat.format(new Date(materialDatePicker.getHeaderText()))));
            }
        });

        binding.addLeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.leaveTypeLayout.setVisibility(View.VISIBLE);
                binding.reasonLayout.setVisibility(View.VISIBLE);
                binding.fromToDateLL.setVisibility(View.VISIBLE);
                binding.firstHalfLayout.setVisibility(View.GONE);
                binding.secondHalfLayout.setVisibility(View.GONE);
                binding.scopingLayout.setVisibility(View.GONE);
                preferenceManager.putString(Constants.KEY_DATA_TYPE, "leave");
            }
        });

        binding.autoCompleteTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();

            }
        });

        binding.nameTitleView.setText(preferenceManager.getString(Constants.KEY_NAME));

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

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(preferenceManager.getString(Constants.KEY_DATA_TYPE), "work")) {

                    if (Objects.equals(fetchedLeaveType, "first half")) {
                        if (TextUtils.isEmpty(binding.secondHalfWorkEditText.getText().toString())) {
                            binding.secondHalfLayout.setError("field can't be empty!");
                        } else {
                            binding.secondHalfLayout.setError(null);
                        }
                        addWork();
                    } else if (Objects.equals(fetchedLeaveType, "second half")){
                        if (TextUtils.isEmpty(binding.firstHalfWorkEditText.getText().toString())) {
                            binding.firstHalfLayout.setError("field can't be empty!");
                        } else {
                            binding.firstHalfLayout.setError(null);
                        }
                        addWork();
                    } else {
                        if (TextUtils.isEmpty(binding.firstHalfWorkEditText.getText().toString())) {
                            binding.firstHalfLayout.setError("field can't be empty!");
                        } else {
                            binding.firstHalfLayout.setError(null);
                            if (TextUtils.isEmpty(binding.secondHalfWorkEditText.getText().toString())) {
                                binding.secondHalfLayout.setError("field can't be empty!");
                            } else {
                                binding.secondHalfLayout.setError(null);
                                addWork();
                            }
                        }


                    }

                } else if (Objects.equals(preferenceManager.getString(Constants.KEY_DATA_TYPE), "leave")) {
                    if (TextUtils.isEmpty(binding.autoCompleteTv.getText().toString())) {
                        binding.leaveTypeLayout.setError("you must select one leave");
                    } else {
                        binding.leaveTypeLayout.setError(null);
                        if (TextUtils.isEmpty(binding.reasonEditText.getText().toString())) {
                            binding.reasonLayout.setError("field can't be empty!");
                        } else {
                            binding.reasonLayout.setError(null);
                            addLeave();
                        }
                    }

                }
            }
        });
    }


    private void fetchLeaves(String todayDate) {

        empId = preferenceManager.getString(Constants.KEY_EMPID);

        Call<List<LeavesData>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .fetchLeaves(empId, todayDate);

        call.enqueue(new Callback<List<LeavesData>>() {
            @Override
            public void onResponse(Call<List<LeavesData>> call, Response<List<LeavesData>> response) {
                List<LeavesData> data = response.body();
                if (data.get(0).getLeaveType() != null) {
                    if (Objects.equals(data.get(0).getLeaveType(), "first half")) {
                        binding.firstHalfLayout.setVisibility(View.GONE);
                        binding.secondHalfLayout.setVisibility(View.VISIBLE);
                        fetchedLeaveType = "first half";
                    } else if (Objects.equals(data.get(0).getLeaveType(), "second half")) {
                        binding.firstHalfLayout.setVisibility(View.VISIBLE);
                        binding.secondHalfLayout.setVisibility(View.GONE);
                        fetchedLeaveType = "second half";
                    } else if (Objects.equals(data.get(0).getLeaveType(), "full leave")) {
                        binding.firstHalfLayout.setVisibility(View.GONE);
                        binding.secondHalfLayout.setVisibility(View.GONE);
                        binding.scopingLayout.setVisibility(View.GONE);
                        binding.todayFullLeaveLayout.setVisibility(View.VISIBLE);
                        fetchedLeaveType = "full leave";
                    }
                } else {
                    fetchedLeaveType = "no leaves";
                }
            }

            @Override
            public void onFailure(Call<List<LeavesData>> call, Throwable t) {

            }
        });

    }

    private void addWork() {
        department = preferenceManager.getString(Constants.KEY_DEPARTMENT);
        fullName = preferenceManager.getString(Constants.KEY_NAME);
        empId = preferenceManager.getString(Constants.KEY_EMPID);

        date = binding.dateEditText.getText().toString();

        if (TextUtils.isEmpty(binding.firstHalfWorkEditText.getText().toString())) {
            firstHalfWork = "-";
        } else {
            firstHalfWork = binding.firstHalfWorkEditText.getText().toString();
        }
        if (TextUtils.isEmpty(binding.secondHalfWorkEditText.getText().toString())) {
            secondHalfWork = "-";
        } else {
            secondHalfWork = binding.secondHalfWorkEditText.getText().toString();
        }

        if (TextUtils.isEmpty(binding.scopingEditText.getText().toString())) {
            scoping = null;
        } else {
            scoping = binding.scopingEditText.getText().toString();
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            formattedDateTime = localDateTime.format(formatter);
        }

        Data inputData = new Data.Builder()
                .putString("empId",empId)
                .putString("fullName", fullName)
                .putString("department", department)
                .putString("date", date)
                .putString("firstHalfWork", firstHalfWork)
                .putString("secondHalfWork", secondHalfWork)
                .putString("scoping", scoping)
                .putString("createdAt", formattedDateTime)
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(WorkSyncWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();


        WorkManager.getInstance(getApplicationContext()).enqueue(myWorkRequest);

        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

    private void addLeave() {
        department = preferenceManager.getString(Constants.KEY_DEPARTMENT);
        fullName = preferenceManager.getString(Constants.KEY_NAME);
        empId = preferenceManager.getString(Constants.KEY_EMPID);
        fromDate = binding.fromDateEditText.getText().toString();
        toDate = binding.toDateEditText.getText().toString();
        leaveReason = binding.reasonEditText.getText().toString();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            formattedDateTime = localDateTime.format(formatter);
        }

        Data inputData = new Data.Builder()
                .putString("empId",empId)
                .putString("fullName", fullName)
                .putString("department", department)
                .putString("fromDate", fromDate)
                .putString("toDate", toDate)
                .putString("leaveType", selectedItem)
                .putString("leaveReason", leaveReason)
                .putString("createdAt", formattedDateTime)
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(LeaveSyncWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();


        WorkManager.getInstance(getApplicationContext()).enqueue(myWorkRequest);


        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
