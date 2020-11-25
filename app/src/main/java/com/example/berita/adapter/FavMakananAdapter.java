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

import java.util.ArrayList;

public class FavMakananAdapter extends RecyclerView.Adapter<FavMakananAdapter.Holder> {
    private ArrayList<MealsItem> listData = new ArrayList<>();
    private Context context;
    private MakananAdapter.OnItemClickCallback onItemClickCallback;

    public ArrayList<MealsItem> getListMakanan() {
        return listData;
    }

    public FavMakananAdapter(Context context) {
        this.context= context;
    }

    public void setListMakanan(ArrayList<MealsItem> listMovie) {

        if (listMovie.size() > 0) {
            this.listData.clear();
        }
        this.listData.addAll(listMovie);

        notifyDataSetChanged();
    }

    public void addItem(MealsItem note) {
        this.listData.add(note);
        notifyItemInserted(listData.size() - 1);
    }

    public void updateItem(int position, MealsItem note) {
        this.listData.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position) {
        this.listData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listData.size());
    }

    public void setOnItemClickCallback(MakananAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fav_makanan, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        holder.titleFavMakanan.setText(listData.get(i).getStrCategory());
        holder.descriptionFavMakanan.setText(listData.get(i).getStrCategoryDescription());

        Glide.with(context).load(listData.get(i).getStrCategoryThumb())
                .into(holder.posterFavMakanan);

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
        TextView titleFavMakanan, descriptionFavMakanan;
        ImageView posterFavMakanan;

        Holder(@NonNull View itemView) {
            super(itemView);

            titleFavMakanan = itemView.findViewById(R.id.title_fav_makanan);
            descriptionFavMakanan = itemView.findViewById(R.id.description_fav_makanan);
            posterFavMakanan = itemView.findViewById(R.id.poster_fav_makanan);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MealsItem data);
    }
}
