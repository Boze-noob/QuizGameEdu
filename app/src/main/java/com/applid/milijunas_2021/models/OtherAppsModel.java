package com.applid.milijunas_2021.models;

public class OtherAppsModel {

    Integer appsLogo;
    String appsName;

    public OtherAppsModel(Integer appsLogo, String appsName) {
        this.appsLogo = appsLogo;
        this.appsName = appsName;
    }

    public Integer getAppsLogo() {
        return appsLogo;
    }

    public void setAppsLogo(Integer appsLogo) {
        this.appsLogo = appsLogo;
    }

    public String getAppsName() {
        return appsName;
    }

    public void setAppsName(String appsName) {
        this.appsName = appsName;
    }
}
