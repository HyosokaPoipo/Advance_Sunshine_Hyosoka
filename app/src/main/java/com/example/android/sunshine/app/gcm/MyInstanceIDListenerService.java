package com.example.android.sunshine.app.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by HyosokaPoipo on 4/15/2016.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {
    private static final String TAG = "MyInstanceIDLS";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //ngambil updated Instance ID token
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
