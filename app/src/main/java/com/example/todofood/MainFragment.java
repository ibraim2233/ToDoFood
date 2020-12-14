package com.example.todofood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainFragment extends Fragment implements View.OnClickListener {
    SharedPreferences sPref;
    boolean flag = false;
TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        textView = v.findViewById(R.id.text_with_adress);
        sPref = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        for (int i=0;i<sPref.getString("Adress","Не указан").length();i++)
        {
            if(sPref.getString("Adress","Не указан").charAt(i) != ' ' )
                flag=true;
        }
        if(sPref.getString("Adress","Не указан").equals("") || sPref.getString("Adress","Не указан").equals(" ")  || sPref.getString("Adress","Не указан").equals(null) || flag==false)
            textView.setText("Адресс не указан");
        else
            textView.setText(sPref.getString("Adress","Не указан"));
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

        });
        return v;
    }


@Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),RestoranMenu.class);
        startActivity(intent);
        getActivity().finish();

    }

}