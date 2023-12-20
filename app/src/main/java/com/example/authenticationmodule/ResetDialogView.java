package com.example.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResetDialogView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_dialog_view);
        Button BtnConfirmEmail = findViewById(R.id.BtnConfirmEmail);
        TextView ETResetStatement = findViewById(R.id.TVResetStatement);
        EditText ETEmailBox = findViewById(R.id.ETEmailBox);
        Button BtnCancel = findViewById(R.id.BtnCancel);


    }
}