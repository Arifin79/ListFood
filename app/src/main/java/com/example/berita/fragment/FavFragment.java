package com.example.berita.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.berita.R;
import com.example.berita.activity.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {


    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return getView() != null ? getView() :
                inflater.inflate(R.layout.fragment_fav, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerAdapter viewPagerAdapter = new  ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new FavMakananFragment(), getResources().getString(R.string.makanan));
        viewPagerAdapter.addFragment(new FavMinumanFragment(), getResources().getString(R.string.minuman));

        ViewPager viewPager = view.findViewById(R.id.pager_favorite);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabs = view.findViewById(R.id.tab_favorite);
        tabs.setupWithViewPager(viewPager);
    }

}
