package com.example.authenticationmodule;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        EditText ETName = findViewById(R.id.ETName);
        EditText ETHpNumber = findViewById(R.id.ETHpNumber);
        EditText ETEmail = findViewById(R.id.ETEmail);
        EditText ETPassword = findViewById(R.id.ETPassword);
        EditText EtConfirmPassword = findViewById(R.id.ETConfirmPassword);
        Button BtnRegister = findViewById(R.id.BtnRegister);


        TextView TVLogin = findViewById(R.id.TVLogin);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        TVLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, email, password, phone;
                username = String.valueOf(ETName.getText());
                email = String.valueOf(ETEmail.getText());
                password = String.valueOf(ETPassword.getText());
                phone = String.valueOf(ETHpNumber.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User registration successful
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Store additional user data in Firestore
                                        Map<String, Object> userData = new HashMap<>();
                                        userData.put("username", username);
                                        userData.put("email", email);
                                        userData.put("password", password);
                                        userData.put("phone number",phone);
                                        userData.put("role", "user");

                                        DocumentReference userRef = db.collection("users").document(user.getUid());
                                        userRef.set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> firestoreTask) {
                                                if (firestoreTask.isSuccessful()) {
                                                    // Firestore data stored successfully
                                                    Toast.makeText(Register.this, "Account created.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    // Firestore data storage failed
                                                    Toast.makeText(Register.this, "Firestore data storage failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    // User registration failed
                                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}