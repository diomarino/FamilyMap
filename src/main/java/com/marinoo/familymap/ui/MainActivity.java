package com.marinoo.familymap.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.marinoo.familymap.R;
import com.marinoo.familymap.cmodel.FamilyTree;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Iconify.with(new FontAwesomeModule());
        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFragment = fm.findFragmentById(R.id.fragmentContainer);
        Fragment mapFragment = fm.findFragmentById(R.id.fragmentContainer);

        if (!FamilyTree.getInstance().isLoggedIn()) {
            if (FamilyTree.getInstance().getAuthToken() == null) {
                loginFragment = new LoginFragment(this);
                fm.beginTransaction()
                        .add(R.id.fragmentContainer, loginFragment)
                        .commit();
            }
        } else {

            if (mapFragment == null) {
                mapFragment = new MapFragment(this);
                fm.beginTransaction()
                        .replace(R.id.fragmentContainer, mapFragment)
                        .commit();
            }
        }

    }

    public void onLoginSuccess() {
        FamilyTree familyTree = FamilyTree.getInstance();
        familyTree.setLoggedIn(true);
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragment);

        if (mapFragment == null) {
            mapFragment = new MapFragment(this);
            mapFragment.setEventActivity(false);
            fm.beginTransaction()
                    .replace(R.id.fragmentContainer, mapFragment)
                    .commit();
        }
    }
}
