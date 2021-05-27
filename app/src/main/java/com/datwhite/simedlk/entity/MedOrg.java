package com.datwhite.simedlk.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MedOrg implements Serializable, Parcelable {

    private String id;
    private String title;

    public MedOrg() {
    }

    public MedOrg(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected MedOrg(Parcel in) {
        id = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MedOrg> CREATOR = new Creator<MedOrg>() {
        @Override
        public MedOrg createFromParcel(Parcel in) {
            return new MedOrg(in);
        }

        @Override
        public MedOrg[] newArray(int size) {
            return new MedOrg[size];
        }
    };
}
