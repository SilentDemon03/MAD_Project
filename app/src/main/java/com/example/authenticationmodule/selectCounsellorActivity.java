package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.authenticationmodule.adapter.CounsellorsAdapter;
import com.example.authenticationmodule.databinding.ActivitySelectCounsellorBinding;
import com.example.authenticationmodule.listeners.CounsellorListener;
import com.example.authenticationmodule.model.Counsellor;
import com.example.authenticationmodule.utilities.Constants;
import com.example.authenticationmodule.utilities.PreferenceManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
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
        getCounsellors();
    }

    private void setListeners(){
        binding.imageBack.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), displayCounsellor.class)));
    }

    private void getCounsellors() {

        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                //.whereEqualTo("role", "counsellor")
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Counsellor> counsellors = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                                continue;
                            }

                            Counsellor counsellor = new Counsellor();
                            counsellor.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            counsellor.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            counsellor.image = queryDocumentSnapshot.getString(Constants.KEY_IMAGE);
                            counsellor.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            counsellor.id = queryDocumentSnapshot.getId();
                            counsellors.add(counsellor);
                        }
                        if (counsellors.size() > 0) {
                            CounsellorsAdapter counsellorsAdapter = new CounsellorsAdapter(counsellors, this);
                            binding.counsellorsRecyclerView.setAdapter(counsellorsAdapter);
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