package com.vowme.app.utilities.api;

import android.os.AsyncTask;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.response.AppIdentityResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
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

    protected String doInBackground(Void... params) {
        BufferedReader reader;
        String line;
        InputStream errorStream;
        Exception e;
        URL url;
        AppIdentityResult result;
        Throwable th;
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();
        int responseCode = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        try {
            URL url2 = new URL(this.baseURL + this.route);
            urlConnection = (HttpURLConnection) url2.openConnection();
            urlConnection.setRequestMethod(this.requestType.getValue());
            urlConnection.setRequestProperty("Authorization", "Bearer " + this.accessToken);
            if ("POST".equalsIgnoreCase(this.requestType.getValue())) {
                urlConnection.setRequestProperty("content-type", "application/json");
                byte[] outputInBytes = this.bodyParameters.toString().getBytes("UTF-8");
                OutputStream os = urlConnection.getOutputStream();
                os.write( outputInBytes );
                os.close();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, HttpRequest.CHARSET_UTF8));
                writer.close();
            }
            reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(urlConnection.getInputStream()), HttpRequest.CHARSET_UTF8));
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                builder.append(line).append("\n");
            }
            reader.close();
            return builder.toString();
        } catch (Exception ex) {
            Log.d("Error",ex.getMessage());
        }
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

    private String getPostDataFromJson(JSONObject jsonObject) throws UnsupportedEncodingException, JSONException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext() ) {
            String key = keys.next();
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(key, HttpRequest.CHARSET_UTF8));
            result.append("=");
            result.append(URLEncoder.encode( jsonObject.get(key).toString() , HttpRequest.CHARSET_UTF8));
        }
        return result.toString();
    }

}
