package com.parth.ygm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.parth.ygm.R;
import com.parth.ygm.utilities.RetrofitClient;
import com.parth.ygm.databinding.ActivitySecondBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;

    private String fullName, date, createdAt;
    private String presentOrLeave = "leave";
    private String presentWorkText = "";
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

        setListener();
    }

    private void setListener() {
        binding.presentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    binding.leaveCheck.setChecked(false);
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

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendData();

            }
        });
    }


    private void sendData() {

        if (binding.presentCheck.isChecked()) {
            presentOrLeave = "present";
            presentWorkText = binding.workEditText.getText().toString();
            LocalDateTime localDateTime = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                createdAt = localDateTime.format(formatter);
            }


            Call<ResponseBody> call = RetrofitClient
                    .getInstance()
                    .getAPI()
                    .submitData(fullName, presentOrLeave, "-", "-", presentWorkText, "-", createdAt);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    startActivity(new Intent(getApplicationContext(), SuccessActivity.class));
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(SecondActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else if (binding.leaveCheck.isChecked()){
            presentOrLeave = "leave";

            if (binding.halfLeaveCheck.isChecked()) {
                if (binding.firstHalfCheck.isChecked()) {
                    firstOrSecond = "first half";
                } else if (binding.secondHalfCheck.isChecked()){
                    firstOrSecond = "second half";
                }

                halfWorkText = binding.halfWorkEditText.getText().toString();
                LocalDateTime localDateTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    localDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    createdAt = localDateTime.format(formatter);
                }

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .submitData(fullName, presentOrLeave, firstOrSecond, "-", halfWorkText, "-", createdAt);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        startActivity(new Intent(getApplicationContext(), SuccessActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SecondActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (binding.fullLeaveCheck.isChecked()) {

                leaveReason = binding.leaveReasonEditText.getText().toString();
                LocalDateTime localDateTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    localDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    createdAt = localDateTime.format(formatter);
                }

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .submitData(fullName, presentOrLeave, "-", "yes", "-", leaveReason, createdAt);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        startActivity(new Intent(getApplicationContext(), SuccessActivity.class));
                        finish();
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
        finishAffinity();
    }
}