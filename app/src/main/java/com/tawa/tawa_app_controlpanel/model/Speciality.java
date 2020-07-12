package com.tawa.tawa_app_controlpanel.model;

public  class Speciality {
    private String name;
    private int SpecialistsNum =0;

    public Speciality() {

    }
public  Speciality(String name){
        this.name=name;
}
    public Speciality(String name,int numberOfSpecialist) {
        this.name = name;
        this.SpecialistsNum =numberOfSpecialist;
    }


    public int getSpecialistsNum() {
        return SpecialistsNum;
    }

    public void setSpecialistsNum(int specialistsNum) {
        this.SpecialistsNum = specialistsNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
