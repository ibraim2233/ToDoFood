package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationContinueActivity extends AppCompatActivity {
    TextView textAboutCode;
    EditText editTextCode;
    SharedPreferences sPref;
    SharedPreferences.Editor ed;
    String codeConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registration_continue);
        editTextCode = findViewById(R.id.editTextCode);
        textAboutCode = findViewById(R.id.text_about_code);
        sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        ed= sPref.edit();
        codeConfirm = "0000";
        textAboutCode.setText("На номер +"+sPref.getString("Phone","False")  + " отправлено СМС с кодом.Телефон важен, что бы курьер смог связаться в вами");
    }
    public void onClickBackToPhoneReg(View v)
    {
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        finish();
    }
    public void onClickGoToEnterName(View v){
        if(editTextCode.getText().toString().equals(codeConfirm))
        {
            Intent intent = new Intent(this,EnterNameActivity.class);

        ed.putString("Code", editTextCode.getText().toString());
        ed.apply();
        startActivity(intent);
        finish();
        }
        else {
            Toast.makeText(this, "Введен неверный код!", Toast.LENGTH_SHORT).show();
        }
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Нажмите назад снова чтобы закрыть",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }
}