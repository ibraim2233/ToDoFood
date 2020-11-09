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
    MainActivity mainActivity=new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        mainActivity.sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        //Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }
    public void onClickBackToCodeConfirm(View v){
        Intent intent = new Intent(this,RegistrationContinueActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSaveAcc(View v){
     /*   mainActivity.sPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mainActivity.sPref.edit();
        ed.putBoolean("SAVE_BOOLEAN", true);
        ed.apply();
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF="+mainActivity.sPref.getBoolean("SAVE_BOOLEAN",false));
*/


/*        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);*/
        finish();

    }


}