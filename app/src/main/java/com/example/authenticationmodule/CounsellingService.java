package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CounsellingService extends AppCompatActivity {

    ImageButton btnBackFromCounselling;
    Button btnPatient, btnCounsellor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselling_service);

        btnBackFromCounselling = findViewById(R.id.BtnBackFromCounselling);
        btnPatient = findViewById(R.id.BtnPatient);
        btnCounsellor = findViewById(R.id.BtnCounsellor);
        btnBackFromCounselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CounsellingService.this, HealthHome.class));
            }
        });

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CounsellingService.this, displayCounsellor.class));
            }
        });

        btnCounsellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}