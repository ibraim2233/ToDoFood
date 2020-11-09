package com.example.todofood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity  {
    public CardView[] Card= new CardView[3];
    public BottomNavigationView bottomNav;
    MenuItem menuItem;
    SharedPreferences sPref;
    public boolean goToBasketCheck=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bottomNav= findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);

        menuItem =bottomNav.getMenu().findItem(R.id.nav_basket);
        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ="+goToBasketCheck);
        //changePersonal();
        //boolean savedText = sPref.getBoolean("SAVE_BOOLEAN", false);
        //System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH="+sPref.getBoolean("SAVE_BOOLEAN",false));
/*
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW="+savedText.savePersonalData);*/



/*        if(savedText==true)
            changePersonal();*/
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MainFragment()).commit();
/*            for(int i=0;i<3;i++){
                Card[i]=findViewById(getResources().getIdentifier("card"+i+1,"id",getPackageName()));
                Card[i].setOnClickListener(this);
            }*/
        }

    }
    public void changePersonal(){
        TextView textViewPersonalData = findViewById(R.id.go_to_regestration_or_data);
        textViewPersonalData.setText("Мои данные");
        RelativeLayout relativeLayout = findViewById(R.id.my_orders_view);
        relativeLayout.setVisibility(View.VISIBLE);
        View shadowPersonal = findViewById(R.id.shadow_persanal_two);
        shadowPersonal.setVisibility(View.VISIBLE);
        Button buttonRegestrationOrPersonalData=findViewById(R.id.fill_data_or_go_to_data_button);

    }



    ////////////////////////////////////////////////
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS="+item);
                    switch (item.getItemId())
                    {
                        case R.id.nav_main:
                            selectedFragment=new MainFragment();
                            break;
                        case R.id.nav_basket:
                            selectedFragment=new BasketFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment=new SearchFragment();
                            break;
                        case R.id.nav_personal:
                            selectedFragment=new PersonalFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }

            };

    public void onBackPressed() { }

    public void onClickGoToRestoran(View v) {
        Intent intent = new Intent(this,RestoranMenu.class);
        startActivity(intent);
        //finish();
    }

    public void onClickRegestration(View v)
    {
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        //finish();
    }
    public void onClickBackToFoodMenu(View v){
        bottomNav.getMenu().getItem(3).setChecked(false);
        bottomNav.getMenu().getItem(2).setChecked(false);
        bottomNav.getMenu().getItem(1).setChecked(false);
        bottomNav.getMenu().getItem(0).setChecked(true);

/*        Menu menu = bottomNav.getMenu();
        menu.findItem(R.id.nav_basket).setIcon(R.drawable.ic_basket);
        menu.findItem(R.id.nav_basket).setTitle("Корзина");*/
        //View view = bottomNav.findViewById(R.id.nav_basket);
        //navListener.onNavigationItemSelected(menuItem);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MainFragment()).commit();
    }
    public void onClickOpenPersonalData(View v){
        //
    }
}