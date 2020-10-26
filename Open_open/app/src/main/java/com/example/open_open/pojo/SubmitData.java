package com.example.open_open.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitData {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("pesan")
    @Expose
    private String pesan;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
