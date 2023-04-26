package com.findworker.findworker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Home homefrag = new Home();
    Add addfrag = new Add();
    Profile profilefrag = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem((R.id.navProfile)).setChecked(true);
        //getSupportFragmentManager().beginTransaction().replace(R.id.openPageProfile, profilefrag).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navProfile:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.openPageProfile, profilefrag).commit();
                        return true;

                    case R.id.navHome:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.openPageProfile, homefrag).commit();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;

                    case R.id.navAdd:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.openPageProfile, addfrag).commit();
                        startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        return true;
                }
                return false;
            }
        });
    }

}