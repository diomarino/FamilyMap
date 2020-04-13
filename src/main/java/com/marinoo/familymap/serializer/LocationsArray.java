package com.marinoo.familymap.serializer;

public class LocationsArray {
    private Location[] data;

    public LocationsArray() {
        this.data = new Location[1467];
    }

    public Location[] getData() {
        return data;
    }

    public void setData(Location[] data) {
        this.data = data;
    }

    public Location getLocationAt(int index) {
        return data[index];
    }
}
