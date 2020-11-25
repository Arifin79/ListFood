package com.example.berita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.berita.R;
import com.example.berita.model.makanan.MealsItem;
import com.example.berita.model.minuman.DrinksItem;

import java.util.ArrayList;
import java.util.List;

public class MinumanAdapter extends RecyclerView.Adapter<MinumanAdapter.Holder>{

    private List<DrinksItem> listData = new ArrayList<>();
    private Context context;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListData(List<DrinksItem> listData){
        this.listData.clear();
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public MinumanAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_minuman, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        holder.titleMinuman.setText(listData.get(i).getStrDrink());
        Glide.with(context).load(listData.get(i).getStrDrinkThumb())
                .into(holder.posterMinuman);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listData.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    class Holder extends RecyclerView.ViewHolder {
        TextView titleMinuman;
        ImageView posterMinuman;

        Holder(@NonNull View itemView) {
            super(itemView);

            titleMinuman = itemView.findViewById(R.id.title_minuman);
            posterMinuman = itemView.findViewById(R.id.poster_minuman);
        }

    }
    public interface OnItemClickCallback {
        void onItemClicked(DrinksItem data);
    }
}
