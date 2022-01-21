package com.example.watchatask;

import android.os.Bundle;

import com.example.watchatask.model.DataManager;
import com.example.watchatask.ui.favorite.FavoriteFragment;
import com.example.watchatask.ui.music.MusicFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.init(this);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, new MusicFragment(), "music").commitAllowingStateLoss();

        // Fragment를 이동해도 Fragment의 상태가 유지될 수 있도록 하는 custom ItemSelected handler
        navView.setOnItemSelectedListener((item) -> {
            for (Fragment fragment : fragmentManager.getFragments()) {
                fragmentManager.beginTransaction().hide(fragment).commit();
            }
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_music :
                    fragment = fragmentManager.findFragmentByTag("music");
                    if (fragment == null)
                        fragmentManager.beginTransaction()
                                .add(R.id.nav_host_fragment, new MusicFragment(), "music").commit();
                    else
                        fragmentManager.beginTransaction().show(fragment).commit();
                    break;

                case R.id.navigation_favorite :
                    fragment = fragmentManager.findFragmentByTag("favorite");
                    if (fragment == null)
                        fragmentManager.beginTransaction()
                                .add(R.id.nav_host_fragment, new FavoriteFragment(), "favorite").commit();
                    else
                        fragmentManager.beginTransaction().show(fragment).commit();
                    break;

                default:
                    return false;
            }
            return true;
        });
    }

}