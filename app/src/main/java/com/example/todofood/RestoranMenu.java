package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class RestoranMenu extends AppCompatActivity {

    public ScrollView scrollView;
    public View shadow;
    public Button goToBasket;
    public int summa=0;
    public int colvoZakazov=0;
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_menu);
         scrollView = findViewById(R.id.scrol_view);
         shadow = findViewById(R.id.shadow_line);
         goToBasket = findViewById(R.id.button_go_to_basket);

    }
    public void onClickBackToMain(View v) {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();}
        public void onClickGoToBasket(View v){
            MainActivity mainActivity = new MainActivity();
            mainActivity.goToBasketCheck=true;
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

            finish();
        }
        public void onClickAdd(View v){
            summa=summa+150;
            colvoZakazov=colvoZakazov+1;
            params.addRule(RelativeLayout.ABOVE,R.id.button_go_to_basket);
            scrollView.setLayoutParams(params);
            goToBasket.setVisibility(View.VISIBLE);
            shadow.setVisibility(View.VISIBLE);
            goToBasket.setText("В корзину "+colvoZakazov+". На суму "+summa+"р" );
        }

}