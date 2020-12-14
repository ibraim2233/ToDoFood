package com.example.todofood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    SharedPreferences sPref;
    Gson gson = new Gson();
    /////////////////////////////////////////
    RecyclerView recyclerView;
    RestoranMenuAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    boolean burgerChecked=false;
    boolean drinkChecked = false;

    ArrayList<String> listName = new ArrayList<>();
    ArrayList<String> listPrice = new ArrayList<>();
    ArrayList<String> listImage = new ArrayList<>();
    ArrayList<String> listDishCount = new ArrayList<>();

    ArrayList<String> listTag = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_search, container, false);

        ///////////////////////////

        ////////////////////
        final ArrayList<RestoranMenuCard> dishList = new ArrayList<>();
        final ArrayList<RestoranMenuCard> dishListTemp = new ArrayList<>();
        final ArrayList<RestoranMenuCard> dishListFinal = new  ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lydesiapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ToDoFoodApi toDoFoodApi = retrofit.create(ToDoFoodApi.class);
        Call<List<RestoranMenuPost>> call = toDoFoodApi.getPosts();
        call = toDoFoodApi.getPosts();

        call.enqueue(new Callback<List<RestoranMenuPost>>() {
            @Override
            public void onResponse(Call<List<RestoranMenuPost>> call, Response<List<RestoranMenuPost>> response) {
                if (!response.isSuccessful()) {
                    //textTime.setText("code: " + response.code());
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
                    dishListFinal.add(new RestoranMenuCard(post.getTitle(),post.getDescription(),Integer.toString(post.getPrice()) ,post.getImage()));

                    listTag.add(post.getTag());

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
        recyclerView = view.findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());////проверить
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
/*                summa = summa + Integer.parseInt(dishList.get(position).getPrice());    //////////////////////////////
                sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt("Summa", summa);
                ed.apply();
                */
            }
        });
        /////////////////////////////
        //scrollView = findViewById(R.id.scrol_view);
        EditText editText = view.findViewById(R.id.search_dish);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(null)) || !(s.toString().equals("")) || !(s.toString().equals(" "))) {
                    String text = s.toString();
                    System.out.println("1111111111111111111111111111111");
                    dishList.clear();
                    for (int i = 0; i < dishListFinal.size(); i++) {
                        System.out.println("2222222222222222222222");
                        if (dishListFinal.get(i).getName().toLowerCase().contains(text.toLowerCase())) {
                            {
                                System.out.println("33333333333333333333333");
                                dishList.add(dishListFinal.get(i));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }//при изменении текста
                else
                {
                    System.out.println("4444444444444444444444");
                    dishList.clear();
                    for (int i = 0; i < dishListFinal.size(); i++) {
                        dishList.add(dishListFinal.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Button ButtonFilterBurger = (Button) view.findViewById(R.id.filter_burger);
        Button ButtonFilterDrink = (Button) view.findViewById(R.id.filter_drink);
        ButtonFilterBurger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(burgerChecked==false) {
                    burgerChecked = true;
                    drinkChecked=false;
                    ButtonFilterBurger.setBackgroundResource(R.drawable.filter_button_presed);
                    ButtonFilterDrink.setBackgroundResource(R.drawable.filter_button);
                    ButtonFilterBurger.setAllCaps(false);
                    for (int i = 0; i < dishListFinal.size(); i++) {
                        if (listTag.get(i).equals("burger")) {
                            dishListTemp.add(dishListFinal.get(i));
                        }
                    }
                    dishList.clear();
                    for (int i = 0; i < dishListTemp.size(); i++) {
                        dishList.add(dishListTemp.get(i));
                    }
                    dishListTemp.clear();
                    adapter.notifyDataSetChanged();
                }
                else{
                    burgerChecked = false;
                    ButtonFilterBurger.setBackgroundResource(R.drawable.filter_button);
                    ButtonFilterBurger.setAllCaps(false);
                    dishList.clear();
                    for (int i = 0; i < dishListFinal.size(); i++) {
                        dishList.add(dishListFinal.get(i));
                    }
                    dishListTemp.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        ButtonFilterDrink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(drinkChecked==false) {
                    drinkChecked=true;
                    burgerChecked=false;
                    ButtonFilterDrink.setBackgroundResource(R.drawable.filter_button_presed);
                    ButtonFilterBurger.setBackgroundResource(R.drawable.filter_button);
                    ButtonFilterBurger.setAllCaps(false);
                    for (int i = 0; i < dishListFinal.size(); i++) {
                        if (listTag.get(i).equals("drink")) {
                            dishListTemp.add(dishListFinal.get(i));
                        }
                    }
                    dishList.clear();
                    for (int i = 0; i < dishListTemp.size(); i++) {
                        dishList.add(dishListTemp.get(i));
                    }
                    dishListTemp.clear();
                    adapter.notifyDataSetChanged();
                }
                else{
                    drinkChecked=false;
                    ButtonFilterDrink.setBackgroundResource(R.drawable.filter_button);
                    ButtonFilterBurger.setAllCaps(false);
                    dishList.clear();
                    for (int i = 0; i < dishListFinal.size(); i++) {
                        dishList.add(dishListFinal.get(i));
                    }
                    dishListTemp.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return view;



    }


}