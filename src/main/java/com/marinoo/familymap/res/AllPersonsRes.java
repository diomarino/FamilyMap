package com.marinoo.familymap.res;

import com.marinoo.familymap.model.Person;


import java.util.Arrays;
import java.util.Objects;

public class AllPersonsRes {
    private Person[] data;
    private boolean success;

    public AllPersonsRes() {
        this.data = new Person[1000];
        this.success = false;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
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
        AllPersonsRes that = (AllPersonsRes) o;
        return success == that.success &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(success);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
