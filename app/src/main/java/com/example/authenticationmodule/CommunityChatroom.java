package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class CommunityChatroom extends AppCompatActivity {

    MaterialButton BtnBackChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_chatroom);

        BtnBackChat = findViewById(R.id.BtnBack1);

        BtnBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CommunityChatroom.this, HealthHome.class));
            }
        });
    }
}