package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class MyOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_orders);
    }
    public void onClickCloseOrders(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("Activity","Registration");//////////////////////nado proveritb
        intent.putExtra("Fragment","Personal");
        startActivity(intent);
        finish();
    }
    public void onClickOpenSomeOrder(View view){
        Intent intent = new Intent(this,SomeOrder.class);
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