package com.vowme.app.utilities.api;

import android.os.AsyncTask;

import com.vowme.app.models.Enum.HttpRequestType;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import io.fabric.sdk.android.services.network.HttpRequest;

public class ApiRestFullRequest extends AsyncTask<Void, Void, String> {
    private static final String boundary = "YOUR_BOUNDARY_STRING";
    protected String accessToken;
    protected String baseURL;
    protected JSONObject bodyParameters;
    protected File dirPath;
    protected String fileName;
    protected boolean isMultipartResponse;
    protected HashMap<String, String> mapParameters;
    protected File multipartFile;
    protected HttpRequestType requestType;
    protected String route;
    protected String stringAsJsonParameters;

    public ApiRestFullRequest(HttpRequestType requestType, String baseURL, String route, String accessToken) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
        this.accessToken = accessToken;
    }

    public ApiRestFullRequest(HttpRequestType requestType, String baseURL, String route, HashMap<String, String> mapParameters, String accessToken) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
        this.mapParameters = mapParameters;
        this.accessToken = accessToken;
    }

    public ApiRestFullRequest(HttpRequestType requestType, String baseURL, String route, JSONObject bodyParameters, String accessToken) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
        this.accessToken = accessToken;
        this.bodyParameters = bodyParameters;
    }

    public ApiRestFullRequest(HttpRequestType requestType, String baseURL, String route, String stringAsJsonParameters, String accessToken) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
        this.accessToken = accessToken;
        this.stringAsJsonParameters = stringAsJsonParameters;
    }

    public ApiRestFullRequest(HttpRequestType requestType, String baseURL, String route, File multipartFile, String accessToken) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
        this.accessToken = accessToken;
        this.multipartFile = multipartFile;
    }

    public ApiRestFullRequest(HttpRequestType requestType, String baseURL, String route, String accessToken, boolean isMultipartResponse, String fileName, File dirPath) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
        this.accessToken = accessToken;
        this.isMultipartResponse = isMultipartResponse;
        this.fileName = fileName;
        this.dirPath = dirPath;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected String doInBackground(Void... paramVarArgs)
    {
        return "";
    }

    protected void onPostExecute(String result) {
        onPostExecuteBody(result);
    }

    protected void onPostExecuteBody(String result) {
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode((String) entry.getKey(), HttpRequest.CHARSET_UTF8));
            result.append("=");
            result.append(URLEncoder.encode((String) entry.getValue(), HttpRequest.CHARSET_UTF8));
        }
        return result.toString();
    }
}
