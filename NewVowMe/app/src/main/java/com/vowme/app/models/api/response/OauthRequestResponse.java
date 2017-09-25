package com.vowme.app.models.api.response;

public class OauthRequestResponse {
    private int errorCode;
    private String jsonAsString;

    public OauthRequestResponse(int errorCode, String jsonAsString) {
        this.errorCode = errorCode;
        this.jsonAsString = jsonAsString;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getJsonAsString() {
        return this.jsonAsString;
    }
}
