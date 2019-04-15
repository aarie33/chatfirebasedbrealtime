package com.example.arieahmad.chatfirebase.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arie Ahmad on 7/15/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "chatfirebase.db";
    public static final String TABLE_PESAN = "pesan";
    public static final String ID_PESAN = "id_pesan";
    public static final String NAMA = "nama";
    public static final String PESAN = "pesan";
    public static final String STATUS = "status";
    public static final String WAKTU = "waktu";

    public SQLiteHelper(Context context) {
        super(context, DATABASE, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PESAN + "(" + ID_PESAN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAMA + " TEXT, " + PESAN + " TEXT, " + STATUS + " TEXT, " + WAKTU + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PESAN);

        onCreate(sqLiteDatabase);
    }

    public boolean insertPesan(String nama,String pesan, String status, String waktu){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMA, nama);
        contentValues.put(PESAN, pesan);
        contentValues.put(STATUS, status);
        contentValues.put(WAKTU, waktu);
        long result = db.insert(TABLE_PESAN, null, contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updatePesan(String id, String nama,String pesan, String status, String waktu){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMA, nama);
        contentValues.put(PESAN, pesan);
        contentValues.put(STATUS, status);
        contentValues.put(WAKTU, waktu);
        db.update(TABLE_PESAN, contentValues, ID_PESAN + " = ? ", new String[]{id});
        return true;
    }

    public Integer deletePesan(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PESAN, ID_PESAN + " = ?", new String[]{id});
    }

    public Cursor getData(String sql){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        return res;
    }
}
