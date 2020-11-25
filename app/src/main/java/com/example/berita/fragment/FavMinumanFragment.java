package com.example.berita.fragment;


import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.berita.adapter.FavMinumanAdapter;
import com.example.berita.adapter.MinumanAdapter;
import com.example.berita.db.MakananHelper;
import com.example.berita.db.MinumanHelper;
import com.example.berita.model.makanan.MealsItem;
import com.example.berita.model.minuman.DrinksItem;

import java.lang.ref.WeakReference;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMinumanFragment extends Fragment implements LoadMinumanCallBack {
    private ProgressBar pgFavMinuman;
    private MinumanHelper minumanHelper;
    private FavMinumanAdapter favMinumanAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";


    public FavMinumanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return getView() != null ? getView() :
                inflater.inflate(R.layout.fragment_fav_minuman, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavDrink = view.findViewById(R.id.rv_fav_minuman);
        rvFavDrink.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavDrink.setHasFixedSize(true);
        pgFavMinuman = view.findViewById(R.id.pg_fav_minuman);

        minumanHelper = MinumanHelper.getInstance(getActivity());
        try {
            minumanHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        favMinumanAdapter = new FavMinumanAdapter(getActivity());
        rvFavDrink.setAdapter(favMinumanAdapter);

        favMinumanAdapter.setOnItemClickCallback(new FavMinumanAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DrinksItem data) {
                showSelectedTv(data);
            }
        });

        if (savedInstanceState == null) {
            Log.d("favoritedrink", "onViewCreated: saved instance kosong");
            new FavMinumanFragment.LoadTvAsync(minumanHelper, this).execute();
        } else {
            ArrayList<DrinksItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favMinumanAdapter.setListMinuman(list);
            }
            Log.d("favoritedrink", "onViewCreated: saved instance ada : " + list);
        }
    }

    private void showSelectedTv(DrinksItem data) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_DETAIL_DATA, data);
        intent.putExtra(DetailActivity.KEY_JENIS_DATA, "drinks");
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favMinumanAdapter.getListMinuman());
        Log.d("favoritedrink", "onSaveInstanceState: " + favMinumanAdapter.getListMinuman());
    }

    @Override
    public void preExecute() {
        new Runnable() {
            @Override
            public void run() {
                pgFavMinuman.setVisibility(View.VISIBLE);
            }
        };
        Log.d("favoritedrink", "preExecute: masuk");

    }

    @Override
    public void postExecute(ArrayList<DrinksItem> drinksItem) {
        pgFavMinuman.setVisibility(View.INVISIBLE);
        favMinumanAdapter.setListMinuman(drinksItem);
        Log.d("favoritedrink", "postExecute: " + drinksItem.toString());

    }

    private class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<DrinksItem>> {
        private final WeakReference<MinumanHelper> tvHelperWeakReference;
        private final WeakReference<LoadMinumanCallBack> tvCallbackWeakReference;

        LoadTvAsync(MinumanHelper tvShowsHelper, LoadMinumanCallBack loadTvShowsCallback) {
            tvHelperWeakReference = new WeakReference<>(tvShowsHelper);
            tvCallbackWeakReference = new WeakReference<>(loadTvShowsCallback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvCallbackWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<DrinksItem> doInBackground(Void... voids) {
            return tvHelperWeakReference.get().getAllMinuman();
        }

        @Override
        protected void onPostExecute(ArrayList<DrinksItem> drinksItem) {
            super.onPostExecute(drinksItem);

            tvCallbackWeakReference.get().postExecute(drinksItem);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        minumanHelper.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        minumanHelper = MinumanHelper.getInstance(getActivity());
        try {
            minumanHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log.d("favoritedrink", "onViewCreated: saved instance kosong");
        new FavMinumanFragment.LoadTvAsync(minumanHelper, this).execute();
    }
}
