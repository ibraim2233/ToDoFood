package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class SomeOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_some_order);

        TextView textView = findViewById(R.id.divided_summa);
        EditText editText = findViewById(R.id.editTextPerson);
        textView.setText(" =500");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("ssssssssssssssssssssssssssss="+s.length());
                if(!(s.equals(null)) &&!(s.equals("")) && s.length()>0  ) {

                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                        textView.setText(" =" +decimalFormat.format(500 / (Double.parseDouble(s.toString()))));
                }
                else
                {

                    textView.setText(" =500");
                }
            }
        });
    }
    public void onClickBackToMyOrders(View view){
        Intent intent = new Intent(this,MyOrders.class);
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