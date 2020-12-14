package com.example.todofood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;


public class RestoranMenu extends AppCompatActivity {

    private TextView textTime;
    SharedPreferences sPref;
    Gson gson = new Gson();
    /////////////////////////////////////////
    RecyclerView recyclerView;
    RestoranMenuAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public ArrayList<BasketCard> listForBasket = new ArrayList<>();

    ArrayList<String> listName = new ArrayList<>();
    ArrayList<String> listPrice = new ArrayList<>();
    ArrayList<String> listImage = new ArrayList<>();
    ArrayList<String> listDishCount = new ArrayList<>();
    ///////////////////////////////
    //public ScrollView scrollView;
    public View shadow;
    public Button goToBasket;
    public int summa = 0;
    public int colvoZakazov = 0;
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT);
    ////////////////////////////////////////////////////
    public List<BasketCard> getListForBasket() {
        return listForBasket;
    }
/////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_restoran_menu);
        textTime = findViewById(R.id.text123456789);
        final ArrayList<RestoranMenuCard> dishList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lydesiapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ToDoFoodApi toDoFoodApi = retrofit.create(ToDoFoodApi.class);
        Call<List<RestoranMenuPost>> call = toDoFoodApi.getPosts();
        TextView textRestoranName = findViewById(R.id.restoran_name);
        if( getIntent().getExtras().getString("Restoran").equals("CFC")){
             call = toDoFoodApi.getPostsCFC();
             textRestoranName.setText("CFK");
        }
        else if(getIntent().getExtras().getString("Restoran").equals("MC")) {
             call = toDoFoodApi.getPostsMC();
            textRestoranName.setText("MacDak");
        }
     /////////////


        call.enqueue(new Callback<List<RestoranMenuPost>>() {
            @Override
            public void onResponse(Call<List<RestoranMenuPost>> call, Response<List<RestoranMenuPost>> response) {
                if (!response.isSuccessful()) {
                    textTime.setText("code: " + response.code());
                    return;
                }
                List<RestoranMenuPost> posts = response.body();

                for (RestoranMenuPost post : posts) {
/*                    String content = "";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Description: " + post.getDescription() + "\n";
                    content += "Price: " + post.getPrice() + "\n\n";
                    System.out.println(content);
                    textTime.append(content);*/
                    dishList.add(new RestoranMenuCard(post.getTitle(),post.getDescription(),Integer.toString(post.getPrice()) ,post.getImage()));

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<RestoranMenuPost>> call, Throwable t) {
                //textTime.setText(t.getMessage());
            }
        });
        ////////////////////////////////


        /*dishList.add(new RestoranMenuCard("Бургер1", "булка,котлета,сыр...", "199","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        dishList.add(new RestoranMenuCard("Бургер2", "булка,котлета,сыр2...", "200","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        dishList.add(new RestoranMenuCard("Бургер3", "булка,котлета,сыр3...", "250","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
*/
        recyclerView = findViewById(R.id.recycler_view_restoran_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RestoranMenuAdapter(dishList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new RestoranMenuAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                //listForBasket.add( new BasketCard(dishList.get(position).getName(),dishList.get(position).getPrice(),dishList.get(position).getImage()));
                //System.out.println("fffffffffffffff="+listForBasket.get(position).getName());
                boolean flag=false;
                for(int i=0 ; i<listName.size();i++){
                    if(listName.get(i).equals(dishList.get(position).getName())){
                        flag=true;
                        listDishCount.set(i,Integer.toString(Integer.parseInt(listDishCount.get(i))+1));
                        listPrice.set(i,Integer.toString(Integer.parseInt(dishList.get(position).getPrice())*Integer.parseInt(listDishCount.get(i))));
                    }
                }
                if(flag==false) {
                    listName.add(dishList.get(position).getName());
                    listPrice.add(dishList.get(position).getPrice());
                    listImage.add(dishList.get(position).getImage());
                    listDishCount.add("1");
                }

                //////////////////////////////////////////////////////////////////////////////////////////////
                summa = summa + Integer.parseInt(dishList.get(position).getPrice());    //////////////////////////////
                sPref = getSharedPreferences("Data",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt("Summa", summa);
                ed.apply();
                colvoZakazov = colvoZakazov + 1;
                params.addRule(RelativeLayout.ABOVE, R.id.button_go_to_basket);
                //scrollView.setLayoutParams(params);
                recyclerView.setLayoutParams(params);
                goToBasket.setVisibility(View.VISIBLE);
                shadow.setVisibility(View.VISIBLE);
                goToBasket.setText("В корзину " + colvoZakazov + ". На суму " + summa + "р");


            }
        });
        /////////////////////////////
        //scrollView = findViewById(R.id.scrol_view);

        shadow = findViewById(R.id.shadow_line);
        goToBasket = findViewById(R.id.button_go_to_basket);

    }

    public void onClickBackToMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Activity", "RestoranMenuNotBasket");
        startActivity(intent);
        finish();
    }

    public void onClickGoToBasket(View v) {
        MainActivity mainActivity = new MainActivity();
        mainActivity.goToBasketCheck = true;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Activity", "RestoranMenu");
        intent.putExtra("Fragment", "Basket");
        //intent.putExtra("Order","True");
        sPref = getSharedPreferences("Data",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("Order", "True");
        ///////////////////////////////////////////////////
        String jsonName = gson.toJson(listName);
        String jsonPrice = gson.toJson(listPrice);
        String jsonImage = gson.toJson(listImage);
        String jsonDishCount = gson.toJson(listDishCount);

        ///////////////////////////////////////////////////
        ed.putString("NameList",jsonName);
        ed.putString("PriceList",jsonPrice);
        ed.putString("ImageList",jsonImage);
        ed.putString("ListDishCount",jsonDishCount);
        ed.apply();


/*        intent.putStringArrayListExtra("NameList",listName);
        intent.putStringArrayListExtra("PriceList",listPrice);

        intent.putStringArrayListExtra("ImageList",listImage);*/

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

/*    public void onClickAdd(View v) {
        summa = summa + 150;
        colvoZakazov = colvoZakazov + 1;
        params.addRule(RelativeLayout.ABOVE, R.id.button_go_to_basket);
        //scrollView.setLayoutParams(params);
        recyclerView.setLayoutParams(params);
        goToBasket.setVisibility(View.VISIBLE);
        shadow.setVisibility(View.VISIBLE);
        goToBasket.setText("В корзину " + colvoZakazov + ". На суму " + summa + "р");
    }*/

}