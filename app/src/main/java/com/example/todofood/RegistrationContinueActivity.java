package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegistrationContinueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_continue);
    }
    public void onClickBackToPhoneReg(View v)
    {
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        finish();
    }
    public void onClickGoToEnterName(View v){
        Intent intent = new Intent(this,EnterNameActivity.class);
        startActivity(intent);
        finish();
    }
}