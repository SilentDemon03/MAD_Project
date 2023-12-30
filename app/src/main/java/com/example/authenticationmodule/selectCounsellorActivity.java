package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.authenticationmodule.adapter.CounsellorsAdapter;
import com.example.authenticationmodule.databinding.ActivitySelectCounsellorBinding;
import com.example.authenticationmodule.listeners.CounsellorListener;
import com.example.authenticationmodule.model.Counsellor;
import com.example.authenticationmodule.utilities.Constants;
import com.example.authenticationmodule.utilities.PreferenceManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class selectCounsellorActivity extends AppCompatActivity implements CounsellorListener {

    private ActivitySelectCounsellorBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCounsellorBinding.inflate(getLayoutInflater());
        FirebaseApp.initializeApp(this);
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), recentConversation.class)));
    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        // Retrieve user role from PreferenceManager
        String userRole = preferenceManager.getString(Constants.KEY_ROLE);

        // Prepare your query based on the user role
        Query query;
        if ("user".equals(userRole)) {
            // If the role is 'user', fetch 'counsellor' list
            query = database.collection(Constants.KEY_COLLECTION_USERS)
                    .whereEqualTo("role", "counsellor");
        } else {
            // If the role is 'counsellor', fetch 'user' list
            query = database.collection(Constants.KEY_COLLECTION_USERS)
                    .whereNotEqualTo("role", "counsellor");
        }

        // Execute the query
        query.get().addOnCompleteListener(task -> {
            loading(false);
            String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
            if (task.isSuccessful() && task.getResult() != null) {
                List<Counsellor> users = new ArrayList<>();
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                    if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                        continue;
                    }
                    Counsellor user = new Counsellor();
                    user.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                    user.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                    user.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                    user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                    user.id = queryDocumentSnapshot.getId();
                    users.add(user);
                }
                if (users.size() > 0) {
                    CounsellorsAdapter usersAdapter = new CounsellorsAdapter(users, this);
                    binding.counsellorsRecyclerView.setAdapter(usersAdapter);
                    binding.counsellorsRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    showErrorMessage();
                }
            } else {
                showErrorMessage();
            }
        });
    }




    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "No user available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(Counsellor counsellor) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, counsellor);
        startActivity(intent);
        finish();
    }
}