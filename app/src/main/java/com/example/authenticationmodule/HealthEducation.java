package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.authenticationmodule.adapter.HealthEduAdapter;
import com.example.authenticationmodule.databinding.ActivityHealthEducationBinding;
import com.example.authenticationmodule.model.EduData;
import com.example.authenticationmodule.utilities.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HealthEducation extends AppCompatActivity {

    private ActivityHealthEducationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHealthEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        getEduData();
    }

    private void setListeners(){
        binding.BtnBackEdu.setOnClickListener(view -> onBackPressed());
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getEduData(){

        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        List<EduData> eduDataList = new ArrayList<>();

        database.collection(Constants.KEY_COLLECTION_HEALTH_EDUCATION)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    if(task.isSuccessful() && task.getResult() != null){
                        System.out.println("Successful");
                        for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult()){
                            EduData eduData = new EduData();
                            eduData.imageUrl = queryDocumentSnapshot.getString("image");
                            eduData.name = queryDocumentSnapshot.getString("author");
                            eduData.title = queryDocumentSnapshot.getString("title");
                            eduDataList.add(eduData);
                            System.out.println("Author" + eduData.name);
                        }

                        if(eduDataList.size()>0){
                            System.out.println(eduDataList.size());
                            HealthEduAdapter healthEduAdapter = new HealthEduAdapter(eduDataList);
                            binding.EducationRecyclerView.setAdapter(healthEduAdapter);
                            binding.EducationRecyclerView.setVisibility(View.VISIBLE);
                        }else{
                            showErrorMessage();
                        }
                    }
                });
    }

    private void showErrorMessage(){
        binding.textErrorMessage.setText(String.format("%s", "No data available"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading){
        if(isLoading){
            binding.progressBar.setVisibility(View.VISIBLE);
        }else{
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }


}