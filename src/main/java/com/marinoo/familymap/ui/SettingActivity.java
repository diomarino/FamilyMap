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
import com.marinoo.familymap.cmodel.Settings;

public class SettingActivity extends AppCompatActivity {
    private Context context;
    private CompoundButton spouseLine;
    private CompoundButton lifeStoryLine;
    private CompoundButton familyTreeLine;


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

                FamilyTree.getInstance().setAuthToken(null);
                FamilyTree.getInstance().setLoggedIn(false);
                Intent intent = new Intent(context, MainActivity.class);
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
