package com.parth.ygm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.parth.ygm.databinding.ActivityWorkFirstBinding;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.PreferenceManager;

import java.util.Calendar;

public class WorkFirstActivity extends AppCompatActivity {

    private ActivityWorkFirstBinding binding;
    PreferenceManager preferenceManager;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setListener();
    }

    private void setListener() {


        binding.nameEditText.setText(preferenceManager.getString(Constants.KEY_NAME));
        binding.nameEditText.setEnabled(false);

        // Get today's date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.dateEditText.setText(day + "/" + (month + 1) + "/" + year);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    selectedDate = i2 + "/" + (i1 + 1) + "/" + i;
                    binding.dateEditText.setText(selectedDate);
                }
            });
        }

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkSecondActivity.class);
                intent.putExtra("fullName", binding.nameEditText.getText().toString());
                intent.putExtra("date", binding.dateEditText.getText().toString());
                startActivity(intent);
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