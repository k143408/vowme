package com.vowme.app.utilities.api;

import android.os.AsyncTask;

import com.vowme.app.models.Enum.HttpRequestType;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.fabric.sdk.android.services.network.HttpRequest;

public class ApiWCFRequest extends AsyncTask<Void, Void, String> {
    protected String baseURL;
    protected JSONObject parameters;
    protected HttpRequestType requestType;
    protected String route;

    public ApiWCFRequest(HttpRequestType requestType, String baseURL, String route) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
    }

    public ApiWCFRequest(HttpRequestType requestType, String baseURL, String route, JSONObject parameters) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
        this.parameters = parameters;


    }

    protected String doInBackground(Void... params) {
        URL url;
        MalformedURLException e;
        Throwable th;
        IOException e2;
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url2 = new URL(this.baseURL + this.route);
            try {
                urlConnection = (HttpURLConnection) url2.openConnection();
                urlConnection.setRequestMethod(this.requestType.getValue());
                if (this.requestType == HttpRequestType.POST) {
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                }
                if (this.parameters != null) {
                    urlConnection.setRequestProperty("content-type", "application/json");
                    urlConnection.setRequestProperty(HttpRequest.HEADER_CONTENT_LENGTH, String.valueOf(this.parameters.toString().length()));
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, HttpRequest.CHARSET_UTF8));
                    writer.write(this.parameters.toString());
                    writer.flush();
                    writer.close();
                    os.close();
                }
                publishProgress(new Void[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(urlConnection.getInputStream()), HttpRequest.CHARSET_UTF8));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    builder.append(line).append("\n");
                }
                reader.close();
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    url = url2;
                }
            } catch (MalformedURLException e3) {
                e = e3;
                url = url2;
                try {
                    e.printStackTrace();
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    return builder.toString();
                } catch (Throwable th2) {
                    th = th2;
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (IOException e4) {
                e2 = e4;
                url = url2;
                e2.printStackTrace();
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                return builder.toString();
            } catch (Throwable th3) {
                th = th3;
                url = url2;
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e5) {
            e = e5;
            e.printStackTrace();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            return builder.toString();
        }
        return builder.toString();
    }

    protected void onPostExecute(String result) {
        onPostExecuteBody(result);
    }

    protected void onPostExecuteBody(String result) {
    }
}
