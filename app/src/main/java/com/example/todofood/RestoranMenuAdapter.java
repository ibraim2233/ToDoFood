package com.example.todofood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestoranMenuAdapter extends RecyclerView.Adapter<RestoranMenuAdapter.ExampleViewHolder> {
    ArrayList<RestoranMenuCard> mDishList;

    ///////////////////////////////////////////////////////////////////////
    private OnItemClickListener mListener;

    public  interface OnItemClickListener {
        void onAddClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){mListener = listener;}
    //////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restoran_menu_card,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        RestoranMenuCard curentCard = mDishList.get(position);
        holder.textViewName.setText(curentCard.getName());
        holder.textViewDescription.setText(curentCard.getDescription());
        holder.textViewPrice.setText(curentCard.getPrice());
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
        public TextView textViewDescription;
        public TextView textViewPrice;
        public ImageView imageView;

        ///////////////////////////////////////////////////////////////////////

        public Button buttonAdd;

        //////////////////////////////////////////////////////////////////////
        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener lisener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.dish_name);
            textViewDescription= itemView.findViewById(R.id.dish_description);
            textViewPrice = itemView.findViewById(R.id.dish_price);
            imageView = itemView.findViewById(R.id.dish_image);

            ///////////////////////////////////////////////////////////////////////////
            buttonAdd = itemView.findViewById(R.id.button_add);

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lisener != null){

                        int position = getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            lisener.onAddClick(position);
                        }
                    }
                }
            });

            ///////////////////////////////////////////////////////////////////////////
        }
    }
    public RestoranMenuAdapter(ArrayList<RestoranMenuCard> kDishList){
        mDishList = kDishList;
    }

}
