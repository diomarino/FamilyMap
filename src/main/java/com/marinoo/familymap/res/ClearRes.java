package com.marinoo.familymap.res;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

/**
 * Give response to ClearService.
 */
public class ClearRes {
    /**
     * To print error and success message.
     */
    private String message;

    private boolean success;


    public ClearRes() {
        this.message = "";
        this.success = false;
    }

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClearRes clearRes = (ClearRes) o;
        return success == clearRes.success &&
                Objects.equals(message, clearRes.message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(message, success);
    }
}
