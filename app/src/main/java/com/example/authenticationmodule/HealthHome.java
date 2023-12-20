package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class HealthHome extends AppCompatActivity {

    MaterialButton BtnChat, BtnCounselling, BtnEducation, BtnEmergency;
    Button BtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_home);

        BtnChat = findViewById(R.id.btn_community_chatroom);
        BtnCounselling = findViewById(R.id.btn_counselling);
        BtnEducation = findViewById(R.id.btn_health_education);
        BtnEmergency = findViewById(R.id.btn_emergency_locator);

        BtnBack = findViewById(R.id.backBtntoHome);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthHome.this, Home.class));
            }
        });

        BtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthHome.this, CommunityChatroom.class));
            }
        });

        BtnCounselling.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthHome.this, CounsellingService.class));
            }
        });

        BtnEducation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthHome.this, HealthEducation.class));
            }
        });

        BtnEmergency.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthHome.this, EmergencyLocator.class));
            }
        });
    }
}