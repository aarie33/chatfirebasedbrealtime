package com.example.arieahmad.chatfirebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.arieahmad.chatfirebase.adapters.PesanAdapter;
import com.example.arieahmad.chatfirebase.db.SQLiteHelper;
import com.example.arieahmad.chatfirebase.db.SharedPref;
import com.example.arieahmad.chatfirebase.setterGetter.PesanSetGet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText edtPesan;
    private ImageView imgKirim;
    private String username, temp_key, waktu, nama_psn, pesan_psn, status_psn, waktu_psn;
    private ArrayList listItem = new ArrayList<>();
    private RecyclerView listPesan;
    private PesanAdapter recyclerAdapter;
    private DatabaseReference dbRoot;
    private Query query;
    private SQLiteHelper myDb;
    private AlertDialog.Builder dialogKonf;
    public static boolean actStatus;
    public Notification myNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Chattingku");

        dialogKonf = new AlertDialog.Builder(this);
        myDb = new SQLiteHelper(this);
        edtPesan = (EditText) findViewById(R.id.edtPesan);
        imgKirim = (ImageView) findViewById(R.id.imgKirim);

        //get Time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd-MMM-yyyy");
        waktu = df.format(c.getTime());

        username = SharedPref.getInstance(this).getNama();

        dbRoot = FirebaseDatabase.getInstance().getReference().child("chat");
        query = dbRoot.orderByChild("status").equalTo("0");

        //RecyclerView
        listPesan = (RecyclerView) findViewById(R.id.listPesan);
        recyclerAdapter = new PesanAdapter(getApplicationContext(), listItem);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listPesan.setLayoutManager(layoutManager);
        listPesan.setItemAnimator(new DefaultItemAnimator());
        listPesan.setAdapter(recyclerAdapter);

        showPesan();

        imgKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtPesan.getText())){
                    edtPesan.setError("Pesan harus diisi");
                    edtPesan.requestFocus();
                }else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    temp_key = dbRoot.push().getKey();
                    dbRoot.updateChildren(map);

                    DatabaseReference message_root = dbRoot.child(temp_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("nama", username);
                    map2.put("pesan", edtPesan.getText().toString());
                    map2.put("status", "0");
                    map2.put("waktu", waktu);

                    message_root.updateChildren(map2);

                    edtPesan.setText("");
                }
            }
        });

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //getPesan(dataSnapshot);
                savePesan(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //getPesan(dataSnapshot);
                //savePesan(dataSnapshot);
                cekPesan(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void savePesan(DataSnapshot dataSnapshot){
        Iterator iterator = dataSnapshot.getChildren().iterator();
        //DataSnapshot childKey = (DataSnapshot) dataSnapshot.getChildren();
        PesanSetGet varGetSet = null;

        while(iterator.hasNext()){
            nama_psn = ((DataSnapshot)iterator.next()).getValue().toString();
            pesan_psn = ((DataSnapshot)iterator.next()).getValue().toString();
            status_psn = ((DataSnapshot)iterator.next()).getValue().toString();
            waktu_psn = ((DataSnapshot)iterator.next()).getValue().toString();

            if (!SharedPref.getInstance(getApplicationContext()).getNama().equals(nama_psn)){
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = dataSnapshot.getKey();
                dbRoot.updateChildren(map);

                DatabaseReference message_root = dbRoot.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("status", "1");

                message_root.updateChildren(map2);
            }

            myDb.insertPesan(nama_psn, pesan_psn, status_psn, waktu_psn);

            varGetSet = new PesanSetGet();

            varGetSet.setNama(nama_psn);
            varGetSet.setPesan(pesan_psn);
            varGetSet.setStatus(status_psn);
            varGetSet.setWaktu(waktu_psn);

            listItem.add(varGetSet);
        }

        recyclerAdapter.notifyDataSetChanged();


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

    private void showPesan(){
        Cursor data = myDb.getData("SELECT * FROM " + myDb.TABLE_PESAN +
                " ORDER BY " + myDb.ID_PESAN);

        PesanSetGet varGetSet = null;

        int i = 0;
        while (data.moveToNext()) {
            i++;
            varGetSet = new PesanSetGet();

            varGetSet.setNama(String.valueOf(data.getString(1)));
            varGetSet.setPesan(String.valueOf(data.getString(2)));
            varGetSet.setStatus(String.valueOf(data.getString(3)));
            varGetSet.setWaktu(String.valueOf(data.getString(4)));

            listItem.add(varGetSet);
        }

        recyclerAdapter.notifyDataSetChanged();

        listPesan.smoothScrollToPosition(recyclerAdapter.getItemCount() - 1);
    }

    private void cekPesan(DataSnapshot dataSnapshot){
        Iterator iterator = dataSnapshot.getChildren().iterator();
        PesanSetGet varGetSet = null;

        while(iterator.hasNext()){
            nama_psn = ((DataSnapshot)iterator.next()).getValue().toString();
            pesan_psn = ((DataSnapshot)iterator.next()).getValue().toString();
            status_psn = ((DataSnapshot)iterator.next()).getValue().toString();
            waktu_psn = ((DataSnapshot)iterator.next()).getValue().toString();

            if (!SharedPref.getInstance(getApplicationContext()).getNama().equals(nama_psn) &&
                    status_psn.equals("0")){
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = dataSnapshot.getKey();
                dbRoot.updateChildren(map);

                DatabaseReference message_root = dbRoot.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("status", "1");

                message_root.updateChildren(map2);

                myDb.insertPesan(nama_psn, pesan_psn, status_psn, waktu_psn);

                varGetSet = new PesanSetGet();

                varGetSet.setNama(nama_psn);
                varGetSet.setPesan(pesan_psn);
                varGetSet.setStatus(status_psn);
                varGetSet.setWaktu(waktu_psn);

                listItem.add(varGetSet);
            }
        }

        recyclerAdapter.notifyDataSetChanged();
    }

    private void getPesan(DataSnapshot dataSnapshot){
        Iterator iterator = dataSnapshot.getChildren().iterator();
        PesanSetGet varGetSet = null;

        while(iterator.hasNext()){
            varGetSet = new PesanSetGet();

            varGetSet.setNama(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setPesan(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setWaktu(((DataSnapshot)iterator.next()).getValue().toString());

            listItem.add(varGetSet);
        }

        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            dialogKonf.setTitle("Logout");
            dialogKonf.setMessage("Apakah Anda yakin akan keluar dari akun ini ?");
            dialogKonf.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPref.getInstance(getApplicationContext()).logout();
                    startActivity(new Intent(getApplicationContext(), InputName.class));
                    finish();
                    dialogInterface.dismiss();
                }
            });

            dialogKonf.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            final AlertDialog alertDialog = dialogKonf.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        actStatus = true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        actStatus = false;
    }
}
