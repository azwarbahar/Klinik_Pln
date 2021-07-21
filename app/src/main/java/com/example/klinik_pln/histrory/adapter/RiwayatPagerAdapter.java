package com.example.klinik_pln.histrory.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RiwayatPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listfragmentriwayat = new ArrayList<>();
    private final List<String> listtitleriwayat = new ArrayList<>();

    public RiwayatPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listfragmentriwayat.get(position);
    }

    @Override
    public int getCount() {
        return listtitleriwayat.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listtitleriwayat.get(position);
    }

    public void AddFragment(Fragment fragment, String title){
        listfragmentriwayat.add(fragment);
        listtitleriwayat.add(title);
    }
}
