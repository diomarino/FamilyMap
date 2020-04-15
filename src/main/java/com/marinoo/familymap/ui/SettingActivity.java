package com.marinoo.familymap.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.marinoo.familymap.R;
import com.marinoo.familymap.cmodel.FamilyTree;
import com.marinoo.familymap.cmodel.Filter;
import com.marinoo.familymap.cmodel.Settings;

public class SettingActivity extends AppCompatActivity {
    private Context context;
    private CompoundButton spouseLine;
    private CompoundButton lifeStoryLine;
    private CompoundButton familyTreeLine;
    private CompoundButton fatherSide;
    private CompoundButton motherSide;
    private CompoundButton maleEvents;
    private CompoundButton femaleEvents;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Family Map: Settings");

        View logoutLayout = findViewById(R.id.logoutSetting);
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetSettings();
                FamilyTree.getInstance().setAuthToken(null);
                FamilyTree.getInstance().setLoggedIn(false);
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        setCompoundButton();
    }

    private void setCompoundButton() {

        spouseLine = findViewById(R.id.spouseLine);
        spouseLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Settings.getInstance().setShowSpouseLines(isChecked);
            }
        });

        lifeStoryLine = findViewById(R.id.lifeStoryLine);
        lifeStoryLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Settings.getInstance().setShowLifeStoryLines(isChecked);
            }
        });

        familyTreeLine = findViewById(R.id.familyTreeLine);
        familyTreeLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.getInstance().setShowFamilyTreeLines(isChecked);
            }
        });

        fatherSide = findViewById(R.id.fatherSide);
        fatherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().setShowFatherSide(isChecked);
            }
        });

        motherSide = findViewById(R.id.motherSide);
        motherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().setShowMotherSide(isChecked);
            }
        });

        maleEvents = findViewById(R.id.maleEvents);
        maleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().setShowMaleEvents(isChecked);
            }
        });

        femaleEvents = findViewById(R.id.femaleEvents);
        femaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().setShowFemaleEvents(isChecked);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        spouseLine = findViewById(R.id.spouseLine);
        spouseLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.getInstance().setShowSpouseLines(isChecked);
            }
        });
        spouseLine.setChecked(Settings.getInstance().isShowSpouseLines());

        lifeStoryLine = findViewById(R.id.lifeStoryLine);
        lifeStoryLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.getInstance().setShowLifeStoryLines(isChecked);
            }
        });
        lifeStoryLine.setChecked(Settings.getInstance().isShowLifeStoryLines());

        familyTreeLine = findViewById(R.id.familyTreeLine);
        familyTreeLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.getInstance().setShowFamilyTreeLines(isChecked);
            }
        });
        familyTreeLine.setChecked(Settings.getInstance().isShowFamilyTreeLines());

        fatherSide = findViewById(R.id.fatherSide);
        fatherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().setShowFatherSide(isChecked);
            }
        });
        fatherSide.setChecked(Filter.getInstance().isShowFatherSide());

        motherSide = findViewById(R.id.motherSide);
        motherSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().setShowMotherSide(isChecked);
            }
        });
        motherSide.setChecked(Filter.getInstance().isShowMotherSide());

        maleEvents = findViewById(R.id.maleEvents);
        maleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().setShowMaleEvents(isChecked);
            }
        });
        maleEvents.setChecked(Filter.getInstance().isShowMaleEvents());

        femaleEvents = findViewById(R.id.femaleEvents);
        femaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.getInstance().setShowFemaleEvents(isChecked);
            }
        });
        femaleEvents.setChecked(Filter.getInstance().isShowFemaleEvents());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class); intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(intent);
        }
        return true;
    }

    private void resetSettings() {

        spouseLine = findViewById(R.id.spouseLine);
        spouseLine.setChecked(false);
        Settings.getInstance().setShowSpouseLines(false);

        lifeStoryLine = findViewById(R.id.lifeStoryLine);
        lifeStoryLine.setChecked(false);
        Settings.getInstance().setShowLifeStoryLines(false);

        familyTreeLine = findViewById(R.id.familyTreeLine);
        familyTreeLine.setChecked(false);
        Settings.getInstance().setShowFamilyTreeLines(false);

        fatherSide = findViewById(R.id.fatherSide);
        fatherSide.setChecked(true);
        Filter.getInstance().setShowFatherSide(true);

        motherSide = findViewById(R.id.motherSide);
        motherSide.setChecked(true);
        Filter.getInstance().setShowMotherSide(true);

        maleEvents = findViewById(R.id.maleEvents);
        maleEvents.setChecked(true);
        Filter.getInstance().setShowMaleEvents(true);

        femaleEvents = findViewById(R.id.femaleEvents);
        femaleEvents.setChecked(true);
        Filter.getInstance().setShowFemaleEvents(true);
    }
}
