package com.example.advik_farm_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MilkAddFragment extends Fragment {

    ImageView more;
    DrawerLayout drawer_layout;
    NavigationView nav_drawer;

    public MilkAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceSate)
    {
        View view = inflater.inflate(R.layout.milk_add_frag,container,false);

        return view;
    }
}
