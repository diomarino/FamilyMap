package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.marinoo.familymap.model.Event;
import java.util.Arrays;
import java.util.Objects;

public class AllEventRes {

    private Event[] data;
    private boolean success;

    public AllEventRes() {
        this.data = new Event[200];
        this.success = false;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllEventRes that = (AllEventRes) o;
        return success == that.success &&
                Arrays.equals(data, that.data);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        int result = Objects.hash(success);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
