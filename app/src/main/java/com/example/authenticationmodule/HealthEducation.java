package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.authenticationmodule.adapter.HealthEduAdapter;
import com.example.authenticationmodule.databinding.ActivityHealthEducationBinding;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HealthEducation extends AppCompatActivity {

    private ActivityHealthEducationBinding binding;
    MaterialButton BtnBackEducation;
    RecyclerView recyclerView;
    HealthEduAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BtnBackEducation = findViewById(R.id.BtnBackEdu);
        recyclerView = findViewById(R.id.EducationRecyclerView);

        BtnBackEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthEducation.this, HealthHome.class));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EduData[] eduData = new EduData[]{
                new EduData("test1", "test1", "test1", R.drawable.ic_launcher_background),
                new EduData("test2", "test2", "test2", R.drawable.ic_launcher_background),
                new EduData("test3", "test3", "test3", R.drawable.ic_launcher_background)

        };

        adapter = new HealthEduAdapter(eduData,this );
        recyclerView.setAdapter(adapter);
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

//    private void getInfo(){
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        database.collection("health_education")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if(task.isSuccessful()&&task.getResult()!=null){
//
//                    }
//                });
//    }
}