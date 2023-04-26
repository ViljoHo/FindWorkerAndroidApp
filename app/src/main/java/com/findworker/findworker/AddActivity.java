package com.findworker.findworker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AddActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Home homefrag = new Home();
    Add addfrag = new Add();
    Profile profilefrag = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem((R.id.navAdd)).setChecked(true);
        //getSupportFragmentManager().beginTransaction().replace(R.id.openPageAdd, addfrag).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navAdd:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.openPageAdd, addfrag).commit();
                        return true;

                    case R.id.navProfile:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.openPageAdd, profilefrag).commit();
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        return true;

                    case R.id.navHome:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.openPageAdd, homefrag).commit();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        return true;
                }
                return false;
            }
        });
    }
}