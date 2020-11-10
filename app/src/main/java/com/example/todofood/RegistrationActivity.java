package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
    public void onClickDelete(View v)
    {
        EditText editText = findViewById(R.id.editTextPhone);
        editText.setText("");
    }
    public void onClickClose(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Activity","Registration");
        intent.putExtra("Fragment","Personal");
        startActivity(intent);
        finish();
    }
    public void onClickPhoneCofirm(View v)
    {
        Intent intent = new Intent(this,RegistrationContinueActivity.class);
        startActivity(intent);
        finish();
    }
}