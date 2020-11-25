package com.example.berita.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.berita.R;
import com.example.berita.activity.DetailActivity;
import com.example.berita.adapter.MakananAdapter;
import com.example.berita.model.makanan.MealsItem;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MakananShowFragment extends Fragment {
    private MakananAdapter makananAdapter;
    private RecyclerView rvMakanan;
    private ProgressBar pgMakanan;


    public MakananShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return getView() != null ? getView() :
                inflater.inflate(R.layout.fragment_makanan_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MakananViewModel moviesViewModel = ViewModelProviders.of(MakananShowFragment.this)
                .get(MakananViewModel.class);
        moviesViewModel.getListmakanan().observe(this, getMakanan);

        rvMakanan = view.findViewById(R.id.rv_makanan);
        pgMakanan = view.findViewById(R.id.pg_makanan);


        makananAdapter = new MakananAdapter(getActivity());
        makananAdapter.notifyDataSetChanged();

        moviesViewModel.setListMovies();
        showLoading(true);
        showRecyclerList();
    }

    private Observer<List<MealsItem>> getMakanan = new Observer<List<MealsItem>>() {
        @Override
        public void onChanged(@Nullable List<MealsItem> mealsItems) {
            if (mealsItems != null) {
                makananAdapter.setListData(mealsItems);
                showLoading(false);
            }
        }
    };

    private void showRecyclerList() {
        rvMakanan.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMakanan.setAdapter(makananAdapter);

        makananAdapter.setOnItemClickCallback(new MakananAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MealsItem data) {
                showSelectedIndo(data);
            }
        });
    }

    private void showSelectedIndo(MealsItem makanan) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_DETAIL_DATA, makanan);
        intent.putExtra(DetailActivity.KEY_JENIS_DATA, "meals");
        startActivity(intent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            pgMakanan.setVisibility(View.VISIBLE);
        } else {
            pgMakanan.setVisibility(View.GONE);
        }
    }

}
