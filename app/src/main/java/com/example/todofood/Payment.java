package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Payment extends AppCompatActivity {
    SharedPreferences sPref;
    TextView currentDateTime;
    boolean oplataFlag=false;
    Button buttonCard;
    Button buttonNal;
    Button buttonPay;
    CardView cardView;
    Calendar dateAndTime=Calendar.getInstance();

    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_payment);

        TextView textViewStoimost= findViewById(R.id.price_pay);
        TextView textViewItogo= findViewById(R.id.itogo);
        sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        textViewStoimost.setText(""+sPref.getInt("Summa",0));
        textViewItogo.setText(""+sPref.getInt("Summa",0));
        buttonCard=findViewById(R.id.pay_card);
        buttonNal=findViewById(R.id.pay_nal);
        buttonPay= findViewById(R.id.button_pay);
        buttonCard.setBackgroundResource(R.drawable.filter_button_presed);
        cardView = findViewById(R.id.container_card_payment);
        buttonNal.setAllCaps(false);
        buttonCard.setAllCaps(false);

        currentDateTime=(TextView)findViewById(R.id.textDataPay);
        setInitialDateTime();

    }
    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(Payment.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(Payment.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        /*| DateUtils.FORMAT_SHOW_TIME*/));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    public void onClickBackToMenu(View view){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Activity", "Payment");
        intent.putExtra("Fragment", "Basket");
        startActivity(intent);
        finish();
    }
    public void onClickPay(View view){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Activity", "Payment");
        intent.putExtra("Fragment", "BasketEmpty");
        sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Order", "False");
        ed.apply();
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



    public void onClickCard(View view){
            oplataFlag = true;  //оплата картой
            buttonCard.setBackgroundResource(R.drawable.filter_button_presed);
            buttonNal.setBackgroundResource(R.drawable.filter_button);
            cardView.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.BELOW, R.id.container_card_payment);
        params.setMargins(40,0,40,40);
            buttonPay.setLayoutParams(params);

    }

    public void onClickNal(View view){
        oplataFlag = false;  //оплата наличкой
        buttonNal.setBackgroundResource(R.drawable.filter_button_presed);
        buttonCard.setBackgroundResource(R.drawable.filter_button);
        cardView.setVisibility(View.INVISIBLE);
        params.addRule(RelativeLayout.BELOW, R.id.liner_container);
        params.setMargins(40,0,40,40);
        buttonPay.setLayoutParams(params);
    }
}