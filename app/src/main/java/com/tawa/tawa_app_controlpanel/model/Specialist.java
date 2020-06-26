package com.tawa.tawa_app_controlpanel.model;

public class Specialist  {
    private String name="";
    private  String imageUrl="";
    private String speciality="";
    private String address="";
    private String phone="";
    private String email="";
    private String region="";
    private String governorate;
    private Boolean visibility=true;
    private  String jobTitle="";
    private  String description="";
    private String facebook="";
    private String instagram="";

    public Specialist() {
    }

    public Specialist(String name,  String jobTitle ,String imageUrl, String speciality, String address, String phone, String email, String region, String governorate, Boolean visibility) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.speciality = speciality;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.region = region;
        this.governorate = governorate;
        this.visibility = visibility;
        this.jobTitle = jobTitle;
    }

    public Specialist(String name, String imageUrl, String speciality, String address, String phone, String email, String region, String governorate, Boolean visibility, String jobTitle, String description, String facebook, String instagram) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.speciality = speciality;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.region = region;
        this.governorate = governorate;
        this.visibility = visibility;
        this.jobTitle = jobTitle;
        this.description = description;
        this.facebook = facebook;
        this.instagram = instagram;
    }



    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
