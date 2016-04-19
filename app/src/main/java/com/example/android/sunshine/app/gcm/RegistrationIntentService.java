package com.example.android.sunshine.app.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.sunshine.app.MainActivity;
import com.example.android.sunshine.app.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by HyosokaPoipo on 4/15/2016.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntService";

    public RegistrationIntentService()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try{
            //pake teknik synchronized biar tiap refresh event itu dihandle berurutan
            synchronized (TAG)
            {
                //initial call buat ngedapatin token
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_dfultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
                sendRegistrationToServer(token);

                //simpen nilai boolean yg nunjukin tokennya udh dikirim ke server ato belum
                //klu udah berarti true, klu belum berarti false
                sharedPreferences.edit().putBoolean(MainActivity.SENT_TOKEN_TO_SERVER, true).apply();
            }
        }catch (Exception e)
        {
            Log.d(TAG, "Gagal eksekusi refresh token",e);

            //mastiin tokennya kembali diupdate lagi kemudian jika sebelumnya gagal...
            sharedPreferences.edit().putBoolean(MainActivity.SENT_TOKEN_TO_SERVER,false).apply();
        }
    }

    private void sendRegistrationToServer(String token) {
        Log.i(TAG, "GCM Registration Token: " + token);
    }
}
