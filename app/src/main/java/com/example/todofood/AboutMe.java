package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AboutMe extends AppCompatActivity {
    TextView name;
    TextView phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_about_me);
        name = findViewById(R.id.my_name);
        phone = findViewById(R.id.my_phone);
        SharedPreferences sPref;
        sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        name.setText("Имя: "+sPref.getString("Name","False"));
        phone.setText("Телефон: +"+sPref.getString("Phone","False"));

    }
    public void onClickChangeMyInfo(View view){
        Intent intent = new Intent(this,RegistrationActivity.class);
        intent.putExtra("Activity","AboutMe");//////////////////////nado proveritb
        startActivity(intent);
        finish();
    }
    public void onClickCloseInfo(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Activity","Registration");//////////////////////nado proveritb
        intent.putExtra("Fragment","Personal");
        startActivity(intent);
        finish();
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