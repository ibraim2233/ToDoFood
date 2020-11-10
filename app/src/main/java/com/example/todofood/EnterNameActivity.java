package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EnterNameActivity extends AppCompatActivity {
    SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        //Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }
    public void onClickBackToCodeConfirm(View v){
        Intent intent = new Intent(this,RegistrationContinueActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSaveAcc(View v){
        sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("SAVE_STRING", "True");
        ed.apply();
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF="+sPref.getString("SAVE_STRING","false"));


        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Activity","EnterName");
        startActivity(intent);
        finish();

    }


}