package com.parth.ygm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.parth.ygm.databinding.ActivityHomeBinding;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.PreferenceManager;

import java.util.Calendar;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    PreferenceManager preferenceManager;

    private String selectedDate;


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

        binding.nameEditText.setText(preferenceManager.getString(Constants.KEY_NAME));
        binding.nameEditText.setEnabled(false);

        // Get today's date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        long today = calendar.getTimeInMillis();

        // Set the minimum date allowed for the DatePicker to today's date
        binding.datePicker.setMinDate(today);
        binding.dateEditText.setText(day + "-" + (month + 1) + "-" + year);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    selectedDate = i2 + "-" + (i1 + 1) + "-" + i;
                    binding.dateEditText.setText(selectedDate);
                }
            });
        }


        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("fullName", binding.nameEditText.getText().toString());
                intent.putExtra("date", binding.dateEditText.getText().toString());
                startActivity(intent);
                finish();
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
