package com.vowme.app.models.api;

public class ChangePasswordAPI extends PostApiModel {
    public String currentPassword;
    public String newPassword;

    public ChangePasswordAPI(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
