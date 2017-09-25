package com.vowme.app.utilities.api.oauth;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import com.vowme.app.models.Enum.PostAccessTokenType;
import com.vowme.app.models.api.response.OauthRequestResponse;
import com.vowme.vol.app.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.fabric.sdk.android.services.network.HttpRequest;

public class PostAccessToken extends AsyncTask<Void, Void, OauthRequestResponse> {
    protected String baseURL;
    protected Context context;
    protected String loginProvider;
    protected String password;
    protected PostAccessTokenType postAccessTokenType;
    protected String providerKey;
    protected String refreshToken;
    protected String route;
    protected String userName;

    public PostAccessToken(String baseURL, String route, PostAccessTokenType postAccessTokenType, Context context) {
        this.baseURL = baseURL;
        this.route = route;
        this.postAccessTokenType = postAccessTokenType;
        this.context = context;
    }

    public PostAccessToken(String baseURL, String route, PostAccessTokenType postAccessTokenType, Context context, String userName, String password) {
        this.baseURL = baseURL;
        this.route = route;
        this.postAccessTokenType = postAccessTokenType;
        this.context = context;
        this.userName = userName;
        this.password = password;
    }

    public PostAccessToken(String baseURL, String route, PostAccessTokenType postAccessTokenType, Context context, String refreshToken) {
        this.baseURL = baseURL;
        this.route = route;
        this.postAccessTokenType = postAccessTokenType;
        this.context = context;
        this.refreshToken = refreshToken;
    }

    public PostAccessToken(String baseURL, String route, Context context, String loginProvider, String providerKey) {
        this.baseURL = baseURL;
        this.route = route;
        this.postAccessTokenType = PostAccessTokenType.EXTERNAL;
        this.context = context;
        this.loginProvider = loginProvider;
        this.providerKey = providerKey;
    }

