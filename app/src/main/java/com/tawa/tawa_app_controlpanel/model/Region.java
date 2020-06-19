package com.tawa.tawa_app_controlpanel.model;

public class Region {
    private String name;
    private String governorate;
    public Region(){

    }

    public Region(String name,String governorate){
        this.name=name;
        this.governorate=governorate;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}