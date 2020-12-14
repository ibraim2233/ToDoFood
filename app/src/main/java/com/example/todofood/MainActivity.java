package com.example.todofood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity  {
    public CardView[] Card= new CardView[3];
    public BottomNavigationView bottomNav;
    MenuItem menuItem;
    SharedPreferences sPref;
    String activityStr;
    String fragmentStr;
    String savedText;
    String savedOrder;
    RecyclerView recyclerView;
    RestoranMenuAdapter adapter;
    int counter;
    boolean savedFragmentPersonal=false;
    boolean savedFragmentBasket=false;
    public boolean goToBasketCheck=false;
    public boolean savedFragmentBasketFinal=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bottomNav= findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Fragment fragment = new MainFragment();
        activityStr=getIntent().getExtras().getString("Activity");
        fragmentStr = getIntent().getExtras().getString("Fragment");
        sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);

        menuItem =bottomNav.getMenu().findItem(R.id.nav_basket);
        //System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ="+goToBasketCheck);
        //changePersonal();
        savedText = sPref.getString("SAVE_STRING","false");
        savedOrder = sPref.getString("Order","False");
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH="+sPref.getString("SAVE_STRING","false"));
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW="+savedText);



/*        if(savedText==true)
            changePersonal();*/
        if (savedInstanceState == null) {

            if(activityStr!= null && activityStr.equals("Registration"))
            {
                if( fragmentStr.equals("Personal"))
                {
                    savedFragmentPersonal=true;
                    fragment=new PersonalFragment();
                    bottomNav.getMenu().findItem(R.id.nav_personal).setChecked(true);
                }
            }
            if(activityStr!= null && (activityStr.equals("RestoranMenu") ||activityStr.equals("Payment") ))
            {
                if( fragmentStr.equals("Basket"))
                {
                    savedFragmentBasketFinal=true;
                    savedFragmentBasket = true;
                    fragment=new BasketFragment();
                    bottomNav.getMenu().findItem(R.id.nav_basket).setChecked(true);
                }else if(fragmentStr.equals("BasketEmpty"))
                {
                    fragment=new BasketFragment();
                    bottomNav.getMenu().findItem(R.id.nav_basket).setChecked(true);
                }
            }else if(activityStr!= null && activityStr.equals("EnterName")){

                savedFragmentPersonal=true;
                fragment=new PersonalFragment();
                bottomNav.getMenu().findItem(R.id.nav_personal).setChecked(true);

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();


        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if(savedText.equals("True") && savedFragmentPersonal==true)
        {
            savedFragmentPersonal=false;
            changePersonal();
        }
        if(savedOrder!= null && savedOrder.equals("True") && savedFragmentBasket==true)
        {
            savedFragmentBasket=false;
            TextView BasketEmpty=findViewById(R.id.basket_text_view);
            BasketEmpty.setVisibility(View.INVISIBLE);
            Button goToMain = findViewById(R.id.button_back_to_main);
            goToMain.setVisibility(View.INVISIBLE);
            TextView basketHeader = findViewById(R.id.basket);
            basketHeader.setVisibility(View.VISIBLE);
            Button doOrder = findViewById(R.id.button_do_order);
            doOrder.setVisibility(View.VISIBLE);
            View shadowLineBasket=findViewById(R.id.shadow_line_basket_main);
            shadowLineBasket.setVisibility(View.VISIBLE);
            View shadowLineBasket2=findViewById(R.id.shadow_line_basket2);
            shadowLineBasket2.setVisibility(View.VISIBLE);
            RecyclerView recyclerView = findViewById(R.id.recycler_view_basket);
            recyclerView.setVisibility(View.VISIBLE);
        }
        // Возобновите все приостановленные обновления UI,
        // потоки или процессы, которые были "заморожены",
        // когда данный объект был неактивным.
    }

    public void changePersonal(){
        TextView textViewPersonalData = findViewById(R.id.go_to_regestration_or_data);
        textViewPersonalData.setText("Мои данные");
        RelativeLayout relativeLayout = findViewById(R.id.my_orders_view);
        relativeLayout.setVisibility(View.VISIBLE);
        View shadowPersonal = findViewById(R.id.shadow_persanal_two);
        shadowPersonal.setVisibility(View.VISIBLE);
        Button buttonRegestrationOrPersonalData=findViewById(R.id.fill_data_or_go_to_data_button);
        buttonRegestrationOrPersonalData.setClickable(false);

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
                            savedFragmentBasket=true;
                            selectedFragment=new BasketFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment=new SearchFragment();
                            break;
                        case R.id.nav_personal:
                            savedFragmentPersonal=true;
                            selectedFragment=new PersonalFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    onResume();

                    return true;

                }

            };


    public void onClickMinus(View v){


    }
    public void onClickPlus(View v){

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
////////////////////////////////////////////////////////////////////////////////////////

    public void onClickGoToRestoranCFC(View v) {
        Intent intent = new Intent(this,RestoranMenu.class);
        intent.putExtra("Restoran","CFC");
        startActivity(intent);
        finish();
    }
    public void onClickGoToRestoranMC(View v) {
        Intent intent = new Intent(this,RestoranMenu.class);
        intent.putExtra("Restoran","MC");
        startActivity(intent);
        finish();
    }

    public void onClickRegestration(View v)
    {
        if(savedText.equals("True"))
            OpenPersonalData();
        else {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void onClickOpenOrders(View v){
        Intent intent = new Intent(this, MyOrders.class);
        startActivity(intent);
        finish();
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
    public void onClickDoOrder(View v){
/*        savedFragmentBasketFinal=false;
        TextView BasketEmpty=findViewById(R.id.basket_text_view);
        BasketEmpty.setVisibility(View.VISIBLE);
        Button goToMain = findViewById(R.id.button_back_to_main);
        goToMain.setVisibility(View.VISIBLE);
        TextView basketHeader = findViewById(R.id.basket);
        basketHeader.setVisibility(View.INVISIBLE);
        Button doOrder = findViewById(R.id.button_do_order);
        doOrder.setVisibility(View.INVISIBLE);
        View shadowLineBasket=findViewById(R.id.shadow_line_basket_main);
        shadowLineBasket.setVisibility(View.INVISIBLE);
        View shadowLineBasket2=findViewById(R.id.shadow_line_basket2);
        shadowLineBasket2.setVisibility(View.INVISIBLE);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_basket);
        recyclerView.setVisibility(View.INVISIBLE);*/
        //getIntent().putExtra("Order","False");

        Intent intent = new Intent(this,Payment.class);
        startActivity(intent);
        finish();
    }
    public void OpenPersonalData(){
        Intent intent = new Intent(this,AboutMe.class);
        startActivity(intent);
        finish();
    }
}