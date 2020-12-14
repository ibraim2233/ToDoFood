package com.example.todofood;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class BasketFragment extends Fragment {
    SharedPreferences sPref;
    /////////////////////////////////////////
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button buttonDoOrder;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> priceList = new ArrayList<>();
    ArrayList<String> imageList = new ArrayList<>();
    ArrayList<String> listDishCount = new ArrayList<>();

    ///////////////////////////////
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_basket, container, false);
/*        nameList=getActivity().getIntent().getExtras().getStringArrayList("NameList");
        priceList=getActivity().getIntent().getExtras().getStringArrayList("PriceList");
        imageList=getActivity().getIntent().getExtras().getStringArrayList("ImageList");*/

        sPref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        buttonDoOrder = view.findViewById(R.id.button_do_order);
        buttonDoOrder.setText("Оформить заказ на "+sPref.getInt("Summa",0)+"р");             //менять когда + или - и в файл тоже записывать
        Gson gson = new Gson();
    String jsonName = sPref.getString("NameList", "False");
    String jsonPrice = sPref.getString("PriceList", "False");
    String jsonImage = sPref.getString("ImageList", "False");
    String jsonDishCount = sPref.getString("ListDishCount", "False");

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        if(!(sPref.getString("NameList", "False").equals("False")))
        {nameList = gson.fromJson(jsonName, type);
        priceList = gson.fromJson(jsonPrice, type);
        imageList = gson.fromJson(jsonImage, type);
        listDishCount = gson.fromJson(jsonDishCount,type);}
        //System.out.println("KKKKKKKKKKKKKKK= "+getActivity().getIntent().getExtras().getString("Fragment"));
        //sPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        ////////////////////////////////
        final ArrayList<BasketCard> dishList = new ArrayList<>();
RestoranMenu restoranMenu = new RestoranMenu();
//        System.out.println("ssssssssssssssssssssssssssssssssss======"+restoranMenu.getListForBasket().get(0).getName());
        /*dishList.add(new BasketCard("Бургер1",  "199","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        dishList.add(new BasketCard("Бургер2",  "200","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        dishList.add(new BasketCard("Бургер3",  "250","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        dishList.add(new BasketCard("Бургер2",  "200","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        dishList.add(new BasketCard("Бургер2",  "200","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        dishList.add(new BasketCard("Бургер2",  "200","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        dishList.add(new BasketCard("Бургер2",  "200","https://kfc-burgers.ru/wp-content/uploads/2018/11/SHefburger.png"));
        */
        if(nameList != null && !nameList.isEmpty()) {
            for (int i = 0; i < nameList.size(); i++) {
                dishList.add(new BasketCard(nameList.get(i), priceList.get(i), imageList.get(i),listDishCount.get(i)));
            }

        }
        //recyclerView = findViewById(R.id.recycler_view_restoran_menu);
        recyclerView = view.findViewById(R.id.recycler_view_basket);
        //recyclerView = new RecyclerView(getContext());

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new BasketAdapter(dishList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        /////////////////////////////
        return view;
    }

}