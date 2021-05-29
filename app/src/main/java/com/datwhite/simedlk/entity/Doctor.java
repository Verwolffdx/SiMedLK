package com.datwhite.simedlk.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Doctor implements Serializable, Parcelable {
    private String id;
    private String name;
    private String photo;
    private String desc;
    private String specialization;
    private String qualification;
    private String services;
    private List<Integer> DOCT_IDs;

    public Doctor() {
    }

    public Doctor(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public Doctor(String id, String name, String photo, String desc, String specialization, String qualification, String services, List<Integer> DOCT_IDs) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.desc = desc;
        this.specialization = specialization;
        this.qualification = qualification;
        this.services = services;
        this.DOCT_IDs = DOCT_IDs;
    }

    protected Doctor(Parcel in) {
        id = in.readString();
        name = in.readString();
        photo = in.readString();
        desc = in.readString();
        specialization = in.readString();
        qualification = in.readString();
        services = in.readString();
        DOCT_IDs = new ArrayList<Integer>();
        in.readList(DOCT_IDs, Doctor.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeString(desc);
        dest.writeString(specialization);
        dest.writeString(qualification);
        dest.writeString(services);
        dest.writeList(DOCT_IDs);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public List<Integer> getDOCT_IDs() {
        return DOCT_IDs;
    }

    public void setDOCT_IDs(List<Integer> DOCT_IDs) {
        this.DOCT_IDs = DOCT_IDs;
    }

    @Override
    public String toString() {
        return name;
    }
}
