package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.authenticationmodule.adapter.HealthEduAdapter;
import com.google.android.material.button.MaterialButton;

public class HealthEducation extends AppCompatActivity {
    MaterialButton BtnBackEducation;
    RecyclerView recyclerView;
    HealthEduAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_education);

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
}