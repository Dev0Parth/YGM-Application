package com.parth.ygm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
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
import android.widget.Toast;

import com.parth.ygm.R;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.PreferenceManager;
import com.parth.ygm.utilities.RetrofitClient;
import com.parth.ygm.databinding.ActivitySecondBinding;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;

    PreferenceManager preferenceManager;

    private String fullName, date, department, createdAt;
    private String present = "";
    private String leaveType = "";
    private String presentWorkText = "";
    private String scoping = "";
    private String firstOrSecond = "";
    private String halfWorkText = "";
    private String leaveReason = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        fullName = intent.getStringExtra("fullName");
        date = intent.getStringExtra("date");

        preferenceManager = new PreferenceManager(getApplicationContext());

        setListener();
    }

    private void setListener() {
        binding.presentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    binding.leaveCheck.setChecked(false);
                    binding.halfLeaveCheck.setChecked(false);
                    binding.fullLeaveCheck.setChecked(false);
                    binding.firstHalfCheck.setChecked(false);
                    binding.secondHalfCheck.setChecked(false);
                    binding.linearLayout3.setVisibility(View.VISIBLE);
                    binding.linearLayout6.setVisibility(View.GONE);
                    binding.linearLayout5.setVisibility(View.GONE);
                    binding.linearLayout7.setVisibility(View.GONE);
                } else {
                    binding.linearLayout3.setVisibility(View.GONE);
                }
            }
        });

        binding.leaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    binding.presentCheck.setChecked(false);
                    binding.halfLeaveCheck.setChecked(false);
                    binding.fullLeaveCheck.setChecked(false);
                    binding.firstHalfCheck.setChecked(false);
                    binding.secondHalfCheck.setChecked(false);
                    binding.linearLayout4.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout4.setVisibility(View.GONE);
                }
            }
        });

        binding.halfLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    binding.fullLeaveCheck.setChecked(false);
                    binding.firstHalfCheck.setChecked(false);
                    binding.secondHalfCheck.setChecked(false);
                    binding.linearLayout5.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout5.setVisibility(View.GONE);
                }
            }
        });

        binding.fullLeaveCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    binding.halfLeaveCheck.setChecked(false);
                    binding.firstHalfCheck.setChecked(false);
                    binding.secondHalfCheck.setChecked(false);
                    binding.linearLayout6.setVisibility(View.VISIBLE);
                    binding.linearLayout7.setVisibility(View.GONE);
                } else {
                    binding.linearLayout6.setVisibility(View.GONE);
                }
            }
        });

        binding.firstHalfCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.secondHalfCheck.setChecked(false);
                    binding.linearLayout7.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout7.setVisibility(View.GONE);
                }
            }
        });

        binding.secondHalfCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.firstHalfCheck.setChecked(false);
                    binding.linearLayout7.setVisibility(View.VISIBLE);
                } else {
                    binding.linearLayout7.setVisibility(View.GONE);
                }
            }
        });

        binding.workEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.primary));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.primary));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.halfWorkEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.primary));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.primary));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        binding.leaveReasonEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.primary));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    binding.submitBtn.setEnabled(true);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.primary));
                } else {
                    binding.submitBtn.setEnabled(false);
                    binding.submitBtn.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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

        if (binding.presentCheck.isChecked()) {
            present = "present";
            presentWorkText = binding.workEditText.getText().toString();
            if (TextUtils.isEmpty(binding.scopingEditText.getText().toString())) {
                scoping = null;
            } else {
                scoping = binding.scopingEditText.getText().toString();
            }


            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .submitData(preferenceManager.getString(Constants.KEY_EMPID), fullName, department, date, present, "-", presentWorkText, scoping, "-");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    if (response.isSuccessful()) {

                        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
                        View view = LayoutInflater.from(SecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

                        AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
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
                        Toast.makeText(SecondActivity.this, "Error occurred, please try again later!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(SecondActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else if (binding.leaveCheck.isChecked()){
            if (binding.halfLeaveCheck.isChecked()) {
                if (binding.firstHalfCheck.isChecked()) {
                    leaveType = "first half leave";
                } else if (binding.secondHalfCheck.isChecked()){
                    leaveType = "second half leave";
                }

                halfWorkText = binding.halfWorkEditText.getText().toString();
                if (TextUtils.isEmpty(binding.halfscopingEditText.getText().toString())) {
                    scoping = null;
                } else {
                    scoping = binding.halfscopingEditText.getText().toString();
                }

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .submitData(preferenceManager.getString(Constants.KEY_EMPID), fullName, department, date, "-", leaveType, halfWorkText, scoping, "-");

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
                            View view = LayoutInflater.from(SecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

                            AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
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
                            Toast.makeText(SecondActivity.this, "Error occurred, please try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SecondActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (binding.fullLeaveCheck.isChecked()) {

                leaveType = "full leave";
                leaveReason = binding.leaveReasonEditText.getText().toString();

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .submitData(preferenceManager.getString(Constants.KEY_EMPID), fullName, department, date, "-", leaveType, "-", null, leaveReason);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
                            View view = LayoutInflater.from(SecondActivity.this).inflate(R.layout.success_dialog_layout, successConstraintLayout);

                            AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
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
                            Toast.makeText(SecondActivity.this, "Error occurred, please try again later!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SecondActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}