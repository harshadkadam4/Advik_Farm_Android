package com.example.advik_farm_2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    ImageView more;
    NavigationView nav_drawer;
    DrawerLayout drawer_layout;
    RelativeLayout mainRelLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        more = findViewById(R.id.more);
        nav_drawer = findViewById(R.id.nav_drawer);
        drawer_layout = findViewById(R.id.drawer_layout);
        mainRelLayout = findViewById(R.id.mainRelLayout);

        MilkAddFragment milkAddFragment = new MilkAddFragment();
        ExpenseFragment expenseFragment = new ExpenseFragment();

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(nav_drawer);
            }
        });

        nav_drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.milk_add)
                {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.subRelLayout, milkAddFragment)
                            .commit();
                    drawer_layout.closeDrawers();
                    return true;
                }
                else if(id == R.id.expense)
                {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.subRelLayout, expenseFragment)
                            .commit();
                    drawer_layout.closeDrawers();
                    return true;
                }

                return false;
            }
        });

    }


}