package com.example.todofood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ExampleViewHolder>{

    ArrayList<BasketCard> mDishList;


    @NonNull
    @Override
    public BasketAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_card,parent,false);
        BasketAdapter.ExampleViewHolder evh = new BasketAdapter.ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.ExampleViewHolder holder, int position) {
        BasketCard curentCard = mDishList.get(position);
        holder.textViewName.setText(curentCard.getName());
        holder.textViewPrice.setText(curentCard.getPrice());
        holder.dishCount.setText(curentCard.getDishCount());
        Picasso.with(holder.imageView.getContext())
                .load(curentCard.getImage())
                .into(holder.imageView);
        //holder.imageView.setImageResource(curentCard.getImage());//надо заменить на гетер для картинки

    }

    @Override
    public int getItemCount() {
        return mDishList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public TextView textViewPrice;
        public ImageView imageView;
        public TextView dishCount;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.title_basket_card);
            textViewPrice = itemView.findViewById(R.id.price_basket_card);
            imageView = itemView.findViewById(R.id.image_basket_card);
            dishCount = itemView.findViewById(R.id.colichestvo);

        }
    }
    public BasketAdapter(ArrayList<BasketCard> kDishList){
        mDishList = kDishList;
    }
}
