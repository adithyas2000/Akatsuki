package com.akatsuki.greenarcade;

import android.text.Editable;
import android.widget.EditText;

public class TrackOrderSeller {


    private Integer tel;
    private String toBeDispatched;
    private String Dispatched;

    public TrackOrderSeller() {
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getToBeDispatched() {
        return toBeDispatched;
    }

    public void setToBeDispatched(String toBeDispatched) {
        this.toBeDispatched = toBeDispatched;
    }

    public String getDispatched() {
        return Dispatched;
    }

    public void setDispatched(String dispatched) {
        Dispatched = dispatched;
    }
}
