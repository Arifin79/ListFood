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
import com.example.berita.adapter.MinumanAdapter;
import com.example.berita.model.minuman.DrinksItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MinumanShowFragment extends Fragment {
    private MinumanAdapter minumanAdapter;
    private RecyclerView rvMinuman;
    private ProgressBar pgMinuman;


    public MinumanShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_minuman_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MinumanViewModel moviesViewModel = ViewModelProviders.of(MinumanShowFragment.this)
                .get(MinumanViewModel.class);
        moviesViewModel.getListminuman().observe(this, getMinuman);

        rvMinuman = view.findViewById(R.id.rv_minuman);
        pgMinuman = view.findViewById(R.id.pg_minuman);


        minumanAdapter = new MinumanAdapter(getActivity());
        minumanAdapter.notifyDataSetChanged();

        moviesViewModel.setListminuman();
        showLoading(true);
        showRecyclerList();
    }

    private Observer<List<DrinksItem>> getMinuman = new Observer<List<DrinksItem>>() {
        @Override
        public void onChanged(@Nullable List<DrinksItem> drinksItems) {
            if (drinksItems != null) {
                minumanAdapter.setListData(drinksItems);
                showLoading(false);
            }
        }
    };

    private void showRecyclerList() {
        rvMinuman.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMinuman.setAdapter(minumanAdapter);

        minumanAdapter.setOnItemClickCallback(new MinumanAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DrinksItem data) {
                showSelectedIndo(data);
            }
        });
    }

    private void showSelectedIndo(DrinksItem drinks) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_DETAIL_DATA, drinks);
        intent.putExtra(DetailActivity.KEY_JENIS_DATA, "drinks");
        startActivity(intent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            pgMinuman.setVisibility(View.VISIBLE);
        } else {
            pgMinuman.setVisibility(View.GONE);
        }
    }


}