    protected OauthRequestResponse doInBackground(Void... params) {
        String line;
        MalformedURLException e;
        URL url;
        IOException e2;
        Throwable th;
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();
        int responseCode = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        BufferedReader reader;
        InputStream errorStream;


        try {
            URL url2 = new URL(this.baseURL + this.route);
            try {
                urlConnection = (HttpURLConnection) url2.openConnection();
                urlConnection.setRequestMethod(HttpRequest.METHOD_POST);
                urlConnection.setRequestProperty("content-type", HttpRequest.CONTENT_TYPE_FORM);
                String inputString = "";
                if (this.postAccessTokenType == PostAccessTokenType.CREDENTIAL) {
                    inputString = "grant_type=client_credentials&client_id=" + this.context.getResources().getString(R.string.client_id) + "&client_secret=" + this.context.getResources().getString(R.string.client_secret);
                } else if (this.postAccessTokenType == PostAccessTokenType.PASSWORD) {
                    inputString = "grant_type=password&username=" + this.userName + "&password=" + this.password + "&client_id=" + this.context.getResources().getString(R.string.client_id) + "&scope=mobile";
                } else if (this.postAccessTokenType == PostAccessTokenType.EXTERNAL) {
                    inputString = "grant_type=external_vol&LoginProvider=" + this.loginProvider + "&ProviderKey=" + this.providerKey + "&client_id=" + this.context.getResources().getString(R.string.client_id) + "&client_secret=" + this.context.getResources().getString(R.string.client_secret) + "&scope=mobile";
                } else {
                    inputString = "grant_type=refresh_token&refresh_token=" + this.refreshToken + "&client_id=" + this.context.getResources().getString(R.string.client_id) + "&client_secret=" + this.context.getResources().getString(R.string.client_secret);
                }
                urlConnection.setRequestProperty(HttpRequest.HEADER_CONTENT_LENGTH, String.valueOf(inputString.length()));
                urlConnection.setDoOutput(true);
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, HttpRequest.CHARSET_UTF8));
                writer.write(inputString);
                writer.flush();
                writer.close();
                os.close();
                reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(urlConnection.getInputStream()), HttpRequest.CHARSET_UTF8));
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    builder.append(line).append("\n");
                }
                reader.close();
                if (urlConnection != null) {
                    try {
                        errorStream = urlConnection.getErrorStream();
                        if (errorStream != null) {
                            responseCode = urlConnection.getResponseCode();
                            reader = new BufferedReader(new InputStreamReader(errorStream, HttpRequest.CHARSET_UTF8));
                            while (true) {
                                line = reader.readLine();
                                if (line == null) {
                                    break;
                                }
                                builder.append(line).append("\n");
                            }
                            reader.close();
                        }
                    } catch (MalformedURLException e3) {
                        e3.printStackTrace();
                    }
                    urlConnection.disconnect();
                    url = url2;
                }
            } catch (MalformedURLException e4) {
                MalformedURLException e3 = e4;
                url = url2;
                try {
                    e3.printStackTrace();
                    responseCode = 404;
                    if (urlConnection != null) {
                        try {
                            errorStream = urlConnection.getErrorStream();
                            if (errorStream != null) {
                                responseCode = urlConnection.getResponseCode();
                                reader = new BufferedReader(new InputStreamReader(errorStream, HttpRequest.CHARSET_UTF8));
                                while (true) {
                                    line = reader.readLine();
                                    if (line != null) {
                                        break;
                                    }
                                    builder.append(line).append("\n");
                                }
                                reader.close();
                            }
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                        urlConnection.disconnect();
                    }
                    return new OauthRequestResponse(responseCode, builder.toString());
                } catch (Throwable th2) {
                    th = th2;
                    if (urlConnection != null) {
                        try {
                            errorStream = urlConnection.getErrorStream();
                            if (errorStream != null) {
                                responseCode = urlConnection.getResponseCode();
                                reader = new BufferedReader(new InputStreamReader(errorStream, HttpRequest.CHARSET_UTF8));
                                while (true) {
                                    line = reader.readLine();
                                    if (line != null) {
                                        break;
                                    }
                                    builder.append(line).append("\n");
                                }
                                reader.close();
                            }
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                        urlConnection.disconnect();
                    }
                }
            } catch (IOException e5) {
                IOException e222 = e5;
                url = url2;
                e222.printStackTrace();
                responseCode = 404;
                if (urlConnection != null) {
                    try {
                        errorStream = urlConnection.getErrorStream();
                        if (errorStream != null) {
                            responseCode = urlConnection.getResponseCode();
                            reader = new BufferedReader(new InputStreamReader(errorStream, HttpRequest.CHARSET_UTF8));
                            while (true) {
                                line = reader.readLine();
                                if (line != null) {
                                    break;
                                }
                                builder.append(line).append("\n");
                            }
                            reader.close();
                        }
                    } catch (IOException e2222) {
                        e2222.printStackTrace();
                    }
                    urlConnection.disconnect();
                }
                return new OauthRequestResponse(responseCode, builder.toString());
            } catch (Throwable th3) {
                th = th3;
                url = url2;
                if (urlConnection != null) {
                    errorStream = urlConnection.getErrorStream();
                    if (errorStream != null) {
                        responseCode = urlConnection.getResponseCode();
                        reader = new BufferedReader(new InputStreamReader(errorStream, HttpRequest.CHARSET_UTF8));
                        while (true) {
                            line = reader.readLine();
                            if (line != null) {
                                break;
                            }
                            builder.append(line).append("\n");
                        }
                        reader.close();
                    }
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e6) {
            MalformedURLException e3 = e6;
            e3.printStackTrace();
            responseCode = 404;
            if (urlConnection != null) {
                errorStream = urlConnection.getErrorStream();
                if (errorStream != null) {
                    try {
                        responseCode = urlConnection.getResponseCode();
                        reader = new BufferedReader(new InputStreamReader(errorStream, HttpRequest.CHARSET_UTF8));
                        while (true) {
                            line = reader.readLine();
                            if (line != null) {
                                break;
                            }
                            builder.append(line).append("\n");
                        }
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
                urlConnection.disconnect();
            }
            return new OauthRequestResponse(responseCode, builder.toString());
        } catch (IOException e7) {
            IOException e2222 = e7;
            e2222.printStackTrace();
            responseCode = 404;
            if (urlConnection != null) {
                errorStream = urlConnection.getErrorStream();
                if (errorStream != null) {
                    try {
                        responseCode = urlConnection.getResponseCode();
                        reader = new BufferedReader(new InputStreamReader(errorStream, HttpRequest.CHARSET_UTF8));
                        while (true) {
                            line = reader.readLine();
                            if (line != null) {
                                break;
                            }
                            builder.append(line).append("\n");
                        }
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
                urlConnection.disconnect();
            }
            return new OauthRequestResponse(responseCode, builder.toString());
        }
        return new OauthRequestResponse(responseCode, builder.toString());
    }

    protected void onPostExecute(OauthRequestResponse result) {
        onPostExecuteBody(result);
    }

    protected void onPostExecuteBody(OauthRequestResponse result) {
    }
}
