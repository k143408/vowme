package com.vowme.app.utilities.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {
    private static final String TAG = "MyInstanceIDLS";

    public void onTokenRefresh() {
        startService(new Intent(this, RegistrationIntentService.class));
    }
}
