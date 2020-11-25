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
import java.util.List;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.Holder>{

    private List<MealsItem> listData = new ArrayList<>();
    private Context context;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListData(List<MealsItem> listData){
        this.listData.clear();
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public MakananAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MakananAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_makanan, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MakananAdapter.Holder holder, int i) {
        holder.titleMakanan.setText(listData.get(i).getStrCategory());
        holder.descriptionMakanan.setText(listData.get(i).getStrCategoryDescription());
        Glide.with(context).load(listData.get(i).getStrCategoryThumb())
                .into(holder.posterMaknan);

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
    public class Holder extends RecyclerView.ViewHolder {
        TextView titleMakanan, descriptionMakanan;
        ImageView posterMaknan;

        public Holder(@NonNull View itemView) {
            super(itemView);

            titleMakanan = itemView.findViewById(R.id.title_berita);
            descriptionMakanan = itemView.findViewById(R.id.year_berita);
            posterMaknan = itemView.findViewById(R.id.poster_berita);
        }

    }
    public interface OnItemClickCallback {
        void onItemClicked(MealsItem data);
    }
}
