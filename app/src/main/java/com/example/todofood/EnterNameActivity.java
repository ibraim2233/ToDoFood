package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EnterNameActivity extends AppCompatActivity {
    SharedPreferences sPref;
    EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_enter_name);
        editTextName = findViewById(R.id.editTextName);
        //Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }
    public void onClickBackToCodeConfirm(View v){
        Intent intent = new Intent(this,RegistrationContinueActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSaveAcc(View v){
        if(editTextName.length()>0) {
            sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("Name", editTextName.getText().toString());
            ed.putString("SAVE_STRING", "True");
            ed.apply();
            System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF=" + sPref.getString("SAVE_STRING", "false"));


            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Activity", "EnterName");
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Введите имя!", Toast.LENGTH_SHORT).show();
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