package com.vowme.app.utilities.api.user;

import android.os.AsyncTask;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.response.AppIdentityResult;

import org.json.JSONException;
import org.json.JSONObject;

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

public class PostUserData extends AsyncTask<Void, Void, AppIdentityResult> {
    protected String accessToken;
    protected String baseURL;
    protected HttpRequestType requestType;
    protected String route;
    protected String stringAsJsonParameters;

    public PostUserData(HttpRequestType requestType, String baseURL, String route, String stringAsJsonParameters, String accessToken) {
        this.requestType = requestType;
        this.baseURL = baseURL;
        this.route = route;
        this.accessToken = accessToken;
        this.stringAsJsonParameters = stringAsJsonParameters;
    }

    protected AppIdentityResult doInBackground(Void... params) {
        BufferedReader reader;
        String line;
        InputStream errorStream;
        Exception e;
        URL url;
        AppIdentityResult result;
        Throwable th;
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();
        int responseCode = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        try {
            URL url2 = new URL(this.baseURL + this.route);
            try {
                urlConnection = (HttpURLConnection) url2.openConnection();
                urlConnection.setRequestMethod(this.requestType.getValue());
                urlConnection.setRequestProperty("Authorization", "Bearer " + this.accessToken);
                if (this.stringAsJsonParameters != null) {
                    urlConnection.setRequestProperty("content-type", "application/json");
                    urlConnection.setRequestProperty(HttpRequest.HEADER_CONTENT_LENGTH, String.valueOf(this.stringAsJsonParameters.toString().length()));
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, HttpRequest.CHARSET_UTF8));
                    writer.write(this.stringAsJsonParameters.toString());
                    writer.flush();
                    writer.close();
                    os.close();
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
                if (urlConnection != null) {
                    try {
                        errorStream = urlConnection.getErrorStream();
                        if (errorStream != null) {
                            responseCode = urlConnection.getResponseCode();
                            BufferedReader reader2 = new BufferedReader(new InputStreamReader(errorStream, HttpRequest.CHARSET_UTF8));
                            while (true) {
                                try {
                                    line = reader2.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    builder.append(line).append("\n");
                                } catch (IOException e2) {
                                    e = e2;
                                    reader = reader2;
                                }
                            }
                            reader2.close();
                            reader = reader2;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        urlConnection.disconnect();
                        url = url2;
                        result = new AppIdentityResult();
                        if (responseCode != 200) {
                            return new AppIdentityResult(builder.toString(), false);
                        }
                        try {
                            return new AppIdentityResult(new JSONObject(builder.toString()));
                        } catch (JSONException e4) {
                            e4.printStackTrace();
                            return result;
                        }
                    }
                    urlConnection.disconnect();
                    url = url2;
                } else {
                    url = url2;
                }
            } catch (MalformedURLException e5) {
                e = e5;
                url = url2;
                try {
                    e.printStackTrace();
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
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                        urlConnection.disconnect();
                    }
                    result = new AppIdentityResult();
                    if (responseCode != 200) {
                        return new AppIdentityResult(builder.toString(), false);
                    }
                    return new AppIdentityResult(new JSONObject(builder.toString()));
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
                        } catch (IOException e62) {
                            e62.printStackTrace();
                        }
                        urlConnection.disconnect();
                    }

                }
            } catch (IOException e7) {
                url = url2;
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
                    } catch (IOException e622) {
                        e622.printStackTrace();
                    }
                    urlConnection.disconnect();
                }
                result = new AppIdentityResult();
                if (responseCode != 200) {
                    try {
                        return new AppIdentityResult(new JSONObject(builder.toString()));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                return new AppIdentityResult(builder.toString(), false);
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
        } catch (MalformedURLException e8) {
            e = e8;
            e.printStackTrace();
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
            result = new AppIdentityResult();
            if (responseCode != 200) {
                return new AppIdentityResult(builder.toString(), false);
            }
            try {
                return new AppIdentityResult(new JSONObject(builder.toString()));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e9) {
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
            result = new AppIdentityResult();
            if (responseCode != 200) {
                try {
                    return new AppIdentityResult(new JSONObject(builder.toString()));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            return new AppIdentityResult(builder.toString(), false);
        }
        result = new AppIdentityResult();
        if (responseCode != 200) {
            try {
                return new AppIdentityResult(new JSONObject(builder.toString()));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return new AppIdentityResult(builder.toString(), false);
    }

    protected void onPostExecute(AppIdentityResult result) {
        onPostExecuteBody(result);
    }

    protected void onPostExecuteBody(AppIdentityResult result) {
    }
}
