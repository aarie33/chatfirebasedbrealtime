package com.example.arieahmad.chatfirebase.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Arie Ahmad on 9/15/2017.
 */

public class ReceiverPesanFirebase extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, ServicePesanFirebase.class));
    }
}
