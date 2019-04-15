package com.example.arieahmad.chatfirebase;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.arieahmad.chatfirebase.adapters.Chat2Adapter;
import com.example.arieahmad.chatfirebase.adapters.Chat2AdapterLv;
import com.example.arieahmad.chatfirebase.db.SQLiteHelper;
import com.example.arieahmad.chatfirebase.db.SharedPref;
import com.example.arieahmad.chatfirebase.setterGetter.Chat2SetGet;
import com.google.android.gms.appinvite.AppInviteInvitation;
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

public class Chat2 extends AppCompatActivity {
    private EditText edtPesan;
    private ImageView imgKirim, imgGbr;
    private String username, temp_key, waktu, nama_psn, pesan_psn, status_psn, waktu_psn;
    private ArrayList listItem = new ArrayList<>();
    private RecyclerView listPesan;
    private Chat2Adapter recyclerAdapter;
    private DatabaseReference dbRoot;
    private Query query;
    private SQLiteHelper myDb;
    private AlertDialog.Builder dialogKonf;
    public static boolean actStatus;
    public Notification myNotif;
    private LinearLayoutManager layoutManager;
    private ScrollView scrollView;
    private static final int REQUEST_INVITE = 100;

    //ListView
    private ArrayList lvItem;
    private ListView lvPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        setTitle("Chattingku");
        getSupportActionBar().setSubtitle("Online");

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        dialogKonf = new AlertDialog.Builder(this);
        myDb = new SQLiteHelper(this);
        edtPesan = (EditText) findViewById(R.id.edtPesan);
        imgKirim = (ImageView) findViewById(R.id.imgKirim);
        imgGbr = (ImageView) findViewById(R.id.imgGbr);

        imgGbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), AsyncTaskTes.class));
            }
        });

        //get Time
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd-MMM-yyyy");
        waktu = df.format(c.getTime());

        username = SharedPref.getInstance(this).getNama();

        dbRoot = FirebaseDatabase.getInstance().getReference().child("chat");

        //RecyclerView
        /*listPesan = (RecyclerView) findViewById(R.id.listPesan);
        recyclerAdapter = new Chat2Adapter(getApplicationContext(), listItem);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        listPesan.setLayoutManager(layoutManager);
        listPesan.setItemAnimator(new DefaultItemAnimator());
        listPesan.setAdapter(recyclerAdapter);*/
        //listPesan.smoothScrollToPosition(1);
        //layoutManager.setReverseLayout(true);

        //ListView
        lvPesan = (ListView) findViewById(R.id.lvPesan);
        lvItem = new ArrayList<HashMap<String,String>>(); //aktif untuk getonline
        //Chat2.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

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

        dbRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getPesan(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getPesan(dataSnapshot);

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

    private void getPesan(DataSnapshot dataSnapshot){
        Iterator iterator = dataSnapshot.getChildren().iterator();
        Chat2SetGet varGetSet = null;

        while(iterator.hasNext()){
            varGetSet = new Chat2SetGet();

            varGetSet.setNama(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setPesan(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setStatus(((DataSnapshot)iterator.next()).getValue().toString());
            varGetSet.setWaktu(((DataSnapshot)iterator.next()).getValue().toString());

            //listItem.add(varGetSet);
            lvItem.add(varGetSet);
        }

        //recyclerAdapter.notifyDataSetChanged();

        //listPesan.getLayoutManager().scrollToPosition(0);

        ListAdapter adapter = new Chat2AdapterLv(Chat2.this, lvItem);
        lvPesan.setAdapter(adapter);
        lvPesan.setSelection(lvItem.size() - 1);
        lvPesan.smoothScrollToPosition(lvItem.size() - 1);
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
        }else if(id == R.id.action_share){
            onInviteClicked();
        }

        return super.onOptionsItemSelected(item);
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder("Share with firebase")
                .setMessage("Share your Chat app with firebase")
                .setDeepLink(Uri.parse("http://google.com"))
                .setCallToActionText("Invitation")
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_INVITE){
            String [] ids = AppInviteInvitation.getInvitationIds(resultCode, data);

            for(String id : ids){
                System.out.println("onActivityResult:"+id);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Error invite", Toast.LENGTH_SHORT).show();
        }
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
