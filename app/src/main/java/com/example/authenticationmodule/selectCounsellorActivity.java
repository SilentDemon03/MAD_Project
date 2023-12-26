package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.authenticationmodule.databinding.ActivitySelectCounsellorBinding;
import com.example.authenticationmodule.utilities.PreferenceManager;

public class selectCounsellorActivity extends AppCompatActivity {

    private ActivitySelectCounsellorBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCounsellorBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_select_counsellor);
        preferenceManager = new PreferenceManager(getApplicationContext());
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}