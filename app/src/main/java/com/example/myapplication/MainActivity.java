package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.fragment.DaftarFilmFragment;
import com.example.myapplication.fragment.FavoriteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Set listener untuk item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment selectedFragment = null;

                // Memilih fragmen berdasarkan item yang dipilih dengan if-else
                if (item.getItemId() == R.id.nav_film) {
                    selectedFragment = new DaftarFilmFragment(); // Ganti dengan fragmen daftar film
                } else if (item.getItemId() == R.id.nav_favorite) {
                    selectedFragment = new FavoriteFragment(); // Ganti dengan fragmen favorite
                }

                // Menampilkan fragmen yang dipilih
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            }
        });

        // Set default fragment saat aplikasi pertama kali dijalankan (opsional)
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_film); // Menetapkan fragmen default
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_film) {
            // Ganti fragment ke DaftarFilmFragment
            switchFragment(new DaftarFilmFragment());
            return true;
        } else if (id == R.id.nav_favorite) {
            // Ganti fragment ke FavoriteFragment
            switchFragment(new FavoriteFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
