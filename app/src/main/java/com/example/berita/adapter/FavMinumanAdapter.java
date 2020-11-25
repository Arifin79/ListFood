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
import com.example.berita.model.minuman.DrinksItem;

import java.util.ArrayList;

public class FavMinumanAdapter extends RecyclerView.Adapter<FavMinumanAdapter.Holder> {
    private ArrayList<DrinksItem> listData = new ArrayList<>();
    private Context context;
    private OnItemClickCallback onItemClickCallback;

    public ArrayList<DrinksItem> getListMinuman() {
        return listData;
    }

    public FavMinumanAdapter(Context context) {
        this.context= context;
    }

    public void setListMinuman(ArrayList<DrinksItem> listMinuman) {

        if (listMinuman.size() > 0) {
            this.listData.clear();
        }
        this.listData.addAll(listMinuman);

        notifyDataSetChanged();
    }

    public void addItem(DrinksItem note) {
        this.listData.add(note);
        notifyItemInserted(listData.size() - 1);
    }

    public void updateItem(int position, DrinksItem note) {
        this.listData.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position) {
        this.listData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listData.size());
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fav_minuman, viewGroup, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        holder.titleFavMinuman.setText(listData.get(i).getStrDrink());
        Glide.with(context).load(listData.get(i).getStrDrinkThumb())
                .into(holder.posterFavMinuman);

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
        TextView titleFavMinuman;
        ImageView posterFavMinuman;

        Holder(@NonNull View itemView) {
            super(itemView);

            titleFavMinuman = itemView.findViewById(R.id.title_fav_minuman);
            posterFavMinuman = itemView.findViewById(R.id.poster_fav_minuman);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(DrinksItem data);
    }
}
