package com.example.authenticationmodule;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authenticationmodule.utilities.Constants;
import com.example.authenticationmodule.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private PreferenceManager preferenceManager;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String userId = currentUser.getUid();
            //preferenceManager.putString(Constants.KEY_USER_ID, userId);
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView ETLoginEmail = findViewById(R.id.ETLoginEmail);
        TextView ETLoginPassword = findViewById(R.id.ETLoginPassword);
        Button BtnSignIn = findViewById(R.id.BtnSignIn);
        TextView TVRegisterNow = findViewById(R.id.TVRegisterNow);
        mAuth = FirebaseAuth.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());
        TextView TVForgotPassword = findViewById(R.id.TVForgotPassword);

        TVRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                finish();
            }
        });

        TVForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (getApplicationContext(),ResetPassword.class);
                startActivity(intent);
                finish();
            }
        });
        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                email = String.valueOf(ETLoginEmail.getText());
                password = String.valueOf(ETLoginPassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this,"Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    preferenceManager.putString(Constants.KEY_EMAIL, email);
                                    database.collection(Constants.KEY_COLLECTION_USERS)
                                            .whereEqualTo(Constants.KEY_EMAIL,email)
                                            .get()
                                            .addOnCompleteListener(task1 -> {
                                                if(task1.isSuccessful() && task1.getResult().getDocuments().size()>0){
                                                    DocumentSnapshot documentSnapshot = task1.getResult().getDocuments().get(0);
                                                    preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                                                    preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                                                    preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                                                    preferenceManager.putString(Constants.KEY_ROLE, documentSnapshot.getString(Constants.KEY_ROLE));
                                                }
                                            });

                                    Toast.makeText(getApplicationContext(), "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),Home.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}