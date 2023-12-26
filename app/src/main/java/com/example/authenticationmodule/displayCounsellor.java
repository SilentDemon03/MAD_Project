package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.authenticationmodule.databinding.ActivityDisplayCounsellorBinding;
import com.example.authenticationmodule.utilities.Constants;
import com.example.authenticationmodule.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class displayCounsellor extends AppCompatActivity {

    private ActivityDisplayCounsellorBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayCounsellorBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_display_counsellor);
        preferenceManager = new PreferenceManager(getApplicationContext());
        getToken();
        setListeners();
    }

    private void setListeners(){
        binding.fabNewChat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), selectCounsellorActivity.class)));
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );

        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnSuccessListener(unused -> showToast("Token updated successfully"))
                .addOnFailureListener(e -> showToast("Unable to update token"));
    }
}