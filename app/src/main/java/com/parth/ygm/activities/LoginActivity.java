package com.parth.ygm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.parth.ygm.models.User;
import com.parth.ygm.utilities.Constants;
import com.parth.ygm.utilities.PreferenceManager;
import com.parth.ygm.utilities.RetrofitClient;
import com.parth.ygm.databinding.ActivityLoginBinding;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(getApplicationContext());

        setListener();
    }

    private void setListener(){
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.usernameEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();

                Call<User> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .login(username, password);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                        if (response.isSuccessful()) {
                            User user = response.body();

                            String name = user.getName();
                            if (name != null) {
                                String empId = user.getEmpId();
                                String email = user.getEmail();
                                String phone = user.getPhone();

                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Error occurred, please try again later!", Toast.LENGTH_SHORT).show();
                        }

//                            Gson gson = new Gson();
//                            Type type = new TypeToken<Map<String, Object>>() {}.getType();
//                            Map<String, Object> responseMap = gson.fromJson(String.valueOf(response), type);
//
//                            String fullName = responseMap.get("fullName").toString();
//                            String empId = responseMap.get("empId").toString();
//                            String email = responseMap.get("email").toString();
//                            String phone = responseMap.get("phone").toString();
//
//                            preferenceManager.putString(Constants.KEY_NAME, fullName);
//                            preferenceManager.putString(Constants.KEY_EMPID, empId);
//                            preferenceManager.putString(Constants.KEY_EMAIL, email);
//                            preferenceManager.putString(Constants.KEY_PHONE, phone);
//
//                            preferenceManager.putString(Constants.KEY_IS_SIGNED_IN, "yes");


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}