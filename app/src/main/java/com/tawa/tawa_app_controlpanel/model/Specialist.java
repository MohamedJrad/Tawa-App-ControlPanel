package com.tawa.tawa_app_controlpanel.model;

public class Specialist  {
    private String name;
    private  String imageUrl;
    private String speciality;
    private String address;
    private String phone;
    private String email;
    private String region;
    private Boolean visibility;

    public Specialist() {
    }

    public Specialist( String imageUrl,String name, String address, String phone, String email,String speciality, String region, String governorate,Boolean visibility) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.speciality = speciality;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.region = region;
        this.governorate = governorate;
        this.visibility=visibility;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    private String governorate;
}
