package com.marinoo.familymap.serializer;

public class NamesArray {

    private String[] data;

    public NamesArray() {
        data = new String[200];
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getValueAt(int index) {
        return data[index];
    }
}
