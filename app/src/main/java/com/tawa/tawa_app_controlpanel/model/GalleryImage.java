package com.tawa.tawa_app_controlpanel.model;

public class GalleryImage {

    private String url;
  private String state;


    public GalleryImage() {
    }

    public GalleryImage(String url, String state) {
        this.url = url;
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
