package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.authenticationmodule.adapter.RecentConversationsAdapter;
import com.example.authenticationmodule.databinding.ActivityDisplayCounsellorBinding;
import com.example.authenticationmodule.model.ChatMessage;
import com.example.authenticationmodule.utilities.Constants;
import com.example.authenticationmodule.utilities.PreferenceManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class displayCounsellor extends AppCompatActivity {

    private ActivityDisplayCounsellorBinding binding;
    private PreferenceManager preferenceManager;
    private List<ChatMessage> conversations;
    private RecentConversationsAdapter conversationsAdapter;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityDisplayCounsellorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        init();
        getToken();
        setListeners();
    }

    private void init(){
        conversations = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(conversations);
        binding.conversationRecyclerView.setAdapter(conversationsAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void setListeners(){
        binding.fabNewChat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), selectCounsellorActivity.class)));
        binding.imageBack.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CounsellingService.class)));
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        String userEmail = preferenceManager.getString(Constants.KEY_EMAIL);

        if (userEmail != null) {
            FirebaseFirestore database = FirebaseFirestore.getInstance();

            database.collection("users")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            String userId = queryDocumentSnapshots.getDocuments().get(0).getId();
                            DocumentReference documentReference =
                                    database.collection(Constants.KEY_COLLECTION_USERS).document(userId);
                            preferenceManager.putString(Constants.KEY_USER_ID, userId);

                            documentReference.update(Constants.KEY_FCM_TOKEN, token)
                                    .addOnSuccessListener(unused -> showToast("Token updated successfully"))
                                    .addOnFailureListener(e -> showToast("Unable to update token"));
                        } else {
                            showToast("User not found in Firestore");
                        }
                    })
                    .addOnFailureListener(e -> {
                        showToast("Error retrieving user: " + e.getMessage());
                    });
        } else {
            showToast("User email not found in SharedPreferences");
        }
    }

}