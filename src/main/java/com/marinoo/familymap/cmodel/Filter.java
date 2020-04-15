package com.marinoo.familymap.cmodel;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class Filter {
    private static Filter instance;
    private boolean showFatherSide = true;
    private boolean showMotherSide = true;
    private boolean showMaleEvents = true;
    private boolean showFemaleEvents = true;
    private ArrayList<Marker> mapMarkers = new ArrayList<>();

    public static Filter getInstance() {
        if (instance == null) {
            instance = new Filter();
        }
        return instance;
    }

    public boolean isShowFatherSide() {
        return showFatherSide;
    }

    public void setShowFatherSide(boolean showFatherSide) {
        this.showFatherSide = showFatherSide;
    }

    public boolean isShowMotherSide() {
        return showMotherSide;
    }

    public void setShowMotherSide(boolean showMotherSide) {
        this.showMotherSide = showMotherSide;
    }

    public boolean isShowMaleEvents() {
        return showMaleEvents;
    }

    public void setShowMaleEvents(boolean showMaleEvents) {
        this.showMaleEvents = showMaleEvents;
    }

    public boolean isShowFemaleEvents() {
        return showFemaleEvents;
    }

    public void setShowFemaleEvents(boolean showFemaleEvents) {
        this.showFemaleEvents = showFemaleEvents;
    }

    public ArrayList<Marker> getMapMarkers() {
        return mapMarkers;
    }

    public void setMapMarkers(ArrayList<Marker> mapMarkers) {
        this.mapMarkers = mapMarkers;
    }
}
