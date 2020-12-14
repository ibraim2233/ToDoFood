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
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registration);
         editTextPhone = findViewById(R.id.editTextPhone);
    }
    public void onClickDelete(View v)
    {
        EditText editText = findViewById(R.id.editTextPhone);
        editText.setText("");
    }
    public void onClickClose(View v)
    {
        if(getIntent().getExtras().getString("Activity").equals("AboutMe")) {
            Intent intent = new Intent(this, AboutMe.class);
            intent.putExtra("Activity", "Registration");
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this, AboutMe.class);
            intent.putExtra("Activity", "Registration");
            intent.putExtra("Fragment", "Personal");
            startActivity(intent);
            finish();
        }
    }
    public void onClickPhoneCofirm(View v)
    {
        if( editTextPhone.length() != 0  &&!(editTextPhone.getText().length()<11)) {
            Intent intent = new Intent(this, RegistrationContinueActivity.class);
            SharedPreferences sPref;
            sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("Phone", editTextPhone.getText().toString());
            ed.apply();
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Введен неверный номер!", Toast.LENGTH_SHORT).show();
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