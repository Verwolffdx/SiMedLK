package com.datwhite.simedlk.entity;

import java.io.Serializable;
import java.util.List;

public class Doctor implements Serializable {
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

    public Doctor(String id, String name, String photo, String desc, String specialization, String qualification, String services) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.desc = desc;
        this.specialization = specialization;
        this.qualification = qualification;
        this.services = services;
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
        return "Doctor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", desc='" + desc + '\'' +
                ", specialization='" + specialization + '\'' +
                ", qualification='" + qualification + '\'' +
                ", services='" + services + '\'' +
                ", DOCT_IDs=" + DOCT_IDs +
                '}';
    }
}
