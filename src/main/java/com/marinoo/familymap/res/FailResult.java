package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class FailResult {

    private String message;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public FailResult() {
        this.message = "";
        this.success = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FailResult that = (FailResult) o;
        return success == that.success &&
                Objects.equals(message, that.message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(message, success);
    }
}
