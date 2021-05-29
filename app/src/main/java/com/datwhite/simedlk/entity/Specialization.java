package com.datwhite.simedlk.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Specialization implements Parcelable {
    private String id;
    private String name;

    public Specialization(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
