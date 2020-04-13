package com.marinoo.familymap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.marinoo.familymap.R;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.model.Event;

public class EventActivity extends AppCompatActivity {

    private String selectedEventID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        selectedEventID = getIntent().getExtras().getString("selectedEventID");
        Event selectedEvent = FamilyTree.getInstance().getEventMap().get(selectedEventID);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mapContainer);

        if (mapFragment == null) {

            mapFragment = new MapFragment(this);
            mapFragment.setEventActivity(true);
            mapFragment.setMapsCenteredEvent(selectedEvent);
            fragmentManager.beginTransaction()
                    .add(R.id.mapContainer, mapFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class); intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(intent);
        }
        return true;
    }
}
