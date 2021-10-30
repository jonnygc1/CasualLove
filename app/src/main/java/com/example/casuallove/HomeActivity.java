package com.example.casuallove;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.casuallove.fragments.Filtrar;
import com.example.casuallove.fragments.Home;
import com.example.casuallove.fragments.Mensajes;
import com.example.casuallove.fragments.perfil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);




    }

    public void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigationHome:
                            openFragment(Home.newInstance("", ""));
                            return true;
                        case R.id.navigationChat:
                            openFragment(Mensajes.newInstance("", ""));
                            return true;
                        case R.id.navigationFiltro:
                            openFragment(Filtrar.newInstance("", ""));
                            return true;
                        case R.id.navigationPerfil:
                            openFragment(perfil.newInstance("", ""));
                            return true;
                    }
                    return true;
                }
            };








}