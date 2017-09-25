package com.vowme.app.models.api;

public class AppUserLoginInfo extends PostApiModel {
    public String loginProvider;
    public String providerKey;

    public AppUserLoginInfo(String loginProvider, String providerKey) {
        this.loginProvider = loginProvider;
        this.providerKey = providerKey;
    }
}
