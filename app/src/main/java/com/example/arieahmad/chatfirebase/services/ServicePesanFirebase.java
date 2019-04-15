package com.example.arieahmad.chatfirebase.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.example.arieahmad.chatfirebase.Chat2;
import com.example.arieahmad.chatfirebase.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import static com.example.arieahmad.chatfirebase.Chat2.actStatus;

public class ServicePesanFirebase extends Service {

    public Notification myNotif;
    private DatabaseReference dbRoot;
    private Query query;

    public ServicePesanFirebase() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getApplicationContext(), "Service berjalan", Toast.LENGTH_SHORT).show();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*dbRoot = FirebaseDatabase.getInstance().getReference().child("chat");
        query = dbRoot.orderByChild("status").equalTo("2");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //setMyNotif();
                Toast.makeText(getApplicationContext(), "Notif", Toast.LENGTH_SHORT).show();
                showNotif();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getApplicationContext(), "Notif", Toast.LENGTH_SHORT).show();
                showNotif();
                //setMyNotif();
                //getPesan(dataSnapshot);
                //savePesan(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //setMyNotif();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        metTes();
        return Service.START_STICKY;//super.onStartCommand(intent, flags, startId);
    }

    public void metTes(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Notif for testing a service", Toast.LENGTH_SHORT).show();
                metTes();
                showNotif();
            }
        }, 10000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent("RESTART_SERVICE");
        sendBroadcast(intent);
    }

    /*public void setMyNotif(){
        if (actStatus == false ) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            PendingIntent iBuka = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            myNotif = new Notification.Builder(getApplicationContext())
                    .setTicker("Chattingku pesan baru")
                    .setContentTitle("Chattingku")
                    .setContentText("Pesan baru")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setVibrate(new long[]{100, 500})
                    .setContentIntent(iBuka).getNotification();

            myNotif.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(0, myNotif);
        }
    }*/

    private void showNotif(){
        Intent intent = new Intent(getApplicationContext(), Chat2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent iBuka = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        if (actStatus == false ) {
            Notification notif = new Notification.Builder(getApplicationContext())
                    .setTicker("Chattingku pesan baru")
                    .setContentTitle("Chattingku")
                    .setContentText("Pesan baru ini hanya fiktif belaka, " +
                            "jika ada kesamaan dalam penulisan nama atau karakter mungkin itu disengaja")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setVibrate(new long[]{100, 500})
                    .setContentIntent(iBuka).getNotification();

            notif.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(0, notif);
        }
    }
}
