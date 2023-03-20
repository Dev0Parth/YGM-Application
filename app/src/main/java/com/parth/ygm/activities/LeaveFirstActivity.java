package com.parth.ygm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parth.ygm.databinding.ActivityLeaveFirstBinding;
import com.parth.ygm.models.EmployeeData;
import com.parth.ygm.models.User;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.LeaveAdapter;
import com.parth.ygm.utilities.PreferenceManager;
import com.parth.ygm.utilities.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveFirstActivity extends AppCompatActivity {

    private ActivityLeaveFirstBinding binding;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaveFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setListener();
    }

    private void setListener() {

        String empId = preferenceManager.getString(Constants.KEY_EMPID);

        Call<List<EmployeeData>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getLeaves(empId);

        call.enqueue(new Callback<List<EmployeeData>>() {
            @Override
            public void onResponse(Call<List<EmployeeData>> call, Response<List<EmployeeData>> response) {
                List<EmployeeData> data = response.body();
                LeaveAdapter adapter = new LeaveAdapter(data);
                binding.leaveRV.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<EmployeeData>> call, Throwable t) {
                Toast.makeText(LeaveFirstActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.addLeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LeaveSecondActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}