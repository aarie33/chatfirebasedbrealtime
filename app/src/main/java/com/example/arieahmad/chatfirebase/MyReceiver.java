package com.example.arieahmad.chatfirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.arieahmad.chatfirebase.services.ServicePesanFirebase;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        context.startService(new Intent(context, ServicePesanFirebase.class));
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
