package com.vowme.app.models.api;

public class SecondaryEmailModel extends PostApiModel {
    public String Email;
    public String Source;

    public SecondaryEmailModel(String email, String source) {
        this.Email = email;
        this.Source = source;
    }
}
