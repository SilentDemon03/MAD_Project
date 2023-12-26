package com.example.authenticationmodule;

import static com.example.authenticationmodule.cords.FirebaseCords.mAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class chatRoom extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton backBtn;

    EditText chatBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        backBtn = findViewById(R.id.BtnBackFromChat);
        toolbar = findViewById(R.id.toolbar);
        chatBox = findViewById(R.id.chatBox);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(chatRoom.this, CommunityChatroom.class));
            }
        });
    }


}