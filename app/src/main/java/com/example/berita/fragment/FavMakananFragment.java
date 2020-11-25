package com.example.berita.fragment;


import android.content.Intent;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.berita.R;
import com.example.berita.activity.DetailActivity;
import com.example.berita.adapter.FavMakananAdapter;
import com.example.berita.adapter.MakananAdapter;
import com.example.berita.db.MakananHelper;
import com.example.berita.model.makanan.MealsItem;


import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMakananFragment extends Fragment implements LoadMakananCallback {
    private ProgressBar pgFavMakanan;
    private MakananHelper makananHelper;
    private FavMakananAdapter favMakananAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";


    public FavMakananFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return getView() != null ? getView() :
                inflater.inflate(R.layout.fragment_fav_makanan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavTv = view.findViewById(R.id.rv_fav_makanan);
        rvFavTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavTv.setHasFixedSize(true);
        pgFavMakanan = view.findViewById(R.id.pg_fav_makanan);

        makananHelper = MakananHelper.getInstance(getActivity());
        try {
            makananHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        favMakananAdapter = new FavMakananAdapter(getActivity());
        rvFavTv.setAdapter(favMakananAdapter);

        favMakananAdapter.setOnItemClickCallback(new MakananAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MealsItem data) {
                showSelectedTv(data);
            }
        });

        if (savedInstanceState == null) {
            Log.d("favoritemeal", "onViewCreated: saved instance kosong");
            new LoadMakananAsync(makananHelper, this).execute();
        } else {
            Log.d("favoritemeal", "onViewCreated: saved instance ada");
            ArrayList<MealsItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favMakananAdapter.setListMakanan(list);
            }
        }
    }

    private void showSelectedTv(MealsItem data) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_DETAIL_DATA, data);
        intent.putExtra(DetailActivity.KEY_JENIS_DATA, "meals");
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favMakananAdapter.getListMakanan());
    }

    @Override
    public void preExecute() {
        new Runnable() {
            @Override
            public void run() {
                pgFavMakanan.setVisibility(View.VISIBLE);
            }
        };
        Log.d("favoritemeal", "preExecute: masuk");
    }

    @Override
    public void postExecute(ArrayList<MealsItem> tvItems) {
        pgFavMakanan.setVisibility(View.INVISIBLE);
        favMakananAdapter.setListMakanan(tvItems);
        Log.d("favoritemeal", "postExecute: " + tvItems.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        makananHelper.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        makananHelper = MakananHelper.getInstance(getActivity());
        try {
            makananHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log.d("favoritemeal", "onViewCreated: saved instance kosong");
        new LoadMakananAsync(makananHelper, this).execute();
    }

}
